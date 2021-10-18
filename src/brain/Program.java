package brain;

import java.time.LocalDate;

public class Program implements AssignmentInterface{
	int score;
	char letter;
	String name;
	LocalDate date;
	String concept;
	
	public Program(int score, char letter, String name, LocalDate date, String concept) {
		this.score = score;
		this.letter = letter;
		this.name = name;
		this.date = date;
		this.concept = concept;
	}
	

	public Program() {
	}
	
	public String getConcept(){
		return concept;
	}
	
	public void setConcept(String concept) {
		this.concept = concept;
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
		String myString = "Program - Name: " + name + ", Score: " + score + ", Letter: " + letter + ", Date: " + date + ", Concept: " + concept;
		return myString;
	}
}
