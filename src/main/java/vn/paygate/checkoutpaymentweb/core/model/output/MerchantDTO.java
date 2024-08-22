package vn.paygate.checkoutpaymentweb.core.model.output;

import lombok.Data;

@Data
public class MerchantDTO {
    private String integrationSecureCode;
    private String integrationMerchantCode;
    private String decryptedSecureCode;
}
