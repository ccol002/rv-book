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
  
property bad behaviour{  
(![before BackEnd.initialise])*; [before UserInfo.openSession]
}


