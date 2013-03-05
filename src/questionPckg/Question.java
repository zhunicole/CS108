package questionPckg;


//import

public abstract class Question {	
	public static int questionID;
	public String questionType;
	
	//Assume for now that questionType is passed into constructor
	public Question(int questionID) {
		Question.questionID = questionID; 
		questionType = getQuestionType();

	}
	
	
	public abstract String getPrompt();
	public abstract String getAnswer();
	public abstract boolean isCorrect(Object ans);
	public abstract String getFeedback(boolean state);	//often a string, except picture-response
	
	//public abstract void writeQuestion(); maybe should implement at the sublevel

	/**
	 * Searches in questionID to questionType table to find
	 * the questionType
	 * @param questionID
	 * @return questionType (string)
	 */
	public static String getQuestionType(){
		DBConnection db = new DBConnection();
		String qType = db.getType(questionID);
		return qType;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Should say mc: " + getQuestionType());

	}

}
