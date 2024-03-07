package execlLoad;

import java.util.List;

import de.bwvaachen.botscheduler.calculate.CalcSchueler;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

public interface IExport {
	
	void exportStudentData(List<Schueler> students);
	
	void exportCompanyData(List<Unternehmen> companies);
	
	void exportRoomData(List<Raum> rooms);
	
	void exportStudentSchedule(List<CalcSchueler> cSchueler);
	
	void exportRoomUsage(List<Unternehmen> unternehmen);
	
	void exportParticipants(List<Unternehmen> unternehmen);

}
