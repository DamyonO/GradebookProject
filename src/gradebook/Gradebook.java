package gradebook;

import java.util.*;

import brain.*;

public class Gradebook {
	public static void main(String args[]) {
		int numGrades;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome to your Gradebook!");
		System.out.println("Please enter how many grades you would like to insert.");
		System.out.print("Number of grades : ");
		numGrades = Integer.parseInt(sc.nextLine());
		while(numGrades > 20 || numGrades < 0) {
			System.out.println("Number of grades can only be a positive integer less than 20");
			System.out.println("Please enter how many grades you would like to insert.");
			System.out.print("Number of grades : ");
			numGrades = Integer.parseInt(sc.nextLine());
		}
		AssignmentInterface[] openGradebook = new AssignmentInterface[numGrades];
		String action;
		int count = 0;
		
		displayMenu();
		while(true) {
			System.out.print("Enter a Command : ");
			action = sc.nextLine();
			
			if (action.equalsIgnoreCase("menu")) {
				displayMenu();
			} else if (action.equalsIgnoreCase("add")) {
				openGradebook =  add(openGradebook, count);
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
			} else if (action.equalsIgnoreCase("exit")) {
				exit();
			} else {
				System.out.println("Invalid Command!");
			}
		}
		
		
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
	
	public static AssignmentInterface[] add(AssignmentInterface[] gb, int count) {
		Scanner sc = new Scanner(System.in);
		String action;
		
		int score;
		String name;
		String date;
		int numQ;
		String concept;
		String reading;
		
		System.out.println("What type of grade would you like to add?");
		System.out.println("Choices: quiz, discussion, program");
		System.out.println("Choice : ");
		action = sc.nextLine();
		while (!action.equalsIgnoreCase("quiz")
				&& !action.equalsIgnoreCase("discussion")
				&& !action.equalsIgnoreCase("program")) {
			System.out.println("Invalid Choice!");
			System.out.println("Choice : ");
			action = sc.nextLine();
		}
		
		if (action.equalsIgnoreCase("quiz")) {
			
			Quiz newQuiz = new Quiz();
			System.out.print("What Was The Grade? : ");
			score = Integer.parseInt(sc.nextLine());
			while (score > 100 || score < 0) {
				System.out.println("Grade Invalid. Please Enter New Grade : ");
				score = Integer.parseInt(sc.nextLine());
			}
			newQuiz.setScore(score);
			newQuiz.setLetter(score);
			
			System.out.print("What Was The Name? : ");
			name = sc.nextLine();
			newQuiz.setName(name);
			
			System.out.print("What Was The Due Date (yyyy-mm-dd) : ");
			date = sc.nextLine();
			newQuiz.setDate(date);
			
			System.out.print("How Many Questions Does The Quiz Have?");
			numQ = Integer.parseInt(sc.nextLine());
			newQuiz.setNumQuestions(numQ);
			
			gb[count] = newQuiz;
			
		} else if (action.equalsIgnoreCase("discussion")) {
			Discussion newDiscuss = new Discussion();
			System.out.print("What Was The Grade? : ");
			score = Integer.parseInt(sc.nextLine());
			while (score > 100 || score < 0) {
				System.out.println("Grade Invalid. Please Enter New Grade : ");
				score = Integer.parseInt(sc.nextLine());
			}
			newDiscuss.setScore(score);
			newDiscuss.setLetter(score);
			
			System.out.print("What Was The Name? : ");
			name = sc.nextLine();
			newDiscuss.setName(name);
			
			System.out.print("What Was The Due Date (yyyy-mm-dd) : ");
			date = sc.nextLine();
			newDiscuss.setDate(date);
			
			System.out.print("What was the reading for this discussion?");
			reading = sc.nextLine();
			newDiscuss.setAssociatedReading(reading);
			
			gb[count] = newDiscuss;
		} else if (action.equalsIgnoreCase("program")) {
			gb[count] = new Program();
			System.out.print("What Was The Grade? : ");
			score = Integer.parseInt(sc.nextLine());
			while (score > 100 || score < 0) {
				System.out.println("Grade Invalid. Please Enter New Grade : ");
				score = Integer.parseInt(sc.nextLine());
			}
			gb[count].setScore(score);
			
			System.out.print("What Was The Name? : ");
			name = sc.nextLine();
			
			System.out.print("What Was The Due Date (yyyy-mm-dd) : ");
			date = sc.nextLine();
			gb[count].setDate(date);
			
			System.out.print("What is the concept of this program? : ");
			concept = sc.nextLine();
			
			gb[count] = new Program(score, gb[count].getLetter(), name, gb[count].getDate(), concept);
		}
		System.out.print("Successfully Added : ");
		String test = gb[count].toString();
		System.out.println(test);
		return gb;
	}
	
	public static boolean findName(AssignmentInterface[] gb, String name, int count) {
		boolean nameFound = false;
		for(int i = 0; i < count; i++) {
			System.out.println(gb[i].getName() + " " + name);
			if(name.equalsIgnoreCase(gb[i].getName())) {
				return true;
			}
		}
		return false;
		
	}
	
	public static AssignmentInterface[] del(AssignmentInterface[] gb, int count, int maxSize) {
		String nameDel;
		boolean wasNameFound = false;
		boolean nameFound = false;
		int j = 0;
		Scanner sc = new Scanner(System.in);
		AssignmentInterface[] tmp = new AssignmentInterface[maxSize];
		
		System.out.println("Which grade would you like deleted?");
		nameDel = sc.nextLine();
		wasNameFound = findName(gb, nameDel, count);
		if(!wasNameFound) {
			System.out.println("Sorry, that grade was not found in the book!");
			return gb;
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
		return tmp;
		
	}
	
	public static void print(AssignmentInterface[] gb, int max) {
		for (int i = 0; i < max; i++) {
			if(gb[i] != null) {
				System.out.println(gb[i].toString());
			}
		}
	}

	public static void average(AssignmentInterface[] gb, int max) {
		int count = 0;
		int average = 0;
		
		for(int i = 0; i < max; i++) {
			if(gb[i] != null) {
				average = average + gb[i].getScore();
				count++;
			}
		}
		average = average/count;
		System.out.println("Average: " + average);
	}
	
	public static void highlow(AssignmentInterface[] gb, int max) {
		int high = 0;
		int low = 100;
		AssignmentInterface[] tmpHigh = new AssignmentInterface[1];
		AssignmentInterface[] tmpLow = new AssignmentInterface[1];
		
		for (int i = 0; i < max; i++) {
			if(gb[i] != null) {
				if(gb[i].getScore() > high) {
					high = gb[i].getScore();
					tmpHigh[0] = gb[i];
				}
				if(gb[i].getScore() < low) {
					low = gb[i].getScore();
					tmpLow[0] = gb[i];
				}
			}
		}
		System.out.println("High : " + tmpHigh[0].toString() + "\n" +
							"Low : " + tmpLow[0].toString());
	}
	
	public static void avgQuestions(AssignmentInterface[] gb, int count) {
		int totalQs = 0;
		int avg = 0;
		
		for(int i = 0; i < count; i++) {
			if(gb[i] instanceof Quiz) {
				avg = avg + ((Quiz)gb[i]).getNumQuestions();
				totalQs++;
			}
		}
		System.out.println("Average Number of Quiz Questions: " + avg/totalQs);
	}
	
	public static void printDiscuss(AssignmentInterface[] gb, int count) {
		int totalDiscuss = 0;
		
		System.out.println("Discussion's Associated Readings: ");
		for (int i = 0; i < count; i++) {
			if(gb[i] instanceof Discussion) {
				System.out.println(((Discussion)gb[i]).getAssociatedReading());
				totalDiscuss++;
			}
		}
		if (totalDiscuss == 0) {
			System.out.println("No Discussion Readings Present");
		}
	}
	
	public static void printConcept(AssignmentInterface[] gb, int count) {
		int totalConcept = 0;
		System.out.println("Program Concept's: ");
		for(int i = 0; i < count; i++) {
			if(gb[i] instanceof Program) {
				System.out.println(((Program)gb[i]).getConcept());
				totalConcept++;
			}
		}
		if (totalConcept == 0) {
			System.out.println("No Concepts Present");
		}
	}
	
	public static void exit() {
		System.out.println("Bye!");
		System.exit(0);
	}
	
}
