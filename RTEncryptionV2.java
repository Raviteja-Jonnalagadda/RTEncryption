/*
 * Copyright 2025 Raviteja Jonnalagadda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.enc.RTE;

import java.security.SecureRandom;

/**
 * RTEncryptionV2 provides simple symmetric encryption and decryption methods
 * based on ASCII value transformations, random key generation, and string manipulation.
 * <p>
 * The encryption process involves converting each character to its ASCII code,
 * multiplying by a randomly generated key, and embedding this transformed data into an obfuscated string.
 * The decryption reverses this process to retrieve the original message.
 * </p>
 * 
 * Created on 11 August 2025
 * 
 * @author Raviteja J
 */
public class RTEncryptionV2 {
	 /**
     * Separator used to delimit ASCII-encoded segments in the encrypted string.
     */
	private final static String separator = "߀"; // U+07C0
	 /**
     * Encrypts a plain text string into an obfuscated ASCII-based encrypted string.
     * <p>
     * The method performs the following steps:
     * <ul>
     *   <li>Generates a random key between 10 and 99.</li>
     *   <li>Converts each character in the input string to its ASCII code, multiplies by the key, and appends with separators.</li>
     *   <li>Calculates a check digit based on the last digit of the concatenated string.</li>
     *   <li>Inserts the key into the encrypted string at position determined by the check digit.</li>
     * </ul>
     * </p>
     * 
     * @param pval The plaintext string to be encrypted.
     * @return The encrypted string, or "NullValue" if input is invalid.
     * @author Raviteja J
     */
	public static String asciiencrypt(String pval) {
		String eval = null;
		if (pval.trim().isEmpty() || pval == null || pval.length() <= 0) {
			return "NullValue";
		}
		/**
		 * Block 1 Chnaging the recived value to the ASCII Code And Multiplying each
		 * value random generated value
		 */
		StringBuilder envval = new StringBuilder();
		SecureRandom srm = new SecureRandom();
		int key = srm.nextInt(90) + 10;
		//System.out.println(key);
		try {
			for (int i = 0; i < pval.length(); i++) {
				int temp = (int) pval.charAt(i);
				int ftemp = temp * key;
				envval.append(ftemp).append(separator);
			}
		} catch (Exception e) {
			System.err.println("Error in Block 1 --->   " + e);
			return "ERRBL1~" + e;
		}
		String encval = envval.toString().substring(0, envval.toString().length() - 1);
		/**
		 * Block 3 The key is mixed with the encrypted value based on the last digit
		 * 'CheckDigite'
		 */
		try {
			String lastnum = encval.toString().substring(encval.toString().length() - 1);
			int checkdigit = Integer.parseInt(lastnum);
			int count = 0;
			StringBuilder keymixer = new StringBuilder();
			for (int i = 0; i < encval.toString().length(); i++) {
				if (count == checkdigit) {
					keymixer.append(key);
				}
				keymixer.append(encval.toString().charAt(i));
				count++;
			}
			eval = keymixer.toString();
		} catch (Exception e) {
			System.err.println("Error in Block 2 --->   " + e);
			return "ERRBL2~" + e;
		}
		return eval;
	}

	/**
     * Decrypts an encrypted string produced by {@link #asciiencrypt(String)} into the original message.
     * <p>
     * The method performs the following steps:
     * <ul>
     *   <li>Extracts the embedded key from the encrypted string based on the check digit.</li>
     *   <li>Removes the key from the string to retrieve the ASCII-encoded data.</li>
     *   <li>Splits the data into segments, divides each by the key to recover ASCII codes, and reconstructs the original message.</li>
     * </ul>
     * </p>
     * 
     * @param pval The encrypted string to be decrypted.
     * @return The original plaintext message, or "NullValue" if input is invalid.
     * @author Raviteja J
     */
	public static String asciidecrypt(String pval) {
		String dval = null;
		if (pval.trim().isEmpty() || pval == null || pval.length() <= 0) {
			return "NullValue";
		}
		/**
		 * Block 1 Extract the Key from the Encrypted value
		 */
		String key = null;
		StringBuilder tmp1 = new StringBuilder();
		try {
			int checkdigit = Integer.valueOf(pval.substring(pval.length() - 1));
			for (int i = 0; i < pval.length(); i++) {
				if (i == checkdigit) {
					key = String.valueOf(pval.charAt(i));
				} else if (i == checkdigit + 1) {
					key = key + String.valueOf(pval.charAt(i));
				} else {
					tmp1.append(pval.charAt(i));
				}
			}
		} catch (Exception e) {
			System.err.println("Error in Block 1 --->   " + e);
			return "ERRBL1~" + e;
		}
		/**
		 * Block 2 Getting the Actuall value or the Actuall Message from the Decrypted
		 * value by dividing the key with the value
		 */
		try {
			String asciival = tmp1.toString();
			int ky = Integer.parseInt(key);
			String[] parts = asciival.toString().split(String.valueOf(separator));
			StringBuilder fnval = new StringBuilder();
			for (String part : parts) {
				if (!part.isEmpty()) {
					int decrypted = Integer.parseInt(part) / ky;
					char originalChar = (char) decrypted;
					fnval.append(originalChar);
				}
			}
			dval = fnval.toString();
		} catch (Exception e) {
			System.err.println("Error in Block 2 --->   " + e);
			return "ERRBL2~" + e;
		}
		return dval;
	}
	
	/**
	 * Main method demonstrating the usage of encryption and decryption.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		String planevalue = "Raviteja123@123";
		System.out.println("plane value Value is --->  " + planevalue);
		String encval = asciiencrypt(planevalue);
		System.out.println("Encrypted Value is --->  " + encval);
		String decval = asciidecrypt(encval);
		System.out.println("Decrypted Value is --->  " + decval);
	}
}

