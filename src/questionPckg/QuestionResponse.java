package questionPckg;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QuestionResponse extends Question{
	public static String prompt; 
	public static String answerA, answerB, answerC;

	/**
	 * Table format
	 * | questionID | question | answerA | answerB | answerC |
	 */
	public QuestionResponse(int questionID) {
		super(questionID);
		getDataFromTable();
	}
	
	private void getDataFromTable(){
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
			rs = stmt.executeQuery("SELECT * FROM questionResponse WHERE " +
					"questionID = " + questionID + ";");
		} catch (SQLException e) {
			System.out.println("Cannot query ID: " + questionID + " in questionToType table");
			e.printStackTrace();
		}
		try {
			while(rs.next()) {
				prompt = rs.getString("question");
				answerA = rs.getString("answerA");
				answerB = rs.getString("answerB");
				answerC = rs.getString("answerC");
			}
		} catch (SQLException e) {
			System.out.println("Trouble parsing resultset from table");
			e.printStackTrace();
		}
	}

	/**
	 * Random test code
	 * @param args
	 */
	public static void main(String[] args) {
		QuestionResponse q = new QuestionResponse(4);
		System.out.println(q.getFeedback(false));
	}

	@Override
	public String getPrompt() {
		return prompt;
	}

	/**
	 * returns a string containing all the various answers
	 */
	@Override
	public String getAnswer() {
		String answer = "";
		answer += answerA ;
		if (!answerB.equals(null)) { 
			answer+= " or " + answerB;
			if (!answerC.equals(null)) {
				answer += " or " + answerC;
			}
		}
		
		return answer;
	}

	@Override
	public boolean isCorrect(Object ans) {
		String userAns = (String) ans;
		if (userAns.equals(answerA) || userAns.equals(answerB) ||
				userAns.equals(answerC)) return true;
		return false;
	}


	@Override
	public String getFeedback(boolean state) {
		String feedback = "";
		if (state) {			//if user go the correct answer
			feedback =  prompt + '\n' + "Your answer is correct!";
		} else {
			feedback = "Your answer to the question: " + prompt + 
					" is incorrect. " + '\n' + "The correct answer is: " + getAnswer();
		}
		return feedback;
	}

	public void writeQuestion() {
		// TODO Auto-generated method stub
		
	}
}
