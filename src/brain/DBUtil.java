package brain;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class DBUtil {
	private static Connection connection;
	
	private DBUtil() {}
	
	
	public static Connection getConnection() throws SQLException {
		if (connection != null) {
			return connection;
		} else {
			try {
				Scanner sc = new Scanner(System.in);
				System.out.println("Connecting to the MySQL Database\n");
				String url = "jdbc:mysql://cpsc2810-1.cjbcacjugpfv.us-east-1.rds.amazonaws.com/Gradebook";
				System.out.println("Please Enter Your Username For MySQL : ");
				String username = sc.nextLine();
				System.out.println("Please Enter Your Password For MySQL : ");
				String password = sc.nextLine();
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(url, username, password);
				System.out.println("\n\tConnected to MySQL!\n");
				return connection;
				
			} catch (SQLException | ClassNotFoundException e) {
				throw new SQLException(e);
			}
		}
	}
	
	public static synchronized void closeConnection() throws SQLException {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("\n\tConnection to MySQL Has Closed!");
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                connection = null;
            }
        }
    }
	
	public static void createTable() throws SQLException {
		try {
			Connection connection = getConnection();
			PreparedStatement create = connection.prepareStatement("CREATE TABLE IF NOT EXISTS gradebook(id int NOT NULL AUTO_INCREMENT, type varchar(255), name varchar(255), grade int, letter varchar(1), date varchar(10), info varchar(255), PRIMARY KEY(id))");
			create.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			System.out.println("\n\tMySQL Table Found/Created!");
		}
	}
	
	public static void toMySQL(ArrayList<AssignmentInterface> gb) throws SQLException {
		try {
			String infoType = new String();
			String type = new String();
			Connection connection = getConnection();
			createTable();
			for(int i = 0; i < gb.size(); i++) {
				if(gb.get(i) instanceof Quiz) {
					type = "Quiz";
					infoType = Integer.toString(((Quiz) gb.get(i)).getNumQuestions());
				} else if (gb.get(i) instanceof Program) {
					type = "Program";
					infoType = ((Program) gb.get(i)).getConcept();
				} else {
					type = "Discussion";
					infoType = ((Discussion) gb.get(i)).getAssociatedReading();
				}
			
				PreparedStatement send = connection.prepareStatement("INSERT INTO gradebook (type, name, grade, letter, date, info) "
					+ "VALUES ('" + type + "', '" + gb.get(i).getName() + "', '" + gb.get(i).getScore() + "', '" 
					+ gb.get(i).getLetter() + "', '" + gb.get(i).getDate() + "', '" + infoType + "')");
				send.executeUpdate();
			}
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			System.out.println("\n\tSuccesfully Added To Database!");
		}
	}
	
	public static ArrayList<AssignmentInterface> getMySQL(ArrayList<AssignmentInterface> gb) throws SQLException{
		try {
			Connection connection = getConnection();
			PreparedStatement get = connection.prepareStatement("SELECT type, name, grade, letter, date, info FROM gradebook");
			
			ResultSet result = get.executeQuery();
			while(result.next()) {
				boolean exists = false;
				if(result.getString("type").equalsIgnoreCase("Quiz")) {
					Quiz newQuiz = new Quiz();
					newQuiz.setName(result.getString("name"));
					newQuiz.setScore(result.getInt("grade"));
					newQuiz.setLetter(result.getInt("grade"));
					newQuiz.setDate(result.getString("date"));
					newQuiz.setNumQuestions(Integer.parseInt(result.getString("info")));
					if(gb.size() != 0) {
						for(int i = 0; i < gb.size(); i++) {
							if (gb.get(i) instanceof Quiz && gb.get(i).getName().compareTo(newQuiz.getName()) == 0 && gb.get(i).getDate().compareTo(newQuiz.getDate()) == 0) {
								exists = true;
							}
						}
					}
					if (!exists) { gb.add(newQuiz); }
				}
				if(result.getString("type").equalsIgnoreCase("Discussion")) {
					Discussion newDis = new Discussion();
					newDis.setName(result.getString("name"));
					newDis.setScore(result.getInt("grade"));
					newDis.setLetter(result.getInt("grade"));
					newDis.setDate(result.getString("date"));
					newDis.setAssociatedReading(result.getString("info"));
					if(gb.size() != 0) {
						for(int i = 0; i < gb.size(); i++) {
							if (gb.get(i) instanceof Discussion && gb.get(i).getName().compareTo(newDis.getName()) == 0 && gb.get(i).getDate().compareTo(newDis.getDate()) == 0) {
								exists = true;
							}
						}
					}
					if (!exists) { gb.add(newDis); }
				}
				if(result.getString("type").equalsIgnoreCase("Program")) {
					Program newProg = new Program();
					newProg.setName(result.getString("name"));
					newProg.setScore(result.getInt("grade"));
					newProg.setLetter(result.getInt("grade"));
					newProg.setDate(result.getString("date"));
					newProg.setConcept(result.getString("info"));
					if(gb.size() != 0) {
						for(int i = 0; i < gb.size(); i++) {
							if (gb.get(i) instanceof Program && gb.get(i).getName().compareTo(newProg.getName()) == 0 && gb.get(i).getDate().compareTo(newProg.getDate()) == 0) {
								exists = true;
							}
						}
					}
					if (!exists) { gb.add(newProg); }
				}
			}
			System.out.println("\n\tGrades Have Been Transferred From MySWL Database!");
			return gb;
		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}
	
	public static ArrayList<AssignmentInterface> searchMySQL(ArrayList<AssignmentInterface> gb, int choice) throws SQLException{
		ArrayList<AssignmentInterface> tmp = new ArrayList<>();
		tmp = getMySQL(tmp);
		ArrayList<AssignmentInterface> result = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		String decision = new String();
		boolean valid = false;
		
		switch (choice) {
			case 1:
				for(int i = 0; i < tmp.size(); i++) {
					if(tmp.get(i) instanceof Quiz) {
						result.add(tmp.get(i));
					}
				}
				break;
			case 2:
				for(int i = 0; i < tmp.size(); i++) {
					if(tmp.get(i) instanceof Program) {
						result.add(tmp.get(i));
					}
				}
				break;
			case 3:
				for(int i = 0; i < tmp.size(); i++) {
					if(tmp.get(i) instanceof Discussion) {
						result.add(tmp.get(i));
					}
				}
				break;
			case 4:
				System.out.println("What Is The Min Grade You Want To See?");
				int min = getInt();
				System.out.println("What Is The Max Grade You Want To See?");
				int max = getInt();
				for(int i = 0; i < tmp.size(); i++) {
					if(tmp.get(i).getScore() >= min && tmp.get(i).getScore() <= max) {
						result.add(tmp.get(i));
					}
				}
				break;
			case 5:
				boolean validDate = false;
				String date;
				LocalDate minDate = null;
				LocalDate maxDate = null;
				System.out.println("What Is The First Due Date Range (yyyy-mm-dd)? : ");
				while (validDate == false) {
					date = sc.nextLine();
					try {
						minDate = LocalDate.parse(date);
						validDate = true;
					} catch (DateTimeParseException e) {
						System.out.println("\tERROR: Date Entered is Invalid. Please Try Again (yyyy-mm-dd) : ");
					}
				}
				validDate = false;
				System.out.println("What Is The Last Due Date Range (yyyy-mm-dd)? : ");
				while (validDate == false) {
					date = sc.nextLine();
					try {
						maxDate = LocalDate.parse(date);
						validDate = true;
					} catch (DateTimeParseException e) {
						System.out.println("\tERROR: Date Entered is Invalid. Please Try Again (yyyy-mm-dd) : ");
					}
				}
				
				for (int i = 0; i < tmp.size(); i++) {
					if(tmp.get(i).getDate().compareTo(minDate) > 0 && tmp.get(i).getDate().compareTo(maxDate) < 0) {
						result.add(tmp.get(i));
					}
				}
				break;
			case 6:
				for(int i = 0; i < tmp.size(); i++) {
					if(tmp.get(i).getScore() % 2 == 0) {
						result.add(tmp.get(i));
					}
				}
				break;
			default:
				System.out.println("Something Went Wrong!");
		}
		while(valid == false) {
			System.out.println("Search Complete! Would You Like To Add Them To Your Gradebook?(y or n)");
			decision = sc.nextLine();
			if(decision.equalsIgnoreCase("y")) {
				gb.addAll(result);
				return gb;
			} else if (decision.equalsIgnoreCase("n")){
				return gb;
			} else {
				System.out.println("Please Eter y or n");
			}
		}
		
		return gb;
		
		
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
		return i;
	}
}
