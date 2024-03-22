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

	/**
	 * Haupt Methode um alle Listen auf einmal zu speichern.
	 * @param raeume Liste der Räume. List<Raum>
	 * @param schueler Liste der Schüler. List<Schueler>
	 * @param unternehmen Liste der Unternehmen. List<UnternehmenDAO>
	 * @param kurse Liste der Kurse. List<Kurs>
	 * @param raeumeInput Liste der Räume die von der GUI als Input kommen. List<Raum>
	 * @param schuelerInput Liste der Schüler die von der GUI als Input kommen. List<Schueler>
	 * @param unternehmenInput Liste der Unternehmen die von der GUI als Input kommen. List<UnternehmenDAO>
	 * @throws SQLException zum Speichern von Daten muss es ein Connection zu der Datenbank aufgebaut werden,
	 * wenn diese Connection nicht aufgebaut werden kann wird eine SQLException geworfen.
	 * @throws ClassNotFoundException wird von der connection Methode weiter geworfen da in der Connection Methode nach
	 * der Driver-Klasse gesucht wird.
	 */
	void saveState(List<Raum> raeume, List<Schueler> schueler,
						  List<UnternehmenDAO> unternehmen, List<KursDAO> kurse, List<Raum> raeumeInput, List<Schueler> schuelerInput,
						  List<UnternehmenDAO> unternehmenInput) throws SQLException, ClassNotFoundException;

	/**
	 * Es wird in der Datenbank eine separate Liste von Räumen angelegt in der Input-Daten aus der GUI mitenthalten sind.
	 * Diese Methode gibt diese Liste zurück.
	 * @return Eine Liste von Räumen inkl. Räume die in der GUI hinzugefügt oder gelöscht wurden. List<Raum>
	 * @throws SQLException zum Laden von Daten muss es ein Connection zu der Datenbank aufgebaut werden,
	 * wenn diese Connection nicht aufgebaut werden kann wird eine SQLException geworfen.
	 * @throws ClassNotFoundException wird von der connection Methode weiter geworfen da in der Connection Methode nach
	 * der Driver-Klasse gesucht wird.
	 */
	List<Raum> loadRoomsInput() throws SQLException, ClassNotFoundException;

	/**
	 * Es wird in der Datenbank eine separate Liste von Schülern angelegt in der Input-Daten aus der GUI mitenthalten sind.
	 * Diese Methode gibt diese Liste zurück.
	 * @return Eine Liste von Schülern inkl. Schüler die in der GUI hinzugefügt oder gelöscht wurden. List<Schueler>
	 * @throws SQLException zum Laden von Daten muss es ein Connection zu der Datenbank aufgebaut werden,
	 * wenn diese Connection nicht aufgebaut werden kann wird eine SQLException geworfen.
	 * @throws ClassNotFoundException wird von der connection Methode weiter geworfen da in der Connection Methode nach
	 * der Driver-Klasse gesucht wird.
	 */
	List<Schueler> loadSchuelerInput() throws SQLException, ClassNotFoundException;

	/**
	 * Es wird in der Datenbank eine separate Liste von Unternehmen angelegt in der Input-Daten aus der GUI mitenthalten sind.
	 * Diese Methode gibt diese Liste zurück.
	 * @return Eine Liste von Unternehmen inkl. Unternehmen die in der GUI hinzugefügt oder gelöscht wurden. List<UnternehmenDAO>
	 * @throws SQLException zum Laden von Daten muss es ein Connection zu der Datenbank aufgebaut werden,
	 * wenn diese Connection nicht aufgebaut werden kann wird eine SQLException geworfen.
	 * @throws ClassNotFoundException wird von der connection Methode weiter geworfen da in der Connection Methode nach
	 * der Driver-Klasse gesucht wird.
	 */
	List<UnternehmenDAO> loadUnternehmenInput() throws SQLException, ClassNotFoundException;

	/**
	 * Es werden alle Räume aus der Datenbank geladen
	 * @return eine Liste von allen Räumen aus der Datenbank
	 * @throws SQLException zum Laden von Daten muss es ein Connection zu der Datenbank aufgebaut werden,
	 * wenn diese Connection nicht aufgebaut werden kann wird eine SQLException geworfen.
	 * @throws ClassNotFoundException wird von der connection Methode weiter geworfen da in der Connection Methode nach
	 * der Driver-Klasse gesucht wird.
	 */
	List<Raum> loadRooms() throws SQLException, ClassNotFoundException;

	/**
	 * Es werden Schüler aus der Datenbank geladen. Aus den gespeicherten Informationen aus der Datenbnak werden
	 * Schülerobjekte zusammengebaut und in eine Liste gepackt
	 * @return Eine Liste von Schülern 'List<Schueler>'
	 * @throws SQLException zum Laden von Daten muss es ein Connection zu der Datenbank aufgebaut werden,
	 * wenn diese Connection nicht aufgebaut werden kann wird eine SQLException geworfen.
	 * @throws ClassNotFoundException wird von der connection Methode weiter geworfen da in der Connection Methode nach
	 * der Driver-Klasse gesucht wird.
	 */
	List<Schueler> loadSchueler() throws SQLException, ClassNotFoundException;

	/**
	 * Es werden die Unternehmen die in der Datenbank gespeichert werden, geladen und als eine Liste zurückgegeben.
	 * @return alle in der Datenbank gespeicherten Unternehmen als Liste 'List<UnternehmenDAO>
	 * @throws SQLException zum Laden von Daten muss es ein Connection zu der Datenbank aufgebaut werden,
	 * wenn diese Connection nicht aufgebaut werden kann wird eine SQLException geworfen.
	 * @throws ClassNotFoundException wird von der connection Methode weiter geworfen da in der Connection Methode nach
	 * der Driver-Klasse gesucht wird.
	 */
	List<UnternehmenDAO> loadUnternehmen() throws SQLException, ClassNotFoundException;

	/**
	 * Kurse werden aus der Datenbank geladen. Damit keine neuen Objekte aus der Datenbank erstellt werden, wird die
	 * Liste mitgegeben die gespeichert wurde und zurückgegeben wird diese Liste mit den Informationen aus der Datenbank.
	 * @param schlrList Liste der Schueler die gespeichert wurden
	 * @param raum Liste der Räume die gespeichert wurden
	 * @param unternehmen Liste der Unternehmen die gespeichert wurden
	 * @return eine Liste von KursDAOs
	 * @throws SQLException zum Laden von Daten muss es ein Connection zu der Datenbank aufgebaut werden,
	 * wenn diese Connection nicht aufgebaut werden kann wird eine SQLException geworfen.
	 * @throws ClassNotFoundException wird von der connection Methode weiter geworfen da in der Connection Methode nach
	 * der Driver-Klasse gesucht wird.
	 */
	List<KursDAO> loadKurse(List<Schueler> schlrList, List<Raum> raum, List<UnternehmenDAO> unternehmen) throws SQLException, ClassNotFoundException;

}
