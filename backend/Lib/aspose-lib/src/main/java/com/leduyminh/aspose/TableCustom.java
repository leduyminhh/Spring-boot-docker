package com.leduyminh.aspose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TableCustom {
	private String						name;
	private List<String>				columns;
	private List<Map<Integer, String>>	variables;

	public TableCustom(String name) {
		this.name = name;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public List<String> getColumns () {
		return columns;
	}

	public void setColumns (List<String> columns) {
		this.columns = columns;
	}

	public void setColumns (String... columns) {
		this.columns = Arrays.asList (columns);
	}

	
	public List<Map<Integer, String>> getVariables () {
		return variables;
	}

	public void setVariables (List<Map<Integer, String>> variables) {
		this.variables = variables;
	}

	public void setVariable (Map<Integer, String>... variables) {
		this.variables = Arrays.asList (variables);
	}

	public void addVariable (Map<Integer, String> variable) {
		if (this.variables == null)
			this.variables = new ArrayList<Map<Integer, String>> ();
		
		this.variables.add (variable);
	}
}
