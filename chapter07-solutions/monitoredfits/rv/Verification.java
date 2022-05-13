
package rv;

public class Verification {

	static public Integer fitsExternalMoneyTransferCount;
	static public Double fitsExternalMoneyTransferAmount;

	static public void setupVerification() {

		fitsExternalMoneyTransferCount = 0;
		fitsExternalMoneyTransferAmount = 0.0;

// required to reset the automata to their initial state
		Properties.setupVerification();

		hasTriggered53b93 = false;
		state53b93 = "Start";

		hasTriggeredde80c = false;
		statede80c = "Start";
	}

	static public String state53b93;
	static public Boolean hasTriggered53b93;

	static public String statede80c;
	static public Boolean hasTriggeredde80c;
}
