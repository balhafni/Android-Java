package com.example.finalproject;

import java.util.ArrayList;

public class Profile {
	String id, firstName, lastName, dateOfBirth, major;
	String classesTaken;
	byte[] blob;

	public Profile(String id, String firstName, String lastName, String dateOfBirth, String major, String classesTaken,
			byte[] blob) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.major = major;
		this.classesTaken = classesTaken;
		this.blob = blob.clone();
	}

	public String getId() {
		return id;
	}

	public byte[] getBlob() {
		return blob;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getClassTaken() {
		return classesTaken;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String toString() {
		return "ID" + getId() + "\nFirst name: " + getFirstName() + "\nLast name: " + getLastName() + "\nMajor: "
				+ getMajor() + "\nDate of Brith:" + getDateOfBirth() + "\nClasses Taken: " + getClassTaken();
	}
}
