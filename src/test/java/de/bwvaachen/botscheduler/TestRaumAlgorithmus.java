package de.bwvaachen.botscheduler;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.bwvaachen.botscheduler.RaumAlgorithm.RaumAlgorithmus;
import de.bwvaachen.botscheduler.calculate.CalcSchueler;
import de.bwvaachen.botscheduler.calculate.KursPlaner;
import de.bwvaachen.botscheduler.calculate.Zeitslot;
import de.bwvaachen.botscheduler.calculate.Zeitslot.Typ;
import execlLoad.ImportFile;
import junit.framework.Assert;
import klassenObjekte.Kurse;
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
	private static List<Kurse> kursListe;
	RaumAlgorithmus algo;

	@BeforeAll
	static void fillListen() throws URISyntaxException
	{
		String schuelerPath = TestRaumAlgorithmus.class.getResource("IMPORT BOT2_Wahl.xlsx").toURI().getPath();
		schueler = ImportFile.getChoices(schuelerPath); // Holt sich die ganzen Schueler und packt sie in eine ArrayList

		String eventPath = TestRaumAlgorithmus.class.getResource("IMPORT BOT1_Veranstaltungsliste.xlsx").toURI()
				.getPath();
		unternehmen = ImportFile.getCompany(eventPath); // Holt sich die ganzen Veranstaltungen und packt diese in eine
														// ArrayList

		String roomPath = TestRaumAlgorithmus.class.getResource("IMPORT BOT0_Raumliste.xlsx").toURI().getPath();
		raeume = ImportFile.getRoom(roomPath); // Holt sich die Raeume und packt diese in eine ArrayList

		planer = new KursPlaner();
		planer.belegeKurse(schueler, unternehmen, raeume);
		kursListe = (planer.getKurse()); // fuellt die Kursliste
	}

	@Test
	void testeObLauffaehig()
	{
		// Arrange
		boolean laeuft = false;
		algo = new RaumAlgorithmus();

		// Act

		try
		{
			algo.verteileVeranstaltungenAufRaeume(kursListe, raeume);

			laeuft = true;
		} catch (Exception e)
		{
			fail();
		}

		// Assert
		Assert.assertEquals(true, laeuft);
	}

	@Test
	void testeObFehlendeRaumKapazitaetErfasstWird() throws URISyntaxException
	{
		// Arrange
		KursPlaner testPlaner = new KursPlaner();
		List<Raum> errorRaumListe = new ArrayList<Raum>();
		Map<Kurse, String> nichtZuordbareKurseMap = new HashMap<>();

		for (int i = 0; i < 5; i++)
		{
			errorRaumListe.add(new Raum("RaumNR:" + i, 15));
		}

		testPlaner = new KursPlaner();
		testPlaner.belegeKurse(schueler, unternehmen, errorRaumListe);
		kursListe = (testPlaner.getKurse()); // fuellt die Kursliste

		// Act
		algo = new RaumAlgorithmus();
		algo.verteileVeranstaltungenAufRaeume(kursListe, errorRaumListe);
		nichtZuordbareKurseMap = algo.getNichtZuordbareKurseMap();

		// Assert
		if (nichtZuordbareKurseMap.isEmpty())
		{
			fail();
		}else
		{
			for (Map.Entry<Kurse, String> entry : nichtZuordbareKurseMap.entrySet())
			{
				Kurse kurs = entry.getKey();
				String inhalt = entry.getValue();
				System.out.println("Kurs: " + kurs.getUnternehmen() + ", Inhalt: " + inhalt);
			}
		}

	}

}
