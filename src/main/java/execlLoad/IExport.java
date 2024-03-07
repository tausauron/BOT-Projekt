package execlLoad;

import java.util.List;

import de.bwvaachen.botscheduler.calculate.CalcSchueler;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

public interface IExport {
	
	void exportStudentData(List<Schueler> students, String path);
	
	void exportCompanyData(List<Unternehmen> companies, String path);
	
	void exportRoomData(List<Raum> rooms, String path);
	
	void exportStudentSchedule(List<CalcSchueler> cSchueler, String path);
	
	void exportRoomUsage(List<Unternehmen> unternehmen, String path);
	
	void exportParticipants(List<Unternehmen> unternehmen, String path);

}
