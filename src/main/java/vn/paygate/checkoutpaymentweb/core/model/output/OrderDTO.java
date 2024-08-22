package vn.paygate.checkoutpaymentweb.core.model.output;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class OrderDTO {
    private String merchantId;
    private String userId;
    private String orderAmount;
    private String orderCode;
    private String merchantCode;
    private String orderMerchantTime;
    private String orderPaymentMethodBankCode;
    private String orderPaymentMethodCode;
    private String successRedirectUrl;
    private String cancelRedirectUrl;
    private String successCallbackUrl;
    private String status;
    private String displayRequestFirstTime;
    private String orderCreateTime;
    private String customerIpFirstTime;
    private String displayRequestLastTime;
    private String selectedPaymentMethodCode;
    private String selectedPaymentMethodCodeTime;
    private String selectebPaymentMethodBankCode;
    private String slectebPaymentMethodBankCodeTime;
    private String selectebConfigMerchantFeeId;
    private String cashinId;
    private String transactionId;
    private String currency;
    private String integrationMerchantCode;
    private String checkOutUrl;
}
