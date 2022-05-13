package fits;

// The OverheadsScenario class implements a scenario with 10000 users
// performing 10000 transactions amongst them. The scenario times the 
// transactions themselves, not the creation of users or accounts, nor
// the time taken logging in and out. The number of milliseconds taken
// is returned by the method.

// Run this scenario with and without monitoring of properties 3, 8 and 
// 10 to assess the overheads in time consumed by the monitors. Run 
// multiple times to get an average reading.

public class Main {

	public static void main(String[] args) {
		System.out.println("The scenario took " + OverheadsScenario.runScenario() + " milliseconds to execute.");
	}

}
