package de.bwvaachen.botscheduler;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.bwvaachen.botscheduler.calculate.CalcSchueler;
import de.bwvaachen.botscheduler.calculate.KursPlaner;
import de.bwvaachen.botscheduler.calculate.SchuelerSlot;
import de.bwvaachen.botscheduler.calculate.Zeitslot.Typ;
import execlLoad.ImportFile;
import klassenObjekte.Kurse;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

class TestKursplaner {

	private static List<Schueler> schueler;
	private static List<Unternehmen> unternehmen;
	private static KursPlaner planer;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		String path = TestKursplaner.class.getResource("IMPORT BOT2_Wahl.xlsx").toURI().getPath();
		schueler = ImportFile.getChoices(path);
		unternehmen = new ArrayList<>();
		for (int i = 1; i <= 27; i++) {
			unternehmen.add(new Unternehmen(i, "Unternehmen" + i, "Fachrichtung" + i, 20, 5, "A"));
		}
		planer = new KursPlaner();


	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}


	@Test
	void testBelegeKurse() {
		String score = "0.0 %";

		score = planer.belegeKurse(schueler, unternehmen);
		

		for (CalcSchueler cSchuel : planer.getcSchueler()) {

			String zeile = cSchuel.getSchueler().getNachname() + " | ";

			for (SchuelerSlot slot : cSchuel.getSlots()) {
				Kurse kurs = slot.getKurs();
				if (kurs != null) {
					zeile += slot.getKurs().getUnternehmen().getFirmenID() + " | ";
				} else {
					zeile += "null |";
				}
			}

			System.out.println(zeile);
		}

//		for(Kurse kurs : planer.getKurse()) {
//			
//			String zeile = kurs.getUnternehmen().getFirmenID() + " | ";
//			zeile += kurs.getKursTeilnehmer().size();
//			System.out.println(zeile);
//			
//		}

		for (Unternehmen unt : planer.getUnternehmen()) {
			String zeile = unt.getFirmenID() + " | ";
			for (Typ typ : Typ.values()) {
				Kurse kurs = unt.getKurse().get(typ);
				if (kurs != null) {
					zeile += kurs.getKursTeilnehmer().size() + " | ";
				} else {
					zeile += "null | ";
				}
			}
			System.out.println(zeile);
		}
		
		System.out.println("Score: " + score);

		assertNotEquals(score, "0.0 %");

	}

}
