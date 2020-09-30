package com.github.dmtest.utils.cripto;

public interface Cipher {
    int ABC_CAPACITY = 26;

    default char shiftSymbol(char symbol, int shift) {
        if (!Character.isLetter(symbol)) {
            return symbol;
        }
        char baseSymbol = Character.isUpperCase(symbol) ? 'A' : 'a';
        int j = (symbol - baseSymbol + shift) % ABC_CAPACITY;
        return (char) (j + baseSymbol);
    }

    String processText(String text, Command cmd);

}
