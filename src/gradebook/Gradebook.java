/*
 * Assignment: Gradebook Project
 * Name: Damyon Olson
 */
package gradebook;

import java.time.format.DateTimeParseException;
import java.util.*;

import brain.*;

/*
 * The Gradebook class holds main, as well as a few helper functions to enure the program
 * runs smoothly and as intended.
 */
public class Gradebook {
	public static void main(String args[]) {
			int numGrades = 0;
			Scanner sc = new Scanner(System.in);
		
			System.out.println("Welcome to your Gradebook!");
			System.out.println("Please enter how many grades you would like to insert.");
			System.out.println("Number must be a positive integer from 1 to 20");
			System.out.print("Number of grades : ");
		
			numGrades = getInt(1,20); // calls the getInt(min, max) helper function //
		
			AssignmentInterface[] openGradebook = new AssignmentInterface[numGrades]; // Interface Array Created //
			String action;
			int count = 0;
		
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
					openGradebook =  add(openGradebook, count, numGrades);
					count = updateCount(openGradebook);
					System.out.println("Gradebook Size is Now: " + count + "/" + numGrades + "\n");

				} else if (action.equalsIgnoreCase("del")) {
					
					/*
					 * The following will delete a grade from the gradebook,
					 * update the overall count for how many items are inside of the gradebook,
					 * and display the amount of items within the gradebook.
					 */
					openGradebook = del(openGradebook, count, numGrades);
					count = updateCount(openGradebook);
					System.out.println("Gradebook Size is Now: " + count + "/" + numGrades + "\n");
				
				} else if (action.equalsIgnoreCase("print")) {

					print(openGradebook, count); // Prints Every Item In Gradebook //
				
				
				} else if (action.equalsIgnoreCase("average")) {
				
					average(openGradebook, count); // Displays The Average of Every Grade in the Gradebook //
				
				
				} else if (action.equalsIgnoreCase("h/l")) {
				
					highlow(openGradebook, count); // Displays the Highest and Lowest Grades in the Gradebook //
				
				
				} else if (action.equalsIgnoreCase("quiz")) {
				
					avgQuestions(openGradebook, count); // Displays the Average Amount of Questions that Quizzes Have //
				
				
				} else if (action.equalsIgnoreCase("discuss")) {
				
					printDiscuss(openGradebook, count); // Displays a List of Each Discussion Reading //
				
				
				} else if (action.equalsIgnoreCase("program")) {
				
					printConcept(openGradebook, count); // Displays a List of Each Program Concept //
				
				
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
		System.out.println("add      - Adds Grade to Gradebook");
		System.out.println("del      - Deleets Grade from Gradebook");
		System.out.println("print    - Prints Grades in Gradebook");
		System.out.println("average  - Prints Grade Average");
		System.out.println("h/l      - Prints Highest and Lowest Grade");
		System.out.println("quiz     - Prints Quiz Question Average");
		System.out.println("discuss  - Prints All Discussion Readings");
		System.out.println("program  - Prints All Program Concepts");
		System.out.println("exit     - Exits This Program\n");
	}
	
	/*
	 * The updateCount helper function will check exactly how many elements inside
	 * of the gradebook are being used and returns an int. Count is used throughout
	 * the program so memory bounds are not exceeded.
	 */
	public static int updateCount(AssignmentInterface[] gb) {
		int count = 0;
		for(int i = 0; i < gb.length; i++) {
			if(gb[i] != null) {
				count++;
			}
		}
		return count;
	}
	
	/*
	 * The add helper function returns an AssignmentInterface array so that the
	 * Interface object inside of main is updated properly. The add function allows
	 * the user to choose which type of assignment they want to add to the gradebook,
	 * as well as fill in all of the necessary prompts such as name, date, score, etc. 
	 */
	public static AssignmentInterface[] add(AssignmentInterface[] gb, int count, int numGrades) throws GradebookFullException {
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
		if(count == numGrades) {
			throw new GradebookFullException("\n\t"+"The Gradebook Is Full\n");
		}
		
		// User is asked which type of assignment they would like to add //
		System.out.println("What type of grade would you like to add?");
		System.out.println("Choices: quiz, discussion, program");
		System.out.println("Choice : ");
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
			
			gb[count] = newQuiz; // Insert Object to Array //
			
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
			
			gb[count] = newDiscuss; // Insert Object to Array //
			
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
			
			gb[count] = newProgram; // Insert Object to Array //
		}
		
