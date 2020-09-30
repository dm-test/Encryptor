package com.github.dmtest.utils.cripto;

import static com.github.dmtest.utils.cripto.Command.ENCRYPT;

public class CesarCipher implements Cipher {
    protected final int offset;

    public CesarCipher(int offset) {
        this.offset = offset;
    }

    @Override
    public String processText(String text, Command cmd) {
        int resultOffset = cmd.equals(ENCRYPT) ? offset : -offset;
        int shift = resultOffset % ABC_CAPACITY + ABC_CAPACITY;
        StringBuilder sb = new StringBuilder();
        char[] inputChars = text.toCharArray();
        for (char ch : inputChars) {
            char outputChar = shiftSymbol(ch, shift);
            sb.append(outputChar);
        }
        return sb.toString();
    }


}
