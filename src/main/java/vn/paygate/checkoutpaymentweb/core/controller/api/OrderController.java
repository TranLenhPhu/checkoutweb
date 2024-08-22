/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.paygate.checkoutpaymentweb.core.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import vn.paygate.checkoutpaymentweb.core.service.CyberSourcePayment;
import vn.paygate.checkoutpaymentweb.core.service.MerchantService;
import vn.paygate.checkoutpaymentweb.core.model.input.OrderInfo;
import vn.paygate.checkoutpaymentweb.core.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.paygate.checkoutpaymentweb.core.model.output.MerchantDTO;
import vn.paygate.checkoutpaymentweb.core.model.output.OrderDTO;
import vn.paygate.lib.encryption.MerchantEncryption;


/**
 *
 * @author haibui
 */

@RestController
@RequestMapping("/api")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private CyberSourcePayment cyberSourcePayment;


    @GetMapping("/validate-checksum")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderInfo orderInfo ,
                                                @RequestParam String checksum,
                                                @RequestParam String integrationMerchantCode) throws Exception {
        MerchantDTO merchant = merchantService.getMerchantInfo(integrationMerchantCode);
        MerchantEncryption merchantEncryption = new MerchantEncryption();
        String decryptedSecureCode = merchantEncryption.decryptSecurePass(merchant.getIntegrationSecureCode());
        String calculatedChecksum = orderService.calculateChecksum(orderInfo,decryptedSecureCode);
        System.out.println("thoong tin " + calculatedChecksum);
        if (!calculatedChecksum.equals(checksum)) {
            OrderDTO createdOrder = new OrderDTO();
            createdOrder.setMerchantId(calculatedChecksum);
            createdOrder.setCheckOutUrl("thuc hien loi");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createdOrder);
        }
        OrderDTO createdOrder = orderService.createOrder(orderInfo);
        return ResponseEntity.ok(createdOrder);
    }



    @PostMapping("/process")
    public ResponseEntity<String> processPayment(@RequestBody String requestBody) {
        try {
            return cyberSourcePayment.processPayment(requestBody);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing payment");
        }
    }

}