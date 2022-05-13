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

		s = s.trim();

		// loop over automata
		while (s.length() != 0) {

			System.out.println("Parsing regular expression");

			// temporary variables
			Matching matching = null;

			// Property
			Integer index_property = s.indexOf("property");
			if (index_property == -1)
				throw (new Exception("property keyword not found"));

			s = s.substring(index_property + 8).trim();

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
			regExps.add(regExpSpec);

		}

		rEScript.setRegExpSpecs(regExps);

	}

}
