package com.example.empsdatabase;

public class Employee {
	private String fName, lName, startDate, favGame1, favGame2, favFood, favColor, gender;

	public Employee() {

	}

	public Employee(String fname, String lname, String date, String favFood, String favGame1, String favGame2,
			String favColor, String gender) {
		super();
		this.fName = fname;
		this.lName = lname;
		this.startDate = date;
		this.favGame1 = favGame1;
		this.favGame2 = favGame2;
		this.favFood = favFood;
		this.favColor = favColor;
		this.gender = gender;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getFavGame1() {
		return favGame1;
	}

	public void setFavGame1(String favGame1) {
		this.favGame1 = favGame1;
	}

	public String getFavGame2() {
		return favGame2;
	}

	public void setFavGame2(String favGame2) {
		this.favGame2 = favGame2;
	}

	public String getFavFood() {
		return favFood;
	}

	public void setFavFood(String favFood) {
		this.favFood = favFood;
	}

	public String getFavColor() {
		return favColor;
	}

	public void setFavColor(String favColor) {
		this.favColor = favColor;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "First Name: " + getfName() + "\n" + "Last Name: " + getlName() + "\n" + "Start Date: " + getStartDate()
				+ "\n" + "Fav Food: " + getFavFood() + "\n" + "Fav Game1: " + getFavGame1() + "\n" + "Fav Game2: "
				+ getFavGame2() + "\n" + "Fav Color: " + getFavColor() + "\n" + "Gender: " + getGender() + "\n";
	}
}
