package gradebook;

import java.time.format.DateTimeParseException;
import java.util.*;

import brain.*;

public class Gradebook {
	public static void main(String args[]) {
		try {
			int numGrades = 0;
			Scanner sc = new Scanner(System.in);
		
			System.out.println("Welcome to your Gradebook!");
			System.out.println("Please enter how many grades you would like to insert.");
			System.out.println("Number must be a positive integer from 1 to 20");
			System.out.print("Number of grades : ");
		
			numGrades = getInt(1,20);
		
			AssignmentInterface[] openGradebook = new AssignmentInterface[numGrades];
			String action;
			int count = 0;
		
			displayMenu();
			while(true) {
				try {
				System.out.print("Enter a Command : ");
				action = sc.nextLine();
			
				if (action.equalsIgnoreCase("menu")) {
					displayMenu();
				
				} else if (action.equalsIgnoreCase("add")) {
					openGradebook =  add(openGradebook, count, numGrades);
					count = updateCount(openGradebook);
					System.out.println("Gradebook Size is Now: " + count + "\n");

				} else if (action.equalsIgnoreCase("del")) {
					openGradebook = del(openGradebook, count, numGrades);
					count = updateCount(openGradebook);
					System.out.println("Gradebook Size is Now: " + count + "\n");
				
				} else if (action.equalsIgnoreCase("print")) {
					print(openGradebook, count);
				
				} else if (action.equalsIgnoreCase("average")) {
					average(openGradebook, count);
				
				} else if (action.equalsIgnoreCase("h/l")) {
					highlow(openGradebook, count);
				
				} else if (action.equalsIgnoreCase("quiz")) {
					avgQuestions(openGradebook, count);
				
				} else if (action.equalsIgnoreCase("discuss")) {
					printDiscuss(openGradebook, count);
				
				} else if (action.equalsIgnoreCase("program")) {
					printConcept(openGradebook, count);
				
				} else if (action.equalsIgnoreCase("exit") || action.equalsIgnoreCase("quit")) {
					sc.close();
					exit();
					
				} else {
					System.out.println("\tInvalid Command! Type In 'menu' If You Would Like To See The Menu\n");
				}
				} catch (Exception e) {
					System.out.println("An Error Has Occured : " + e);
				}
			}
		} finally {}
	}
	
