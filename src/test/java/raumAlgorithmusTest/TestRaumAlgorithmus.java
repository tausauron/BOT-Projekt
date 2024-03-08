package raumAlgorithmusTest;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import de.bwvaachen.botscheduler.calculate.KursPlaner;
import execlLoad.ImportFile;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

class TestRaumAlgorithmus
{

	/*
	 * Meine Klasse braucht eine Raum und Veranstaltungsliste
	 * 
	 */
	
	private static List<Schueler> schueler;
	private static List<Unternehmen> unternehmen;
	private static KursPlaner planer;
	private static List<Raum> raeume;
	
	@BeforeAll
	void fillListen() throws URISyntaxException
	{
		String schuelerPath = TestRaumAlgorithmus.class.getResource("IMPORT BOT2_Wahl.xlsx").toURI().getPath();
		schueler = ImportFile.getChoices(schuelerPath); // Holt sich die ganzen Schueler und packt sie in eine ArrayList
		
		String eventPath =  TestRaumAlgorithmus.class.getResource("IMPORT BOT1_Veranstaltungsliste.xlsx").toURI().getPath();
		unternehmen = ImportFile.getCompany(eventPath); // Holt sich die ganzen Veranstaltungen und packt diese in eine ArrayList
		
		String roomPath = TestRaumAlgorithmus.class.getResource("IMPORT BOT0_Raumliste.xlsx").toURI().getPath();
		raeume = ImportFile.getRoom(roomPath); //Holt sich die Raeume und packt diese in eine ArrayList
		
		System.out.println(roomPath);
	}
	
	@Test
	void test()
	{
		fail("Not yet implemented");
	}

}
