package com.github.dmtest.utils.cripto;

public class GammaCipher implements Cipher {
    private static final int A = 3;
    private static final int B = 2;
    private static final int M = 40692;
    private int key;

    public GammaCipher(int startKey) {
        key = startKey;

    }

    @Override
    public String processText(String text, Command cmd) {
        StringBuilder sb = new StringBuilder();
        char[] inputChars = text.toCharArray();
        for (char ch : inputChars) {
            char outputChar;
            if (cmd.equals(Command.ENCRYPT)) {
                outputChar = encryptSymbol(ch);
            } else {
                outputChar = decryptSymbol(ch);
            }
            sb.append(outputChar);
        }
        return sb.toString();
    }

    @Override
    public char encryptSymbol(char originalChar) {
        char ch = (char) (originalChar ^ key);
        nextValue();
        return ch;
    }

    @Override
    public char decryptSymbol(char originalChar) {
        return encryptSymbol(originalChar);
    }

    private void nextValue() {
        key = (key * A + B) % M;
    }
}
