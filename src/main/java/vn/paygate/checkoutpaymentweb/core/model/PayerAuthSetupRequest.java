package vn.paygate.checkoutpaymentweb.core.model;

import lombok.Data;

@Data
public class PayerAuthSetupRequest {
    private ClientReferenceInformation clientReferenceInformation;
    // Add other fields as required

    @Data
    public static class ClientReferenceInformation {
        private String code;

        // Getters and Setters
    }
}
