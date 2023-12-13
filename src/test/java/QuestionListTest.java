//import org.junit.Test;
//import org.junit.Assert;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * Purpose of those Tests is to check whether Question Objects can be stored
// * and then used for the expert system. Two files in a format ".txt" can be used
// * together to form a Question.
// */
//public class QuestionListTest {
//    List<Question> questionsList = new ArrayList<>();
//
//    @Test
//    public void testAddQuestions() {
//        questionsList.add(new Question("Does binary search work on unsorted list?", "1. Yes>2. No"));
//        questionsList.add(new Question("What are the variables needed to find value with use of binary search?", "1. max, min>2. left, right>3. max, mid"));
//        Assert.assertEquals("Adding two Questions", 2, questionsList.size());
//    }
//
//    @Test
//    public void testRemoveQuestion(){
//        questionsList.add(new Question("Does binary search work on unsorted list?", "1. Yes>2. No"));
//        questionsList.add(new Question("What are the variables needed to find value with use of binary search?", "1. max, min>2. left, right>3. max, mid"));
//        questionsList.remove(0);
//        Assert.assertEquals("Removing one Question", 1, questionsList.size());
//    }
//
//    @Test
//    public void testRemoveAllQuestions(){
//        questionsList.add(new Question("Does binary search work on unsorted list?", "1. Yes>2. No"));
//        questionsList.add(new Question("What are the variables needed to find value with use of binary search?", "1. max, min>2. left, right>3. max, mid"));
//        questionsList.clear();
//        Assert.assertEquals("Removing all Questions", 0, questionsList.size());
//    }
//
//    @Test
//    public void testSize(){
//        questionsList = Arrays.asList(
//                new Question("Does binary search work on unsorted list?", "1. Yes>2. No"),
//                new Question("What are the variables needed to find value with use of binary search?", "1. max, min>2. left, right>3. max, mid")
//        );
//        Assert.assertEquals("Checking size of the Question List", 2, questionsList.size());
//    }
//}// End of Test Class
