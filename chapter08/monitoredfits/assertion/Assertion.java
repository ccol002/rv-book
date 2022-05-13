package assertion;

public class Assertion {

	public static void alert(String errorString) {
		System.out.println(errorString);
	}

	public static void check(Boolean condition, String errorString) {
		if (!condition)
			alert(errorString);
	}

}
