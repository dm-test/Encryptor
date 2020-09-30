package com.github.dmtest.utils.cripto;

import java.util.ArrayList;
import java.util.List;

import static com.github.dmtest.utils.cripto.Command.ENCRYPT;

public class AffineCipher extends CesarCipher {
    private final int key1;

    public AffineCipher(int key1, int key2) {
        super(key2);
        this.key1 = key1;
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
        return (y != 0) ? gcd(y, x%y) : x;
    }

    public static void main(String[] args) {
        System.out.println((61) % 26);
    }

}
