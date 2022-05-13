package fits;

public class Scenarios {

	private static Integer SCENARIO_COUNT = 0;

	static DistTransactionSystem disttransactionsystem = new DistTransactionSystem();

	private static void runScenario(Integer n) {
	}

	private static void resetScenarios() {
		disttransactionsystem.setup();
	}

	public static void runAllScenarios() {
		for (Integer n = 1; n <= SCENARIO_COUNT; n++) {
			resetScenarios();
			;
			runScenario(n);
		}
	}
}
