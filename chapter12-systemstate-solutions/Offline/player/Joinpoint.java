package player;

import java.util.ArrayList;
import java.util.HashMap;

public class Joinpoint {
	public enum JoinpointKind {
		BEFORE, AFTER
	};

	// Modality
	private JoinpointKind joinpoint_kind = JoinpointKind.BEFORE;
	// Method name
	private String method_class = "";
	private String method_name = "";
	// Target
	private String target_type = "";
	private String target_value = "";
	// Arguments
	private Integer arg_count = 0;
	private ArrayList<String> arg_values;
	private ArrayList<String> arg_names;
	private ArrayList<String> arg_types;
	// Parameters
	private HashMap<String, String> parameter_mapping;
	// Return value
	private Boolean has_return_value = false;
	private String return_type = "";
	private String return_value = "";
	// Timestamp
	private String timestamp = "";

	// Getters and setters for joinpoint kind
	public JoinpointKind getJoinpointKind() {
		return joinpoint_kind;
	}

	public void setJoinpointKind(JoinpointKind joinpoint_kind) {
		this.joinpoint_kind = joinpoint_kind;
	}

	// Getters and setters for method
	public String getFullMethodName() {
		return (getMethodClass() + "." + getMethodName());
	}

	public String getMethodName() {
		return method_name;
	}

	public void setMethodName(String method_name) {
		this.method_name = method_name;
	}

	public String getMethodClass() {
		return method_class;
	}

	public void setMethodClass(String method_class) {
		this.method_class = method_class;
	}

	// Getters and setters for target
	public String getTargetValue() {
		return target_value;
	}

	public void setTargetValue(String target_value) {
		this.target_value = target_value;
	}

	public String getTargetType() {
		return target_type;
	}

	public void setTargetType(String target_type) {
		this.target_type = target_type;
	}

	// Getters and setters for return value
	public Boolean hasReturnValue() {
		return has_return_value;
	}

	public void setNoReturnValue() {
		has_return_value = false;
	}

	public void setReturnValue(String return_value) {
		has_return_value = true;
		this.return_value = return_value;
	}

	public String getReturnValue() {
		if (has_return_value)
			return return_value;
		return "";
	}

	public void setReturnType(String return_type) {
		has_return_value = true;
		this.return_type = return_type;
	}

	public String getReturnType() {
		if (has_return_value)
			return return_type;
		return "";
	}

	// Getters and setters for timestamp
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	// Getters and setters for arguments
	public void resetArguments() {
		arg_count = 0;
		arg_values = new ArrayList<String>();
		arg_names = new ArrayList<String>();
		arg_types = new ArrayList<String>();
	}

	public void addArgument(String arg_type, String arg_name, String arg_value) {
		arg_count++;
		arg_types.add(arg_type);
		arg_names.add(arg_name);
		arg_values.add(arg_value);
	}

	public Integer getArgumentCount() {
		return arg_count;
	}

	public String getArgumentType(Integer n) {
		return arg_types.get(n);
	}

	public String getArgumentName(Integer n) {
		return arg_names.get(n);
	}

	public String getArgumentValue(Integer n) {
		return arg_values.get(n);
	}

	public String getArgumentValueFromName(String name) {
		for (int i = 0; i < getArgumentCount(); i++)
			if (getArgumentName(i).equals(name))
				return getArgumentValue(i);
		return "";
	}

	// Getters and setters for parameters
	public void resetParameters() {
		parameter_mapping = new HashMap<String, String>();
	}

	public void setParameter(String key, String value) {
		parameter_mapping.put(key, value);
	}

	public String getParameterValue(String key) {
		return parameter_mapping.get(key);
	}

