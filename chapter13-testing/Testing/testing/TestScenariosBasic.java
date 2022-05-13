package testing;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fits.FrontEnd;
import fits.TransactionSystem;
import rv.Verification;

class TestScenariosBasic {

	static TransactionSystem ts = new TransactionSystem();

	@BeforeEach
	void setUp() throws Exception {
		ts = new TransactionSystem();
		Verification.setupVerification();
	}

	@Test
	void testUserEnabled() {

		FrontEnd fe = ts.getFrontEnd();

		fe.ADMIN_initialise();
		int uid = fe.ADMIN_createUser("Fred", "France");
		fe.ADMIN_enableUser(uid);

		assertTrue(fe.getBackEnd().getUserInfo(uid).isEnabled());

	}

	@Test
	void testUserSilver() {

		// TODO
	}

}
