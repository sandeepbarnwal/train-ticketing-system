package com.cloudbees.tms.common;

import java.util.Random;

public class TrainPnrGenerator {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random random = new Random();

    public static String generatePnr() {
        StringBuilder pnr = new StringBuilder();

        pnr.append(random.nextInt(9) + 1);

        for (int i = 0; i < 6; i++) {
            pnr.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }

        int sum = 0;
        for (int i = 0; i < pnr.length() - 1; i++) {
            char ch = pnr.charAt(i);
            if (Character.isDigit(ch)) {
                sum += Character.getNumericValue(ch);
            } else {
                sum += (ch - 'A') + 1;
            }
        }
        int checkDigit = (10 - (sum % 10)) % 10;
        pnr.append(checkDigit);

        return pnr.toString();
    }
}
