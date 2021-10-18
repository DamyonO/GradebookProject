package brain;

import java.time.LocalDate;

public class Discussion implements AssignmentInterface{
	int score;
	char letter;
	String name;
	LocalDate date;
	String associatedReading;
	
	public Discussion(int score, char letter, String name, LocalDate date, String ar) {
		this.score = score;
		this.letter = letter;
		this.name = name;
		this.date = date;
		this.associatedReading = ar;
	}
	

	public Discussion() {
	}
	
	public String getAssociatedReading(){
		return associatedReading;
	}
	
	public void setAssociatedReading(String ar) {
		this.associatedReading = ar;
	}
	
	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public char getLetter() {
		return letter;
	}

	@Override
	public void setLetter(int score) {
		if (score >= 90) {
			this.letter = 'A';
		} else if (score < 100 && score >= 80) {
			this.letter = 'B';
		} else if (score < 100 && score >= 70) {
			this.letter = 'C';
		} else if(score < 100 && score >= 60) {
			this.letter = 'D';
		} else if (score < 60) {
			this.letter = 'F';
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public LocalDate getDate() {
		return date;
	}

	@Override
	public void setDate(String date) {
		this.date = LocalDate.parse(date);
	}

	@Override
	public String toString() {
		String myString = "Discussion - Name: " + name + ", Score: " + score + ", Letter: " + letter + ", Date: " + date + ", Associated Reading: " + associatedReading;
		return myString;
	}

}
