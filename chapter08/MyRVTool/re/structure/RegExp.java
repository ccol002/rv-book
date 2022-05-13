package re.structure;

public abstract class RegExp {

	/*
	 * RE ::= | ? Any | 0 Nothing | 1 End of string | a Proposition | !a All the
	 * propositions except a | RE + RE Choice | RE ; RE Sequence | RE* Repetition |
	 * (RE) Bracketed expression
	 */

//	public abstract Set<Event> getRelevantEvents();
//
//	public abstract boolean hasEmpty();

	public abstract RegExp clone();

//	public abstract RegExp derivative(Event event);

	public abstract String getConstructor();

//	public abstract boolean cannotMatch();

	public abstract RegExp simplify();
}