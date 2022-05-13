package fits;

class InternalTransaction {

	private UserInfo owner;
	private BankAccount from_account;
	private BankAccount to_account;
	private Double amount;

	public InternalTransaction(UserInfo owner, BankAccount from_account, BankAccount to_account, Double amount) {
		this.amount = amount;
		this.from_account = from_account;
		this.to_account = to_account;
	}

	public BankAccount getSourceAccount() {
		return this.from_account;
	}

	public BankAccount getDestinationAccount() {
		return this.to_account;
	}

	public Double getAmount() {
		return this.amount;
	}

	public UserInfo getOwner() {
		return this.owner;
	}

	public Boolean execute(FrontEnd frontend, Integer sid) {
		return (frontend.USER_transferOwnAccounts(owner.getId(), sid, from_account.getAccountNumber(),
				to_account.getAccountNumber(), amount));
	}
}
