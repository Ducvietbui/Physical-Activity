package PhysicalActivities;

/* PHYSICAL ACTIVITIES APPLICATION
 * Author: VIET BUI - e1700690
 * This application created GUI for users to calculate the energy they burnt through fitness workout.
 * */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;


public class PAApp extends Application {
	
	private final ComboBox<String> CmbAc = new ComboBox<>();
	private final ComboBox<String> CmbShow = new ComboBox<>();
	private final ComboBox<String> CmbDate = new ComboBox<>();
	private final ComboBox<String> CmbChart = new ComboBox<>();
	private final TextField text1 = new TextField();
	private final TextField text2 = new TextField();
	private final TextField text3 = new TextField();
	private final TextField textAc = new TextField();
	private final TextField textEnergy = new TextField();
	private final TextArea textArea = new TextArea();
	private final TextArea textArea2 = new TextArea();
	private final FileChooser fileChooser = new FileChooser();
	private final String ActivityLink ="PhysicalActivities/activity.txt";
	private Calculation fitness;
	private Diary currentdiary;
	private Diary activity_dic;
	private HashMap<String, Diary> users = new HashMap<>();
	private HashMap<String, Calculation> fit = new HashMap<>();
	private File currentfile = null;
	private TableView<String[]> table = new TableView();
	private TableView<String[]> tabledic = new TableView();
	private final String[] charts = {"Energy"};
	private int flag;
	@Override
	public void start(Stage primaryStage) throws Exception{
		
		try {
			readFile();
		} catch(IOException ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Problems with the file");
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
			return;
		}
		
		/*Create UI for the app
		 *Create three scenes
		 *Scene 1: create scene for user to insert data
		 *Scene 2 : Calculate the fitness and draw charts
		 *Scene 3: Activity Dictionary. In this scene, users could search the energy of the activity
		 * */

		/*Scene 1: create scene for user to insert data
		 *Create Border Pane
		 *Create Grid Pane
		 *Create Menu Bar
		 *Create Table View
		 * */
		
		BorderPane root = new BorderPane();
		GridPane s1grid1 = new GridPane();
		
		// Grid 1
		
		s1grid1.setHgap(10); //Horizontal space between columns
		s1grid1.setVgap(10); //Vertical space between rows
		s1grid1.setPadding(new Insets(10, 10, 10, 10));
				
		//Create and add instruction text to grid 1
		
		Text instrText1 = new Text("Give user name and date to create diary ");
		s1grid1.add(instrText1, 0, 2);
		Text instrText2 = new Text("Insert activity and time");
		s1grid1.add(instrText2, 0, 6);
		Text instrText3 = new Text("Calculate the energy and draw charts");
		s1grid1.add(instrText3, 0, 10);
		Text instrText4 = new Text("Show diary of days inserted");
		s1grid1.add(instrText4, 0, 12);
		Text instrText5 = new Text("Dictionary show the energy bunrt of activities");
		s1grid1.add(instrText5, 0, 14);
		
		//Create and add label to grid 1
		
		Label s1Label1 = new Label("Name: ");
		s1grid1.add(s1Label1, 0, 3);
		Label s1Label2 = new Label("Date (dd.mm.yyyy): ");
		s1grid1.add(s1Label2, 0, 4);
		Label s1Label3 = new Label("Activity");
		s1grid1.add(s1Label3, 0, 7);
		Label s1Label4 = new Label("Time (h)");
		s1grid1.add(s1Label4, 0, 8);
		Label s1Label5 = new Label("Show");
		s1grid1.add(s1Label5, 0, 13);
		
		//Create and add text field to grid 1
		
		s1grid1.add(text1, 1, 3);
		s1grid1.add(text2, 1, 4);
		s1grid1.add(text3, 1, 8);
		
		//Create and add button to grid 1
		
		Button createDiarybtn = new Button("Create Diary");
		s1grid1.add(createDiarybtn, 0, 5);
		Button add = new Button("Add");
		s1grid1.add(add, 0, 9);
		Button cal = new Button("Calculate");
		s1grid1.add(cal, 0, 11);
		Button dic = new Button("Dictionary");
		s1grid1.add(dic, 0, 15);
		
		//Add Combo Box to grid 1
		
		s1grid1.add(CmbAc, 1, 7);
		s1grid1.add(CmbShow, 1, 13);
		
		//Create Menu bar and File Menu
		
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		menuBar.getMenus().add(menuFile);
		MenuItem startNew = new MenuItem("New");
		MenuItem open = new MenuItem("Open");
		MenuItem save = new MenuItem("Save");
		MenuItem saveAs = new MenuItem("Save As");
		MenuItem exit = new MenuItem("Exit");
		menuFile.getItems().addAll(startNew, open, save, saveAs, new SeparatorMenuItem(),exit);
		
		//File extension
		
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Text Files (*.txt)", "*.txt"),
				new ExtensionFilter("Image Files (*.png, *.jpg, *.gif)", "*.png)", "*.jpg", "*.gif"),
				new ExtensionFilter("Audio Files (*.wav, *.mp3, *.aac)", "*.wav", "*.mp3", "*.aac"),
				new ExtensionFilter("All Files (*.*)", "*.*"));
				
		
		//Create table view
		//Create table column
		
