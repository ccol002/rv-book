package rv;

import assertion.Assertion;
import fits.FrontEnd;
import fits.UserInfo;

public aspect Properties {

	
	//Property 5
	// This adds a Boolean attribute to class UserInfo 
	private Boolean UserInfo.isEnabled = false; 
	
	before (UserInfo u): call(* UserInfo.makeDisabled(..)) && target(u) { 
		u.isEnabled = false; 
	} 
	
	before (FrontEnd fe, Integer uid): call(* FrontEnd.ADMIN_enableUser(..)) && target(fe) && args(uid) { 
		fe.getBackEnd().getUserInfo(uid).isEnabled = true; } 
	
	before (UserInfo u): call(* UserInfo.withdrawFrom(..)) && target(u) { 
		Assertion.check(u.isEnabled, "P5 violated"); }
}
