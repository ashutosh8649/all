package com.offershopper.uaa.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.ErrorCodes;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.offershopper.uaa.database.TempRepo;
import com.offershopper.uaa.database.UaaProxyRepo;
import com.offershopper.uaa.database.tokenRepo;
import com.offershopper.uaa.feignproxy.LoginProxyRepo;
import com.offershopper.uaa.feignproxy.RegisterProxyRepo;
import com.offershopper.uaa.feignproxy.UserVendorActivity;
import com.offershopper.uaa.model.RegisterInfo;
import com.offershopper.uaa.model.UaaModel;
import com.offershopper.uaa.model.UserInformation;
import com.offershopper.uaa.model.passBean;
import com.offershopper.uaa.service.NotificationService;

@RestController
@CrossOrigin
public class LoginRegisterController {
	@Autowired
	private NotificationService notificationService;

	private Logger logger = LoggerFactory.getLogger(LoginRegisterController.class);

	@Autowired
	private TempRepo temprepo;

	@Autowired
	private tokenRepo tokenrepo;

	@Autowired
	private LoginProxyRepo loginproxyrepo;

	@Autowired
	private RegisterProxyRepo registerproxyrepo;

	@Autowired
	private UaaProxyRepo uaaproxyrepo;

	@Autowired
	private UserVendorActivity userVendorActivity;

	@PostMapping("/token/login")
	public ResponseEntity<String> tokenlogin(@RequestBody UserInformation obj) {
		// check the user credentials are true
		System.out.println("********addd1");

		ResponseEntity<String> retrieveinfo = loginproxyrepo.loginUser(obj);

		System.out.println("********addd2");

		// if user credentials are not same return false
		if (retrieveinfo.getBody().equals("User Does not exist") || retrieveinfo.getBody().equals("Password InCorrect"))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UnAuthorized");

		String[] data = retrieveinfo.getBody().split(",");
		String uname = data[0];
		String role = data[1];
		// call function for make token
		String token = createJwtToken(uname, role);
		String[] tokeninfo = token.split("\n");
		String jwt = tokeninfo[0];
		String publickey = tokeninfo[1];
		// check if token already exits
		System.out.println("********addd3");

		Optional<UaaModel> uaaobject = uaaproxyrepo.findById(uname);
		System.out.println("********addd4");

		int activeUser = 0;

		try {
			activeUser = uaaobject.get().getActiveUser();
		} catch (Exception e) {
			activeUser = 1;
		}

		// if uaaobject is not present add entry to database

		if (!uaaobject.isPresent()) {
			token = jwt;
			uaaproxyrepo.save(new UaaModel(uname, jwt, publickey, activeUser));
		} else {
			// else get saved token from database

			uaaproxyrepo.save(new UaaModel(uaaobject.get().getUserId(), uaaobject.get().getToken(),
					uaaobject.get().getPublicKey(), activeUser + 1));
			token = uaaobject.get().getToken();
		}
		return ResponseEntity.status(HttpStatus.OK).body(token);

	}

	@PostMapping("/token/register")
	public ResponseEntity<String> tokenregister(@RequestBody RegisterInfo obj) {
		ResponseEntity<String> retriveinfo = registerproxyrepo.addUserInformation(obj);
		// if user already exists return registered
		if (retriveinfo.getBody().equals("Already Exists"))
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate User");
		// save in temporary database
		temprepo.save(obj);
		// send email
		getemail(obj.getEmail());

		return ResponseEntity.status(HttpStatus.CREATED).body("Success");
	}

