# VigenereCipher
Cracking the Vigenere Cipher using the basic principles of Caesar Cipher and Java as the programming language.
🔐 Vigenère Cipher – Encryption, Decryption & Automatic Breaker

This project provides a complete Java implementation of the Vigenère Cipher, including:

VigenereCipher.java – Encrypts & decrypts using a user-supplied key

VigenereDecryption.java – Performs decryption using a known key

VigenereBreaker.java – Automatically breaks an encrypted Vigenère message using frequency analysis, dictionaries, and key-length guessing

It is designed for learning classical cryptography, algorithm design, dictionary-based cracking, and modular Java programming.

📁 Project Structure
├── VigenereCipher.java        # Core Vigenère cipher implementation
├── VigenereDecryption.java    # Decrypts text with a provided key
├── VigenereBreaker.java       # Breaks cipher automatically via analysis
└── README.md

✨ Features
🔸 VigenereCipher

Encrypts text with a multi-character key

Decrypts using the same key

Handles alphabet wrapping

Implements modular arithmetic on characters

🔸 VigenereDecryption

Reads encrypted input

Accepts key as an array of shifts

Produces full plaintext output

Useful for testing known keys

🔸 VigenereBreaker

Attempts to crack the cipher without knowing the key

Uses:

Slicing text into Caesar-shifted segments

Frequency analysis

Dictionary-based scoring

Iterative key length testing

Detects the best key by maximizing valid-word matches

Prints preview of decrypted text

🚀 Getting Started
Prerequisites

Java 8+

A dictionary file (e.g., dictionary.txt)

An encrypted message file

Compile
javac VigenereCipher.java VigenereDecryption.java VigenereBreaker.java

Run (Cipher)
java VigenereCipher

Run (Decrypt with Known Key)
java VigenereDecryption

Run (Breaker – automatic cracking)
java VigenereBreaker

🧠 How the Cipher Works

The Vigenère cipher applies repeated Caesar shifts based on a key:

Plaintext:  HELLOWORLD
Key:        KEYKEYKEYK
Ciphertext: RIJVSUYVJN


This project modularizes:

Character shifting

Key iteration

Frequency scoring

Dictionary checks

📊 How the Breaker Works

The automated breaker:

Guesses key lengths (1–100 or user-defined)

Splits cipher text into segments shifted by each key index

Applies Caesar-breaking to each slice

Builds candidate keys

Scores decryptions using dictionary word-count

Outputs best-scoring key + decrypted text snippet

This is similar to the classical Kasiski + Friedman combined approach.

📎 Example Output (from VigenereBreaker)
Trying key length: 5
Valid words count = 423
Guessed key: [17, 4, 2, 19, 14]
Decrypted preview:
THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG...

🛠️ Customization

You can modify:

Maximum key length in tryKeyLength loops

Dictionary file for multilingual support

Alphabet handling for punctuation or lowercase

Debug prints to see intermediate key guesses

📄 License

This project is open-source and free to use for educational and research purposes.
