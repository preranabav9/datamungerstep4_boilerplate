package com.stackroute.datamunger.query;

public class Header {
String[] header;
public Header(String[] header)
{
	super();
	this.header=header;
	
}
public String[] getHeader()
{
	return header;
}
public void setHeader(String[] header) {
	this.header=header;
}
	/*
	 * This class should contain a member variable which is a String array, to hold
	 * the headers and should override toString() method as well.
	 */
	public String[] getHeaders() {
		return header;
	}

}