		TableColumn<String[], String> actCol = new TableColumn<>("Activity");
		TableColumn<String[], String> amountCol = new TableColumn<>("Time");
		TableColumn<String[], String> energyCol = new TableColumn<>("Energy");
		table.getColumns().add(actCol);
		table.getColumns().add(amountCol);
		table.getColumns().add(energyCol);

		for(int i=0; i < 3 ; i++ ) {
			TableColumn<String[], String> col = (TableColumn<String[], String>) table.getColumns().get(i);
			final int colNo = i;
			col.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>(){
				public ObservableValue<String> call(CellDataFeatures<String[], String> p){
					return new SimpleStringProperty((p.getValue()[colNo]));
				}
			});
			col.setPrefWidth(140);
		}
		
		//Grid 2	
		GridPane s1grid2 = new GridPane();
		s1grid2.add(textArea, 0, 0);
		s1grid2.add(table,0,1);
		s1grid2.setHgap(5);
		s1grid2.setVgap(10);
		s1grid2.setPadding(new Insets(13,13,13,13));
		textArea.setPrefSize(5, 5);
		textArea.setWrapText(true);
		textArea.setEditable(false);
		textArea.setFont(Font.font(" Arial ", FontWeight.NORMAL, 12));
		
		//Set the the positions of each part in scene 1
		
		root.setTop(menuBar);
		root.setLeft(s1grid1);
		root.setCenter(s1grid2);	
		
		/*Scene 2: In this scene, this application would calculate value of energy
		 * */
		
		BorderPane calculation = new BorderPane();
		GridPane s2grid1 = new GridPane();
		s2grid1.setHgap(10); //Horizontal space between columns
		s2grid1.setVgap(10); //Vertical space between rows
		s2grid1.setPadding(new Insets(25, 25, 25, 25));
		
		//Setting the text area
		
		textArea2.setWrapText(true);
		textArea2.setEditable(false);
		textArea2.setFont(Font.font(" Arial ", FontWeight.NORMAL, 12));
		
		//Create button
	
		Button s2showbtn = new Button("Show");
		Button s2backbtn = new Button("Back");
		
		//Create FlowPane
		//FlowPane has boxes for date and charts for user to choose
		
		FlowPane topbar = new FlowPane();
		topbar.setPadding(new Insets(5,5,5,5));
		topbar.setHgap(10);
		topbar.getChildren().add(new Label("\t\t\t\t\t\t\tDate"));
		topbar.getChildren().add(CmbDate);
		topbar.getChildren().add(new Label("\t\t\t\t\t\t\t\t\t\t\t\t\tCharts"));
		topbar.getChildren().add(CmbChart);
		topbar.getChildren().add(s2showbtn);
		topbar.getChildren().add(s2backbtn);
		
		// Create bar chart
		// x-axis is category of activities
		// y-axis is calculated value of energy
		
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> barchart = new BarChart<>(xAxis,yAxis);
		barchart.setLegendVisible(false);
		barchart.setAnimated(false);
		xAxis.setLabel("Activity");
		yAxis.setLabel("Total (kJ)");
		
		// Set positions for each part of scene 2
		
		calculation.setCenter(s2grid1);
		calculation.setTop(topbar);
		calculation.setRight(textArea2);
		
		/*Scene 3: In this scene, this application would create the dictionary of activities
		 *  Create table-view for the dictionary
		 * */
		
		BorderPane dictionary = new BorderPane();
		
		//Create buttons
		
		Button s3backbtn = new Button("Back");
		Button s3insert = new Button("Insert");
		
		//Create FlowPane
		
		FlowPane dictBar = new FlowPane();
		dictBar.setPadding(new Insets(5,5,5,5));
		dictBar.setHgap(10);
		dictBar.getChildren().add(new Label("Activity"));
		dictBar.getChildren().add(textAc);
		dictBar.getChildren().add(new Label("Energy"));
		dictBar.getChildren().add(textEnergy);
		dictBar.getChildren().add(s3insert);
		dictBar.getChildren().add(s3backbtn);
		
		//Create table-view
		
		TableColumn<String[], String> actColdic = new TableColumn<>("Activity");
		TableColumn<String[], String> energyColdic = new TableColumn<>("Energy");
		tabledic.getColumns().add(actColdic);
		tabledic.getColumns().add(energyColdic);
		
		for(int i=0; i < 2 ; i++ ) {
			TableColumn<String[], String> col = (TableColumn<String[], String>) tabledic.getColumns().get(i);
			final int colNo = i;
			col.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>(){
				public ObservableValue<String> call(CellDataFeatures<String[], String> p){
					return new SimpleStringProperty((p.getValue()[colNo]));
				}
			});
			col.setPrefWidth(140);
		}
		
		// Set the positions for each parts of scene
		
		dictionary.setCenter(tabledic);
		dictionary.setTop(dictBar);
		
		//Disable everything. All the components would be enable when create diary button is clicked
		add.setDisable(true);
		cal.setDisable(true);
		dic.setDisable(true);
		text3.setDisable(true);
		CmbAc.setDisable(true);
		CmbShow.setDisable(true);
		
		//Create scenes
		
		Scene scene2 = new Scene(calculation,1280,600);	
		Scene scene1 = new Scene(root,1280,600);
		Scene scene3 = new Scene(dictionary,1280,600);
		primaryStage.setTitle("Activity Diary");
		primaryStage.setScene(scene1);
		primaryStage.show();
		
		/*-----------------------------EVENT HANDLER---------------------------*/
		
		/* Event handler for create button
		 * Every time create new diary, the table would be clear the old data of previous diary
		 * After creating diary, the text field of name would not be editable, so the user could not 
		 * change the name, except start a new session
		 * */
		
		createDiarybtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e){
				CmbAc.getSelectionModel().clearSelection(); // Clear the old value
				text3.clear();	// Clear the old value
				CmbShow.getSelectionModel().clearSelection(); // Clear the old value
				table.getItems().clear();		// Clear the old value of previous session
				createDiary(e);
				if(flag == 1) {
					text1.setDisable(true);
					text3.setDisable(false);
					add.setDisable(false);
					cal.setDisable(false);
					dic.setDisable(false);
					CmbAc.setDisable(false);
					CmbShow.setDisable(false);
				}
				
			}
		});
		
		/* Event handler for add button
		 * Add button would add the activity and amount to the table
		 * */
		
		add.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				addActivityDiary(e);
			}
		});
		
		/* Event handler for calculation button
		 * Calculation button would direct the scene to new scene, i.e scene 2
		 * */
		
		cal.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				CmbChart.getItems().clear();	//Clear previous selected item
				for(int i =0; i < charts.length; i++) {
					CmbChart.getItems().add(charts[i]);
				}
				primaryStage.setTitle("Energy Calculation");
				primaryStage.setScene(scene2);
				primaryStage.show();
			}
		});	
		
		/*Save option in menu bar would save the current file
		 * If the file is null, i.e user has not saved the current file,
		 * the application would open dialog to save the file
		 * If the file is not null, the data would save to the current file
		 * */
		
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(currentfile == null) {
					File file = fileChooser.showSaveDialog(primaryStage);
					if(file != null) {
						currentfile = file;
						saveDiarytoFile(currentfile);
					}else {
						saveDiarytoFile(currentfile);
					}
				}
			}
		});
		
		/* Save as option in menu bar would save the file to a new file
		 * */
		
		saveAs.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				File file = fileChooser.showSaveDialog(primaryStage);
				if(file != null){
					currentfile = file;
					saveDiarytoFile(currentfile);
				}
			}
		});
		
		/* Open option in menu bar would open the files
		 * */
		
		open.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				File file = fileChooser.showOpenDialog(primaryStage);
				if(file != null){
					openFile(file);
				}
			}
		});
		
		/*Exit option in menu bar would close the app */
		
		exit.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				Platform.exit();
			}
		});
		
		/* New option in menu bar would clear everything and open a new session
		 * */
		
		startNew.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				text1.setDisable(false);
				text1.setEditable(true);
				users.clear();
				CmbShow.getItems().clear();
				clearControl();
				table.getItems().clear();
				barchart.getData().clear();
				CmbDate.getItems().clear();
				s2grid1.getChildren().clear();
				textArea2.clear();
				add.setDisable(true);
				cal.setDisable(true);
				dic.setDisable(true);
				text3.setDisable(true);
				CmbAc.setDisable(true);
				CmbShow.setDisable(true);
			}
		});
		
		/*Combo Box Show would show the diary and the activity added to the diary
		 * */
		
		CmbShow.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				showDiary(e);
			}
		});
		
		/* Show button in scene 2 would show the chart and the total fitness values in text area
		 * Users choose the date, the program would read data of this date 
		 * and add it to bar chart
		 * x-axis would read activity category
		 * y-axis would read the energy values of each activity
		 *  */
		
		s2showbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				barchart.getData().clear();		//Clear previous value
				s2grid1.getChildren().clear();	//Clear grid
				String choosenChart;
				String date = CmbDate.getValue();
				currentdiary = users.get(date);
				textArea2.setText(currentdiary.TotalValue());
				choosenChart = CmbChart.getValue();
					if(choosenChart.equals("Energy")) {
						barchart.setTitle("Energy");
						s2grid1.add(barchart, 0, 1, 4, 1);
						final String[] activityList = ((Diary)currentdiary).getActivityList();
						final double[] energyValue = ((Diary)currentdiary).getEnergyValue();
						XYChart.Series<String, Number> series1 = new Series<>();
						for(int i = 0; i < activityList.length; i++) {
							series1.getData().add(new Data<>(activityList[i],energyValue[i]));	
						}
						barchart.getData().add(series1);					
				} 
			}
		});
		
		/* Back button in scene 2 would change the scene back to scene 1
		 * */
		
		s2backbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				primaryStage.setScene(scene1);
				primaryStage.show();
			}
		});	
		
		/* Back button in scene 3 would change the scene back to scene 1
		 * */
		
		s3backbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				primaryStage.setScene(scene1);
				primaryStage.show();
			}
		});
		
		/* Dictionary button in scene 1 would show the scene 3, i.e dictionary scene
		 * */
		
		dic.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				tabledic.getItems().clear();
				showDictionary();
				primaryStage.setTitle("Activity Dictionary");
				primaryStage.setScene(scene3);
				primaryStage.show();
			}
		});
		
		/* Insert button in scene 3 would insert the activity and energy values of this activity
		 * to the dictionary
		 * */
		
		s3insert.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				insertactivity(); 
				tabledic.getItems().clear();	//Clear previous value of table
				showDictionary();
			}
		});		
	}
	
	/*	Read from file
	 *  Create URL for file data which stores data for the activity
	 *  Initialize the object activity_dic to stores data which are read from file data
	 *  activity_dic is used later to add data to the table in dictionary scene
	 * */
	
	public void readFile() throws IOException {
		ClassLoader cl = this.getClass().getClassLoader();
		URL url = cl.getResource(ActivityLink);
		try (InputStream in = url.openStream(); BufferedReader input = new BufferedReader(new InputStreamReader(in))){
			activity_dic = new Diary("", new Date());
			String line;
			double energy;
			while((line = input.readLine()) != null){
				energy = Double.parseDouble(input.readLine());
				fitness = new Calculation(energy);
				fit.put(line, fitness);
				activity_dic.addActivity(new Calculation(line, fitness));
			}
				  List<String> names = fit.keySet().stream().sorted().collect(Collectors.toList());
				  CmbAc.setItems(FXCollections.observableArrayList(names));
		}
	}
	
	/* Create diary
	 * Check the date. If the date is already exist, warning the date is already exist
	 * If the date is not exist, create new one
	 * */
	
	private void createDiary (ActionEvent e){
		try {
			String user = text1.getText();
			String date = text2.getText();
			int count = date.length() - date.replace(".", "").length();
			if(!checkDay(date) && count == 2 && checkdate(date)) {
				currentdiary = new Diary(user, new Date(date));
				textArea.setText(currentdiary.toString());
				users.put(date, currentdiary);
				CmbDate.getItems().add(date);
				CmbShow.getItems().add(date);
				flag = 1;
			}else {
				Alert alertday = new Alert(AlertType.WARNING,"This day is already exist or this day is not the right format !");
				alertday.showAndWait();
				flag = 0;
			}
			
		}catch(NumberFormatException ex) {
			Alert alert = new Alert(AlertType.ERROR, "Check your input please !");
			alert.showAndWait();
		}
	}
		
	/* Add activity to diary
	 * Check the activity . If the activity is already in the table, add amount to the activity
	 * If the activity is not in the table, add it to the table
	 * */
	
	private void addActivityDiary(ActionEvent e){
		try {
			String addactivity = CmbAc.getValue();
			if(!checkactivity(addactivity) && CmbAc.getSelectionModel().getSelectedItem() != null) {
					double amount = Double.parseDouble(text3.getText());
					currentdiary.addActivity(new Calculation(addactivity,fit.get(addactivity),amount));
		
			} else {
				double amount = Double.parseDouble(text3.getText());
				currentdiary.addAmount(addactivity, amount);
			}
			table.getItems().clear();
			String[][] activityarray = currentdiary.ActivityArray();
			table.getItems().addAll(Arrays.asList(activityarray));
		} catch(NumberFormatException ex) {
			Alert alert1 = new Alert(AlertType.ERROR, " Please check your input");
			alert1.showAndWait();
		}
	}
	
	/* Show the diary
	 * Users choose the date from diary, then the program would show the data inserted in this date
	 * */
	
	private void showDiary(ActionEvent e) {
		if(!CmbShow.getSelectionModel().isEmpty()) {
			String date = CmbShow.getValue();
			currentdiary = users.get(date);
			table.getItems().clear();
			String[][] show = currentdiary.ActivityArray();
			table.getItems().addAll(Arrays.asList(show));
			textArea.setText(currentdiary.toString());
		}
	}
	
	/* Save file
	 * */
	
	private void saveDiarytoFile(File file){
		System.out.println(file.getAbsolutePath());
		try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file))){
			output.writeObject(users);
		}catch (IOException ex){
			System.out.println("Error" + ex.getMessage());
		}
	}
	
	//Open file
	
	@SuppressWarnings("unchecked")
	private void openFile(File file){
		try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))){
			users = (HashMap<String, Diary>) input.readObject();
			CmbShow.getItems().clear();
			CmbDate.getItems().clear();
			for(String key: users.keySet()) {
				CmbShow.getItems().add(key);
				CmbDate.getItems().add(key);
			}
			clearControl();
		}catch(IOException | ClassNotFoundException e) {
			System.out.println("Error" + e.getMessage());
		}
	}
	
	/* Show the dictionary in scene 3 */
	
	private void showDictionary() {
		String[][] activity_dictionary = activity_dic.activity_dic();
		tabledic.getItems().addAll(Arrays.asList(activity_dictionary));
	}
	
	/* Insert activity to the dictionary
	 * Firstly, check the activity, if the activity is already in the dictionary, warning the activity is exist
	 * If the activity is not in the dictionary, add activity to the dictionary
	 * */
	
	private void insertactivity() {
		try {	
			if(!checkdictionary()) {
				String acinserted = textAc.getText();
				double energyinserted = Double.parseDouble(textEnergy.getText());
				fitness = new Calculation(energyinserted);
				fit.put(acinserted, fitness);
				activity_dic.addActivity(new Calculation(acinserted,fitness));
				CmbAc.getItems().add(acinserted);
			}else {
				Alert alert3 = new Alert(AlertType.WARNING, "The activity is exist");
				alert3.showAndWait();
			}
		}catch(Exception ex) {
			Alert alert2 = new Alert(AlertType.ERROR, "Error: " + ex.getMessage());
			alert2.showAndWait();
		}
	}
	
	/* Check the activity in scene 3
	 * If the activity is already exist, return true
	 * If the activity is not exist, return false
	 * */
	
	public boolean checkdictionary() {
		String[] checkactivity = activity_dic.getActivityList();
		String check = textAc.getText();
		for(int i = 0; i < checkactivity.length; i++) {
			if(check.equalsIgnoreCase(checkactivity[i]))
				return true;
		}
		return false;
	}
	
	/* Check the day in scene 1:
	 * If the day is exist, return true
	 * If the day is not exist, return false
	 * */
	
	public boolean checkDay(String day) {
		for(String key: users.keySet()) {
			if(key.equalsIgnoreCase(day))
				return true;
		}
		return false;
	}
	
	/* Check the activity in table in scene 1
	 * If the activity is exist, return true
	 * If the activity is not exist, return false
	 * */
	
	public boolean checkactivity(String activity) {
		String[] checkactivitylist = currentdiary.getActivityList();
		for(int i = 0; i < checkactivitylist.length; i++) {
			if(activity.equalsIgnoreCase(checkactivitylist[i]))
				return true;
		}
		return false;
	}
	
	/* Check the date
	 * If the date is the right format, return true
	 * else return false
	 * */
	
	public boolean checkdate(String date) {
		String[] dateSplitted = date.split("\\.");
		int[] dateArray = new int[dateSplitted.length];
		for (int i = 0; i < dateSplitted.length; i++) {
			dateArray[i] = Integer.parseInt(dateSplitted[i]);
		}
		int day = dateArray[0];
		int month = dateArray[1];
		int year = dateArray[2];
		if(day >= 0 && day <= 31 && month >= 0 && month <= 12 && year > 0) {
			return true;
		}else
			return false;
	}
	
	// Clear
	private void clearControl(){
		text1.setText(null);
		text2.setText(null);
		text3.setText(null);
		textArea.setText(null);
		CmbShow.getSelectionModel().clearSelection();
		CmbAc.getSelectionModel().clearSelection();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
