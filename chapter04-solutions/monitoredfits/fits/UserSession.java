package fits;

import rv.Verification;

public class UserSession {
	protected Integer sid;
	protected String log;
	protected Integer owner;

	public UserSession(Integer uid, Integer sid) {
		this.sid = sid;
		owner = uid;
		log = "";
	}

	public Integer getId() {
		return sid;
	}

	public Integer getOwner() {
		return owner;
	}

	public String getLog() {
		return log;
	}

	public void openSession() {
		Verification.sessionOpen(this);
	}

	public void log(String l) {
		Verification.sessionLogInformation(this);

		log += l + "\n";
	}

	public void closeSession() {
		Verification.sessionClose(this);
	}

}
