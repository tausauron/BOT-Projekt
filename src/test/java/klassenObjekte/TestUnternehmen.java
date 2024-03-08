package klassenObjekte;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.bwvaachen.botscheduler.calculate.Zeitslot;
import de.bwvaachen.botscheduler.calculate.Zeitslot.Typ;

class TestUnternehmen {
	
	private static Unternehmen unt;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	
		unt = new Unternehmen(1, "Test", "Testfachrichtung", 25, 3, "A");
	}
	
	@Test
	void testFreeSlot(){
		unt.getKurse().put(Typ.A, new Kurse(new ArrayList<>(), unt, new Zeitslot(Typ.A)));
		System.out.println(unt.freeSlot());	
		System.out.println(unt.getKurse().values().size());
		assertEquals(unt.freeSlot(), true);	

		unt.getKurse().put(Typ.B, new Kurse(new ArrayList<>(), unt, new Zeitslot(Typ.B)));
		unt.getKurse().put(Typ.C, new Kurse(new ArrayList<>(), unt, new Zeitslot(Typ.C)));
		System.out.println(unt.getKurse().values().size());

		System.out.println(unt.freeSlot());
		System.out.println(unt.getKurse().values().size());

		assertEquals(unt.freeSlot(), false);


	}
}

