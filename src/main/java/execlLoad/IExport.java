package execlLoad;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import de.bwvaachen.botscheduler.calculate.CalcSchueler;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

/**
 * Interface fuer den Export von Excel-Dateien
 */
public interface IExport {
	
	/**
	 * exportiert die aktuelle Schuelerliste in eine Excel-Datei
	 * 
	 * @param students Liste von Schuelern
	 * @param path Pfad fuer die Excel-Datei
	 */
	void exportStudentData(List<Schueler> students, String path);
	
	/**
	 * exportiert die aktuelle Veranstaltungsliste in eine Excel-Datei
	 * 
	 * @param companies Liste von Veranstaltungstypen
	 * @param path Pfad fuer die Excel-Datei
	 */
	void exportCompanyData(List<Unternehmen> companies, String path);
	
	/**
	 * exportiert die aktuelle Raumliste in eine Excel-Datei
	 * 
	 * @param rooms Liste von Raeumen
	 * @param path Pfad fuer die Excel-Datei
	 */
	void exportRoomData(List<Raum> rooms, String path);
	
	/**
	 *exportiert die Laufzettel der Schueler in eine Excel-Datei
	 * 
	 * @param cSchueler Liste von Ergebnis-Schuelerobjekten mit erfuellten Wuenschen
	 * @param path Pfad fuer die Excel-Datei
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	void exportStudentSchedule(List<CalcSchueler> cSchueler, String path) throws FileNotFoundException, IOException;
	
	/**
	 * exportiert die Raumbelegungsliste in eine Excel-Datei
	 * 
	 * @param unternehmen Liste Veranstaltungstypen, die die Kursinformationen enthaelt
	 * @param path Pfad fuer die Excel-Datei
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	void exportRoomUsage(List<Unternehmen> unternehmen, String path) throws FileNotFoundException, IOException;
	
	/**
	 * exportiert die Anwesenheitslisten in eine Excel-Datei
	 * 
	 * @param unternehmen Liste Veranstaltungstypen, die die Kursinformationen enthaelt
	 * @param path Pfad fuer die Excel-Datei
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	void exportParticipants(List<Unternehmen> unternehmen, String path) throws FileNotFoundException, IOException;

}
