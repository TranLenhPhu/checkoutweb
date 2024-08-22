package vn.paygate.checkoutpaymentweb.core.model.output;

import lombok.Data;

@Data
public class MerchantInfo {
    private String integrationSecureCode;
    private String integrationSecurePassMassedPan;
}
