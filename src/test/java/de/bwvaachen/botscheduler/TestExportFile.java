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
import execlLoad.ExportFile;
import execlLoad.ImportFile;
import klassenObjekte.Kurse;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

public class TestExportFile {

	public TestExportFile() {
		// TODO Auto-generated constructor stub
	}

	private static List<Schueler> schueler;
	private static List<Unternehmen> unternehmen;
	private static KursPlaner planer;
	private static List<Raum> raeume;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		String schuelerPath = TestKursplaner.class.getResource("IMPORT BOT2_Wahl.xlsx").toURI().getPath();
		schueler = ImportFile.getChoices(schuelerPath);
//		unternehmen = new ArrayList<>();
//		for (int i = 1; i <= 27; i++) {
//			unternehmen.add(new Unternehmen(i, "Unternehmen" + i, "Fachrichtung" + i, 20, 5, "A"));
//		}
		
		String eventPath =  TestKursplaner.class.getResource("IMPORT BOT1_Veranstaltungsliste.xlsx").toURI().getPath();
		unternehmen = ImportFile.getCompany(eventPath);
		
		planer = new KursPlaner();
		//String roomPath = TestKursplaner.class.getResource("IMPORT BOT0_Raumliste.xlsx").toURI().getPath();
		raeume = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			raeume.add(new Raum("Raum" + i, 20));
		}
		raeume.add(new Raum("Raum 13", 25));
		raeume.add(new Raum("Aula", 50));
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

		score = planer.belegeKurse(schueler, unternehmen, raeume);
		

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

		
		ExportFile.exportResult(planer.getcSchueler(), "H:\\ExportedData.xlsx");
		
		
		
		assertNotEquals(score, "0.0 %");

	}
}