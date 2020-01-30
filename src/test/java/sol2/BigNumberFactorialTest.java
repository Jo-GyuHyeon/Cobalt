package sol2;

import org.junit.Assert;
import org.junit.Test;

public class BigNumberFactorialTest {

    @Test
    public void factorial() {
        BigNumberFactorial bigNumberFactorial = new BigNumberFactorial();
        String expect = "93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000";
        String result = bigNumberFactorial.factorial("100!");
        System.out.println("result = " + result);

        Assert.assertEquals(expect, result);
    }
}