	// Output joinpoint in string format
	public String toString() {
		String s = ((getJoinpointKind() == JoinpointKind.BEFORE) ? "before" : "after") + ":\n" + "method: "
				+ getFullMethodName() + "\n" + "target: " + getTargetType() + ":" + getTargetValue() + "\n"
				+ "timestamp: " + getTimestamp() + "\n";
		for (int i = 0; i < getArgumentCount(); i++)
			s += "arg: " + getArgumentType(i) + ":" + getArgumentName(i) + ":" + getArgumentValue(i) + "\n";
		if (hasReturnValue())
			s += "return: " + getReturnType() + ":" + getReturnValue() + "\n";
		for (String key : parameter_mapping.keySet())
			s += "param: " + key + ":" + getParameterValue(key) + "\n";
		return s;
	}

	// Check whether method name matches (ignores package, class)
	public Boolean match(String method_name) {
		String toMatch = method_name.substring(method_name.lastIndexOf(".") + 1).trim();

		return getMethodName().equals(method_name);
	}

	// Check whether method name matches (include package, class)
	public Boolean matchFullName(String method_name) {
		String[] given_name_components = method_name.split("\\.");
		String[] joinpoint_name_components = (getMethodClass() + "." + getMethodName()).split("\\.");

		int i = given_name_components.length - 1;
		int j = joinpoint_name_components.length - 1;
		while (i >= 0 && j >= 0) {
			if (!(given_name_components[i].equals("*") || joinpoint_name_components[j].equals("*")
					|| given_name_components[i].equals(joinpoint_name_components[j]))) {
				return false;
			}
			i--;
			j--;
		}
		return true;
	}

	// Constructor parses string
	public Joinpoint(String s) {
		// Joinpoint kind
		if (s.contains("before:")) {
			setJoinpointKind(JoinpointKind.BEFORE);
		} else {
			setJoinpointKind(JoinpointKind.AFTER);
		}
		// Method name and type
		int index_start_method = s.indexOf("method:") + 7;
		int index_end_method = s.indexOf("\n", index_start_method);
		String method_info = s.substring(index_start_method, index_end_method);
		int index_start_method_name = method_info.lastIndexOf(".");
		setMethodClass(method_info.substring(0, index_start_method_name).trim());
		setMethodName(method_info.substring(index_start_method_name + 1).trim());
		// Target
		int index_start_target = s.indexOf("target:") + 7;
		int index_end_target = s.indexOf("\n", index_start_target);
		String[] target_info = s.substring(index_start_target, index_end_target).split(":");
		setTargetType(target_info[0].trim());
		setTargetValue(target_info[1].trim());
		// Timestamp
		int index_start_timestamp = s.indexOf("timestamp:") + 10;
		int index_end_timestamp = s.indexOf("\n", index_start_timestamp);
		setTimestamp(s.substring(index_start_timestamp, index_end_timestamp).trim());
		// Return
		int index_start_return = s.indexOf("return:") + 7;
		if (index_start_return != -1 + 7) {
			int index_end_return = s.indexOf("\n", index_start_return);
			String[] return_info = s.substring(index_start_return, index_end_return).split(":");
			setReturnType(return_info[0].trim());
			setReturnValue(return_info[1].trim());
		} else {
			setNoReturnValue();
		}
		// Arguments
		resetArguments();
		int index_start_arg = s.indexOf("arg:") + 4;
		while (index_start_arg != -1 + 4) {
			int index_end_arg = s.indexOf("\n", index_start_arg);
			String[] arg_info = s.substring(index_start_arg, index_end_arg).split(":");
			addArgument(arg_info[0].trim(), arg_info[1].trim(), arg_info[2].trim());
			index_start_arg = s.indexOf("arg:", index_end_arg) + 4;
		}
		// Parameters
		resetParameters();
		int index_start_param = s.indexOf("param:") + 6;
		while (index_start_param != -1 + 6) {
			int index_end_param = s.indexOf("\n", index_start_param);
			String[] param_info = s.substring(index_start_param, index_end_param).split(":");
			setParameter(param_info[0].trim(), param_info[1].trim());
			index_start_param = s.indexOf("param:", index_end_param) + 6;
		}
	}

	public void replay() {
		// Dummy method to capture by offline monitoring tool
		// Uncomment the following line to see replay of events
		// System.out.println(this.toString());
	}

}