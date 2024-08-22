package vn.paygate.checkoutpaymentweb.core.service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CyberSourceUtils {

//    public static String createDigest(String requestBody) throws Exception {
//        MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        byte[] hash = digest.digest(requestBody.getBytes(StandardCharsets.UTF_8));
//        return Base64.getEncoder().encodeToString(hash);
//    }
public static String GenerateDigest() throws NoSuchAlgorithmException {
    String bodyText = "{\n" +
            "  \"clientReferenceInformation\": {\n" +
            "    \"code\": \"cybs_test\",\n" +
            "    \"partner\": {\n" +
            "      \"developerId\": \"7891234\",\n" +
            "      \"solutionId\": \"89012345\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"paymentInformation\": {\n" +
            "    \"card\": {\n" +
            "      \"type\": \"001\",\n" +
            "      \"expirationMonth\": \"12\",\n" +
            "      \"expirationYear\": \"2025\",\n" +
            "      \"number\": \"4000000000000101\"\n" +
            "    }\n" +
            "  }\n" +
            "}";;
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    md.update(bodyText.getBytes(StandardCharsets.UTF_8));
    byte[] digest = md.digest();
    return Base64.getEncoder().encodeToString(digest);
}

}