	public ResponseEntity<String> getemail(String email) {
		try {
			notificationService.sendNotification(email);
		} catch (MailException e) {
			System.out.println("Failed mailing");
			logger.info(e.getMessage());
			// error handle here
			return ResponseEntity.status(HttpStatus.CREATED).body("Not Sent");

		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Success");

	}

	// verify the token for registration
	@GetMapping("/registration/verification/{token}")
	public ResponseEntity<String> verifyToken(@PathVariable String jwe) {

		// That other party, the receiver, can then use JsonWebEncryption to decrypt the
		// message.
		JsonWebEncryption receiverJwe = new JsonWebEncryption();

		// Set the algorithm constraints based on what is agreed upon or expected from
		// the sender
		AlgorithmConstraints algConstraints = new AlgorithmConstraints(ConstraintType.WHITELIST,
				KeyManagementAlgorithmIdentifiers.DIRECT);
		receiverJwe.setAlgorithmConstraints(algConstraints);
		AlgorithmConstraints encConstraints = new AlgorithmConstraints(ConstraintType.WHITELIST,
				ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
		receiverJwe.setContentEncryptionAlgorithmConstraints(encConstraints);

		// Set the compact serialization on new Json Web Encryption object
		try {
			receiverJwe.setCompactSerialization(jwe);
		} catch (JoseException e2) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Token Invalid");
		}

		// The shared secret or shared symmetric key represented as a octet sequence
		// JSON Web Key (JWK)
		String jwkJson = "{\"kty\":\"oct\",\"k\":\"Fdh9u8rINxfivbrianbbVT1u232VQBZYKx1HGAGPt2I\"}";
		JsonWebKey jwk = null;
		try {
			jwk = JsonWebKey.Factory.newJwk(jwkJson);
		} catch (JoseException e2) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Token Invalid");
		}

		// Symmetric encryption, like we are doing here, requires that both parties have
		// the same key.
		// The key will have had to have been securely exchanged out-of-band somehow.
		receiverJwe.setKey(jwk.getKey());
		// Get the message that was encrypted in the JWE. This step performs the actual
		// decryption steps.
		String plaintext = null;
		try {
			plaintext = receiverJwe.getPlaintextString();
		} catch (JoseException e2) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Token Invalid");
		}

