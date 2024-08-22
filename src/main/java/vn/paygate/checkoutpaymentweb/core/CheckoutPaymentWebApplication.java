package vn.paygate.checkoutpaymentweb.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CheckoutPaymentWebApplication {

    public static void main(String[] args) {
        //TODO: Tách môi trường với các tham số phụ thuộc theo môi trường, tương tự các dự án khác
        SpringApplication.run(CheckoutPaymentWebApplication.class, args);
    }

}
