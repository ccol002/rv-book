package fits;

import java.util.ArrayList;
import java.util.Iterator;

public class UserInfo {
	protected enum UserMode {
		ENABLED, DISABLED, FROZEN;
	}

	public enum UserStatus {
		WHITELISTED, GREYLISTED, BLACKLISTED;
	}

	public enum UserType {
		GOLD, SILVER, NORMAL
	}

	protected Integer uid;
	protected String name;
	protected UserMode mode;
	protected UserStatus status;
	protected UserType type;
	protected ArrayList<UserSession> sessions;
	protected ArrayList<BankAccount> accounts;
	protected Integer next_session_id;
	protected Integer next_account_number;
	protected String country;

	public UserInfo(Integer uid, String name, String country) {
		this.uid = uid;
		this.name = name;

		makeDisabled();
		makeWhitelisted();
		makeNormalUser();

		sessions = new ArrayList<UserSession>();
		accounts = new ArrayList<BankAccount>();

		next_session_id = 0;
		next_account_number = 1;

		this.country = country;
	}

	// Basic information
	public Integer getId() {
		return uid;
	}

	public String getName() {
		return name;
	}

	public String getCountry() {
		return country;
	}

	public ArrayList<BankAccount> getAccounts() {
		return accounts;
	}

	public ArrayList<UserSession> getSessions() {
		return sessions;
	}

	// User type (Gold/Silver/Normal)
	public Boolean isGoldUser() {
		return (type == UserType.GOLD);
	}

	public Boolean isSilverUser() {
		return (type == UserType.SILVER);
	}

	public Boolean isNormalUser() {
		return (type == UserType.NORMAL);
	}

	public void makeGoldUser() {
		type = UserType.GOLD;
	}

	public void makeSilverUser() {
		type = UserType.SILVER;
	}

	public void makeNormalUser() {
		type = UserType.NORMAL;
	}

	// Status (White/Black/Greylisted)
	public Boolean isWhitelisted() {
		return (status == UserStatus.WHITELISTED);
	}

	public Boolean isGreylisted() {
		return (status == UserStatus.GREYLISTED);
	}

	public Boolean isBlacklisted() {
		return (status == UserStatus.BLACKLISTED);
	}

	public void makeBlacklisted() {
		status = UserStatus.BLACKLISTED;
	}

	public void makeGreylisted() {
		status = UserStatus.GREYLISTED;
	}

	public void makeWhitelisted() {
		status = UserStatus.WHITELISTED;
	}

	// Mode (Enabled/Frozen/Disabled)
	public Boolean isEnabled() {
		return (mode == UserMode.ENABLED);
	}

	public Boolean isFrozen() {
		return (mode == UserMode.FROZEN);
	}

	public Boolean isDisabled() {

		return (mode == UserMode.DISABLED);
	}

	public void makeEnabled() {

		mode = UserMode.ENABLED;
	}

	public void makeFrozen() {
		mode = UserMode.FROZEN;
	}

	public void makeUnFrozen() {
		mode = UserMode.DISABLED;// note bug here
	}

	public void makeDisabled() {

		mode = UserMode.DISABLED;
	}

	// User sessions
	public UserSession getSession(Integer sid) {
		UserSession s;

		Iterator<UserSession> iterator = sessions.iterator();
		while (iterator.hasNext()) {
			s = iterator.next();
			if (s.getId() == sid)
				return s;
		}
		return null;
	}

	public Integer openSession() {
		Integer sid = next_session_id;

		UserSession session = new UserSession(uid, sid);
		session.openSession();

		sessions.add(session);

		next_session_id++;

		return (sid);
	}

	public void closeSession(Integer sid) {
		UserSession s = getSession(sid);

		s.closeSession();
	}

	// User accounts
	public BankAccount getAccount(String account_number) {
		BankAccount a;

		Iterator<BankAccount> iterator = accounts.iterator();
		while (iterator.hasNext()) {
			a = iterator.next();
			if (a.getAccountNumber() == account_number)
				return a;
		}
		return null;
	}

	public String createAccount(Integer sid) {
		String accnum = uid.toString() + next_account_number.toString();
		next_account_number++;
		BankAccount a = new BankAccount(uid, accnum);
		accounts.add(a);
		return accnum;
	}

	public void deleteAccount(String accnum) {
		BankAccount a = getAccount(accnum);
		a.closeAccount();
	}

	public void withdrawFrom(String account_number, double amount) {
		getAccount(account_number).withdraw(amount);
	}

	public void depositTo(String account_number, double amount) {
		getAccount(account_number).deposit(amount);
	}

	// Calculate the charges when this user makes a transfer
	public Double getChargeRate(Double amount) {
		if (isGoldUser()) {
			if (amount <= 100)
				return 0.00; // no charges
			if (amount <= 1000)
				return (amount * 0.02); // 2% charges
			return (amount * 0.01); // 1% charges
		}
		if (isSilverUser()) {
			if (amount <= 1000)
				return (amount * 0.03); // 3% charges
			return (amount * 0.02); // 2% charges
		}
		if (isNormalUser()) {
			if (amount * 0.05 > 2.0) {
				return (amount * 0.05);
			} else {
				return 2.00;
			} // 5% charges, minimum of $2
		}
		return 0.00;
	}

}
