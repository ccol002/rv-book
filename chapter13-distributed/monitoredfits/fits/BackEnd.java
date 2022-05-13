package fits;

import java.util.ArrayList;
import java.util.Iterator;

public class BackEnd {
	protected Boolean initialised;
	protected ArrayList<UserInfo> users;
	protected Integer next_user_id;

	// Constructor
	public BackEnd() {
		users = new ArrayList<UserInfo>();
		next_user_id = 0;
		initialised = false;
	}

	// Get the users currently registered in the system
	public ArrayList<UserInfo> getUsers() {
		return users;
	}

	// Initialise the transaction system with a single admin user
	// called Clark Kent from Malta
	public void initialise() {
		users = new ArrayList<UserInfo>();
		next_user_id = 1;
		initialised = true;

		Integer admin_uid = addUser("Clark Kent", "Malta");
		UserInfo admin = getUserInfo(admin_uid);

		admin.makeSilverUser();
		admin.makeEnabled();
	}

	// Lookup a user by user-id
	public UserInfo getUserInfo(Integer uid) {
		UserInfo u;

		Iterator<UserInfo> iterator = users.iterator();
		while (iterator.hasNext()) {
			u = iterator.next();
			if (u.getId() == uid)
				return u;
		}
		return null;
	}

	// Add a user to the system
	public Integer addUser(String name, String country) {
		Integer uid = next_user_id;
		next_user_id++;

		UserInfo u = new UserInfo(uid, name, country);
		users.add(u);
		if (otherTransactionSystem != null) {
			u.register(otherTransactionSystem);
		}

		return uid;
	}

	// Add knowledge of the other system
	private TransactionSystem otherTransactionSystem = null;

	public void register(TransactionSystem ts) {
		otherTransactionSystem = ts;
		for (UserInfo user : users) {
			user.register(ts);
		}
	}

	private void sendToOtherInstance(String msg) {
		if (otherTransactionSystem != null) {
			otherTransactionSystem.message(msg);
		}
	}

}
