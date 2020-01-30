package sol3;

import java.util.Arrays;

public class HangeulAutomata {
    // First '가' : 0xAC00(44032), 끝 '힟' : 0xD79F(55199)
    private final int FIRST_HANGUL = 44032;

    private final char[] CHOSUNG_LIST = {
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
            'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };
    private final String CHOSUNG_STRING = new String(CHOSUNG_LIST);

    private final char[] JUNGSUNG_LIST = {
            'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ',
            'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ',
            'ㅣ'
    };
    private final String JUNGSUNG_STRING = new String(JUNGSUNG_LIST);

    private final char[] JONGSUNG_LIST = {
            ' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ',
            'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ',
            'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };
    private final String JONGSUNG_STRING = new String(JONGSUNG_LIST);

    private final char[][] CHOSUNG_ATOM_LIST =
            {
                    {'ㄱ'}, {'ㄱ', 'ㄱ'}, {'ㄴ'}, {'ㄷ'}, {'ㄷ', 'ㄷ'}, {'ㄹ'}, {'ㅁ'},
                    {'ㅂ'}, {'ㅂ', 'ㅂ'}, {'ㅅ'}, {'ㅅ', 'ㅅ'}, {'ㅇ'}, {'ㅈ'}, {'ㅈ', 'ㅈ'}, {'ㅊ'}, {'ㅋ'}, {'ㅌ'},
                    {'ㅍ'}, {'ㅎ'}
            };
    private final char[][] JUNGSUNG_ATOM_LIST =
            {
                    {'ㅏ'}, {'ㅐ'}, {'ㅑ'}, {'ㅒ'}, {'ㅓ'}, {'ㅔ'}, {'ㅕ'}, {'ㅖ'},
                    {'ㅗ'}, {'ㅗ', 'ㅏ'}, {'ㅗ', 'ㅐ'}, {'ㅗ', 'ㅣ'}, {'ㅛ'}, {'ㅜ'}, {'ㅜ', 'ㅓ'}, {'ㅜ', 'ㅔ'},
                    {'ㅜ', 'ㅣ'}, {'ㅠ'}, {'ㅡ'}, {'ㅡ', 'ㅣ'}, {'ㅣ'}
            };
    private final char[][] JONGSUNG_ATOM_LIST =
            {
                    {}, {'ㄱ'}, {'ㄱ', 'ㄱ'}, {'ㄱ', 'ㅅ'}, {'ㄴ'}, {'ㄴ', 'ㅈ'},
                    {'ㄴ', 'ㅎ'}, {'ㄷ'}, {'ㄹ'}, {'ㄹ', 'ㄱ'}, {'ㄹ', 'ㅁ'}, {'ㄹ', 'ㅂ'}, {'ㄹ', 'ㅅ'}, {'ㄹ', 'ㅌ'},
                    {'ㄹ', 'ㅍ'}, {'ㄹ', 'ㅎ'}, {'ㅁ'}, {'ㅂ'}, {'ㅂ', 'ㅅ'}, {'ㅅ'}, {'ㅅ', 'ㅅ'}, {'ㅇ'}, {'ㅈ'},
                    {'ㅊ'}, {'ㅋ'}, {'ㅌ'}, {'ㅍ'}, {'ㅎ'}
            };

    public String assemble(String atomicJasoString) throws IllegalArgumentException {
        boolean isEmpty = atomicJasoString == null || atomicJasoString.trim().length() == 0;

        if (isEmpty)
            throw new IllegalArgumentException("입력된 자소가 없습니다.");

        StringBuilder assembledHangeul = new StringBuilder();
        String assembledJasoString = assembleJaso(atomicJasoString);

        char[] jasoArray = assembledJasoString.toCharArray();
        int startIndex = 0;

        while (startIndex < jasoArray.length) {
            final int assembleSize = getNextAssembleSize(jasoArray, startIndex);
            assembledHangeul.append(assemble(jasoArray, startIndex, assembleSize));
            startIndex += assembleSize;
        }

        return assembledHangeul.toString();
    }

    private String assemble(char[] jasoArray, final int startIndex, final int assembleSize) throws IllegalArgumentException {
        int unicode = FIRST_HANGUL;

        char chosung = jasoArray[startIndex];
        int chosungIndex = CHOSUNG_STRING.indexOf(chosung);

        if (chosungIndex < 0)
            throw new IllegalArgumentException((startIndex + 1) + "번째 자소가 한글 초성이 아닙니다");

        unicode += JONGSUNG_LIST.length * JUNGSUNG_LIST.length * chosungIndex;

        char jungsung = jasoArray[startIndex + 1];
        int jungsungIndex = JUNGSUNG_STRING.indexOf(jungsung);

        if (jungsungIndex < 0)
            throw new IllegalArgumentException((startIndex + 2) + "번째 자소가 한글 중성이 아닙니다");

        unicode += JONGSUNG_LIST.length * jungsungIndex;

        if (assembleSize > 2) {
            char jongsung = jasoArray[startIndex + 2];
            int jongsungIndex = JONGSUNG_STRING.indexOf(jongsung);

            if (jongsungIndex < 0)
                throw new IllegalArgumentException((startIndex + 3) + "번째 자소가 한글 종성이 아닙니다");

            unicode += jongsungIndex;
        }

        return Character.toString((char) unicode);
    }

    private int getNextAssembleSize(char[] jasoArray, int startIndex) throws IllegalArgumentException {
        final int remainJasoLength = jasoArray.length - startIndex;
        int assembleSize = 0;

        if (remainJasoLength < 2)
            throw new IllegalArgumentException("한글을 구성할 자소가 부족합니다.");

        if (remainJasoLength == 3 || remainJasoLength == 2)
            return remainJasoLength;

        int jungsungIndex = startIndex + 3;
        char junsung = jasoArray[jungsungIndex];
        String junsungString = String.valueOf(junsung);

        if (JUNGSUNG_STRING.contains(junsungString)) {
            assembleSize = 2;
        } else {
            assembleSize = 3;
        }

        return assembleSize;
    }

    private int findAtomicIndex(char[][] atomicJasoList, char[] atomicsJaso) {
        for (int i = 0; i < atomicJasoList.length; i++) {
            boolean isAssemble = Arrays.equals(atomicsJaso, atomicJasoList[i]);
            if (isAssemble) return i;
        }

        return -1;
    }

    private String assembleJaso(String atomicJasoString) {
        StringBuilder assembledJasoString = new StringBuilder();

        for (int i = 0; i < atomicJasoString.length(); i++) {
            char atomicJaso = atomicJasoString.charAt(i);
            char nextAtomicJaso = 0;
            boolean isLastIndex = i == atomicJasoString.length() - 1;

            if (!isLastIndex)
                nextAtomicJaso = atomicJasoString.charAt(i + 1);

            char[] atomicsJaso = {atomicJaso, nextAtomicJaso};
            int atomicIndex = -1;

            atomicIndex = findAtomicIndex(CHOSUNG_ATOM_LIST, atomicsJaso);
            if (atomicIndex > -1) {
                char jaso = CHOSUNG_LIST[atomicIndex];
                assembledJasoString.append(jaso);
                i++;
                continue;
            }

            atomicIndex = findAtomicIndex(JUNGSUNG_ATOM_LIST, atomicsJaso);
            if (atomicIndex > -1) {
                char jaso = JUNGSUNG_LIST[atomicIndex];
                assembledJasoString.append(jaso);
                i++;
                continue;
            }

            atomicIndex = findAtomicIndex(JONGSUNG_ATOM_LIST, atomicsJaso);
            if (atomicIndex > -1) {
                char jaso = JONGSUNG_LIST[atomicIndex];
                assembledJasoString.append(jaso);
                i++;
                continue;
            }

            assembledJasoString.append(atomicJaso);
        }

        return assembledJasoString.toString();
    }

}