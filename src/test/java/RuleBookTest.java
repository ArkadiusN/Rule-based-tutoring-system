//import com.deliveredtechnologies.rulebook.Fact;
//import com.deliveredtechnologies.rulebook.FactMap;
//import com.deliveredtechnologies.rulebook.NameValueReferableMap;
//import com.deliveredtechnologies.rulebook.lang.RuleBookBuilder;
//import com.deliveredtechnologies.rulebook.model.RuleBook;
//import org.junit.Test;
//import org.junit.Assert;
//
///**
// * Purpose of those Tests is to check that the correct feedback is given back
// * when there would be situation of answering for question.
// */
//public class RuleBookTest {
//    // Variables
//    RuleBook ruleBook;
//    NameValueReferableMap factMap = new FactMap();
//    int score = 0;
//
//    /**
//     * rules() method used to make a short testing environment. In real scenario this
//     * would have a numerous rules defined.
//     */
//    public void rules(){
//        ruleBook = RuleBookBuilder.create().withResultType(Integer.class).withDefaultResult(0).asAuditor()
//                .addRule(rule -> rule
//                        .withName("Rule0")
//                        .withNoSpecifiedFactType()
//                        .when(f -> f.getStrVal("F0").equals("2"))
//                        .then((facts, result) -> result.setValue(score += 5))
//                        .then((facts, result) -> result.getValue()))
//                .addRule(rule -> rule
//                        .withName("Rule1")
//                        .withNoSpecifiedFactType()
//                        .when(f -> f.getStrVal("F1").equals("2"))
//                        .then((facts, result) -> result.setValue(score += 5))
//                        .then((facts, result) -> result.getValue()))
//                .addRule(rule -> rule
//                        .withName("Rule2")
//                        .withNoSpecifiedFactType()
//                        .when(f -> f.getStrVal("F2").equals("3"))
//                        .then((facts, result) -> result.setValue(score += 5))
//                        .then((facts, result) -> result.getValue()))
//                .build();
//        ruleBook.run(factMap);
//    }
//
//    @Test
//    public void testGetFivePointsForAnswer(){
//        //Potential answer of the user.
//        String answer = "2";
//
//        //Answer changed to Fact so the rules can understand it.
//        factMap.put(new Fact("F1", answer));
//
//        //Allowing the data to go through the rules.
//        rules();
//        Assert.assertEquals("Getting five points for correct answer", 5, score);
//    }
//
//    @Test
//    public void testGetMultiplePointsForAnswers(){
//        //Potential answers of the user
//        String answerZero = "2";
//        String answerOne = "2";
//        String answerTwo = "3";
//
//        //Answers changed to Facts so the rules can understand it.
//        factMap.put(new Fact("F0", answerZero));
//        factMap.put(new Fact("F1", answerOne));
//        factMap.put(new Fact("F2", answerTwo));
//
//
//        rules();
//        Assert.assertEquals("Getting Multiple points for correct questions", 15, score);
//    }
//
//    /**
//     * In case where questions were answered incorrectly it should not affect the next scored to be granted.
//     * For instance, in situation like [correct, correct, correct, incorrect, correct]
//     * the  score should be 20 (each correct = 5 points).
//     */
//    @Test
//    public void testGetMultiplePointsWithWrongAnswersBetween(){
//        //Potential answers of the user.
//        String answerZero = "2";
//        String answerTwo = "3";
//        //Incorrect answer
//        String answerOne = "99";
//
//        //Answers changed to Facts so the rules can understand it.
//        factMap.put(new Fact("F0", answerZero));
//        factMap.put(new Fact("F1", answerOne));
//        factMap.put(new Fact("F2", answerTwo));
//
//        rules();
//        Assert.assertEquals("Getting Multiple points for correct questions", 10, score);
//    }
//}// End of Test Class
