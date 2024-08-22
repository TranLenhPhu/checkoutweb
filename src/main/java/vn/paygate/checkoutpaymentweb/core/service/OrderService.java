package vn.paygate.checkoutpaymentweb.core.service;

//import com.example.checkoutpaymentweb.clients.AppConfig;
import org.springframework.beans.factory.annotation.Value;
import vn.paygate.checkoutpaymentweb.core.model.input.OrderInfo;
import vn.paygate.checkoutpaymentweb.core.model.output.MerchantFeeDTO;
import vn.paygate.checkoutpaymentweb.core.model.output.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import vn.paygate.lib.encryption.MerchantEncryption;

@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${checkoutwallet.api.url}")
    private String checkoutWalletApiUrl;

    @Value("${corewallet.api.url}")
    private String coreWalletApiUrl;

    public OrderDTO createOrder(OrderInfo orderInfo) {
        String url = checkoutWalletApiUrl + "/checkout/create";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<OrderInfo> request = new HttpEntity<>(orderInfo, headers);
        ResponseEntity<OrderDTO> response = restTemplate.exchange(url, HttpMethod.POST, request, OrderDTO.class);
        return response.getBody();
    }

    public OrderDTO getOrderByOrderCode(String orderCode) {
        String url = checkoutWalletApiUrl + "/checkout/order/" + orderCode;
        ResponseEntity<OrderDTO> response = restTemplate.getForEntity(url, OrderDTO.class);
        return response.getBody();
    }
    public MerchantFeeDTO getMerchantFeeByMerchantId(String merchantId) {
        String url = checkoutWalletApiUrl +"/checkout/merchantFee/"  + merchantId;
        return restTemplate.getForObject(url, MerchantFeeDTO.class);

    }

    public String calculateChecksum(String integrationMerchantCode, String orderCode, String orderAmount, String currency, String orderMerchantTime, String notifyUrl, String integrationSecureCode) throws Exception {
        String data = integrationMerchantCode + '|' + orderCode + '|' + orderAmount
            + '|'+ currency + '|' + orderMerchantTime + '|'
            + notifyUrl + '|' + integrationSecureCode;
        return MerchantEncryption.hash(data);
    }

    public String calculateChecksum(OrderInfo orderInfo, String decryptedSecureCode) throws Exception {
        return calculateChecksum(
                orderInfo.getIntegrationMerchantCode(),
                orderInfo.getOrderCode(),
                orderInfo.getOrderAmount(),
                orderInfo.getCurrency(),
                orderInfo.getOrderMerchantTime(),
                orderInfo.getCancelRedirectUrl(),
                decryptedSecureCode
        );
    }
}
