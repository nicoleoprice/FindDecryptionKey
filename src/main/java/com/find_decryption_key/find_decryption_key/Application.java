package com.find_decryption_key.find_decryption_key;

import com.find_decryption_key.find_decryption_key.model.EncryptionData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

/**
 * This application will provide users with an interactive way to practice RSA encryption. The user is provided with a randomized modulus and corresponding p and q alongside the encryption key.
 * The goal is for the user to guess the decryption key within a set amount of time.
 * If the user guesses the decryption key correctly, they are granted access and the application will stop running.
 * If the user guesses the decryption key incorrectly, they will be notified of remaining guesses left and prompted to guess again. Once the attempts run out, the application will close.
 *
 * Each time the application is run, there is a new set of encryption data, which should be large enough to be feasibly solved by hand.
 */
//@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		//SpringApplication.run(Application.class, args);

		//For demonstration purposes, the interactivity will be in the console

		EncryptionData secretData = new EncryptionData();

		System.out.println("------------------------------------------------\n");

		int attempts = 3;
		int decryptionGuess;
		boolean hasAccess = false;

		Scanner input = new Scanner(System.in);
		System.out.println("You have found out that the modulus to be " + secretData.getModulus() + " = " + secretData.getP() + " * " + secretData.getQ() +
							" and the encryption key to be " + secretData.getEncryptionKey() +
							". Using RSA, input the decryption key to hack into the system: ");

		while (attempts >= 0 && !hasAccess) {
			decryptionGuess = input.nextInt();

			if(decryptionGuess != secretData.getDecryptionKey()) {
				System.out.printf("Incorrect. You have %d attempts remaining.\n", attempts);
			} else {
				System.out.println("ACCESS GRANTED");
				hasAccess = true;
			}

			attempts--;
		}

		if(!hasAccess) {
			System.out.println("You have failed to access the system and will now be locked out.");
		}

		input.close();
	}

}
