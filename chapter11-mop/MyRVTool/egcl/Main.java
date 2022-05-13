package egcl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		try {
			EGCLScript script = new EGCLScript(
					"/Users/gordon/Library/Containers/com.softwareambience.Unclutter/Data/Library/Application Support/Unclutter/FileStorage/Research/Writing Now/rv-book/final-code/dist-final/chapter11-mop-solutions/MyRVTool/script/properties.egcl");

			// Write Verification.java file
			File file_java = new File("/users/gordon/Downloads/Verification.java");
			BufferedWriter output = new BufferedWriter(new FileWriter(file_java));
			output.write(script.getVerificationCode());
			output.close();

			// Write Properties.aj file
			File file_aj = new File("/users/gordon/Downloads/Properties.aj");
			output = new BufferedWriter(new FileWriter(file_aj));
			output.write(script.toAspectJ());
			output.close();

			// Write exceptions files
			for (String exception_name : script.getExceptionNames()) {
				File file_exception = new File("/users/gordon/Downloads/" + exception_name + ".java");
				output = new BufferedWriter(new FileWriter(file_exception));
				output.write("package rv;\n" + "\n" + "@SuppressWarnings(\"serial\")\n" + "public class "
						+ exception_name + " extends RuntimeException {\n" + "  public " + exception_name
						+ "(String msg) { super(msg); }\n" + "}");
				output.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception msg) {
			System.out.println("Error: " + msg);
			msg.printStackTrace();
		}
	}

}