		// Notifies the User on Which Object Was Successfully Added Using toString(), then returns the Array //
		System.out.print("Successfully Added : ");
		String test = gb[count].toString();
		System.out.println(test);
		return gb;
	}
	
	/*
	 * findName is a helper function used by the delete class to check and see
	 * if the grade that the user wants deleted can be found within the array.
	 */
	public static boolean findName(AssignmentInterface[] gb, String name, int count) {
		for(int i = 0; i < count; i++) {
			if(name.equalsIgnoreCase(gb[i].getName())) {
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
	public static AssignmentInterface[] del(AssignmentInterface[] gb, int count, int maxSize) throws GradebookEmptyException, InvalidGradeException {
		String nameDel;
		boolean wasNameFound = false;
		boolean nameFound = false;
		int j = 0;
		Scanner sc = new Scanner(System.in);
		// If the array is empty, GradebookEmptyException is thrown //
		if(count == 0) {
			throw new GradebookEmptyException("\n\t"+"The Gradebook Is Empty");
		}
		AssignmentInterface[] tmp = new AssignmentInterface[maxSize];
		
		// Asks the user for the name of the grade to be deleted //
		System.out.println("Please Enter The Name of the Grade To Be Deleted : ");
		nameDel = sc.nextLine();
		// Check for the name using findName helper function //
		wasNameFound = findName(gb, nameDel, count);
		// If the name isnt found, throw InvalidGradeException
		if(!wasNameFound) {
			throw new InvalidGradeException("\n\t"+"The Grade Entered Could Not Be Found");
		}
		
		// If the name is found, remove it from the array by using a temp array //
		for(int i = 0; i < count - 1; i++) {
			if (nameFound == true) {
				tmp[j] = gb[i];
			}
			if(nameFound == false) {
				if(gb[i].getName() != nameDel) {
					tmp[j] = gb[i];
					j++;
				} else {
					nameFound = true;
				}
			}
		}
		return tmp; // Return tmp array to update the gradebook in main //
		
	}
	
	/*
	 * The following will print the entire arraybook for the user.
	 * If the gradebook is empty, a GradebookEmptyException will be thrown.
	 */
	public static void print(AssignmentInterface[] gb, int count) throws GradebookEmptyException {
		if(count == 0) {
			throw new GradebookEmptyException("\n\t"+"The Gradebook Is Empty");
		}
		for (int i = 0; i < count; i++) {
			System.out.println(gb[i].toString());
		}
	}
	
	/*
	 * The following function will print out a double stating
	 * the average of all the grades within the gradebook.
	 * A GradebookEmptyException is thrown if the
	 * gradebook is empty.
	 */
	public static void average(AssignmentInterface[] gb, int count) throws GradebookEmptyException {
		int totalGrade = 0;
		double average = 0;
		
		if(count == 0) {
			throw new GradebookEmptyException("\n\t"+"The Gradebook Is Empty");
		}
		
		for(int i = 0; i < count; i++) {
			average = average + gb[i].getScore();
			totalGrade++;
		}
		average = average/totalGrade;
		System.out.println("Average: " + average);
	}
	
	/*
	 * The following prints out the highest and lowest grades within
	 * the gradebook. However, if the gradebook is empty,
	 * a GradebookEmptyException is thrown.
	 */
	public static void highlow(AssignmentInterface[] gb, int count) throws GradebookEmptyException {
		int high = 0;
		int low = 100;
		if (count == 0) {
			throw new GradebookEmptyException("\n\t"+"The Gradebook Is Empty");
		}
		AssignmentInterface[] tmpHigh = new AssignmentInterface[1];
		AssignmentInterface[] tmpLow = new AssignmentInterface[1];
		
		for (int i = 0; i < count; i++) {
			if(gb[i].getScore() > high) {
				high = gb[i].getScore();
				tmpHigh[0] = gb[i]; // Sets a tmpHigh variable for the toString to be printed //
			}
			if(gb[i].getScore() < low) {
				low = gb[i].getScore();
				tmpLow[0] = gb[i]; // Sets a tmpLow variable for the toString to be printed //
			}

		}
		System.out.println("High : " + tmpHigh[0].toString() + "\n" +
							"Low : " + tmpLow[0].toString());
	}
	
	/*
	 * Prints out the average amount of questions within only the quiz
	 * objects inside the interface array. If gradebook is empty, GradebookEmptyException
	 * is thrown.
	 */
	public static void avgQuestions(AssignmentInterface[] gb, int count) throws GradebookEmptyException {
		int totalQs = 0;
		int avg = 0;
		
		if(count == 0) {
			throw new GradebookEmptyException("\n\t"+"The Gradebook Is Empty");
		}
		
		for(int i = 0; i < count; i++) {
			if(gb[i] instanceof Quiz) { // instanceof confirms that the current element is an array //
				avg = avg + ((Quiz)gb[i]).getNumQuestions();
				totalQs++;
			}
		}
		if(totalQs == 0) {
			System.out.println("\tNo Quizzes Have Been Made"); // If array is not empty, It tells the user No Quizzes were Added //
		} else {
			System.out.println("Average Number of Quiz Questions: " + (double)avg/totalQs);
		}
	}
	
	/*
	 * Prints out each Discussion reading within the array object. If its empty,
	 * throw GradebookEmptyException.
	 */
	public static void printDiscuss(AssignmentInterface[] gb, int count) throws GradebookEmptyException {
		int totalDiscuss = 0;
		if(count == 0) {
			throw new GradebookEmptyException("\n\t"+"The Gradebook Is Empty");
		}
		
		System.out.println("Discussion's Associated Readings: ");
		for (int i = 0; i < count; i++) {
			if(gb[i] instanceof Discussion) {
				System.out.println(((Discussion)gb[i]).getAssociatedReading());
				totalDiscuss++;
			}
		}
		if (totalDiscuss == 0) {
			System.out.println("\tNo Discussions Have Been Made"); // If array is not empty, prompt uset there is no discussions in array //
		}
	}
	
	/*
	 * Prints out all of the program comcepts in the array.
	 * If its empty, throw gradebook exception.
	 */
	public static void printConcept(AssignmentInterface[] gb, int count) throws GradebookEmptyException {
		int totalConcept = 0;
		if(count == 0) {
			throw new GradebookEmptyException("\n\t"+"The Gradebook Is Empty");
		}
		System.out.println("Program Concept's: ");
		for(int i = 0; i < count; i++) {
			if(gb[i] instanceof Program) {
				System.out.println(((Program)gb[i]).getConcept());
				totalConcept++;
			}
		}
		if (totalConcept == 0) {
			System.out.println("\tNo Programs Have Been Made");
		}
	}
	
	/*
	 * exit the program
	 */
	public static void exit() {
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
