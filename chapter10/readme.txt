
A timer can have:
> a label to identify it
> one or more firing times



Firing times become active upon resetting the timer
For example:

public class Main {

	public static void main(String[] args)
	{
		new Timer().addFiringTime(1000l).reset();
		new Timer("b",2000l).reset();
		try{
	    Thread.sleep(3222);
		}catch(Exception ex){ex.printStackTrace();}
	}
	
	
}

Note that this chapter builds on EGCL
To run the EGCL compiler, use egcl.Main in MyRVTool folder (this is equivalent to the solution from Chapter 6)

To run the FiTS system, use fits.Main