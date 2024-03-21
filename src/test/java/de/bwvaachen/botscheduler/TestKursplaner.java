package de.bwvaachen.botscheduler;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.bwvaachen.botscheduler.calculate.CalcSchueler;
import de.bwvaachen.botscheduler.calculate.KursPlaner;
import execlLoad.ImportFile;
import klassenObjekte.Kurse;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

class TestKursplaner {

	private static List<Schueler> schueler;
	private static List<Unternehmen> unternehmen;
	private static KursPlaner planer;
	private static List<Raum> raeume;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		String schuelerPath = TestKursplaner.class.getResource("IMPORT BOT2_Wahl.xlsx").toURI().getPath();
		schueler = ImportFile.getChoices(schuelerPath);
		
		String eventPath =  TestKursplaner.class.getResource("IMPORT BOT1_Veranstaltungsliste.xlsx").toURI().getPath();
		unternehmen = ImportFile.getCompany(eventPath);		

		String roomPath = TestKursplaner.class.getResource("IMPORT BOT0_Raumliste.xlsx").toURI().getPath();
		raeume = ImportFile.getRoom(roomPath);
		
		planer = new KursPlaner();
		planer.setDebug(true);
	}



	@Test
	void testBelegeKurse() {
		String score = "0.0 %";

		score = planer.belegeKurse(schueler, unternehmen, raeume);
		assertNotEquals(score, "0.0 %");
		
		List<Kurse> kurse = planer.getKurse();
		assertTrue(kurse.size() > 0);
		
		List<CalcSchueler> cSchueler = planer.getcSchueler();
		assertTrue(cSchueler.size() == schueler.size());
	}

}
