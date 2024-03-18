package de.bwvaachen.botscheduler.model;

import java.util.ArrayList;
import java.util.List;

import de.bwvaachen.botscheduler.calculate.CalcSchueler;
import klassenObjekte.Kurse;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

/**
 * Factory zum Erstellen von Data Access Objekten
 * 
 * @author Max Tautenhahn
 */
public class DAOFactory {
	
	public static UnternehmenDAO createUnternehmenDAO(Unternehmen unternehmen) {
		return new UnternehmenDAO(unternehmen.getFirmenID(),
				unternehmen.getUnternehmen(),
				unternehmen.getFachrichtung(),
				unternehmen.getMaxTeilnehmer(),
				unternehmen.getMaxVeranstaltungen(),
				unternehmen.getFruehesterZeitslot());
	}
	
	
	public static KursDAO createKursDAO(Kurse kurs) {
		
		List<Schueler> teilnehmer = new ArrayList<>();
		
		for(CalcSchueler cSchuel : kurs.getKursTeilnehmer()) {
			teilnehmer.add(cSchuel.getSchueler());
		}
		
		return new KursDAO(kurs.getRaum(),
				teilnehmer,
				createUnternehmenDAO(kurs.getUnternehmen()),
				kurs.getZeitslot().getTyp().name());
	}	

}
