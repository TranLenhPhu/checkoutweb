package vn.paygate.checkoutpaymentweb.core.service;

import java.util.*; 
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import vn.paygate.checkoutpaymentweb.core.model.output.MerchantDTO;
import vn.paygate.checkoutpaymentweb.core.model.output.MerchantFeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MerchantService {
    @Autowired
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${corewallet.api.url}")
    private String coreWalletApiUrl;

    @Value("${checkoutwallet.api.url}")
    private String checkoutWalletApiUrl;

    private static final Logger logger = LogManager.getLogger(MerchantService.class);

    public MerchantDTO getMerchantInfo(String integrationMerchantCode) {
        String url = checkoutWalletApiUrl + "/checkout/merchants/" + integrationMerchantCode;
        return restTemplate.getForObject(url, MerchantDTO.class);

    }

    public List<MerchantFeeDTO> getMerchantFees() {
        String url = coreWalletApiUrl + "/merchantFee";
        ResponseEntity<MerchantFeeDTO[]> responseEntity = restTemplate.getForEntity(url, MerchantFeeDTO[].class);
        MerchantFeeDTO[] fees = responseEntity.getBody();
        logger.info("Thông tin merchantFee: " + Arrays.toString(fees));

        return fees != null ? List.of(fees).stream()
                .map(fee -> {
                    MerchantFeeDTO dto = new MerchantFeeDTO();
                    dto.setPayment_method_code(fee.getPayment_method_code());
                    dto.setPayment_method_bank_id(fee.getPayment_method_bank_id());
                    dto.setPayment_method_bank_code(fee.getPayment_method_bank_code());
                    dto.setSender_fee_fix(fee.getSender_fee_fix());
                    dto.setSender_fee_percentage(fee.getSender_fee_percentage());
                    return dto;
                }).collect(Collectors.toList()) : List.of();
    }
}
