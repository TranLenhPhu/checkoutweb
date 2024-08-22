package vn.paygate.checkoutpaymentweb.core.model.input;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class MerchantInfo {
    private Long id;
    private String merchantId;
    private String user_id;
    private String payment_method_id;
    private String payment_method_code;
    private String payment_method_bank_id;
    private String payment_method_bank_code;
    private String sender_fee_percentage;
    private String receiver_fee_fix;
    private String min_amount;
    private String max_amount;
}
