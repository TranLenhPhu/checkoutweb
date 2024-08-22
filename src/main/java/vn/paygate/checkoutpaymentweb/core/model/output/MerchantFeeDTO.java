package vn.paygate.checkoutpaymentweb.core.model.output;

import lombok.Data;


@Data
public class MerchantFeeDTO {
    private String merchantId;
    private String payment_method_code;
    private String payment_method_bank_id;
    private String payment_method_bank_code;
    private String sender_fee_percentage;
    private String sender_fee_fix;
    
    @Override
    public String toString(){
        
        return "merchantId: " + (merchantId == null ? "" : merchantId) + 
                ", payment_method_code: " + (payment_method_code == null ? "" : payment_method_code) + 
                ", payment_method_bank_id: " + (payment_method_bank_id == null ? "" : payment_method_bank_id) + 
                ", payment_method_bank_code: " + (payment_method_bank_code == null ? "" : payment_method_bank_code) + 
                ", sender_fee_percentage: " + (sender_fee_percentage == null ? "" : sender_fee_percentage) + 
                ", sender_fee_fix: " + (sender_fee_fix == null ? "" : sender_fee_fix)
        ;
    }
}
