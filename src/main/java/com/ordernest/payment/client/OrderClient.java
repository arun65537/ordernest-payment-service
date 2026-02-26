package com.ordernest.payment.client;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

@Component
public class OrderClient {

    private final RestClient restClient;

    public OrderClient(@Value("${app.order.base-url}") String orderBaseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(orderBaseUrl)
                .build();
    }

    public OrderDetailsResponse getOrderById(UUID orderId, String authorization) {
        try {
            RestClient.RequestHeadersSpec<?> requestSpec = restClient.get().uri("/api/orders/{orderId}", orderId);
            if (authorization != null && !authorization.isBlank()) {
                requestSpec = requestSpec.header("Authorization", authorization);
            }

            return requestSpec.retrieve()
                    .onStatus(HttpStatusCode::isError, (request, response) -> {
                        throw new IllegalStateException("Unable to fetch order for payment");
                    })
                    .body(OrderDetailsResponse.class);
        } catch (RestClientResponseException ex) {
            throw new IllegalStateException("Unable to fetch order for payment");
        } catch (RestClientException ex) {
            throw new IllegalStateException("Unable to fetch order for payment");
        }
    }
}
