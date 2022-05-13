package egcl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainTimers {
	public static void main(String[] args) {
		try {
			EGCLScript script = new EGCLScript(
					"/Users/chris/Drive Work/Repositories/_archive/rv-book/code/Code solutions/08-GCL-Solution-Timers/src/script/properties.rs");
			try {
				File file_java = new File(
						"/Users/chris/Drive Work/Repositories/_archive/rv-book/code/Code solutions/08-GCL-Solution-Timers/src/transactionsystem/Verification.java");

				BufferedWriter output = new BufferedWriter(new FileWriter(file_java));
				output.write(script.getVerificationCode());
				output.close();

				File file_aj = new File(
						"/Users/chris/Drive Work/Repositories/_archive/rv-book/code/Code solutions/08-GCL-Solution-Timers/src/transactionsystem/Properties.aj");
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
