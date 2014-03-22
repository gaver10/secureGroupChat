/**
 * groupChat/src/clientClient.java
 * CS475 Secure Group Chat client
 * Ken Fox, Gavin Rapp, Andrea Pavia
 * 
 * This is the extension of the clientObject that is used in the chat CLIENT 
 * because it contains fields that are not common to both the client and server
 * programs.
 * 
 * contains encryption and decryption routines as well as key generation
 * 
 *  
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author kenfox
 *
 */
public class clientClient extends clientObject {
	protected String encryptionAlgorithm; 
	private Key keyPrivate;
	// the superclass has the client's nickname, id, accepted, public Key, and  privleges
	
	
	clientClient() {
		super();
		this.encryptionAlgorithm = "RSA";
		// create the public and private keypair on instantiation 
		generateKeypair();
	}


	/**
	 * @return the keyPrivate
	 */
	protected Key getKeyPrivate() {
		return keyPrivate;
	}

	/**
	 * @param keyPrivate the keyPrivate to set
	 */
	protected void setKeyPrivate(Key keyPrivate) {
		this.keyPrivate = keyPrivate;
	}

	
	/**
	 * generates a Public and Private Key pair for this client and stores the values in the 
	 * appropriate fields directly.
	 * 
	 * This was put here because only the clients will be generating key pairs, generally when they
	 * are instantiated; however, there might be a case later on where we want to generate a 
	 * new keypair.
	 * 
	 * http://www.javamex.com/tutorials/cryptography/rsa_encryption.shtml
	 */
	protected void generateKeypair() {
		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance(this.encryptionAlgorithm);
			kpg.initialize(2048);
			KeyPair kp = kpg.genKeyPair();
			this.keyPublic = kp.getPublic();
			this.keyPrivate = kp.getPrivate();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	
	}
	
	//	convert string to bytes
	//	byte[] bytes = example.getBytes();
	// 	String decoded = new String(bytes, "UTF-8"); 
	//	http://stackoverflow.com/questions/5673059/converting-byte-array-to-string-java
	
	// 	http://docs.oracle.com/javase/7/docs/api/javax/crypto/Cipher.html	
	/**
	 * this routine encrypts the byte Array with the the public key and
	 * returns a new byte array which will be transmitted to a target client
	 * @param data
	 * @param keyPublic
	 * @return
	 */
	public byte[] rsaEncrypt(byte[] cleartextData, Key keyPublic) {
		  Cipher cipher;
		  byte[] cipherData = null;
			try {
				cipher = Cipher.getInstance(this.encryptionAlgorithm);
				cipher.init(Cipher.ENCRYPT_MODE, keyPublic);
				cipherData = cipher.doFinal(cleartextData);

			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
			return cipherData;
		}
	
	/**
	 * decrypt the data that's been sent to me encrypted with my 
	 * public key
	 * @param data bytearray
	 * @return
	 */
	public byte[] rsaDecrypt(byte[] encryptedData) {
		 
	  Cipher cipher;
	  byte[] cipherData = null;
		try {
			cipher = Cipher.getInstance(this.encryptionAlgorithm);
			cipher.init(Cipher.DECRYPT_MODE, this.keyPrivate);
			cipherData = cipher.doFinal(encryptedData);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return cipherData;
	}
	
	/**
	 * this is example code on how set up a key factory for handling 
	 * different algorithms. Not sure if I need it yet -- it's here for reference
	 * purposes.
	 * http://www.javamex.com/tutorials/cryptography/rsa_encryption.shtml
	 * @throws NoSuchAlgorithmException 
	 */
	@SuppressWarnings("unused")
	private void keycrap(KeyPair kp) throws NoSuchAlgorithmException {
		KeyFactory fact = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec pub;
		try {
			pub = fact.getKeySpec(kp.getPublic(),
			  RSAPublicKeySpec.class);
			try {
				saveToFile("public.key", pub.getModulus(),
				  pub.getPublicExponent());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		RSAPrivateKeySpec priv;
		try {
			priv = fact.getKeySpec(kp.getPrivate(),
			  RSAPrivateKeySpec.class);
			try {
				saveToFile("private.key", priv.getModulus(),
				  priv.getPrivateExponent());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		
		
	}
	
	/** 
	 * Same deal - not going to persist keys beyond a session but it's nice to know
	 * how, and it's even nicer to have the code available to do it 
	 * From a security perspective having this unused functionality in a 
	 * real program is a bad idea since it's theoretically available to a blackhat
	 * who can manage to abuse the JVM and jump here; Anyway in a production program
	 * I'd remove this  unused code; however, I wanted to have it available to reference 
	 * while I'm still learning this stuff.
	 * @param fileName
	 * @param mod
	 * @param exp
	 * @throws IOException
	 */
	private void saveToFile(String fileName,
			  BigInteger mod, BigInteger exp) throws IOException {
			  ObjectOutputStream oout = new ObjectOutputStream(
			    new BufferedOutputStream(new FileOutputStream(fileName)));
			  try {
			    oout.writeObject(mod);
			    oout.writeObject(exp);
			  } catch (Exception e) {
			    throw new IOException("Unexpected error", e);
			  } finally {
			    oout.close();
			  }
			}
	
	/**
	 * More same code from tutorial -- this reads the saved key data from the file it was saved to above
	 * Again -- it would be removed in a functional program.
	 * @param keyFileName
	 * @return
	 * @throws IOException
	 */
	PublicKey readKeyFromFile(String keyFileName) throws IOException {
		//  InputStream in =  ServerConnection.class.getResourceAsStream(keyFileName);
		
		// below is bad - but example only 
		InputStream in = null; //= new InputStream();

		ObjectInputStream oin =  new ObjectInputStream(new BufferedInputStream(in));
		  try {
		    BigInteger m = (BigInteger) oin.readObject();
		    BigInteger e = (BigInteger) oin.readObject();
		    RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
		    KeyFactory fact = KeyFactory.getInstance("RSA");
		    PublicKey pubKey = fact.generatePublic(keySpec);
		    return pubKey;
		  } catch (Exception e) {
		    throw new RuntimeException("Spurious serialisation error", e);
		  } finally {
		    oin.close();
		  }
		}
	
}
