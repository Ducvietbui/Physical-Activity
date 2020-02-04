package PhysicalActivities;

import java.io.Serializable;

public class Date implements Serializable {

	private static final long serialVersionUID = 1L;
	  public static final String[] MONTHNAMES = { "January", "February", "March",
	  "April", "May", "June", "July", "August", "September", "October", "November",
	  "December" };
	 
	private int day;
	private int month;
	private int year;

	public Date() {
		java.time.LocalDate today = java.time.LocalDate.now();
		this.day = today.getDayOfMonth();
		this.month = today.getMonthValue();
		this.year = today.getYear();
	}

	public Date(String date) {
		String[] dateSplitted = date.split("\\.");
		int[] dateArray = new int[dateSplitted.length];
		for (int i = 0; i < dateSplitted.length; i++) {
			dateArray[i] = Integer.parseInt(dateSplitted[i]);
		}
		this.day = dateArray[0];
		this.month = dateArray[1];
		this.year = dateArray[2];
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getDay() {
		return this.day;
	}

	public int getMonth() {
		return this.month;
	}

	public int getYear() {
		return this.year;
	}

	@Override
	public String toString() {
		return (this.day + "." + this.month + "." + this.year);
	}
}

