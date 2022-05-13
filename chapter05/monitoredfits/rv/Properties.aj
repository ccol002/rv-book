package rv;

import fits.BackEnd;
import fits.UserInfo;

public aspect Properties {

	before (): call(* BackEnd.initialise()) {
		Verification.fitsInitialisation();
	}

	before (): call(* UserInfo.openSession()) {
		Verification.fitsOpenSession();
	}

}
