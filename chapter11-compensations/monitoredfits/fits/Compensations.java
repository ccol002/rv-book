package fits;

import java.util.Deque;
import java.util.LinkedList;

public class Compensations {
	private Deque<InternalTransaction> compensations;

	public void clearCompensations() {
		compensations = new LinkedList<InternalTransaction>();
	}

	public void addCompensation(InternalTransaction t) {
		compensations.push(t);
	}

	public void triggerCompensations(FrontEnd frontend) {
		for (InternalTransaction t : compensations) {
			frontend.ADMIN_transferBetweenAccounts(t.getOwner().getId(), t.getSourceAccount().getAccountNumber(),
					t.getOwner().getId(), t.getDestinationAccount().getAccountNumber(), t.getAmount());
		}
		clearCompensations();
	}
}
