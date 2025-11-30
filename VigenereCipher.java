public class VigenereCipher {
    CaesarCipher[] ciphers;

    public VigenereCipher(int[] key) {
        ciphers = new CaesarCipher[key.length];
        for (int i = 0; i < key.length; i++) {
            ciphers[i] = new CaesarCipher(key[i]);
        }
    }

    public String encrypt(String input) {
        StringBuilder encrypted = new StringBuilder();
        int index = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                int cipherIndex = index % ciphers.length;
                encrypted.append(ciphers[cipherIndex].encrypt(Character.toString(c)));
                index++;
            } else {
                encrypted.append(c);
            }
        }
        return encrypted.toString();
    }

    public String decrypt(String input) {
        StringBuilder decrypted = new StringBuilder();
        int index = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                int cipherIndex = index % ciphers.length;
                decrypted.append(ciphers[cipherIndex].decrypt(Character.toString(c)));
                index++;
            } else {
                decrypted.append(c);
            }
        }
        return decrypted.toString();
    }
}
