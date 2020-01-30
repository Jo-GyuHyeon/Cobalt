package sol2;


public class BigNumberFactorial {
    static final int MAX_DIGIT_SIZE = 500;

    public String factorial(String input) {
        validate(input);

        String[] splitInput = input.split("!");
        String stringNumber = splitInput[0];
        int number = Integer.parseInt(stringNumber);

        return factorial(number);
    }

    public String factorial(int number) {
        if (number <= 0)
            throw new IllegalArgumentException("0보다 큰 자연수로 입력해야 합니다.");

        StringBuilder result = new StringBuilder();
        int[] digits = new int[MAX_DIGIT_SIZE];
        digits[0] = 1;
        int digitsSize = 1;

        for (int i = 2; i <= number; i++)
            digitsSize = multiply(i, digits, digitsSize);

        for (int i = digitsSize - 1; i >= 0; i--)
            result.append(digits[i]);

        return result.toString();
    }

    private int multiply(int number, int[] digits, int digitsSize) {
        int carry = 0;

        for (int i = 0; i < digitsSize; i++) {
            int multiplyByDigit = digits[i] * number + carry;
            digits[i] = multiplyByDigit % 10;
            carry = multiplyByDigit / 10;
        }

        while (carry != 0) {
            digits[digitsSize] = carry % 10;
            carry /= 10;
            digitsSize++;
        }

        return digitsSize;
    }

    private void validate(String input) {
        boolean isEmpty = input == null || input.trim().length() == 0;
        if (isEmpty)
            throw new IllegalArgumentException("입력된 문자가 없습니다.");

        if (!input.contains("!"))
            throw new IllegalArgumentException("팩토리얼 '!' 문자가 포함되어 있지 않습니다.");

        String[] splitInput = input.split("!");
        if (splitInput.length > 1)
            throw new IllegalArgumentException("입력 형식이 올바르지 않습니다.");

    }
}
