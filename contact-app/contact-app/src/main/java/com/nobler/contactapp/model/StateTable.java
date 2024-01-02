package com.nobler.contactapp.model;

import org.apache.commons.lang3.StringUtils;

public class StateTable {
	private long stateID;
	
	private String stateAbbreviation;
	
	private String stateName;
	
	public StateTable()
	{
		this.setStateID(0);
		this.setStateAbbreviation(StringUtils.EMPTY);
		this.setStateName(StringUtils.EMPTY);
	}

	public long getStateID() {
		return stateID;
	}

	public void setStateID(long stateID) {
		this.stateID = stateID;
	}

	public String getStateAbbreviation() {
		return stateAbbreviation;
	}

	public void setStateAbbreviation(String stateAbbreviation) {
		this.stateAbbreviation = stateAbbreviation;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
