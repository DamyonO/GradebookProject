/*
 * Assignment: Gradebook Project
 * Name: Damyon Olson
 */
package gradebook;

import java.time.format.DateTimeParseException;
import java.util.*;
import java.io.*;
import java.sql.SQLException;
import java.time.*;

import brain.*;

/*
 * The Gradebook class holds main, as well as a few helper functions to enure the program
 * runs smoothly and as intended.
 */
public class Gradebook {
	public static void main(String args[]) {
			Scanner sc = new Scanner(System.in);
		
			System.out.println("Welcome to your Gradebook!\n");
			// calls the getInt(min, max) helper function //
		
			ArrayList<AssignmentInterface> openGradebook = new ArrayList<>(); // Interface Array Created //
			String action;
		
			displayMenu(); // calls the displayMenu() helper function //
			/*
			 * This while(true) loop is where all the magic happens.
			 * The loop will only exit when the user type in 'exit'
			 * or 'quit' **'quit' was added for convenience**
			 */
			while(true) {
				// Try {}catch(){} block to call custom exceptions //
				try {
				System.out.print("Enter a Command : ");
				action = sc.nextLine(); // takes user input to determine which menu option is ran //
				
				/*
				 * The following chain of if & if else statements will read and confirm that
				 * user input matches the set commands. If the user input does not match any commands
				 * a final else statement is called to inform the user that the command tyed in
				 * is invalid.
				 */
				if (action.equalsIgnoreCase("menu")) {
					displayMenu(); // Displays menu for user //
				
				} else if (action.equalsIgnoreCase("add")) {
					
					/*
					 * The following will add a new grade to the gradebook,
					 * update the overall count for how many items are inside of the gradebook,
					 * and display the amount of items within the gradebook.
					 */
					openGradebook =  add(openGradebook);
					System.out.println("Gradebook Size is Now: " + openGradebook.size());

				} else if (action.equalsIgnoreCase("del")) {
					
					/*
					 * The following will delete a grade from the gradebook,
					 * update the overall count for how many items are inside of the gradebook,
					 * and display the amount of items within the gradebook.
					 */
					openGradebook = del(openGradebook);
					System.out.println("Gradebook Size is Now: " +openGradebook.size());
				
				} else if (action.equalsIgnoreCase("print")) {
					if (openGradebook.size() == 0) {
						throw new GradebookEmptyException("\n\t"+"The Gradebook Is Empty");
					}
					printChoiceMenu();
					int menuOption = getInt(1, 4);
					print(openGradebook, menuOption); // Prints Every Item In Gradebook //
				
				
				} else if (action.equalsIgnoreCase("fprint")) {
					if (openGradebook.size() == 0) {
						throw new GradebookEmptyException("\n\t"+"The Gradebook Is Empty");
					}
				
					System.out.println("Please enter the name of the file you'd like to write to : ");
					System.out.println("WARNING: If the name entered already exists as a file, it will be overwritten");
					String fileName = sc.nextLine();
					writeToFile(openGradebook, fileName);
				
				
				} else if (action.equalsIgnoreCase("fread")) {
				
					System.out.println("Please enter the name of the file you'd like to read from : ");
					String fileName = sc.nextLine();
					openGradebook = readFromFile(openGradebook, fileName);
					System.out.println("Gradebook Size is Now: " + openGradebook.size());
				
				
				} else if (action.equalsIgnoreCase("tosql")) {
				
					DBUtil.toMySQL(openGradebook);
				
				
				} else if (action.equalsIgnoreCase("fromsql")) {
				
					openGradebook = DBUtil.getMySQL(openGradebook);
					System.out.println("Gradebook Size is Now: " + openGradebook.size());
				
				
				} else if (action.equalsIgnoreCase("search")) {
					
					printSearchMenu();
					int choice = getInt(1,6);
					openGradebook = DBUtil.searchMySQL(openGradebook, choice);
				
				
				} else if (action.equalsIgnoreCase("exit") || action.equalsIgnoreCase("quit")) {
					exit(); // Exits the Program //
					
				} else {
					// If There is an Unknown Command, The Program Prompts The User to Reenter //
					System.out.println("\tInvalid Command! Type In 'menu' If You Would Like To See The Menu\n");
				}
				} catch (Exception e) {
					// This Catches any Errors Thrown From Helper Functions //
					System.out.println("An Error Has Occured :" +e);
				}
			}
	}
	
