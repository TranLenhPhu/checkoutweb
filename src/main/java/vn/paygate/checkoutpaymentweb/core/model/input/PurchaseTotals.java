package vn.paygate.checkoutpaymentweb.core.model.input;

import lombok.Data;

@Data
public class PurchaseTotals {
    private String currency;
    private String grandTotalAmount;
}
