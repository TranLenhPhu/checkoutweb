package vn.paygate.checkoutpaymentweb.core.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Service
public class CyberSourcePayment {
    private static final Logger logger = LoggerFactory.getLogger(CyberSourcePayment.class);
    private static final String MERCHANT_ID = "nganluongtravel";
    private static final String MERCHANT_KEY_ID = "9564b51f-e853-4de2-9360-1f3f73213e42";
    private static final String SECRET_KEY = "YHujWLPYMSuNiuKKUyIWeTBMtkRnbHyF2WQgIA1ZUYk=";
//    private static final String API_URL = "https://apitest.cybersource.com/pts/v2/payments";
    private static final String API_URL = "https://apitest.cybersource.com/risk/v1/authentication-setups";

    public ResponseEntity<String> processPayment(String requestBody) throws Exception {
        String digest = CyberSourceUtils.GenerateDigest();
//        String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());
        String date = "Thu, 22 Aug 2024 03:34:44 GMT";
//        String digest = createDigest(request);
        String signature = createSignature(date, digest);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("v-c-merchant-id", "nganluongtravel");
        headers.set("Date", date);
        headers.set("Host", "apitest.cybersource.com");
        headers.set("Digest",  digest);
        headers.set("Signature", signature);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
    }

    private String createSignature(String date, String digest) throws Exception {
        // Construct the signature string
        StringBuilder signatureString = new StringBuilder();
        signatureString.append("host: apitest.cybersource.com\n");
        signatureString.append("v-c-date: ").append(date).append("\n");
        signatureString.append("(request-target): post /risk/v1/authentication-setups\n");
        signatureString.append("digest: ").append(digest).append("\n");
        signatureString.append("v-c-merchant-id: ").append(MERCHANT_ID);

        // Encode the signature string with the secret key using HMAC SHA-256
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), "HmacSHA256");
        sha256Hmac.init(secretKeySpec);

        byte[] signedBytes = sha256Hmac.doFinal(signatureString.toString().getBytes(StandardCharsets.UTF_8));
        String signature = Base64.getEncoder().encodeToString(signedBytes);

        // Build the final signature header
        String formattedSignature = String.format(
                "keyid=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"",
                MERCHANT_KEY_ID,
                "HmacSHA256",
                "host v-c-date request-target digest v-c-merchant-id",
                signature
        );

        logger.debug("Signature string to be signed: {}", signatureString);
        logger.debug("Generated Signature: {}", formattedSignature);

        return formattedSignature;
    }

}
