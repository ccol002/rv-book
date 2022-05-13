package testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fits.Scenarios;

class TestScenario5 {

	@BeforeEach
	void setUp() throws Exception {

		Scenarios.resetScenarios();
	}

	@Test
	void test5() {
		Scenarios.runScenario(5);

	}

}
