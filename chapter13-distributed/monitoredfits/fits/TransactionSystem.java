package fits;

public class TransactionSystem {

	private FrontEnd frontend;
	private BackEnd backend;

	public TransactionSystem() {
		setup();
	}

	public FrontEnd getFrontEnd() {
		return frontend;
	}

	public BackEnd getBackEnd() {
		return backend;
	}

	public void setup() {
		backend = new BackEnd();
		frontend = new FrontEnd(backend);
		if (otherTransactionSystem != null) {
			backend.register(otherTransactionSystem);
		}
	}

	// Add knowledge of the other system
	private TransactionSystem otherTransactionSystem = null;

	public TransactionSystem getOtherTransactionSystem() {
		return otherTransactionSystem;
	}

	public void register(TransactionSystem ts) {
		otherTransactionSystem = ts;
		if (backend != null) {
			backend.register(ts);
		}
	}

	private void sendToOtherInstance(String msg) {
		if (otherTransactionSystem != null) {
			otherTransactionSystem.message(msg);
		}
	}

	public void message(String m) {
		// Handle the receiving of messages
	}

}
