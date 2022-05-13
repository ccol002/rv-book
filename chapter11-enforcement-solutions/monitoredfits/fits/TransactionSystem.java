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
	}
}