	/*
	 * The displayMenu helper function prints out an organized menu so that
	 * the user knows which commands are available to enter.
	 */
	public static void displayMenu() {
		System.out.println("             COMMAND MENU");
		System.out.println("------------------------------------------");
		System.out.println("menu     - Displays This Menu");
		System.out.println("add      - Adds Grade To Gradebook");
		System.out.println("del      - Delets Grade From Gradebook");
		System.out.println("print    - Prints Grades In Gradebook");
		System.out.println("fprint   - Prints Gradebook To A File");
		System.out.println("fread    - Reads Gradebook From A File");
		System.out.println("tosql    - Adds All Grades To MySQL Table");
		System.out.println("fromsql  - Reads Grades From MySQL And Adds Them To List");
		System.out.println("search   - Searches MySQL Database");
		System.out.println("exit     - Exits This Program\n");
	}
	
	
	/*
	 * The add helper function returns an AssignmentInterface array so that the
	 * Interface object inside of main is updated properly. The add function allows
	 * the user to choose which type of assignment they want to add to the gradebook,
	 * as well as fill in all of the necessary prompts such as name, date, score, etc. 
	 */
	public static ArrayList<AssignmentInterface> add(ArrayList<AssignmentInterface> gb) throws GradebookFullException {
		// Variables Created //
		Scanner sc = new Scanner(System.in);
		String action;
		boolean validDate = false;
		int score;
		String name;
		String date;
		int numQ;
		String concept;
		String reading;
		
		// Throws an exception if the gradebook is full //
		
		// User is asked which type of assignment they would like to add //
		System.out.println("What type of grade would you like to add?");
		System.out.println("Choices: quiz, discussion, program");
		System.out.print("Choice : ");
		action = sc.nextLine();
		
		// If a choice entered is invalid, the user is prompted to reenter //
		while (!action.equalsIgnoreCase("quiz")
				&& !action.equalsIgnoreCase("discussion")
				&& !action.equalsIgnoreCase("program")) {
			System.out.println("Invalid Choice! Please pick : quiz, discussion, or program");
			System.out.println("Choice : ");
			action = sc.nextLine();
		}
		
		/*
		 * The following creates a new Quiz where the user can
		 * fill out name, date, etc. At the end of this if block, the new Quiz is
		 * insterted into the Array.
		 */
		if (action.equalsIgnoreCase("quiz")) {
			
			Quiz newQuiz = new Quiz();
			System.out.print("What Was The Grade? : ");
			score = getInt(0, 100); // getInt(min, max) used here to make sure the grade stays within limits //
			// Score is used for both setScore and setLetter (setLetter Determines Letter Grade W/I Class Files) //
			newQuiz.setScore(score); 
			newQuiz.setLetter(score);
			
			System.out.print("What Was The Name? : ");
			name = sc.nextLine();
			newQuiz.setName(name);
			
			/*
			 * To insure a proper date format is inserted, a try{}catch(){} is
			 * implemented within the while loop. If a date is formatted wrong, or
			 * doesn't exist (ex. Feb 29th on a non-leap year, or Feb 30th any year)
			 * It will prompt the user with an error, and ask that a correctly formated
			 * date is entered.
			 */
			System.out.print("What Was The Due Date (yyyy-mm-dd)? : ");
			while (validDate == false) {
				date = sc.nextLine();
				try {
					newQuiz.setDate(date);
					validDate = true;
				} catch (DateTimeParseException e) {
					System.out.println("\tERROR: Date Entered is Invalid. Please Try Again (yyyy-mm-dd) : ");
				}
			}
			
			// setNumQuestions(); is class specific to Quiz //
			System.out.print("How Many Questions? : ");
			numQ = getInt(1); // getInt(min) is used here to insure a 0 or negative question quiz is inputed //
			newQuiz.setNumQuestions(numQ);
			
			gb.add(newQuiz); // Insert Object to Array //
			
		/*
		 * The following creates a new Discussion where the user can
		 * fill out name, date, etc. At the end of this if block, the new Discussion is
		 * insterted into the Array.
		 */	
		} else if (action.equalsIgnoreCase("discussion")) {
			Discussion newDiscuss = new Discussion();
			System.out.print("What Was The Grade? : ");
			score = getInt(0, 100); // getInt(min, max) used here to make sure the grade stays within limits //
			// Score is used for both setScore and setLetter (setLetter Determines Letter Grade W/I Class Files) //
			newDiscuss.setScore(score);
			newDiscuss.setLetter(score);
			
			System.out.print("What Was The Name? : ");
			name = sc.nextLine();
			newDiscuss.setName(name);
			
			/*
			 * To insure a proper date format is inserted, a try{}catch(){} is
			 * implemented within the while loop. If a date is formatted wrong, or
			 * doesn't exist (ex. Feb 29th on a non-leap year, or Feb 30th any year)
			 * It will prompt the user with an error, and ask that a correctly formated
			 * date is entered.
			 */
			System.out.print("What Was The Due Date (yyyy-mm-dd)? : ");
			while (validDate == false) {
				date = sc.nextLine();
				try {
					newDiscuss.setDate(date);
					validDate = true;
				} catch (DateTimeParseException e) {
					System.out.println("Date Entered is Invalid. Please Try Again (yyyy-mm-dd) : ");
				}
			}
			
			// setAssociatedReading(); is class specific to Discussion //
			System.out.print("What was the reading for this discussion? : ");
			reading = sc.nextLine();
			newDiscuss.setAssociatedReading(reading);
			
			gb.add(newDiscuss); // Insert Object to Array //
			
		/*
		 * The following creates a new Program where the user can
		 * fill out name, date, etc. At the end of this if block, the new Program is
		 * insterted into the Array.
		 */
		} else if (action.equalsIgnoreCase("program")) {
			Program newProgram = new Program();
			System.out.print("What Was The Grade? : ");
			score = getInt(0, 100); // getInt(min, max) used here to make sure the grade stays within limits //
			// Score is used for both setScore and setLetter (setLetter Determines Letter Grade W/I Class Files) //
			newProgram.setScore(score);
			newProgram.setLetter(score);
			
			System.out.print("What Was The Name? : ");
			name = sc.nextLine();
			newProgram.setName(name);
			
			/*
			 * To insure a proper date format is inserted, a try{}catch(){} is
			 * implemented within the while loop. If a date is formatted wrong, or
			 * doesn't exist (ex. Feb 29th on a non-leap year, or Feb 30th any year)
			 * It will prompt the user with an error, and ask that a correctly formated
			 * date is entered.
			 */
			System.out.print("What Was The Due Date (yyyy-mm-dd)? : ");
			while (validDate == false) {
				date = sc.nextLine();
				try {
					newProgram.setDate(date);
					validDate = true;
				} catch (DateTimeParseException e) {
					System.out.println("Date Entered is Invalid. Please Try Again (yyyy-mm-dd) : ");
				}
			}
			// setConcept(); is class specific to Program //
			System.out.print("What is the concept of this program? : ");
			concept = sc.nextLine();
			newProgram.setConcept(concept);
			
			gb.add(newProgram); // Insert Object to Array //
		}
		
		// Notifies the User on Which Object Was Successfully Added Using toString(), then returns the Array //
		System.out.print("Successfully Added : ");
		String test = gb.get(gb.size() - 1).toString();
		System.out.println(test);
		return gb;
	}
	
