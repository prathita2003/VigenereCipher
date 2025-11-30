import java.io.*;
import java.util.*;

public class VigenereDecryption {
    public static HashSet<String> loadDictionary(String filename) throws IOException {
        HashSet<String> words = new HashSet<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim().toLowerCase();
            if (!line.isEmpty()) words.add(line);
        }
        br.close();
        return words;
    }

    public static String readFile(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        return sb.toString().trim();
    }

    public static String decrypt(String encrypted, int[] key) {
        StringBuilder decrypted = new StringBuilder();
        int keyIndex = 0;
        for (char c : encrypted.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int shift = key[keyIndex % key.length];
                char decryptedChar = (char) ((c - base - shift + 26) % 26 + base);
                decrypted.append(decryptedChar);
                keyIndex++;
            } else {
                decrypted.append(c);
            }
        }
        return decrypted.toString();
    }

    public static int countValidWords(String text, HashSet<String> dictionary) {
        String[] words = text.split("\\W+");
        int count = 0;
        for (String word : words) {
            if (word.length() > 1 && dictionary.contains(word.toLowerCase())) count++;
        }
        return count;
    }

    public static int[] tryKeyLength(String encrypted, int keyLength, HashSet<String> dictionary) {
        int[] key = new int[keyLength];
        for (int i = 0; i < keyLength; i++) {
            StringBuilder slice = new StringBuilder();
            for (int j = i; j < encrypted.length(); j += keyLength) {
                char c = encrypted.charAt(j);
                if (Character.isLetter(c)) slice.append(c);
            }

            int bestShift = 0, bestCount = 0;
            for (int shift = 0; shift < 26; shift++) {
                String decrypted = decrypt(slice.toString(), new int[]{shift});
                int count = countValidWords(decrypted, dictionary);
                if (count > bestCount) {
                    bestCount = count;
                    bestShift = shift;
                }
            }
            key[i] = bestShift;
        }
        return key;
    }

    public static void main(String[] args) throws Exception {
        HashSet<String> dictionary = loadDictionary("English.txt");
        String encrypted = readFile("secretmessage2.txt");

        int bestLength = 0, maxValid = 0;
        String bestDecryption = "";

        System.out.println("Testing key lengths (showing samples):");
        System.out.println("============================================");

        for (int keyLength = 1; keyLength <= 40; keyLength++) {
            int[] key = tryKeyLength(encrypted, keyLength, dictionary);
            String decrypted = decrypt(encrypted, key);
            int valid = countValidWords(decrypted, dictionary);

            System.out.printf("Key length %2d → %5d valid words | Sample: %s%n",
                    keyLength, valid, decrypted.substring(0, Math.min(60, decrypted.length())).replaceAll("\n", " "));

            if (valid > maxValid) {
                maxValid = valid;
                bestLength = keyLength;
                bestDecryption = decrypted;
            }
        }

        System.out.println("============================================");
        System.out.println("Most likely key length: " + bestLength);
        System.out.println("Valid words count (best key): " + maxValid);
        System.out.println("First line of decrypted text:");
        System.out.println(bestDecryption.split("\n")[0]);
        System.out.println("============================================");

        int[] key38 = tryKeyLength(encrypted, 38, dictionary);
        String decrypted38 = decrypt(encrypted, key38);
        int valid38 = countValidWords(decrypted38, dictionary);

        System.out.println("For key length 38:");
        System.out.println("Valid words count = " + valid38);
    }
}
