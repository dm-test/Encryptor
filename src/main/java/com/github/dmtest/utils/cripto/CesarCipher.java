package com.github.dmtest.utils.cripto;

public class CesarCipher implements Cipher {
    protected final int shift;

    public CesarCipher(int shift) {
        this.shift = shift;
    }

    @Override
    public char encryptSymbol(char originalChar) {
        int offset = Character.isUpperCase(originalChar) ? 'A' : 'a';
        return (char)((originalChar + shift - offset) % ABC_CAPACITY + offset);
    }

    @Override
    public char decryptSymbol(char originalChar) {
        int offset = Character.isUpperCase(originalChar) ? 'A' : 'a';
        int indexTmp1 = (originalChar - shift - offset) % ABC_CAPACITY;
        int indexTmp2 = indexTmp1 < 0 ? indexTmp1  + ABC_CAPACITY : indexTmp1;
        return (char) (indexTmp2 + offset);
    }
}
