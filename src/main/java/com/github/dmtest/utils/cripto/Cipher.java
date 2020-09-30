package com.github.dmtest.utils.cripto;

public interface Cipher {
    int ABC_CAPACITY = 26;

    char encryptSymbol(char originalChar);
    char decryptSymbol(char originalChar);

    default String processText(String text, Command cmd) {
        StringBuilder sb = new StringBuilder();
        char[] inputChars = text.toCharArray();
        for (char ch : inputChars) {
            char outputChar;
            if (!Character.isLetter(ch)) {
                outputChar = ch;
            } else if (cmd.equals(Command.ENCRYPT)) {
                outputChar = encryptSymbol(ch);
            } else {
                outputChar = decryptSymbol(ch);
            }
            sb.append(outputChar);
        }
        return sb.toString();
    }

}
