public class Question {
    // Variables
    String questions, answerChoices;

    // Constructor for Question Object
    protected Question(String questions, String answerChoices) {
        this.questions = questions;
        this.answerChoices = answerChoices;
    }

    // Getters & Setters
    protected String getQuestions() {
        return questions;
    }

    protected void setQuestions(String questions) {
        this.questions = questions;
    }

    protected String getAnswerChoices() {
        return answerChoices;
    }

    protected void setAnswerChoices(String answerChoices) {
        this.answerChoices = answerChoices;
    }
}// End of Class
