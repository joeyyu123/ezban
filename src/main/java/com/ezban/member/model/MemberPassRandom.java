package com.ezban.member.model;

import java.security.SecureRandom;

public class MemberPassRandom {
	
	private static final SecureRandom RANDOM = new SecureRandom();

    public static String generatePassword(int length) {
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(charSet.length());
            sb.append(charSet.charAt(index));
        }
        return sb.toString();
    }

    public static String generateVerificationCode() {
        return String.format("%06d", RANDOM.nextInt(1000000));
    }

}

