package com.find_decryption_key.find_decryption_key.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * the variables are randomized, so the best way to ensure that they are true is if the variables are outputted in a print statement
 * Re-running tests will produce different numbers
 */
class EncryptionDataTest {

    @Test
    public void testFindP() {
        EncryptionData test = new EncryptionData();
        System.out.println("P: " + test.getP() + " = " + test.getPFactors());
        assertTrue(test.getP() > 5 && test.getP() < 100);
    }

    @Test
    public void testFindQ() {
        EncryptionData test = new EncryptionData();
        System.out.println("P: " + test.getP() + " = " + test.getPFactors());
        System.out.println("Q: " + test.getQ() + " = " + test.getQFactors());
    }

    @Test
    public void getModulus() {
        EncryptionData test = new EncryptionData();
        System.out.println("P: " + test.getP() + " = " + test.getPFactors());
        System.out.println("Q: " + test.getQ() + " = " + test.getQFactors());

        int testModulus = test.getP() * test.getQ();

        assertEquals(testModulus, test.getModulus());
    }

    @Test
    public void getPhi() {
        EncryptionData test = new EncryptionData();
        System.out.println("P: " + test.getP() + " = " + test.getPFactors());
        System.out.println("Q: " + test.getQ() + " = " + test.getQFactors());

        int testPhi = (test.getP() - 1) * (test.getQ() - 1);
        System.out.println("Phi: " + testPhi);

        assertEquals(testPhi, test.getPhi());
    }

    @Test
    public void testFindEncryptionKey() {
        EncryptionData test = new EncryptionData();
        System.out.println("P: " + test.getP() + " = " + test.getPFactors());
        System.out.println("Q: " + test.getQ() + " = " + test.getQFactors());
        System.out.println("Phi: " + test.getPhi());
        System.out.println("Encryption Key: " + test.getEncryptionKey());

        assertTrue(test.getEncryptionKey() < test.getModulus());
    }

    @Test
    public void testFindDecryptionKey() {
        EncryptionData test = new EncryptionData();
        System.out.println("Phi: " + test.getPhi());
        System.out.println("Encryption Key: " + test.getEncryptionKey());
        System.out.println("Decryption Key: " + test.getDecryptionKey());
    }

}