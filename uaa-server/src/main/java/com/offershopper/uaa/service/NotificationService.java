package com.offershopper.uaa.service;

import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.offershopper.uaa.database.tokenRepo;
import com.offershopper.uaa.model.tokenBean;

@Service
public class NotificationService {
	
	@Autowired
	private tokenRepo tokenrepo;

	private JavaMailSender javamailsender;

	public NotificationService(JavaMailSender javamailsender) {
		this.javamailsender = javamailsender;
	}

	// send notification to email
	public void sendNotification(String email) throws MailException {
		String[] data = email.split(",");
		// generate encrypted email
		String token = createJwe(email);
		// send notification;
		//save in email
		tokenrepo.save(new tokenBean(token,data[0]));
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom("mubina101403122@gmail.com");
		mail.setSubject("Account Verification| OfferShopper");
		mail.setText("http://localhost:4200/verifyToken/user/"  + token);
		// TODO resolve this issue
		// throws MailException
		try {
			this.javamailsender.send(mail);
		} catch (Exception e) {
			System.out.println("Failed mailing sending");
		}
	}

	// send link for reset password
	public void sendForgotEmail(String email) {
		// create token
		String token = createJwe(email);
		// save token in database
		// send email
		tokenrepo.save(new tokenBean(token,email));
	
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom("mubina101403122@gmail.com");
		mail.setSubject("Reset Password || offer");
		mail.setText("http://localhost:4200" + "/token/" + token);
		// TODO resolve this issue
		// throws MailException
		try {
			this.javamailsender.send(mail);
		} catch (Exception e) {
			System.out.println("Failed mailing sending");
		}
	}

	// create token for new user
	private static String createJwe(String uname) {
		// The shared secret or shared symmetric key represented as a octet sequence
		// JSON Web Key (JWK)
		String jwkJson = "{\"kty\":\"oct\",\"k\":\"Fdh9u8rINxfivbrianbbVT1u232VQBZYKx1HGAGPt2I\"}";
		JsonWebKey jwk = null;
		try {
			jwk = JsonWebKey.Factory.newJwk(jwkJson);
		} catch (JoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create a new Json Web Encryption object
		JsonWebEncryption senderJwe = new JsonWebEncryption();

		// The plaintext of the JWE is the message that we want to encrypt.
		senderJwe.setPlaintext(uname);

		// Set the "alg" header, which indicates the key management mode for this JWE.
		// In this example we are using the direct key management mode, which means
		// the given key will be used directly as the content encryption key.
		senderJwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.DIRECT);

		// Set the "enc" header, which indicates the content encryption algorithm to be
		// used.
		// This example is using AES_128_CBC_HMAC_SHA_256 which is a composition of AES
		// CBC
		// and HMAC SHA2 that provides authenticated encryption.
		senderJwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);

		// Set the key on the JWE. In this case, using direct mode, the key will used
		// directly as
		// the content encryption key. AES_128_CBC_HMAC_SHA_256, which is being used to
		// encrypt the
		// content requires a 256 bit key.
		senderJwe.setKey(jwk.getKey());

		// Produce the JWE compact serialization, which is where the actual encryption
		// is done.
		// The JWE compact serialization consists of five base64url encoded parts
		// combined with a dot ('.') character in the general format of
		// <header>.<encrypted key>.<initialization vector>.<ciphertext>.<authentication
		// tag>
		// Direct encryption doesn't use an encrypted key so that field will be an empty
		// string
		// in this case.
		String compactSerialization = null;
		try {
			compactSerialization = senderJwe.getCompactSerialization();
		} catch (JoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return compactSerialization;
	}

}
