import com.deliveredtechnologies.rulebook.Fact;
import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import org.junit.Test;
import org.junit.Assert;

/**
 * Purpose of those Tests is to check whether Facts can be provided to FactMap and removed from it
 * in case where the rules would require it as well as the user input.
 */
public class FactMapTest {
    NameValueReferableMap factMap = new FactMap();
    String exampleAnswer = "6";

    @Test
    public void testAddFacts(){
        factMap.put(new Fact("F0", exampleAnswer));
        factMap.put(new Fact("F1", exampleAnswer));
        Assert.assertEquals("Adding Facts to FactMap", 2, factMap.size());
    }

    @Test
    public void testRemoveFact(){
        factMap.put(new Fact("F0", exampleAnswer));
        factMap.put(new Fact("F1", exampleAnswer));
        factMap.put(new Fact("F2", exampleAnswer));
        factMap.put(new Fact("F3", exampleAnswer));
        factMap.remove("F1");
        Assert.assertEquals("Removing one fact", 3, factMap.size());
    }

    @Test
    public void testRemoveAllFacts(){
        factMap.put(new Fact("F0", exampleAnswer));
        factMap.put(new Fact("F1", exampleAnswer));
        factMap.clear();
        Assert.assertEquals("Removing all Facts", 0, factMap.size());
    }

    @Test
    public void testSize(){
        factMap = new FactMap(){{
            put(new Fact("F0", exampleAnswer));
            put(new Fact("F1", exampleAnswer));
        }};
        Assert.assertEquals("Checking size of the FactMap", 2, factMap.size());
    }
}// End of Test Class
