package com.find_decryption_key.find_decryption_key.model;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * T
 */
public class EncryptionData {
    private int modulus;
    private int q;
    private int p;
    private int encryptionKey;
    private int decryptionKey;
    private int phi;
    private ArrayList<Integer> pFactors;
    private ArrayList<Integer> qFactors;

    EncryptionData() {
        p = findP();
        q = findQ();
        modulus = p * q;
        phi = (p - 1) * (q - 1);
        encryptionKey = findEncryptionKey();
        decryptionKey = findDecryption();
    }

    private int findP() {
        //pick a random number and store the factors to compare later
        p = ThreadLocalRandom.current().nextInt(5, 50);

       pFactors = findFactors(p);

        return p;
    }

    /**
     * p is found first. The factors of q will be passed to isRelativelyPrime alongside factors of p to see if they can be paired together
     * @return q
     */
    private int findQ() {
        //since p has been established, q will be compared until a suitable pair is found
        boolean qFound = false;

        while(!qFound){
            q = ThreadLocalRandom.current().nextInt(5, 50);
            if(q != p) {
                //find factors of q
                qFactors = findFactors(q);

                //if q is relatively prime to p then this q will be used
                if(isRelativelyPrime(qFactors, pFactors)) {
                    qFound = true;
                }
            }
        }

        return q;
    }

    /**
     * Relatively Prime: when two numbers only have a common factor of 1
     * All factors except for 1 are within the ArrayLists to be compared
     *
     * @param oneFactors = the first of the two factors to be compared
     * @param twoFactors = the second of the two factors to be compared
     * @return true if they are relatively prime
     */
    private boolean isRelativelyPrime(ArrayList<Integer> oneFactors, ArrayList<Integer> twoFactors) {
        //to take into account varying sizes, pick the smallest size
        int sizeToCompare;

        //if one list is empty, it means that they are prime
        if(oneFactors.isEmpty() || twoFactors.isEmpty()) return true;

        //compare if the factor is contained within the other list
        if(oneFactors.size() > twoFactors.size()) {
            sizeToCompare = twoFactors.size();

            //go through the smallest list (twoFactors)
            for (int i = 0; i < sizeToCompare; i++) {
                if(oneFactors.contains(twoFactors.get(i))) {
                    return false;
                }
            }

        } else {
            //compares if oneFactors is smaller
            sizeToCompare = oneFactors.size();

            for (int i = 0; i < sizeToCompare; i++) {
                if(twoFactors.contains(oneFactors.get(i))) {
                    return false;
                }
            }
        }

        //if no matching factors are found, then they are relatively prime
        return true;
    }

    /**
     * This method is used to find an array of factors for p, q, phi, and encryptionKey
     * @param num can represent p to find pFactors, q to find qFactors, encryptionKey to find encryptionKeyFactors, phi to find phiFactors
     * @return factors ArrayList<Integer>
     */
    private ArrayList<Integer> findFactors(int num) {
        ArrayList<Integer> toReturn = new ArrayList<>();

        //store factors, only need to go halfway through the number to find factors
        for(int i = 1; i <= num / 2; i++) {

            //since we are comparing the factors other than 1, 1 can be skipped
            if (num % i == 0 && i != 1) {
                toReturn.add(i);
            }
        }
        return toReturn;
    }

    /**
     * The encryption key can be any number that does not have any common factors with phi AND is less than the modulus
     * @return encryptionKey
     */
    private int findEncryptionKey() {
        //find factors of phi
        ArrayList<Integer> phiFactors = findFactors(phi);
        ArrayList<Integer> encryptionKeyFactors;
        boolean encryptionKeyFound = false;

        while(!encryptionKeyFound) {
            //pick random number that is less than the modulus and greater than 1
            encryptionKey = ThreadLocalRandom.current().nextInt(2, modulus - 1);
            encryptionKeyFactors = findFactors(encryptionKey);

            //compare factors and double check that the encryptionKey is less than the modulus (some testing showed that it would not be for some reason)
            if(isRelativelyPrime(phiFactors, encryptionKeyFactors) && encryptionKey < modulus) {
                encryptionKeyFound = true;
            }
        }

        return encryptionKey;
    }

    /**
     * To find the decryption key, it would be (encryptionKey * decryptionKey) mod(phi) = 1
     * The decryption key does not need to be relatively prime to (mod n), unlike the encryption key
     *
     * @return decryptionKey
     */

    private int findDecryption() {
        int[] gcdCoefficients = extendedEuclidean(phi, encryptionKey);

        decryptionKey = Math.abs(gcdCoefficients[2]);

        //verify that (encryptionKey * decryptionKey) mod(phi) = 1
        boolean verify = verifyGcd((encryptionKey * decryptionKey), phi);
        if(verify) {
            System.out.println("This decryption key has been verified.");
        } else {
            System.out.println("This decryption key will not work since the remainder is not 1 when (e * d) mod (phi) = 1.");
        }

        return decryptionKey;
    }


    /**
     * Bezout's identity = ax + by = gcd(a,b)
     * When the Euclidean Algorithm reverses and substitutes previous remainders, the end should be 1 = (encryptionKey)(decryptionKey) + (phi)(y)
     *
     * @return int[] {gcd, a, b}
     */
    private int[] extendedEuclidean(int x, int y) {

        if(y == 0) {
            return new int[] {x, 1, 0};
        } else if (x >= y && y > 0){
            int[] current = extendedEuclidean(y, x % y);
            int gcd = current[0];
            int a = current[2];

            int b = current[1] - (x/y) * current[2];
            return new int[] {gcd, a, b};
        }
        return new int[] {0,0,0};
    }

    /**
     * This method is to verify that gcd((encryptionKey * decryptionKey), phi) = 1
     * @return this should return 1
     */
    private boolean verifyGcd(int a, int b) {
        int remainder = a % b;
        if(remainder != 1) {
            remainder = phi - remainder;
        }
        System.out.println("The remainder when verifying the decryption key is: " + remainder);

        return remainder == 1;
    }

    public int getModulus() {
        return modulus;
    }

    public void setModulus(int modulus) {
        this.modulus = modulus;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(int encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public int getDecryptionKey() {
        return decryptionKey;
    }

    public void setDecryptionKey(int decryptionKey) {
        this.decryptionKey = decryptionKey;
    }

    public int getPhi() {
        return phi;
    }

    public void setPhi(int phi) {
        this.phi = phi;
    }


    public ArrayList<Integer> getPFactors() {
        return pFactors;
    }

    public void setPFactors(ArrayList<Integer> pFactors) {
        this.pFactors = pFactors;
    }

    public ArrayList<Integer> getQFactors() {
        return qFactors;
    }

    public void setQFactors(ArrayList<Integer> qFactors) {
        this.qFactors = qFactors;
    }

    @Override
    public String toString() {
        return "Encryption Key: " + encryptionKey +
                "\nDecryption Key: " + decryptionKey +
                "\nModulus: " + modulus;
    }
}
