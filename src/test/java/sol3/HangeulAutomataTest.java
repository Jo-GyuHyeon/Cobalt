package sol3;


import org.junit.Assert;
import org.junit.Test;


public class HangeulAutomataTest {
    private HangeulAutomata hangeulAutomata = new HangeulAutomata();

    @Test
    public void solution() throws Exception {
        String input = "ㅇㅏㄴㄴㅕㅇㅎㅏㅅㅔㅇㅛ";
        String expect = "안녕하세요";
        String result = hangeulAutomata.assemble(input);
        System.out.println("result = " + result);

        Assert.assertEquals(expect, result);
    }

    @Test
    public void solution2() throws Exception {
        String input = "ㅁㅜㄱㅈㅈㅣㅂㅂㅏ";
        String expect = "묵찌빠";
        String result = hangeulAutomata.assemble(input);
        System.out.println("result = " + result);

        Assert.assertEquals(expect, result);
    }

    @Test
    public void solution3() throws Exception {
        String input = "ㄱㄱㅗㅣㄱㄱㅗㄹㅣ";
        String expect = "꾀꼬리";
        String result = hangeulAutomata.assemble(input);
        System.out.println("result = " + result);

        Assert.assertEquals(expect, result);
    }


}