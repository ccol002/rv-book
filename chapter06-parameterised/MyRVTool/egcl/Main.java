package egcl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		try {
			String path = "/Users/chris/Drive Work/Repositories/rv-book-final/rv-book/final-code/project-sources/chapter06-parametrised/";

			EGCLScript script = new EGCLScript(path + "script/properties.egcl");
			try {
				File file_java = new File(path + "src/rv/Verification.java");

				BufferedWriter output = new BufferedWriter(new FileWriter(file_java));
				output.write(script.getVerificationCode());
				output.close();

				File file_aj = new File(path + "src/rv/Properties.aj");
				output = new BufferedWriter(new FileWriter(file_aj));
				output.write(script.toAspectJ());
				output.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception msg) {
			System.out.println("Error: " + msg);
		}
	}

}
