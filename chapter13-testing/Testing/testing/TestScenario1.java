package testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fits.Scenarios;

class TestScenario1 {

	@BeforeEach
	void setUp() throws Exception {

		Scenarios.resetScenarios();
	}

	@Test
	void test1() {
		Scenarios.runScenario(1);

	}

}
