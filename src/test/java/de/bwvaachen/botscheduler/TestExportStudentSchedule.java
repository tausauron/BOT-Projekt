package de.bwvaachen.botscheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.bwvaachen.botscheduler.RaumAlgorithm.RaumAlgorithmus;
import de.bwvaachen.botscheduler.calculate.KursPlaner;
import execlLoad.ExportFile;
import execlLoad.ImportFile;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

public class TestExportStudentSchedule {

	private static List<Schueler> schueler;
	private static List<Unternehmen> unternehmen;
	private static KursPlaner planer;
	private static List<Raum> raeume;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		String schuelerPath = TestExportStudentSchedule.class.getResource("IMPORT BOT2_Wahl.xlsx").toURI().getPath();
		schueler = ImportFile.getChoices(schuelerPath);
//		unternehmen = new ArrayList<>();
//		for (int i = 1; i <= 27; i++) {
//			unternehmen.add(new Unternehmen(i, "Unternehmen" + i, "Fachrichtung" + i, 20, 5, "A"));
//		}
		
		String eventPath =  TestExportStudentSchedule.class.getResource("IMPORT BOT1_Veranstaltungsliste.xlsx").toURI().getPath();
		unternehmen = ImportFile.getCompany(eventPath);
		String roomPath = TestKursplaner.class.getResource("IMPORT BOT0_Raumliste.xlsx").toURI().getPath();
		raeume = ImportFile.getRoom(roomPath);
		
		planer = new KursPlaner();
		planer.belegeKurse(schueler, unternehmen, raeume);
		
		RaumAlgorithmus raumAlg = new RaumAlgorithmus();
		raumAlg.verteileVeranstaltungenAufRaeume(planer.getKurse(), raeume);
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
	void testExportRoomUsage() throws FileNotFoundException, IOException {
		ExportFile exFile = new ExportFile();
		exFile.exportRoomUsage(unternehmen, "target/generated-test-sources/ExportedRoomUsage.xlsx");
	}


	@Test
	void testExportStudentSchedule() throws FileNotFoundException, IOException {
		ExportFile exFile = new ExportFile();
		exFile.exportStudentSchedule(planer.getcSchueler(), "target/generated-test-sources/ExportedData.xlsx");
	}
	
	@Test
	void testExportParticipants() throws IOException {
		ExportFile exFile = new ExportFile();
		exFile.exportParticipants(unternehmen, "target/generated-test-sources/ExportedParticipants.xlsx");
	}
}