package execlLoad;

import de.bwvaachen.botscheduler.calculate.CalcSchueler;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IImport {

    List<Schueler> getChoices( String path);

    List<Unternehmen> getCompany(String path);


    List<Raum> getRoom(String path);


}