	public static void displayMenu() {
		System.out.println("COMMAND MENU");
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
	
	public static int updateCount(AssignmentInterface[] gb) {
		int count = 0;
		for(int i = 0; i < gb.length; i++) {
			if(gb[i] != null) {
				count++;
			}
		}
		return count;
	}
	
	public static AssignmentInterface[] add(AssignmentInterface[] gb, int count, int numGrades) throws GradebookFullException {
		Scanner sc = new Scanner(System.in);
		String action;
		
		boolean validDate = false;
		int score;
		String name;
		String date;
		int numQ;
		String concept;
		String reading;
		
		if(count == numGrades) {
			sc.close();
			throw new GradebookFullException("\n\t"+"The Gradebook Is Full\n");
		}
		
		System.out.println("What type of grade would you like to add?");
		System.out.println("Choices: quiz, discussion, program");
		System.out.println("Choice : ");
		action = sc.nextLine();
		while (!action.equalsIgnoreCase("quiz")
				&& !action.equalsIgnoreCase("discussion")
				&& !action.equalsIgnoreCase("program")) {
			System.out.println("Invalid Choice! Please pick : quiz, discussion, or program");
			System.out.println("Choice : ");
			action = sc.nextLine();
		}
		
		if (action.equalsIgnoreCase("quiz")) {
			
			Quiz newQuiz = new Quiz();
			System.out.print("What Was The Grade? : ");
			score = getInt(0, 100);
			newQuiz.setScore(score);
			newQuiz.setLetter(score);
			
			System.out.print("What Was The Name? : ");
			name = sc.nextLine();
			newQuiz.setName(name);
			
			System.out.print("What Was The Due Date (yyyy-mm-dd) : ");
			while (validDate == false) {
				date = sc.nextLine();
				try {
					newQuiz.setDate(date);
					validDate = true;
				} catch (DateTimeParseException e) {
					System.out.println("Date Entered is Invalid. Please Try Again (yyyy-mm-dd): ");
				}
			}
			
			System.out.print("How Many Questions Does The Quiz Have?");
			numQ = getInt(1);
			newQuiz.setNumQuestions(numQ);
			
			gb[count] = newQuiz;
			
		} else if (action.equalsIgnoreCase("discussion")) {
			Discussion newDiscuss = new Discussion();
			System.out.print("What Was The Grade? : ");
			score = getInt(0, 100);
			newDiscuss.setScore(score);
			newDiscuss.setLetter(score);
			
			System.out.print("What Was The Name? : ");
			name = sc.nextLine();
			newDiscuss.setName(name);
			
			System.out.print("What Was The Due Date (yyyy-mm-dd) : ");
			while (validDate == false) {
				date = sc.nextLine();
				try {
					newDiscuss.setDate(date);
					validDate = true;
				} catch (DateTimeParseException e) {
					System.out.println("Date Entered is Invalid. Please Try Again (yyyy-mm-dd): ");
				}
			}
			
			
			System.out.print("What was the reading for this discussion?");
			reading = sc.nextLine();
			newDiscuss.setAssociatedReading(reading);
			
			gb[count] = newDiscuss;
		} else if (action.equalsIgnoreCase("program")) {
			Program newProgram = new Program();
			System.out.print("What Was The Grade? : ");
			score = getInt(0, 100);
			newProgram.setScore(score);
			newProgram.setLetter(score);
			
			System.out.print("What Was The Name? : ");
			name = sc.nextLine();
			newProgram.setName(name);
			
			System.out.print("What Was The Due Date (yyyy-mm-dd) : ");
			while (validDate == false) {
				date = sc.nextLine();
				try {
					newProgram.setDate(date);
					validDate = true;
				} catch (DateTimeParseException e) {
					System.out.println("Date Entered is Invalid. Please Try Again (yyyy-mm-dd): ");
				}
			}
			
			System.out.print("What is the concept of this program? : ");
			concept = sc.nextLine();
			newProgram.setConcept(concept);
			
			gb[count] = newProgram;
		}
		System.out.print("Successfully Added : ");
		String test = gb[count].toString();
		System.out.println(test);
		sc.close();
		return gb;
	}
	
	public static boolean findName(AssignmentInterface[] gb, String name, int count) {
		for(int i = 0; i < count; i++) {
			if(name.equalsIgnoreCase(gb[i].getName())) {
				return true;
			}
		}
		return false;
		
	}
	
	public static AssignmentInterface[] del(AssignmentInterface[] gb, int count, int maxSize) throws GradebookEmptyException, InvalidGradeException {
		String nameDel;
		boolean wasNameFound = false;
		boolean nameFound = false;
		int j = 0;
		Scanner sc = new Scanner(System.in);
		if(count == 0) {
			sc.close();
			throw new GradebookEmptyException("\n\t"+"The Gradebook Is Empty");
		}
		AssignmentInterface[] tmp = new AssignmentInterface[maxSize];
		
		System.out.println("Which grade would you like deleted?");
		nameDel = sc.nextLine();
		wasNameFound = findName(gb, nameDel, count);
		if(!wasNameFound) {
			sc.close();
			throw new InvalidGradeException("\n\t"+"The Grade Entered Could Not Be Found");
		}
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
		sc.close();
		return tmp;
		
	}
	
	public static void print(AssignmentInterface[] gb, int count) throws GradebookEmptyException {
		if(count == 0) {
			throw new GradebookEmptyException("\n\t"+"The Gradebook Is Empty");
		}
		for (int i = 0; i < count; i++) {
			System.out.println(gb[i].toString());
		}
	}

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
				tmpHigh[0] = gb[i];
			}
			if(gb[i].getScore() < low) {
				low = gb[i].getScore();
				tmpLow[0] = gb[i];
			}

		}
		System.out.println("High : " + tmpHigh[0].toString() + "\n" +
							"Low : " + tmpLow[0].toString());
	}
	
	public static void avgQuestions(AssignmentInterface[] gb, int count) throws GradebookEmptyException {
		int totalQs = 0;
		int avg = 0;
		
		if(count == 0) {
			throw new GradebookEmptyException("\n\t"+"The Gradebook Is Empty");
		}
		
		for(int i = 0; i < count; i++) {
			if(gb[i] instanceof Quiz) {
				avg = avg + ((Quiz)gb[i]).getNumQuestions();
				totalQs++;
			}
		}
		if(totalQs == 0) {
			System.out.println("\tNo Quizs Have Been Made");
		} else {
			System.out.println("Average Number of Quiz Questions: " + avg/totalQs);
		}
	}
	
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
			System.out.println("\tNo Discussions Have Been Made");
		}
	}
	
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
	
	public static void exit() {
		System.out.println("Bye!");
		System.exit(0);
	}
	
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
		sc.close();
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
