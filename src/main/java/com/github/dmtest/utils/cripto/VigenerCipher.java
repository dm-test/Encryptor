package com.github.dmtest.utils.cripto;

public class VigenerCipher implements Cipher {
    private final char[] keyChars;
    private final int keyLength;
    private int keyCharPosition;

    public VigenerCipher(String key) {
        keyChars = key.toCharArray();
        keyLength = key.length();
        keyCharPosition = 0;
    }

    @Override
    public char encryptSymbol(char originalChar) {
        int originalCharOffset = getOffset(originalChar);
        int originalCharIndex = originalChar - originalCharOffset;
        char keyChar = keyChars[getKeyCharPosition()];
        int shift = keyChar - getOffset(keyChar);
        char newChar = (char)((originalCharIndex + shift) % ABC_CAPACITY + originalCharOffset);
        keyCharPosition++;
        return newChar;
    }

    @Override
    public char decryptSymbol(char originalChar) {
        int originalCharOffset = getOffset(originalChar);
        int originalCharIndex = originalChar - originalCharOffset;
        char keyChar = keyChars[getKeyCharPosition()];
        int shift = keyChar - getOffset(keyChar);
        char newChar = (char)((originalCharIndex - shift + ABC_CAPACITY) % ABC_CAPACITY + originalCharOffset);
        keyCharPosition++;
        return newChar;
    }

    private int getOffset(char ch) {
        return Character.isUpperCase(ch) ? 'A' : 'a';
    }

    private int getKeyCharPosition() {
        if (keyCharPosition == keyLength) {
            keyCharPosition = 0;
        }
        return keyCharPosition;

    }

}
