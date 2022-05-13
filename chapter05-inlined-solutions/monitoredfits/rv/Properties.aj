package rv;

import assertion.Assertion;
import fits.UserInfo;
import fits.UserSession;

public aspect Properties {
  
    // Property 6
    private Boolean UserInfo.isGreylisted = false;
    private Integer UserInfo.transferCountSinceGreylisted = 0;
    
    before (UserInfo u): call(* UserInfo.makeGreylisted(..)) &&  target(u) {
    	u.isGreylisted = true;
    	u.transferCountSinceGreylisted = 0;
    }
    before (UserInfo u): call(* UserInfo.makeBlacklisted(..)) &&  target(u) {
    	u.isGreylisted = false;
    	u.transferCountSinceGreylisted = 0;
    }
    before (UserInfo u): call(* UserInfo.makeWhitelisted(..)) &&  target(u) {
    	if (u.isGreylisted) 
    		Assertion.check(u.transferCountSinceGreylisted >= 3, "P6 violated");

    	u.isGreylisted = false;
    	u.transferCountSinceGreylisted = 0;
    }
    before (UserInfo u): call(* UserInfo.depositTo(..)) &&  target(u) 
    {
    	if (u.isGreylisted)
    		u.transferCountSinceGreylisted ++;
    }

    
    // Property 9
    public int UserInfo.activeSessions = 0;
    
    before (UserInfo u): call(* UserInfo.openSession(..)) && target(u) {
    	u.activeSessions++; 
    	Assertion.check(u.activeSessions <= 3, "P9 violated");
    }
    
    before (UserInfo u): call(* UserInfo.closeSession(..)) && target(u) {
    	u.activeSessions--; 
    }

    // Property 10
    public boolean UserSession.loggedIn = false;

    before (UserSession s): call(* UserSession.openSession(..)) && target(s) {
    	s.loggedIn = true; 
    }
    before (UserSession s): call(* UserSession.closeSession(..)) && target(s) {
    	s.loggedIn = false; 
    }
    before (UserSession s): call(* UserSession.log(..)) && target(s) {
    	Assertion.check(s.loggedIn, "P10 violated");
    }
    
}
