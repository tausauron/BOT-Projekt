package de.bwvaachen.botscheduler.database;

import java.util.List;

import de.bwvaachen.botscheduler.model.KursDAO;
import de.bwvaachen.botscheduler.model.UnternehmenDAO;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;

/**
 * Interface für das Datenbankmodul
 * 
 * @author Max Tautenhahn
 */
public interface IDatabase {
	
	public void saveState(List<Raum> raeume, List<Schueler> schueler,
			List<UnternehmenDAO> unternehmen, List<KursDAO> kurse);
	
	public List<Raum> loadRooms();
	
	public List<Schueler> loadSchueler();
	
	public List<UnternehmenDAO> loadUnternehmen();
	
	public List<KursDAO> loadKurse();

}
