VERIFICATIONCODE

//this is ignored

PRELUDE

package rv;

import re.structure.*;
import java.util.HashMap;

import fits.*;

import assertion.Assertion;


REGULAREXPRESSIONS


// P2
// The transaction system must be initialised before any user logs in.
// + The initialisation can only happen before shutdown.

property bad behaviour {
(![before BackEnd.initialise])*;([before BackEnd.initialise];(![before BackEnd.shutdown])*;[before BackEnd.shutdown])*;(![before BackEnd.initialise])*;[before UserInfo.openSession]
}


// P5
// Once a user is disabled, he or she may not withdraw from an account until 
// being made enable again.

property good behaviour {
 ((![before UserInfo.makeDisabled])* ; [before UserInfo.makeDisabled] ; (![before UserInfo.withdrawFrom])* ; [before UserInfo.makeEnabled] )*
}

property bad behaviour {
(?)* ; [before makeDisabled] ; (![before makeEnabled])* ; [before withdrawFrom]
}


// P10
// Logging can only be made to an active session (i.e. between a login and a logout).
property bad behaviour{
(![before UserSession.openSession])*;([before UserSession.openSession];(![before UserSession.closeSession])*;[before UserSession.closeSession])*;(![before UserSession.openSession])*;[before UserSession.log]
}

property good behaviour{
((![before UserSession.log])*;[before UserSession.openSession];(![before UserSession.closeSession])*;[before UserSession.closeSession])*
}