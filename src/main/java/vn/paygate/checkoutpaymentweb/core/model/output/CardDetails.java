package vn.paygate.checkoutpaymentweb.core.model.output;

import lombok.Data;
import vn.paygate.lib.encryption.*;


@Data
public class CardDetails {
    private String cardHolderName;
    private String cardNumber;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;

//    @Override
//    public String toString() {
//
//        return "cardHolderName: " + (cardHolderName == null ? "" : cardHolderName) +
//                ", cardNumber: " + (cardNumber == null ? "" : CardUtil.maskCreditCard(cardNumber)) +
//                ", payment_method_bank_id: " + (expiryMonth == null ? "" : expiryMonth) +
//                ", payment_method_bank_code: " + (expiryYear == null ? "" : expiryYear) +
//                ", cvv: " + (cvv == null ? "" : cvv.charAt(0) + "xx")
//                ;
//    }
}
