package vn.paygate.checkoutpaymentweb.core.controller;

import org.springframework.context.MessageSource;
import vn.paygate.checkoutpaymentweb.core.model.output.CardDetails;
import vn.paygate.checkoutpaymentweb.core.service.OrderService;
import vn.paygate.checkoutpaymentweb.core.service.MerchantService;
import vn.paygate.checkoutpaymentweb.core.model.output.MerchantFeeDTO;
import vn.paygate.checkoutpaymentweb.core.model.output.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("web")
public class PaymentController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MessageSource messageSource;
    private Object Locale;


    @GetMapping("/index/{orderCode}")
    public String getOrderByOrderCode(@PathVariable String orderCode, Model model) {
        OrderDTO orderDTOList = orderService.getOrderByOrderCode(orderCode);
        model.addAttribute("orders", orderDTOList);
        return "web/index";
    }


    @GetMapping("/payment/{orderCode}")
    public String getOrderDetails(@PathVariable String orderCode, Model model) {
        OrderDTO orderDTOList = orderService.getOrderByOrderCode(orderCode);
        MerchantFeeDTO merchantFee = orderService.getMerchantFeeByMerchantId(orderDTOList.getMerchantId());
        model.addAttribute("orders", orderDTOList);
        model.addAttribute("merchantFee", merchantFee);
        model.addAttribute("cardDetails", new CardDetails());
        return "web/QuocTe";
    }

    @PostMapping("/payment/{orderCode}")
    public String validatePayment(@PathVariable String orderCode,
                              @Valid @ModelAttribute("cardDetails") CardDetails cardDetails,
                              BindingResult result, Model model) {
        OrderDTO orderDTO = orderService.getOrderByOrderCode(orderCode);
        MerchantFeeDTO merchantFee = orderService.getMerchantFeeByMerchantId(orderDTO.getMerchantId());

        if (result.hasErrors()) {
            model.addAttribute("orders", orderDTO);
            model.addAttribute("merchantFee", merchantFee);
            //TODO: chuyển sang sử dụng giải pháp multi-language của springboot 
            // - Tham khảo https://www.baeldung.com/spring-boot-internationalization
            model.addAttribute("errorMessage", messageSource.getMessage("error.message.invalidInfo1", null, (java.util.Locale) Locale));
            return "web/QuocTe";
        }

        // Dummy validation logic
        if ("123456789".equals(cardDetails.getCardNumber()) &&
            "123".equals(cardDetails.getCvv())) {
            return "redirect:/web/Success";
        } else {
            model.addAttribute("orders", orderDTO);
            model.addAttribute("merchantFee", merchantFee);
            model.addAttribute("errorMessage",messageSource.getMessage("error.message.invalidInfo1", null, (java.util.Locale) Locale));
            return "web/QuocTe";
        }
    }

    @GetMapping("/Success")
    public String paymentSuccess() {
        return "web/success";
    }
    @GetMapping("/finalSuccess")
    public String showFinalSuccessPage(Model model) {
        String orderCode = (String) model.getAttribute("orderCode");
        model.addAttribute("orderCode" , orderCode);
        return "web/finalSuccess";
    }

//    @GetMapping("/Success/{orderCode}")
//    public String getOrderSuccess(@PathVariable String orderCode, Model model) {
//        OrderDTO orderDTO = orderService.getOrderByOrderCode(orderCode);
//        MerchantFeeDTO merchantFee = orderService.getMerchantFeeByMerchantId(orderDTO.getMerchantId());
//        model.addAttribute("orders", orderDTO);
//        model.addAttribute("merchantFee", merchantFee);
//        return "web/Success";
//    }

}
