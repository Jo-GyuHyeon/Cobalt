package sol1;

public class Random {
    static final int BITS = 31;

    public int getRandom(int bound) {
        if (bound <= 0)
            throw new IllegalArgumentException("0보다 큰 자연수로 입력 해야 됩니다.");

        int randomBit = getRandomBit(BITS);

        if ((bound & bound - 1) == 0)
            return (int) ((bound * (long) randomBit) >> 31);

        int value = randomBit % bound;

        while (value < 0 || value > (bound - 1)) {
            randomBit = getRandomBit(BITS);
            value = randomBit % bound;
        }

        return value;
    }

    private int getRandomBit(int bits) {
        int nextBit = 0;

        for (int i = 0; i < bits; i++) {
            nextBit <<= 1;
            nextBit |= getZeroOrOne();
        }

        return nextBit;
    }

    private int getZeroOrOne() {
        return (int) Math.round(Math.random());
    }

}