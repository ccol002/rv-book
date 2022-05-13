package egcl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		try {
			// EGCLScript script = new
			// EGCLScript("/Users/gordon/Library/Containers/com.softwareambience.Unclutter/Data/Library/Application
			// Support/Unclutter/FileStorage/Research/Writing
			// Now/rv-book/final-code/project-sources/chapter08-solutions/script/properties.rs");
			EGCLScript script = new EGCLScript("/Users/gordon/Desktop/properties.egcl");
			try {
				File file_java = new File(
						// "/Users/gordon/Library/Containers/com.softwareambience.Unclutter/Data/Library/Application
						// Support/Unclutter/FileStorage/Research/Writing
						// Now/rv-book/final-code/project-sources/chapter08-solutions/rv/Verification.java");
						"/Users/gordon/Desktop/Verification.java");

				BufferedWriter output = new BufferedWriter(new FileWriter(file_java));
				output.write(script.getVerificationCode());
				output.close();

				File file_aj = new File(
						// "/Users/gordon/Library/Containers/com.softwareambience.Unclutter/Data/Library/Application
						// Support/Unclutter/FileStorage/Research/Writing
						// Now/rv-book/final-code/project-sources/chapter08-solutions/rv/properties.aj");
						"/Users/gordon/Desktop/Properties.aj");
				// File file_aj = new File("/Users/chris/Drive
				// Work/Repositories/rv-book/code/Code
				// solutions/03-GCL-RS-Solution/src/transactionsystem/Properties.aj");
				output = new BufferedWriter(new FileWriter(file_aj));
				output.write(script.toAspectJOffline());
				output.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception msg) {
			System.out.println("Error: " + msg);
			msg.printStackTrace();
		}
	}

}
