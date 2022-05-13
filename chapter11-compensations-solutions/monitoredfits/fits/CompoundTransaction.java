package fits;

import java.util.ArrayList;

import rv.RVExceptionPropertyCompoundTransaction;

public class CompoundTransaction {

	private UserInfo owner;
	private ArrayList<InternalTransaction> internal_transaction_list;

	public ArrayList<InternalTransaction> getTransactions() {
		return internal_transaction_list;
	}

	public CompoundTransaction(UserInfo owner) {
		internal_transaction_list = new ArrayList<InternalTransaction>();
		this.owner = owner;
	}

	public void addInternalTransaction(InternalTransaction t) {
		if (t.getOwner().getId() == owner.getId()) {
			internal_transaction_list.add(t);
		}
	}

	public ArrayList<InternalTransaction> getInternalTransactions() {
		return internal_transaction_list;
	}

	public UserInfo getOwner() {
		return owner;
	}

	private Compensations compensations = new Compensations();

	public Boolean execute(FrontEnd frontend, Integer sid) {
		compensations.clearCompensations();
		try {
			for (InternalTransaction t : getTransactions()) {
				t.execute(frontend, sid);
				InternalTransaction compensation = new InternalTransaction(t.getOwner(), t.getDestinationAccount(),
						t.getSourceAccount(), t.getAmount());
				compensations.addCompensation(compensation);
				return true;
			}
		} catch (RVExceptionPropertyCompoundTransaction rvX) {
			compensations.triggerCompensations(frontend);
			return false;
		}
		return true;
	}
}
