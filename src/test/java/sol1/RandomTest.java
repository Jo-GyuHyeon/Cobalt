package sol1;

import org.junit.Test;

import static org.junit.Assert.fail;

public class RandomTest {

    @Test
    public void getRandom() {
        Random random = new Random();
        int bound = 4;
        int result = random.getRandom(bound);
        System.out.println("result = " + result);

        if (result < 0 || result >= bound) fail("예상 값 사이의 값을 벗어났습니다.");
    }

}
