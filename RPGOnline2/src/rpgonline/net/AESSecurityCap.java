package rpgonline.net;

import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import java.io.*;

import org.newdawn.slick.util.Log;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * A system for setting up key exchange for AES.
 * @author Jegan Babu
 * @author Wojciech Wirzbicki
 */
class AESSecurityCap {
	/**
	 * The public key to use.
	 */
    private PublicKey publickey;
    /**
   	 * The key agreement.
     */
    KeyAgreement keyAgreement;
    /**
     * The shared secret for key exchange.
     */
    byte[] sharedsecret;

    /**
     * The algorithm to use.
     */
    static final String ALGO = "AES";

    /**
     * Construct a new AESSecurityCap.
     */
    AESSecurityCap() {
        makeKeyExchangeParams();
    }

    /**
     * Generate parameters.
     */
    private void makeKeyExchangeParams() {
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance("EC");
            kpg.initialize(128);
            KeyPair kp = kpg.generateKeyPair();
            publickey = kp.getPublic();
            keyAgreement = KeyAgreement.getInstance("ECDH");
            keyAgreement.init(kp.getPrivate());

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            Log.error(e);
        }
    }

    /**
     * Recieve the public key from the other side of the connection.
     * @param publickey
     */
    public void setReceiverPublicKey(PublicKey publickey) {
        try {
            keyAgreement.doPhase(publickey, true);
            sharedsecret = keyAgreement.generateSecret();
        } catch (InvalidKeyException e) {
            Log.error(e);
        }
    }

    /**
     * Encrypt the message.
     * @param msg The message to encrypt.
     * @return An encrypted string in base64.
     */
    public String encrypt(String msg) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(msg.getBytes());
            return new BASE64Encoder().encode(encVal);
        } catch (BadPaddingException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException e) {
        	Log.error(e);
        }
        return msg;
    }

    /**
     * Decrypt the message.
     * @param msg An encrypted string in base64.
     * @return A string.
     */
    public String decrypt(String encryptedData) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
            byte[] decValue = c.doFinal(decordedValue);
            return new String(decValue);
        } catch (BadPaddingException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | IOException e) {
           Log.error(e);
        }
        return encryptedData;
    }

    /**
     * Get the public key.
     * @return A public key.
     */
    public PublicKey getPublickey() {
        return publickey;
    }
    
    /**
     * Generates a key from the provided public key and shared secret.
     * @return An AES key.
     */
    public Key generateKey() {
        return new SecretKeySpec(sharedsecret, ALGO);
    }
}