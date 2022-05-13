package fits;

import java.util.ArrayList;

public class CompoundTransaction {

	private UserInfo owner;
	private ArrayList<InternalTransaction> internal_transaction_list;

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

	public Boolean execute(FrontEnd frontend, Integer sid) {
		Boolean result = true;
		for (InternalTransaction t : internal_transaction_list) {
			if (result) {
				result &= t.execute(frontend, sid);
			}
		}
		return result;
	}
}
