/*
 * Assignment: Gradebook Project
 * Name: Damyon Olson
 */
package brain;

import java.time.LocalDate;

/*
 * The following creates the class Quiz, which implements the AssignmentInterface interface.
 * It uses the provided methods, as well as creating a new getter/setter method so that it can
 * Set and grab numQuestions.
 */
public class Quiz implements AssignmentInterface{
	// Variables Created //
	int score;
	char letter;
	String name;
	LocalDate date;
    int numQuestions;

    // The following getter/setter methods access numQuestions
	public int getNumQuestions(){
		return numQuestions;
	}
	
	public void setNumQuestions(int numQ) {
		this.numQuestions = numQ;
	}
	// These methods were implemented from AssignmentInterface
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
		} else if (score < 90 && score >= 80) {
			this.letter = 'B';
		} else if (score < 80 && score >= 70) {
			this.letter = 'C';
		} else if(score < 70 && score >= 60) {
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
		String myString = "Quiz - Name: " + name + ", Score: " + score + ", Letter: " + letter + ", Date: " + date + ", Number of Questions: " + numQuestions;
		return myString;
	}
}
