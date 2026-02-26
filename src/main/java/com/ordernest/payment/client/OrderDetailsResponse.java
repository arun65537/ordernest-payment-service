package com.ordernest.payment.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ordernest.payment.dto.OrderItemResponse;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderDetailsResponse(
        String orderId,
        OrderItemResponse item
) {
}
