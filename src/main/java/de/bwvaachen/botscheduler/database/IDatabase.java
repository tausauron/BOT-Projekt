package de.bwvaachen.botscheduler.database;

import java.sql.SQLException;
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
						  List<UnternehmenDAO> unternehmen, List<KursDAO> kurse, List<Raum> raeumeInput, List<Schueler> schuelerInput,
						  List<UnternehmenDAO> unternehmenInput) throws Exception;

	public List<Raum> loadRoomsInput() throws SQLException, ClassNotFoundException;
	public List<Schueler> loadSchuelerInput() throws SQLException, ClassNotFoundException;
	public List<UnternehmenDAO> loadUnternehmenInput() throws SQLException, ClassNotFoundException;

	public List<Raum> loadRooms() throws SQLException, ClassNotFoundException;

	public List<Schueler> loadSchueler() throws SQLException, ClassNotFoundException;
	
	public List<UnternehmenDAO> loadUnternehmen() throws SQLException, ClassNotFoundException;
	
	public List<KursDAO> loadKurse(List<Schueler> schlrList, List<Raum> raum, List<UnternehmenDAO> unternehmen) throws SQLException, ClassNotFoundException;

}
