package com;

import java.util.List;

public class Contact {
	private int contactID;
	private String contactName;
	private String email;
	private List<String> contactNumber;
	public int getContactID() {
		return contactID;
	}
	Contact(){}
	Contact(int c_id, String cn, String email, List<String> cnum) {
		this.contactID = c_id;
		this.contactName = cn;
		this.email = email;
		this.contactNumber = cnum;
	}
	public void setContactID(int contactID) {
		this.contactID = contactID;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(List<String> contactNumber) {
		this.contactNumber = contactNumber;
	}
}