		ResponseEntity<List<String>> users = loginproxyrepo.getAllRegisteredUserInformation();
		if (users.getBody().contains(plaintext))
			return ResponseEntity.status(HttpStatus.OK).body("Success");
		else
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Token Invalid");
	}

	private String createJwtToken(String uname, String role) {

		RsaJsonWebKey rsaJsonWebKey = null;
		try {
			rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
		} catch (JoseException e1) {
			return "UnAuthorized";
		}

		// Give the JWK a Key ID (kid), which is just the polite thing to do
		rsaJsonWebKey.setKeyId("k1");

		// Create the Claims, which will be the content of the JWT
		JwtClaims claims = null;

		// Create the Claims, which will be the content of the JWT
		claims = new JwtClaims();
		claims.setIssuer("OfferShopper"); // who creates the token and signs it
		claims.setAudience("OfferShopperUser"); // to whom the token is intended to be sent
		claims.setExpirationTimeMinutesInTheFuture(20000); // time when the token will expire (10 minutes from now)
		claims.setGeneratedJwtId(); // a unique identifier for the token
		claims.setIssuedAtToNow(); // when the token was issued/created (now)
		claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
		claims.setSubject(role); // the subject/principal is whom the token is about
		claims.setClaim("username", uname); // additional claims/attributes about the subject can be added

		// A JWT is a JWS and/or a JWE with JSON claims as the payload.
		// In this example it is a JWS so we create a JsonWebSignature object.
		JsonWebSignature jws = new JsonWebSignature();

		// The payload of the JWS is JSON content of the JWT Claims
		jws.setPayload(claims.toJson());

		// The JWT is signed using the private key
		jws.setKey(rsaJsonWebKey.getPrivateKey());

		// Set the Key ID (kid) header because it's just the polite thing to do.
		// We only have one key in this example but a using a Key ID helps
		// facilitate a smooth key rollover process
		jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());

		// Set the signature algorithm on the JWT/JWS that will integrity protect the
		// claims
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

		// Sign the JWS and produce the compact serialization or the complete JWT/JWS
		// representation, which is a string consisting of three dot ('.') separated
		// base64url-encoded parts in the form Header.Payload.Signature
		// If you wanted to encrypt it, you can simply set this jwt as the payload
		// of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
		String jwt = null;
		try {
			jwt = jws.getCompactSerialization();
		} catch (JoseException e1) {
			return "UnAuthorized";
		}

		// // A JSON string with only the public key info
		// String publicKeyJwkString =
		// rsaJsonWebKey.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);
		//
		// System.out.println(publicKeyJwkString);

		System.out.println("PublicKey:\n" + rsaJsonWebKey.toJson() + "\ndone");

		// return token,public key
		return jwt + "\n" + rsaJsonWebKey.toJson();
	}

	@GetMapping("/verifytoken/{applicationToken}")
	public String verifyTokenComingFromService(@PathVariable String applicationToken) {
		// public key from token db
		List<UaaModel> userFound = uaaproxyrepo.findByToken(applicationToken);
		System.out.println("here");
		if (userFound.size() == 0)
			return "UnAuthorized";
		System.out.println("Working");
		UaaModel uaaModel = userFound.get(0);
		System.out.println("******" + uaaModel.getUserId());
		PublicJsonWebKey parsedPublicKeyJwk = null;
		PublicKey publicKey = null;
		try {
			parsedPublicKeyJwk = PublicJsonWebKey.Factory.newPublicJwk(uaaModel.getPublicKey());
			publicKey = parsedPublicKeyJwk.getPublicKey();
		} catch (JoseException e2) {
			return "UnAuthorized";
		}

		// Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
		// be used to validate and process the JWT.
		// The specific validation requirements for a JWT are context dependent,
		// however,
		// it typically advisable to require a (reasonable) expiration time, a trusted
		// issuer, and
		// and audience that identifies your system as the intended recipient.
		// If the JWT is encrypted too, you need only provide a decryption key or
		// decryption key resolver to the builder.
		JwtConsumer jwtConsumer = new JwtConsumerBuilder().setRequireExpirationTime() // the JWT must have an expiration
																						// time
				.setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for
													// clock skew
				.setRequireSubject() // the JWT must have a subject claim
				.setExpectedIssuer("OfferShopper") // whom the JWT needs to have been issued by
				.setExpectedAudience("OfferShopperUser") // to whom the JWT is intended for
				.setVerificationKey(publicKey) // verify the signature with the public key
				.setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
						new AlgorithmConstraints(ConstraintType.WHITELIST, // which is only RS256 here
								AlgorithmIdentifiers.RSA_USING_SHA256))
				.build(); // create the JwtConsumer instance

		// create the JwtConsumer instance
		try {
			// Validate the JWT and process it to the Claims
			JwtClaims jwtClaims = jwtConsumer.processToClaims(applicationToken);
			System.out.println("JWT validation succeeded! " + jwtClaims);
			try {
				return "true," + jwtClaims.getSubject() + "," + jwtClaims.getClaimValue("username");
			} catch (MalformedClaimException e) {
				return "UnAuthorized";
			}
		} catch (InvalidJwtException e) {
			// InvalidJwtException will be thrown, if the JWT failed processing or
			// validation in anyway.
			// Hopefully with meaningful explanations(s) about what went wrong.
			System.out.println("Invalid JWT! " + e);

			// Programmatic access to (some) specific reasons for JWT invalidity is also
			// possible
			// should you want different error handling behavior for certain conditions.

			// Whether or not the JWT has expired being one common reason for invalidity
			if (e.hasExpired()) {

				try {
					System.out.println("JWT expired at " + e.getJwtContext().getJwtClaims().getExpirationTime());
				} catch (MalformedClaimException e1) {
					return "UnAuthorized";
				}
			}

			// Or maybe the audience was invalid
			if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID)) {
				try {
					System.out.println("JWT had wrong audience: " + e.getJwtContext().getJwtClaims().getAudience());
				} catch (MalformedClaimException e1) {
					return "UnAuthorized";
				}
			}
			try {
				throw new Exception("UnAuthorized");
			} catch (Exception e1) {
				return "UnAuthorized";
			}
		}
	}

	@GetMapping("/token/register/vendor/{uname}/{role}")
	public ResponseEntity<String> vendorRegistration(@PathVariable String uname, @PathVariable String role) {
		String token = createJwtToken(uname, role);
		String[] tokeninfo = token.split("\n");
		String jwt = tokeninfo[0];
		String publickey = tokeninfo[1];
		// check if token already exits
		Optional<UaaModel> uaaobject = uaaproxyrepo.findById(uname);
		int activeUser = uaaobject.get().getActiveUser();
		// add entry to database
		uaaproxyrepo.save(new UaaModel(uaaobject.get().getUserId(), jwt, publickey, activeUser));
		return ResponseEntity.status(HttpStatus.OK).body(jwt);
	}

	// forget password reset mapping
	@GetMapping("/forgotpassword/{email}")
	public ResponseEntity<String> forgotPassword(@PathVariable String email) {
		/// get all users
		ResponseEntity<List<RegisterInfo>> result = registerproxyrepo.allUsers();
		List<RegisterInfo> users = result.getBody();
		// iterate every user
		ListIterator<RegisterInfo> iterator = users.listIterator();
		RegisterInfo user = null;
		// match is user exists
		int flag = 0;
		while (iterator.hasNext()) {
			user = iterator.next();
			if (user.getEmail().equals(email)) {
				flag = 1;
				break;
			}
		}
		// if exists then send reset email
		if (flag == 1) {
			notificationService.sendForgotEmail(email);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password reset email sent");
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User Does not exist");

	}

	@PostMapping("/forgetpassword")
	public ResponseEntity<String> resetPassword(@RequestBody passBean obj) {
		String isvalid = decryptTokenJwe(obj.getJwe());
		if (isvalid.equals("InValidToken"))
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Password Notset");
		// if token not in database
		if (!(tokenrepo.findById(obj.getJwe()).isPresent())) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Password Notset");
		}
		// deleate token from database
		tokenrepo.deleteById(obj.getJwe());
		ResponseEntity<List<RegisterInfo>> result = registerproxyrepo.allUsers();
		List<RegisterInfo> users = result.getBody();
		// iterate every user
		ListIterator<RegisterInfo> iterator = users.listIterator();
		RegisterInfo user = null;
		// match is user exists
		while (iterator.hasNext()) {
			user = iterator.next();
			if (user.getEmail().equals(isvalid))
				break;
		}
		// if exists then send reset email
		if (user != null) {
			// use feign to send user
			user.setPassword("abc");
			System.out.println("****************reachhere");
			RegisterInfo obj1 = new RegisterInfo(isvalid, obj.getPassword());
			userVendorActivity.resetPassword(obj1);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password set");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password not set");
	}

	// decrypt jwe token for registration
	@GetMapping("/verifyToken/user/{Jwe}")
	public ResponseEntity<String> decryptJwe(@PathVariable String Jwe) {
		// The shared secret or shared symmetric key represented as a octet sequence
		// JSON Web Key (JWK)

		System.out.println("***********valu1");
		if (!(tokenrepo.findById(Jwe).isPresent())) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Not Verified");
		}
		// deleate token from database
		tokenrepo.deleteById(Jwe);
		String jwkJson = "{\"kty\":\"oct\",\"k\":\"Fdh9u8rINxfivbrianbbVT1u232VQBZYKx1HGAGPt2I\"}";
		JsonWebKey jwk = null;
		try {
			jwk = JsonWebKey.Factory.newJwk(jwkJson);
		} catch (JoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// That other party, the receiver, can then use JsonWebEncryption to decrypt the
		// message.
		JsonWebEncryption receiverJwe = new JsonWebEncryption();

		// Set the algorithm constraints based on what is agreed upon or expected from
		// the sender
		AlgorithmConstraints algConstraints = new AlgorithmConstraints(ConstraintType.WHITELIST,
				KeyManagementAlgorithmIdentifiers.DIRECT);
		receiverJwe.setAlgorithmConstraints(algConstraints);
		AlgorithmConstraints encConstraints = new AlgorithmConstraints(ConstraintType.WHITELIST,
				ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
		receiverJwe.setContentEncryptionAlgorithmConstraints(encConstraints);

		// Set the compact serialization on new Json Web Encryption object
		try {
			receiverJwe.setCompactSerialization(Jwe);
		} catch (JoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Not Verified");
		}

		// Symmetric encryption, like we are doing here, requires that both parties have
		// the same key.
		// The key will have had to have been securely exchanged out-of-band somehow.
		receiverJwe.setKey(jwk.getKey());

		// Get the message that was encrypted in the JWE. This step performs the actual
		// decryption steps.
		String data[] = null;
		System.out.println("Reachhere*******************");
		try {
			System.out.println("Reachhere*******************");
			data = receiverJwe.getPlaintextString().split(",");
			System.out.println("Reachhere*******************");

		} catch (JoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Not Verified");
		}
		System.out.println("Reachhere*******************");

		// save in database and email,password
		Optional<RegisterInfo> objj = temprepo.findById(data[0]);
		System.out.println("Reachhere*******************1" + data[0]);
		RegisterInfo result = objj.get();
		System.out.println("Reachhere*******************2");
		temprepo.deleteById(data[0]);
		System.out.println("Reachhere*******************3");
		registerproxyrepo.addUser(result);
		System.out.println("Reachhere*******************4");

		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Verified");
	}

	// function validate jwe for forget password
	public String decryptTokenJwe(String Jwe) {
		// The shared secret or shared symmetric key represented as a octet sequence
		// JSON Web Key (JWK)
		String jwkJson = "{\"kty\":\"oct\",\"k\":\"Fdh9u8rINxfivbrianbbVT1u232VQBZYKx1HGAGPt2I\"}";
		JsonWebKey jwk = null;
		try {
			jwk = JsonWebKey.Factory.newJwk(jwkJson);
		} catch (JoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "InValidToken";

		}

		// That other party, the receiver, can then use JsonWebEncryption to decrypt the
		// message.
		JsonWebEncryption receiverJwe = new JsonWebEncryption();

		// Set the algorithm constraints based on what is agreed upon or expected from
		// the sender
		AlgorithmConstraints algConstraints = new AlgorithmConstraints(ConstraintType.WHITELIST,
				KeyManagementAlgorithmIdentifiers.DIRECT);
		receiverJwe.setAlgorithmConstraints(algConstraints);
		AlgorithmConstraints encConstraints = new AlgorithmConstraints(ConstraintType.WHITELIST,
				ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
		receiverJwe.setContentEncryptionAlgorithmConstraints(encConstraints);

		// Set the compact serialization on new Json Web Encryption object
		try {
			receiverJwe.setCompactSerialization(Jwe);
		} catch (JoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "InValidToken";
		}

		// Symmetric encryption, like we are doing here, requires that both parties have
		// the same key.
		// The key will have had to have been securely exchanged out-of-band somehow.
		receiverJwe.setKey(jwk.getKey());

		// Get the message that was encrypted in the JWE. This step performs the actual
		// decryption steps.
		String data[] = null;
		try {
			data = receiverJwe.getPlaintextString().split(",");
		} catch (JoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "InValidToken";
		}
		return data[0];
	}

	// verification for Google Sign

	@GetMapping("/google/{token}")
	public ResponseEntity<String> verifyTokenGoogle(@PathVariable String token) throws IOException, ParseException {
		StringBuffer response = new StringBuffer();
		URL obj = new URL("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + token);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		// con.setRequestProperty("User-Agent", request.getHeader("user-agent"));
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
		}
		// response from the uaa server
		String verify = response.toString();
		JSONParser parser = new JSONParser();
		JSONObject googleobj = (JSONObject) parser.parse(verify);

		// check in database
		ResponseEntity<List<RegisterInfo>> result = registerproxyrepo.allUsers();
		List<RegisterInfo> users = result.getBody();

		// iterate every user
		ListIterator<RegisterInfo> iterator = users.listIterator();
		RegisterInfo user = null;
		// match is user exists
		String email = googleobj.get("email").toString();
		int flag = 0;
		while (iterator.hasNext()) {
			user = iterator.next();
			if (user.getEmail().equals(email)) {
				flag = 1;
				break;
			}
		}
		if (flag == 1) {
			Optional<UaaModel> uaaobject = uaaproxyrepo.findById(email);
			System.out.println(email);
			System.out.println("*********************************** in user not null");
			uaaproxyrepo.save(new UaaModel(uaaobject.get().getUserId(), uaaobject.get().getToken(),
					uaaobject.get().getPublicKey(), uaaobject.get().getActiveUser() + 1));
			token = uaaobject.get().getToken();
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);

		} else {
			System.out.println(" after create token********************************"+email);

			// call function for make token
			String role = "Customer";
			token = createJwtToken(email, role);
			String[] tokeninfo = token.split("\n");
			String jwt = tokeninfo[0];
			String publickey = tokeninfo[1];
			// Create Token
			System.out.println("working");
			uaaproxyrepo.save(new UaaModel(email, jwt, publickey, 1));
			registerproxyrepo.addUser(new RegisterInfo(email, publickey ,role));
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(jwt);

 		}

	}

}