	/*
	 * findName is a helper function used by the delete class to check and see
	 * if the grade that the user wants deleted can be found within the array.
	 */
	public static boolean findName(ArrayList<AssignmentInterface> gb, String name) {
		for(int i = 0; i < gb.size(); i++) {
			if(name.equalsIgnoreCase(gb.get(i).getName())) {
				return true;
			}
		}
		return false;
		
	}
	
	/*
	 * del is a helper function that returns an AssignmentInterface array so 
	 * that the gradebook inside of main can be properly updated. 
	 * The function will first search the array for the name that the user wishes
	 * to remove. If it is unable to find the name, a InvalidGradeException will be thrown.
	 * If the gradebook is empty, a GradebookEmptyException will be thrown. 
	 */
	public static ArrayList<AssignmentInterface> del(ArrayList<AssignmentInterface> gb) throws GradebookEmptyException, InvalidGradeException {
		String nameDel;
		boolean wasNameFound = false;
		Scanner sc = new Scanner(System.in);
		// If the array is empty, GradebookEmptyException is thrown //
		if(gb.size() == 0) {
			throw new GradebookEmptyException("\n\t"+"The Gradebook Is Empty");
		}
		
		// Asks the user for the name of the grade to be deleted //
		System.out.println("Please Enter The Name of the Grade To Be Deleted : ");
		nameDel = sc.nextLine();
		// Check for the name using findName helper function //
		wasNameFound = findName(gb, nameDel);
		// If the name isn't found, throw InvalidGradeException
		if(!wasNameFound) {
			throw new InvalidGradeException("\n\t"+"The Grade Entered Could Not Be Found");
		}
		
		// If the name is found, remove it from the array by using a temp array //
		for(int i = 0; i < gb.size(); i++) {
			if(gb.get(i).getName().equalsIgnoreCase(nameDel)) {
				gb.remove(i);
				break;
			}
		}
		return gb; // Return tmp array to update the gradebook in main //
		
	}
	
