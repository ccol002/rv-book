package offline;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public privileged aspect Logger {
	
	// Define the events of interest to log
	static Set<String> pointsOfInterest = new HashSet<String>();
	static {
		pointsOfInterest.add("initialise");
		pointsOfInterest.add("openSession");
		pointsOfInterest.add("deposit");
		pointsOfInterest.add("withdraw");
		pointsOfInterest.add("ADMIN_createUser");
	}

	// File to use to log information
	static BufferedWriter outputWriter;

	// Open file upon start of main method
	before(Integer n): execution
		(* *.runScenario(..)) && 
		args(n)
	{
		try {
			outputWriter = 
				new BufferedWriter(new FileWriter(new File(
				"/Users/gordon/Library/Containers/com.softwareambience.Unclutter/Data/Library/Application Support/Unclutter/FileStorage/Research/Writing Now/rv-book/final-code/project-sources/chapter12/log/log"+n+".txt"
			)));
		} catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	// Log all before events of interest
	before(): execution 
		(public * *.*.*(..)) && 
		if (pointsOfInterest.contains(thisJoinPoint.getSignature().getName()))
	{	
		String event_information = 
			"\n"+
			"before: \n"+
			"method: "+thisJoinPoint.getSignature().getDeclaringTypeName()+"."+thisJoinPoint.getSignature().getName()+"\n"+
			"target: "+thisJoinPoint.getTarget().getClass().getSimpleName()+":"+thisJoinPoint.getTarget()+"\n"+
			"timestamp: "+System.currentTimeMillis()+"\n";
		
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		String[] argNames = signature.getParameterNames();
		
		for (Integer i = 0; i < thisJoinPoint.getArgs().length; i++) {
			event_information += 
				"arg: "+
				thisJoinPoint.getArgs()[i].getClass().getSimpleName()+":"+
				argNames[i]+":"+
				thisJoinPoint.getArgs()[i]+"\n";
		}		
		
		try {
			outputWriter.append(event_information);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Close file upon end of main method
	after(): execution(* *.runScenario(..)) {
		try {
			outputWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Log all before events of interest
	after() returning (Object returned_object): execution 
		(public * *.*.*(..)) && 
		if (pointsOfInterest.contains(thisJoinPoint.getSignature().getName()))
	{	
		String event_information = 
			"\n"+
			"after:\n"+
			"method: "+thisJoinPoint.getSignature().getDeclaringTypeName()+"."+thisJoinPoint.getSignature().getName()+"\n"+
			"target: "+thisJoinPoint.getTarget().getClass().getSimpleName()+":"+thisJoinPoint.getTarget()+"\n"+
			"timestamp: "+System.currentTimeMillis()+"\n";
		
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		String[] argNames = signature.getParameterNames();
		
		for (Integer i = 0; i < thisJoinPoint.getArgs().length; i++) {
			event_information += 
				"arg: "+
				thisJoinPoint.getArgs()[i].getClass().getSimpleName()+":"+
				argNames[i]+":"+
				thisJoinPoint.getArgs()[i]+"\n";
		}		
		event_information += "return: "+signature.getReturnType().getSimpleName()+":"+returned_object+"\n";

		try {
			outputWriter.append(event_information);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
