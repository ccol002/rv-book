package egcl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		try {
			String path = "/Users/chris/Drive Work/Repositories/rv-book-final/rv-book/final-code/dist-final/chapter11-enforcement-solutions/";
			EGCLScript script = new EGCLScript(path + "script/properties.egcl"
			// "/Users/gordon/Library/Containers/com.softwareambience.Unclutter/Data/Library/Application
			// Support/Unclutter/FileStorage/Research/Writing
			// Now/rv-book/final-code/wip/chapter11-reparations-solutions/MyRVTool/script/properties.egcl"
			);
			// Write Verification.java file
			File file_java = new File(path + "monitoredfits/rv/Verification.java");
			BufferedWriter output = new BufferedWriter(new FileWriter(file_java));
			output.write(script.getVerificationCode());
			output.close();

			// Write Properties.aj file
			File file_aj = new File(path + "monitoredfits/rv/Properties.aj");
			output = new BufferedWriter(new FileWriter(file_aj));
			output.write(script.toAspectJ());
			output.close();

			// Write exceptions files
			for (String exception_name : script.getExceptionNames()) {
				File file_exception = new File(path + "monitoredfits/rv/" + exception_name + ".java");
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
