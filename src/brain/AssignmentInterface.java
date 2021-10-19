/*
 * Assignment: Gradebook Project
 * Name: Damyon Olson
 */
package brain;

import java.time.*;

/* 
 * The following interface creates the Getters/Setters and toString methods to be used
 * in the classes Discussion.java, Program.java, and Quiz.java.
 */
public interface AssignmentInterface {
	public int getScore();
	public void setScore(int score);
	public char getLetter();
	public void setLetter(int score);
	public String getName();
	public void setName(String name);
	public LocalDate getDate();
	public void setDate(String date);
	public String toString();
}