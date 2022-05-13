VERIFICATIONCODE

//this is ignored

PRELUDE

package rv;

import re.structure.*;
import java.util.HashMap;

import fits.*;

import assertion.Assertion;


REGULAREXPRESSIONS

// P5
// Once a user is disabled, he or she may not withdraw from an account until 
// being made enable again.

property foreach target (UserInfo u) good behaviour {
 ((![before UserInfo.makeDisabled])* ; [before UserInfo.makeDisabled] ; (![before UserInfo.withdrawFrom])* ; [before UserInfo.makeEnabled] )*
}

property foreach target (UserInfo u) bad behaviour {
(?)* ; [before makeDisabled] ; (![before makeEnabled])* ; [before withdrawFrom]
}


// P10
// Logging can only be made to an active session (i.e. between a login and a logout).
property foreach target (UserSession u) bad behaviour{
(![before UserSession.openSession])*;([before UserSession.openSession];(![before UserSession.closeSession])*;[before UserSession.closeSession])*;(![before UserSession.openSession])*;[before UserSession.log]
}

property foreach target (UserSession u) good behaviour{
((![before UserSession.log])*;[before UserSession.openSession];(![before UserSession.closeSession])*;[before UserSession.closeSession])*
}
