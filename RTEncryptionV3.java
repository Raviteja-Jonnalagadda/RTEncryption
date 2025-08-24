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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The {@code RTEncryptionV3} class provides a custom encryption and decryption mechanism
 * for strings. It converts input strings into ASCII representations, maps specific characters
 * to unique counterparts, and allows reversing the process to retrieve the original message.
 * <p>
 * This class is designed to demonstrate a simple, custom encoding scheme and may not be suitable
 * for secure encryption purposes.
 * </p>
 * 
 * <p><strong>Author:</strong> Raviteja J</p>
 * <p><strong> Created on: </strong> 2024-04-27</p>
 * <p><strong>Usage example:</strong></p>
 * <pre>
 *     String original = "Raviteja123!@#";
 *     String encrypted = RTEncryptionV3.Midencrypt(original);
 *     String decrypted = RTEncryptionV3.Middecrypt(encrypted);
 * </pre>
 */
public class RTEncryptionV3 {

	  
    /**
     * Separator used to delimit ASCII-encoded segments within the encrypted string.
     * This character is a stylized 'm' (Unicode U+026F) to minimize collision with typical input.
     */
	private final static String separator = "ɯ"; // U+026F — Latin small letter turned m, looks like a stylized 'm'.
	/**
     * Immutable map that defines the character mapping used during encoding.
     * Maps digits ('0'-'9') and the separator character to specific alphabetic characters.
     */
	private static final Map<Character, Character> hm;
	/**
     * Static initializer block to populate the character mapping {@code hm}.
     * Maps numeric characters and the separator to designated characters.
     * 
     */
	static {
		Map<Character, Character> encchar = new HashMap<Character, Character>();
		encchar.put('0', 'A');
		encchar.put('1', 'B');
		encchar.put('2', 'C');
		encchar.put('3', 'D');
		encchar.put('4', 'E');
		encchar.put('5', 'F');
		encchar.put('6', 'G');
		encchar.put('7', 'H');
		encchar.put('8', 'I');
		encchar.put('9', 'J');
		encchar.put('ɯ', 'R');
		hm = Collections.unmodifiableMap(encchar);
	}

	  /**
     * Encrypts a plain text string into a custom encoded string.
     * <p>
     * The process involves two main steps:
     * <ul>
     *   <li>Converting each character into its ASCII value, separated by a designated separator.</li>
     *   <li>Encoding the ASCII string by replacing specific characters with mapped characters.</li>
     * </ul>
     * </p>
     * <p><strong>Author:</strong> Raviteja J</p>
     * @param pval the input string to be encrypted; if null, returns "NULVAL".
     * @return the encrypted string representing the input.
     */
	public static String Midencrypt(String pval) {
		String eval = null;

		if (pval.trim().isEmpty() || pval == null || pval.length() <= 0) {
			return "NULVAL";
		}
		StringBuffer ascii = new StringBuffer();
		/**
         * Block 1: Convert each character in the input string to its ASCII value.
         * Each ASCII value is appended to the StringBuffer, separated by the separator.
         */
		try {
			for (int i = 0; i < pval.length(); i++) {
				int tpac = (int) pval.charAt(i);
				ascii.append(separator).append(tpac);
			}
		} catch (Exception e1) {
			System.err.println("[Midencrypt] Error in Block 1 ---> [ " + e1.toString() + " ]");
		}
		/**
         * Block 2: Encode the ASCII string by replacing specific characters
         * with their mapped counterparts using the {@link #converter} method.
         */
		try {
			eval = converter(ascii.toString().substring(1));
		} catch (Exception e2) {
			System.err.println("[Midencrypt] Error in Block 2 ---> [ " + e2.toString() + " ]");
		}

		return eval;
	}

    /**
     * Encodes a string by replacing certain characters (digits and separator) with their mapped characters.
     *
     * @author Raviteja J
     * @param a the string to encode
     * @return the encoded string with specific characters replaced.
     */
	public static String converter(String a) {
		StringBuffer cnv = new StringBuffer();
		Set<Character> enckey = hm.keySet();
		for (int i = 0; i < a.length(); i++) {
			char tempval = a.charAt(i);
			if (enckey.contains(tempval)) {
				cnv.append(hm.get(tempval));
			} else {
				cnv.append(tempval);
			}
		}
		return cnv.toString();
	}

	 /**
     * Decodes a string by reversing the character mapping back to the original characters.
     *
     * @author Raviteja J
     * @param a the string to decode
     * @return the original string with replaced characters reverted to their original form.
     */
	public static String deconverter(String a) {
		StringBuffer cnv = new StringBuffer();
		HashMap<Character, Character> rev = new HashMap<Character, Character>();
		Set<Character> enckey = hm.keySet();
		for (char tc : enckey) {
			rev.put(hm.get(tc), tc);
		}
		for (int i = 0; i < a.length(); i++) {
			char tempval = a.charAt(i);
			if (rev.containsKey(tempval)) {
				cnv.append(rev.get(tempval));
			} else {
				cnv.append(tempval);
			}
		}
		return cnv.toString();
	}

	 /**
     * Decrypts an encrypted string back into its original message.
     * Reverses the encoding and ASCII conversion to recover the original text.
     * 
     * The process involves:
     * <ul>
     *   <li>Decoding the string by reversing character mappings using {@link #deconverter}.</li>
     *   <li>Splitting the ASCII values by the separator and converting each back to characters.</li>
     * </ul>
     * @author Raviteja J
     * @param a the encrypted string to decrypt.
     * @return the original message string.
     */
	public static String Middecrypt(String a) {
		String dval = null;
		StringBuffer tdec = new StringBuffer();
		 /**
         * Block 1: Decode the input string back to ASCII values.
         */
		String asvl = null;
		try {
			asvl = deconverter(a);
		} catch (Exception e1) {
			System.err.println("[Middecrypt] Error in Block 1 ---> [ " + e1.toString() + " ]");
		}

        /**
         * Block 2: Convert ASCII values back to characters to reconstruct the original message.
         */
		try {
			String[] tval = asvl.split(separator);
			for (String key : tval) {
				int temp = Integer.parseInt(key);
				char tc = (char) temp;
				tdec.append(tc);
			}
			dval = tdec.toString();
		} catch (Exception e2) {
			System.err.println("[Middecrypt] Error in Block 2 ---> [ " + e2.toString() + " ]");
		}
		return dval;
	}

	 /**
     * Main method demonstrating the encryption and decryption process.
     * Outputs the original, encrypted, and decrypted strings to the console.
     *
     * @param args command line arguments (not used)
     */
	public static void main(String[] args) {
		String val = "Raviteja123!@#";
		System.out.println("Plane Value is -->  "+val);
		String encval = Midencrypt(val);
		System.out.println("Encrypted value is -->  " + encval);
		String decval = Middecrypt(encval);
		System.out.println("Decrypted value is -->  " + decval);
	}

}