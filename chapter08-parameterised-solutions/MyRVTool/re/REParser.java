package re;

import java.util.ArrayList;

import re.RegExpSpec.Matching;
import re.structure.Any;
import re.structure.Choice;
import re.structure.EmptyTrace;
import re.structure.MatchEvent;
import re.structure.MatchEventComplement;
import re.structure.Nothing;
import re.structure.RegExp;
import re.structure.Repetition;
import re.structure.SequentialComposition;

public class REParser {

	public static final String stops = "; +()*[]";

	public static void processBasic(ParsingHelper ph) throws Exception {
		if (ph.startsWith("?")) {
			ph.setTemp(new Any());
			ph.consumeString(1);
		} else if (ph.startsWith("1")) {
			ph.setTemp(new EmptyTrace());
			ph.consumeString(1);
		} else if (ph.startsWith("0")) {
			ph.setTemp(new Nothing());
			ph.consumeString(1);
		} else if (ph.startsWith("!")) {
			ph.consumeString(1);

			ph.setTemp(new MatchEventComplement(ph.getEvent(stops)));
		} else if (ph.startsWith("(")) {
			int end = ph.getBracketed();
			ParsingHelper ph2 = new ParsingHelper(ph.getString().substring(1, end - 1).trim());
			ph.consumeString(end);
			ph.setTemp(processRE(ph2));
		} else {
			ph.setTemp(new MatchEvent(ph.getEvent(stops)));
		}

		if (ph.startsWith("*")) {
			ph.consumeString(1);
			ph.setTemp(new Repetition(ph.getTemp()));
		}
	}

	public static void processCompound(ParsingHelper ph) throws Exception {
		if (ph.startsWith("+")) {
			ph.consumeString(1);
			RegExp left = ph.getTemp();
			processBasic(ph);
			RegExp right = ph.getTemp();
			ph.setTemp(new Choice(left, right));

		} else if (ph.startsWith(";")) {
			ph.consumeString(1);
			RegExp left = ph.getTemp();
			processBasic(ph);
			RegExp right = ph.getTemp();
			ph.setTemp(new SequentialComposition(left, right));
		}
	}

	public static RegExp processRE(ParsingHelper ph) throws Exception {
		processBasic(ph);

		while (!ph.end()) {
			processCompound(ph);
		}
		return ph.getTemp();
	}

	// regexp parser
	static public void parseRegExps(REScript rEScript, String s) throws Exception {
		ArrayList<RegExpSpec> regExps = new ArrayList<RegExpSpec>();
		ArrayList<ParametrisedRegExpSpec> parametrisedRegExps = new ArrayList<ParametrisedRegExpSpec>();
		s = s.trim();

		// loop over automata
		while (s.length() != 0) {

			System.out.println("Parsing regular expression");

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
				matching = Matching.BAD;
				s = s.substring(13).trim();
			} else if (s.startsWith("good behaviour"))// matching
			{
				matching = Matching.GOOD;
				s = s.substring(14).trim();
			} else
				throw (new Exception("bad/good behaviour keyword not found"));

			if (!s.startsWith("{"))
				throw (new Exception("{ not found"));

			Integer index_end_regexp = s.indexOf("}");
			if (index_end_regexp == -1)
				throw (new Exception("} not found"));

			String regexpString = s.substring(1, index_end_regexp).trim();

			s = s.substring(index_end_regexp + 1).trim();// consume regexp and closing bracket

			// two pass parsing
			processRE(new ParsingHelper(regexpString));// first pass to gather identifiers
			// second pass
			RegExp re = processRE(new ParsingHelper(regexpString));

			System.out.println("Parsed: " + re);

			RegExpSpec regExpSpec = new RegExpSpec(re, matching);
			if (target_type == null)
				regExps.add(regExpSpec);
			else
				parametrisedRegExps.add(new ParametrisedRegExpSpec(regExpSpec, target_type, target_variable));

		}

		rEScript.setRegExpSpecs(regExps);
		rEScript.setParametrisedRegExpSpecs(parametrisedRegExps);
	}

}
