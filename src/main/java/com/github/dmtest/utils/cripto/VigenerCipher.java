package com.github.dmtest.utils.cripto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VigenerCipher implements Cipher {
    private static final Logger LOG = LoggerFactory.getLogger(VigenerCipher.class);
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

    public String breakCipher(String inputText) {
        // Отберем из входящего текста только буквенные символы
        List<Character> inputLetters = inputText.toLowerCase().chars()
                .mapToObj(ch -> (char) ch)
                .filter(Character::isLetter)
                .collect(Collectors.toList());
        // Через индекс совпадений определяем длину ключа
        int keyLengthFounded = 2;
        for (; keyLengthFounded < inputText.length(); keyLengthFounded++) {
            double index = getIndexOfCoincidence(inputLetters, keyLengthFounded);
            if (index >= 0.0644) {
                break;
            }
        }
        LOG.info("Определена длина ключа: {} символов", keyLengthFounded);
        StringBuilder sb = new StringBuilder();
        // Отберем символы, которые шифровались одной и той же буквой ключа
        for (int i = 0; i < keyLengthFounded; i++) {
            List<Character> targetLetters = new ArrayList<>();
            for (int j = i; j < inputLetters.size(); j += keyLengthFounded) {
                targetLetters.add(inputLetters.get(j));
            }
            // Подсчитаем количество совпадений каждого символа
            Map<Character, Integer> freq = new HashMap<>();
            for (Character ch : targetLetters) {
                if (!freq.containsKey(ch)) {
                    freq.put(ch, 0);
                } else {
                    freq.put(ch, freq.get(ch) + 1);
                }
            }
            // Найдем символ, который повторяется чаще всего
            int maxValue = freq.values().stream().max(Integer::compareTo).get();
            Character targetChar = freq.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(maxValue))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .get();
            // В английском языке чаще всего повторяется буква e. Подсчитаем сдвиг и определим букву ключа
            int shift = ((targetChar - 'e') % ABC_CAPACITY + ABC_CAPACITY) % ABC_CAPACITY;
            char keyChar = (char) ('a' + shift);
            sb.append(keyChar);
        }
        LOG.info("Определено клюевое слово: {}", sb);
        return sb.toString();
    }

    public double getIndexOfCoincidence(List<Character> inputLetters, int keyLength) {
        List<Character> targetLetters = new ArrayList<>();
        for (int i = 0; i < inputLetters.size(); i += keyLength) {
            targetLetters.add(inputLetters.get(i));
        }
        long sum = 0;
        for (int i = 0; i < ABC_CAPACITY; i++) {
            char alphabetSymbol = (char) ('a' + i);
            long charCount = targetLetters.stream().filter(ch -> ch.equals(alphabetSymbol)).count();
            sum += charCount * (charCount - 1);
        }
        int znamenatel = targetLetters.size() * (targetLetters.size() - 1);
        double index = (double) sum / znamenatel;
        LOG.info("Индекс совпадений для текста с длиной ключа {} равен {}", keyLength, index);
        return index;
    }

}
