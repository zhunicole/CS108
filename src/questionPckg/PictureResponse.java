package questionPckg;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PictureResponse extends Question{
	public static String prompt; 
	public static String answerA, answerB, answerC;
	public static String link; 

	/**
	 * Table format
	 * | questionID | question | answerA | answerB | answerC | link  |
	 */
	public PictureResponse(int questionID) {
		super(questionID);
		getDataFromTable();
	}
	
	public String getLink() {
		return link;
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
			rs = stmt.executeQuery("SELECT * FROM pictureResponse WHERE " +
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
				link = rs.getString("link");
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
		PictureResponse q = new PictureResponse(10);
		System.out.println(prompt + answerA);
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
			feedback = "Your answer is correct!";
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
