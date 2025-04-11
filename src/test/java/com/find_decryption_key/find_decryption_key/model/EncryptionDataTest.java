package com.find_decryption_key.find_decryption_key.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionDataTest {

    @Test
    public void testFindP() {
        EncryptionData test = new EncryptionData();
        System.out.println(test.getP());
        assertTrue(test.getP() > 5 && test.getP() < 100);
    }

    @Test
    public void testFindPFactors() {
        EncryptionData test = new EncryptionData();
        System.out.println(test.getpFactors());
        assertTrue(test.getpFactors().get(0) != 1);
    }

    @Test
    public void testFindQ() {

    }
}