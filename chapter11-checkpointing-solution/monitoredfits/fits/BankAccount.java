package fits;

public class BankAccount implements Cloneable {
	protected Boolean opened;
	protected String account_number;
	protected Double balance;
	protected Integer owner;

	@Override
	protected BankAccount clone() {
		try {
			return (BankAccount) super.clone();
		} catch (CloneNotSupportedException ex) {
			return null;
		}
	}

	public BankAccount(Integer uid, String accnum) {
		account_number = accnum;
		balance = 0.00;
		opened = false;
		owner = uid;
	}

	public Boolean isOpen() {
		return opened;
	}

	public String getAccountNumber() {
		return account_number;
	}

	public Double getBalance() {
		return balance;
	}

	public Integer getOwner() {
		return owner;
	}

	public void enableAccount() {
		opened = true;
	}

	public void closeAccount() {
		opened = false;
	}

	public void withdraw(Double amount) {
		balance -= amount;
	}

	public void deposit(Double amount) {
		balance += amount;
	}

}
