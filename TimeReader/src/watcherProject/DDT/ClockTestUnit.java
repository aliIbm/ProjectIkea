package watcherProject.DDT;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import watcherProject.Clock;

public class ClockTestUnit {
	
	Clock clockInstance;

	@Before
	public void setUp() throws Exception {
		clockInstance = new Clock();
	}
	@Test
	public void testClockCase1() {
		String outcome = clockInstance.TranslateTime("00:00").trim();
		assertEquals("","The time is exactly midnight", outcome);
	}
	@Test
	public void testClockCase2() {
		String outcome = clockInstance.TranslateTime("00:05").trim();
		assertEquals("","The time is five past midnight", outcome);
	}
	@Test
	public void testClockCase3() {
		String outcome = clockInstance.TranslateTime("00:01").trim();
		assertEquals("","The time is just past midnight", outcome);
	}
	@Test
	public void testClockCase4() {
			
		String outcome = clockInstance.TranslateTime("23:45").trim();
		assertEquals("","The time is quarter to midnight", outcome);
	}
}
