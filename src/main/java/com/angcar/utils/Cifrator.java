package com.angcar.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;

public class Cifrator {

    private static Cifrator cifratorInstance;

    private Cifrator() {
    }

    public static Cifrator getInstance() {
        if (cifratorInstance == null) {
            cifratorInstance = new Cifrator();
        }
        return cifratorInstance;
    }

    public Optional<String> sha256(String cadena) {
        MessageDigest md = null;
        byte[] hash = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            hash = md.digest(cadena.getBytes(StandardCharsets.UTF_8));
        } catch (Exception ex) {
            System.err.println("Error al cifrar en SHA-256: " + ex.getMessage());
            System.exit(1);
        }

        if (hash == null){
            return Optional.empty();
        }else{
            return Optional.of(convertToHex(hash));
        }

    }

    private String convertToHex(byte[] raw) {
        StringBuilder sb = new StringBuilder();
        for (byte b : raw) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}