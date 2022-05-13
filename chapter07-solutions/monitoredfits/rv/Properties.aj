
package rv;

import fits.UserInfo;
import fits.BackEnd;







public aspect Properties {
static void setupVerification() {
}

before (): call(* BackEnd.shutdown(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!Verification.hasTriggered53b93 && Verification.state53b93.equals("Initialised") && (true)) {{}Verification.state53b93 = "Shutdown";Verification.hasTriggered53b93 = true;}
  }

before (): call(* UserInfo.openSession(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!Verification.hasTriggered53b93 && Verification.state53b93.equals("Start") && (true)) {{}Verification.state53b93 = "Bad";Verification.hasTriggered53b93 = true; System.out.println("P2 violated");}
  }

before (): call(* UserInfo.openSession(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!Verification.hasTriggered53b93 && Verification.state53b93.equals("Shutdown") && (true)) {{}Verification.state53b93 = "Bad";Verification.hasTriggered53b93 = true; System.out.println("P2 violated");}
  }

before (Integer uid, Integer sid, String from_account_number, double amount): call(* *.USER_payToExternal(..)) &&
    args(uid, sid, from_account_number, amount) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!Verification.hasTriggeredde80c && Verification.statede80c.equals("Start") && ((Verification.fitsExternalMoneyTransferCount+1>=1000 || Verification.fitsExternalMoneyTransferAmount+amount>=1000000))) {{}Verification.statede80c = "Bad";Verification.hasTriggeredde80c = true; System.out.println("P8 violated");}
  }

before (Integer uid, Integer sid, String from_account_number, double amount): call(* *.USER_payToExternal(..)) &&
    args(uid, sid, from_account_number, amount) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!Verification.hasTriggeredde80c && Verification.statede80c.equals("Start") && (true)) {{ Verification.fitsExternalMoneyTransferCount++; Verification.fitsExternalMoneyTransferAmount+=amount; }Verification.statede80c = "Start";Verification.hasTriggeredde80c = true;}
  }

before (): call(* *.*(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.hasTriggered53b93 = false; Verification.hasTriggeredde80c = false;}
  }

after (): call(* BackEnd.initialise(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!Verification.hasTriggered53b93 && Verification.state53b93.equals("Start") && (true)) {{}Verification.state53b93 = "Initialised";Verification.hasTriggered53b93 = true;}
  }

after (): call(* *.ADMIN_reconcile(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!Verification.hasTriggeredde80c && Verification.statede80c.equals("Start") && (true)) {{ Verification.fitsExternalMoneyTransferCount = 0; Verification.fitsExternalMoneyTransferAmount=0.0; }Verification.statede80c = "Start";Verification.hasTriggeredde80c = true;}
  }

after (): call(* *.*(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.hasTriggered53b93 = false; Verification.hasTriggeredde80c = false; }
  }

}