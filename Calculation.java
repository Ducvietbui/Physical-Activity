package PhysicalActivities;

/*This class has constructors for the energy of the activity
 * This class also has methods to calculate the energy,
 * of each activity with the amount of activity
 * */

import java.io.Serializable;

public class Calculation implements Serializable {
	private static final long serialVersionUID = 1L;
	private String activity;
	private double energy;
	private double amount;
	
	public Calculation(String activity, double energy) {
		this.activity = activity;
		this.energy = energy;
	}

	public Calculation(double energy) {
		this.energy = energy;
	}
	
	public Calculation(String activity, Calculation fit, double amount) {
		this.activity = activity;
		this.energy = fit.getEnergy();
		this.amount = amount;
	}
	
	public Calculation(double energy, double amount) {
		this.energy = energy;
		this.amount = amount;
	}
	
	public Calculation(String activity, Calculation fit) {
		this.activity = activity;
		this.energy = fit.getEnergy();
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getActivity() {
		return activity;
	}
	
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	public double getEnergy(){
		return energy;
	}
	
	public void setEnergy(double energy){
		this.energy = energy;
	}
	
	public double calEnergy(){
		return energy*amount;
	}
		
	public String toString() {
		return "Energy: " + energy + "\t\t";
	}
	
}