	/*
	 * The following will print the entire arraybook for the user.
	 * If the gradebook is empty, a GradebookEmptyException will be thrown.
	 */
	public static void printChoiceMenu() {
		System.out.println("\nHow would you like the list sorted?");
		System.out.println("\t1. By Score (Numeric)");
		System.out.println("\t2. By Letter Grade");
		System.out.println("\t3. By Name (Alphabetically");
		System.out.println("\t4. By Due Date\n");
		System.out.print("Please enter a menu option (1 - 4) : ");
	}
	public static void print(ArrayList<AssignmentInterface> gb, int menuOption) throws GradebookEmptyException {
		switch (menuOption) {
			case 1:
				Collections.sort(gb, new Comparator<AssignmentInterface>() {
					public int compare(AssignmentInterface g1, AssignmentInterface g2) {
						return Integer.valueOf(g1.getScore()).compareTo(g2.getScore());
					}
				});
				for (int i = 0; i < gb.size(); i++) {
					System.out.println(gb.get(i).toString());
				}
				break;
			case 2:
				Collections.sort(gb, new Comparator<AssignmentInterface>() {
					public int compare(AssignmentInterface g1, AssignmentInterface g2) {
						Character c1 = g1.getLetter();
						Character c2 = g2.getLetter();
						return c1.compareTo(c2);
					}
				});
				for (int i = 0; i < gb.size(); i++) {
					System.out.println(gb.get(i).toString());
				}
				break;
			case 3:
				Collections.sort(gb, new Comparator<AssignmentInterface>() {
					public int compare(AssignmentInterface g1, AssignmentInterface g2) {
						return String.valueOf(g1.getName()).compareTo(g2.getName());
					}
				});
				for (int i = 0; i < gb.size(); i++) {
					System.out.println(gb.get(i).toString());
				}
				break;
				
			case 4:
				Collections.sort(gb, new Comparator<AssignmentInterface>() {
					public int compare(AssignmentInterface g1, AssignmentInterface g2) {
						return g1.getDate().compareTo(g2.getDate());
					}
				});
				for (int i = 0; i < gb.size(); i++) {
					System.out.println(gb.get(i).toString());
				}
				break;
			default:
				System.out.println("\tSomething Went Wrong!");
		}
	}

	
	public static void writeToFile(ArrayList<AssignmentInterface> gb, String fileName) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\GradeTextFiles\\" + fileName + ".txt"));
			for(int i = 0; i < gb.size(); i++) {
				if(gb.get(i) instanceof Quiz) {
					bw.write("Quiz\t" + gb.get(i).getName() + "\t" + gb.get(i).getScore() + "\t" + 
							 gb.get(i).getLetter() + "\t" + gb.get(i).getDate() + "\t" + ((Quiz) gb.get(i)).getNumQuestions() + "\n");
				}
				if(gb.get(i) instanceof Program) {
					bw.write("Program\t" + gb.get(i).getName() + "\t" + gb.get(i).getScore() + "\t" + 
							 gb.get(i).getLetter() + "\t" + gb.get(i).getDate() + "\t" + ((Program) gb.get(i)).getConcept() + "\n");
				}
				if(gb.get(i) instanceof Discussion) {
					bw.write("Discussion\t" + gb.get(i).getName() + "\t" + gb.get(i).getScore() + "\t" + 
							 gb.get(i).getLetter() + "\t" + gb.get(i).getDate() + "\t" + ((Discussion) gb.get(i)).getAssociatedReading() + "\n");	
				}
			}
			bw.close();
			
		} catch(IOException e) {
			System.out.println(e);
			return;
		}
	}
	
	public static ArrayList<AssignmentInterface> readFromFile(ArrayList<AssignmentInterface> gb, String fileName){
		String currentLine;
		String data[];
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\GradeTextFiles\\" + fileName + ".txt"));
			while((currentLine = br.readLine()) != null) {
				data = currentLine.split("\t");
				System.out.println(data[0]);
				if(data[0].equalsIgnoreCase("quiz")) {
					Quiz addQuiz = new Quiz();
					
					addQuiz.setName(data[1]);
					addQuiz.setScore(Integer.parseInt(data[2]));
					addQuiz.setLetter(addQuiz.getScore());
					addQuiz.setDate(data[4]);
					addQuiz.setNumQuestions(Integer.parseInt(data[5]));
					
					gb.add(addQuiz);
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		return gb;
	}
	
	public static void printSearchMenu() {
		System.out.println("Please Chose From One Of The Following Searches : ");
		System.out.println("1. All Quizzes");
		System.out.println("2. All Programs");
		System.out.println("3. All Discussions");
		System.out.println("4. All Grades Within A Certain Score Range");
		System.out.println("5. All Grades Within A Certain Due Date Range");
		System.out.println("6. All Grades With An Even Score");
	}
	
	/*
	 * exit the program
	 */
	public static void exit() throws SQLException {
		DBUtil.closeConnection();
		System.out.println("Bye!");
		System.exit(0);
	}
	
	/*
	 * The following are three separate getInt helper functions to ensure
	 * All ints are inserted correctly.
	 */
	public static int getInt() {
		boolean valid = false;
		int i = 0;
		Scanner sc = new Scanner(System.in);
		
		while (valid == false) {
			try {
				i = Integer.parseInt(sc.nextLine());
				valid = true;
			} catch (NumberFormatException e) {
				System.out.println("Error! Invalid Integer Value. Please Try Again : ");
			}
		}
		return i;
	}
	
	public static int getInt(int min, int max) {
		int i = 0;
		boolean valid = false;
		while(valid == false) {
			i = getInt();
			if (i < min) {
				System.out.println("Number must be greater than or equal to " + min);
			} else if (i > max) {
				System.out.println("Number must be less than or equal to " + max);
			} else {
				valid = true;
			}
		}
		return i;
	}
	
	public static int getInt(int min) {
		int i = 0;
		boolean valid = false;
		while (valid == false) {
			i = getInt();
			if (i < min) {
				System.out.println("Number must be greater than " + min);
			} else {
				valid = true;
			}
		}
		return i;
	}
	
}
