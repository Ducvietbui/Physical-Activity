package PhysicalActivities;

/* This class contains:
 *  Constructors
 *  Methods to calculate the total energy
 *  Methods to add amount of energy to the activities if the activity is already exist in the table
 *  Methods return the energy in two dimensional array 
 *  which is used for Table View when data is just numeric values
 * */

import java.io.Serializable;
import java.util.ArrayList;

public class Diary implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date date = new Date();
	private String username;
	private ArrayList<Calculation> fitness = new ArrayList<>();
	
	public Diary(String username, Date date){
		this.username = username;
		this.fitness = new ArrayList<>();
		this.date = date;
	}
	
	public void addActivity(Calculation fitness){
		this.fitness.add(fitness);
	}
	
	public Date getDate(){
		return date;
	}
	
	public String getUsername(){
		return username;
	}
	
	
	public void setDate(Date date){
		this.date = date;
	}
	
	public void setUsername(String user){
		this.username = user;
	}
	
	public double totalEnergy(){
		double totalEnergy =0;
		for(Calculation n: this.fitness) totalEnergy += n.calEnergy();
		return totalEnergy;
	}
	
	//Return the array of energy value use for bar chart 
	
	public double[] getEnergyValue() {
		final double[] energyValue = new double[this.fitness.size()];
		int i =0;
		for(Calculation n: this.fitness) {
			energyValue[i] = n.calEnergy();
			i++;
		}
		return energyValue;
	}

	//Return the array of activity for bar chart 
	
	public String[] getActivityList() {
		 String[] activityList = new String[this.fitness.size()];
		 int i = 0;
		for(Calculation n: this.fitness) {
			activityList[i] = n.getActivity();
			i++;
		}
		return activityList;
	}
	
	// Add amount of energy of activity which is already exist in the table
	
	public void addAmount(String activity, double amount) {
		double totalamount = 0;
		for(int i = 0; i < this.fitness.size(); i++) {
			if(activity.equalsIgnoreCase(this.fitness.get(i).getActivity())) {
				totalamount = this.fitness.get(i).getAmount() + amount;
				this.fitness.get(i).setAmount(totalamount);
			}
		}
	}
	
	// Return two-dimensional array used for table view
	
	public String[][] ActivityArray() {
		int i = 0;
		String[][] activityarray = new String[this.fitness.size()][3];
		for (Calculation n: this.fitness) {
			activityarray[i][0] = n.getActivity();
			activityarray[i][1] = String.format("%.2f h", n.getAmount());
			activityarray[i][2] = String.format("%.2f kJ", n.getEnergy());
			i++;
		}
		return activityarray;
	}
	
	// Return two-dimensional array used for table view in dictionary scene
	
	public String[][] activity_dic(){
		int i = 0;
		String[][] activity_dic = new String[fitness.size()][2];
		for(Calculation n: this.fitness) {
			activity_dic[i][0] = n.getActivity();
			activity_dic[i][1] = String.format("%.2f kJ", n.getEnergy());
			i++;
		}
		return activity_dic;
	}
	
	public String TotalValue() {
		return "Name: " + this.username + "\t\tDate: " + this.date +"\n"
				+"Total Energy: " + totalEnergy() +" kJ";
	}
	
	@Override
	public String toString() {
		return "Name: " + this.username + "\t\tDate: " + this.date;
	}
}
