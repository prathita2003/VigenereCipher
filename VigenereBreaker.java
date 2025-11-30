import java.io.*;
import java.nio.file.*;
import java.util.*;

public class VigenereBreaker {
   public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder slice = new StringBuilder();
        for (int i = whichSlice; i < message.length(); i += totalSlices) {
            slice.append(message.charAt(i));
        }
        return slice.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        for (int i = 0; i < klength; i++) {
            String slice = sliceString(encrypted, i, klength);
            CaesarCracker cc = new CaesarCracker(mostCommon);
            int k = cc.getKey(slice);
            key[i] = k;
        }
        return key;
    }

   public HashSet<String> readDictionary(File dictFile) throws IOException {
        HashSet<String> dictionary = new HashSet<>();
        BufferedReader br = new BufferedReader(new FileReader(dictFile));
        String line;
        while ((line = br.readLine()) != null) {
            dictionary.add(line.trim().toLowerCase());
        }
        br.close();
        return dictionary;
    }

    
    public int countWords(String message, HashSet<String> dictionary) {
        String[] words = message.split("\\W+");
        int count = 0;
        for (String word : words) {
            if (dictionary.contains(word.toLowerCase())) {
                count++;
            }
        }
        return count;
    }

    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary) {
        int maxRealWords = 0;
        String bestDecryption = "";
        int[] bestKey = null;

        for (int k = 1; k <= 100; k++) {
            int[] key = tryKeyLength(encrypted, k, 'e'); // assume English
            VigenereCipher vc = new VigenereCipher(key);
            String decrypted = vc.decrypt(encrypted);
            int wordCount = countWords(decrypted, dictionary);

            if (wordCount > maxRealWords) {
                maxRealWords = wordCount;
                bestDecryption = decrypted;
                bestKey = key;
            }
        }

        System.out.println("Best key length: " + (bestKey != null ? bestKey.length : 0));
        System.out.println("Real words in best decryption: " + maxRealWords);
        return bestDecryption;
    }
    
    public void breakVigenere(String encryptedPath, String dictPath) throws IOException {
        String encrypted = Files.readString(Path.of(encryptedPath));
        File dictFile = new File(dictPath);
        HashSet<String> dictionary = readDictionary(dictFile);

        System.out.println("Loaded dictionary with " + dictionary.size() + " words.");
        String decrypted = breakForLanguage(encrypted, dictionary);

        System.out.println("\nDecrypted text (first 500 characters):\n");
        System.out.println(decrypted.substring(0, Math.min(500, decrypted.length())));
    }
}
