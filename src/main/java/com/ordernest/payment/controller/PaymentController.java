package com.ordernest.payment.controller;

import com.ordernest.payment.dto.ProcessPaymentRequest;
import com.ordernest.payment.service.PaymentProcessingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentProcessingService paymentProcessingService;

    @PostMapping("/process")
    public ResponseEntity<Void> processPayment(
            @Valid @RequestBody ProcessPaymentRequest request,
            @RequestHeader(value = "Authorization", required = false) String authorization
    ) {
        paymentProcessingService.process(request, authorization);
        return ResponseEntity.ok().build();
    }
}
