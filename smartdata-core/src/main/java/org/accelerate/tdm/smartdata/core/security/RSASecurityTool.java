package org.accelerate.tdm.smartdata.core.security;

import org.accelerate.tdm.smartdata.core.exception.TdmException;
import org.accelerate.tdm.smartdata.core.tool.FileHelper;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;

/*
openssl genrsa -out private.pem 891
openssl rsa -in private.pem -pubout -out public-key.pem
penssl pkcs8 -topk8 -inform PEM -in private.pem -out private_key.pem -nocrypt

openssl rsautl -encrypt -inkey public-key.pem -pubin -in plaintext.txt -out plaintext.enc

 */
public class RSASecurityTool {
    protected static final Logger LOGGER = LoggerFactory.getLogger(RSASecurityTool.class);

    public static String decrypt(final String privateKeyFilePath, final String encodedFilePath){
        long startTime = System.nanoTime();
        Cipher decryptCipher = null;
        try {
            String absolutePathEncodedFilePath = FileHelper.getAbsolutePath(encodedFilePath);
            String absolutePathPrivateKeyFilePath = FileHelper.getAbsolutePath(privateKeyFilePath);

            decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.init(Cipher.DECRYPT_MODE,readPrivateKey(absolutePathPrivateKeyFilePath));
            byte[] array = Files.readAllBytes(Paths.get((absolutePathEncodedFilePath)));;
            byte[] cipherText = decryptCipher.doFinal(array);
            String result = new String (cipherText, "UTF-8");
            LOGGER.info("RSASecurityTool -  decrypt in {} ms", (System.nanoTime() - startTime)/1000000);
            return result.trim();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
            throw new TdmException(e.getLocalizedMessage());
        }
    }

    private static  RSAPrivateKey readPrivateKey(final String privateKeyFile) throws IOException {
        try (FileReader keyReader = new FileReader(privateKeyFile)) {
            PEMParser pemParser = new PEMParser(keyReader);
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(pemParser.readObject());

            return (RSAPrivateKey) converter.getPrivateKey(privateKeyInfo);
        }
    }
    /*
     private static RSAPublicKey readPublicKey(final String publicKeyFile) throws IOException {
        try (FileReader keyReader = new FileReader(publicKeyFile)) {
            PEMParser pemParser = new PEMParser(keyReader);
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(pemParser.readObject());
            return (RSAPublicKey) converter.getPublicKey(publicKeyInfo);
        }
    }
    public static String encrypt(final String publicKeyFile, final String secretMessage){
        Cipher encryptCipher = null;
        try {
            encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, readPublicKey(publicKeyFile));
            byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
            String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
            return encodedMessage;
        }  catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException e) {
            throw new TdmException(e.getLocalizedMessage());
        }
    }
    */
}
