package vn.paygate.checkoutpaymentweb.core.model.input;

import lombok.Data;

@Data

public class RequestCheckOrder {
    private String _Function;
    private String _Version;
    private String _Merchant_id;
    private String _Merchant_password;
    private String _token;
}
