package questionPckg;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MultipleChoice extends Question{
	public static String prompt; 
	public String choiceA, choiceB, choiceC, choiceD;
	public static String answer;
	
	/**
	 * Table format: 
	 * questionID | question | choiceA | choiceB | choiceC | choiceD | answer |
	 * @param questionNum
	 */
	public MultipleChoice(int questionID) {
		super(questionID);
		getDataFromTable();
	}
	
	/**
	 * Makes connection with server
	 * Sets prompt, choices, and answer
	 */
	private void getDataFromTable() {
		ResultSet rs = null;
		Statement stmt = null;
		Connection con;
		con = (Connection) MyDB.getConnection(); 
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			rs = stmt.executeQuery("SELECT * FROM multipleChoice WHERE " +
					"questionID = " + questionID + ";");
		} catch (SQLException e) {
			System.out.println("Cannot query ID: " + questionID + " in questionToType table");
			e.printStackTrace();
		}
		try {
			while(rs.next()) {
				prompt = rs.getString("question");
				choiceA = rs.getString("choiceA");
				choiceB = rs.getString("choiceB");
				choiceC = rs.getString("choiceC");
				choiceD = rs.getString("choiceD");
				answer = rs.getString("answer");
			}
		} catch (SQLException e) {
			System.out.println("Trouble parsing resultset from table");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * State true if user answered correctly
	 * Gives String of feedback according to right/wrong answer
	 */
	@Override 
	public String getFeedback(boolean state) {
		String feedback = "";
		if (state) {			//if user go the correct answer
			feedback =  prompt + '\n' + "Your answer of: " + answer + " is correct!";
		} else {
			feedback = "Your answer to the question: " + prompt + 
					" is incorrect. " + '\n' + "The correct answer is: " + answer;
		}
		return feedback;
	}
	
	//might change this, object returned may not be a string
	@Override
	public boolean isCorrect(Object ans) {
		String userAns = (String) ans;
		if (userAns.equals(answer)) return true;
		return false;
	}
	
	@Override
	public String getPrompt() {
		return prompt;
	}
	
	/**
	 * Goes into the table corresponding with MC and retrieves correct answer
	 * @param static questionID
	 * @return String of question prompt
	 */
	@Override
	public String getAnswer() {
		return answer;
	}
	
	//~~~~~~~~~ starts part where user writes questions into the database~~~~~~~~~~~~~~~~
	
	public void writeQuestion() {
		
	}

	
	/**
	 * Random test code
	 * @param args
	 */
	public static void main(String[] args) {
		MultipleChoice q = new MultipleChoice(1);
		System.out.println(q.getFeedback(true));
	}


}
