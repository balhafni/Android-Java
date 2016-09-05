package com.example.finalproject;

public class AcademicPlans {
	String className, classTime, major;
	int credits, priority;

	public AcademicPlans(String major, String className, String classTime, int credits, int priority) {
		this.className = className;
		this.classTime = classTime;
		this.major = major;
		this.credits = credits;
		this.priority = priority;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassTime() {
		return classTime;
	}

	public void setClassTime(String classTime) {
		this.classTime = classTime;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMajor() {
		return major;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String toString() {
		return "Major: " + getMajor() + "\nClass Name: " + getClassName() + "\nClass Time: " + getClassTime()
				+ "\nClass Credits: " + getCredits() + "\nClass Priority: "+getPriority();
	}
}
