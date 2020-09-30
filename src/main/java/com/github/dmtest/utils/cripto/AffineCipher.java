package com.github.dmtest.utils.cripto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AffineCipher implements Cipher {
    private final int key1;
    private final int key2;

    public AffineCipher(int key1, int key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    @Override
    public char encryptSymbol(char originalChar) {
        int offset = Character.isUpperCase(originalChar) ? 'A' : 'a';
        return (char)((key1 * originalChar + key2 - offset) % ABC_CAPACITY + offset);
    }

    @Override
    public char decryptSymbol(char originalChar) {
        int offset = Character.isUpperCase(originalChar) ? 'A' : 'a';
        int indexTmp1 = modInverse(key1) * (originalChar - key2 - offset) % ABC_CAPACITY;
        int indexTmp2 = indexTmp1 < 0 ? indexTmp1  + ABC_CAPACITY : indexTmp1;
        return (char) (indexTmp2 + offset);
    }

    public static List<Integer> getAvailableKey1List() {
        List<Integer> availableKeys = new ArrayList<>();
        for (int i = 1; i < ABC_CAPACITY; i++) {
            if (gcd(ABC_CAPACITY, i) == 1) {
                availableKeys.add(i);
            }
        }
        return availableKeys;
    }

    private static int gcd(int x, int y){
        return (y != 0) ? gcd(y, x % y) : x;
    }

    public static int modInverse(int value) {
       return BigInteger.valueOf(value).modInverse(BigInteger.valueOf(ABC_CAPACITY)).intValue();
    }
}
