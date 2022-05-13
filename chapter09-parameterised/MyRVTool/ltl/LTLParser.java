package ltl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ltl.LTLSpec.Matching;
import ltl.structure.And;
import ltl.structure.Finally;
import ltl.structure.Globally;
import ltl.structure.Implies;
import ltl.structure.LTL;
import ltl.structure.MatchEvent;
import ltl.structure.Next;
import ltl.structure.Not;
import ltl.structure.Or;
import ltl.structure.Until;

public class LTLParser {

	public static final String stops = " ()";

	static Set<String> identifiers;

	public static void registerIdentifier(String identifier) {
		identifiers.add(identifier);
	}

	public static void processBasic(ParsingHelper ph) throws Exception {
		if (ph.startsWith("not") || ph.startsWith("Not")) {
			ph.consumeString(3);
			ph.setTemp(new Not(processLTL(ph)));

		} else if (ph.startsWith("next") || ph.startsWith("Next")) {
			ph.consumeString(4);
			ph.setTemp(new Next(processLTL(ph)));

		} else if (ph.startsWith("globally") || ph.startsWith("Globally")) {
			ph.consumeString(8);
			ph.setTemp(new Globally(processLTL(ph)));

		} else if (ph.startsWith("finally") || ph.startsWith("Finally")) {
			ph.consumeString(7);
			ph.setTemp(new Finally(processLTL(ph)));

		} else if (ph.startsWith("(")) {
			int end = ph.getBracketed();
			ParsingHelper ph2 = new ParsingHelper(ph.getString().substring(1, end - 1).trim());
			ph.consumeString(end);
			ph.setTemp(processLTL(ph2));
		} else {
			ph.setTemp(new MatchEvent(ph.getEvent(stops)));
		}
	}

	public static void processCompound(ParsingHelper ph) throws Exception {
		if (ph.startsWith("until") || ph.startsWith("Until")) {
			ph.consumeString(5);
			LTL left = ph.getTemp();
			processBasic(ph);
			LTL right = ph.getTemp();
			ph.setTemp(new Until(left, right));

		} else if (ph.startsWith("and") || ph.startsWith("And")) {
			ph.consumeString(3);
			LTL left = ph.getTemp();
			processBasic(ph);
			LTL right = ph.getTemp();
			ph.setTemp(new And(left, right));
		} else if (ph.startsWith("or") || ph.startsWith("Or")) {
			ph.consumeString(2);
			LTL left = ph.getTemp();
			processBasic(ph);
			LTL right = ph.getTemp();
			ph.setTemp(new Or(left, right));
		} else if (ph.startsWith("implies")) {
			ph.consumeString(7);
			LTL left = ph.getTemp();
			processBasic(ph);
			LTL right = ph.getTemp();
			ph.setTemp(new Implies(left, right));
		}
	}

	public static LTL processLTL(ParsingHelper ph) throws Exception {
		processBasic(ph);

		while (!ph.end()) {
			processCompound(ph);
		}
		return ph.getTemp();
	}

	// ltl parser
	static public void parseLtlExps(LTLScript lTLScript, String s) throws Exception {

		ArrayList<LTLSpec> ltlExps = new ArrayList<LTLSpec>();
		ArrayList<ParameterisedLTLSpec> parametrisedLTLExps = new ArrayList<ParameterisedLTLSpec>();
		s = s.trim();

		// loop over formulae
		while (s.length() != 0) {

			System.out.println("Parsing LTL formula");

			// temporary variables
			Matching matching = null;
			String target_type = null;
			String target_variable = null;

			// Property
			Integer index_property = s.indexOf("property");
			if (index_property == -1)
				throw (new Exception("property keyword not found"));

			s = s.substring(index_property + 8).trim();

			// Foreach (optional)
			if (s.startsWith("foreach")) // note that the foreach keyword is optional
			{
				s = s.substring(7).trim();

				// now expecting the kind of binding
				// currently only supporting "Target" binding
				Integer index_target = s.indexOf("target");
				if (index_target == -1)
					throw (new Exception("target keyword not found"));

				s = s.substring(index_target + 6).trim();

				// Foreach type
				Integer index_opening_bracket = s.indexOf("(");
				if (index_opening_bracket == -1)
					throw (new Exception("Opening bracket not found"));

				s = s.substring(1).trim();

				Integer index_space_separator = s.indexOf(" ");
				if (index_space_separator == -1)
					throw (new Exception("Type or variable name not found"));

				target_type = s.substring(0, index_space_separator).trim();
				System.out.println("  - Foreach type is [" + target_type + "]");

				s = s.substring(index_space_separator + 1).trim();

				// Foreach variable
				Integer index_closing_bracket = s.indexOf(")");
				if (index_closing_bracket == -1)
					throw (new Exception("Closing bracket not found"));

				target_variable = s.substring(0, index_closing_bracket).trim();
				System.out.println("  - Foreach variable is [" + target_variable + "]");

				s = s.substring(index_closing_bracket + 1).trim();

			}

			// starting state

			if (s.startsWith("bad behaviour"))// NOT matching
			{
				throw new Exception("bad behaviour not supported for LTL");
			} else if (s.startsWith("good behaviour"))// matching
			{
				matching = Matching.GOOD;
				s = s.substring(14).trim();
			} else
				throw (new Exception("good behaviour keyword not found"));

			if (!s.startsWith("{"))
				throw (new Exception("{ not found"));

			Integer index_end_ltl = s.indexOf("}");
			if (index_end_ltl == -1)
				throw (new Exception("} not found"));

			String ltlString = s.substring(1, index_end_ltl).trim();

			s = s.substring(index_end_ltl + 1).trim();// consume regexp and closing bracket

			identifiers = new HashSet<String>();// refresh the identifiers registry

			// two pass parsing
			processLTL(new ParsingHelper(ltlString));// first pass to gather identifiers
			// second pass
			LTL ltl = processLTL(new ParsingHelper(ltlString));

			System.out.println("Parsed: " + ltl);

			LTLSpec lTLSpec = new LTLSpec(ltl, matching);
			if (target_type == null)
				ltlExps.add(lTLSpec);
			else
				parametrisedLTLExps.add(new ParameterisedLTLSpec(lTLSpec, target_type, target_variable));

		}

		lTLScript.setLTLExps(ltlExps);
		lTLScript.setParametrisedLTLExps(parametrisedLTLExps);
	}

}
