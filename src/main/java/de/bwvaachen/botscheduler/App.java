package de.bwvaachen.botscheduler;
import de.bwvaachen.botscheduler.grassmann.myInterface.MyController;

/**
 * Hauptklasse, die die Software startet
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
    	new MyController(false);
    }
}
