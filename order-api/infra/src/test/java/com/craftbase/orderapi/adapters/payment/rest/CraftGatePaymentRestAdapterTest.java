package com.craftbase.orderapi.adapters.payment.rest;

import com.craftbase.orderapi.adapters.payment.model.properties.CraftGateProperties;
import com.craftbase.orderapi.common.model.enums.PriceType;
import com.craftbase.orderapi.payment.model.dto.CreditCard;
import com.craftbase.orderapi.payment.model.dto.PaymentCreateItem;
import com.craftbase.orderapi.payment.model.dto.PaymentDto;
import com.craftbase.orderapi.payment.model.usecase.PaymentCreate;
import com.craftbase.orderapi.payment.model.usecase.PaymentRefund;
import io.craftgate.Craftgate;
import io.craftgate.adapter.PaymentAdapter;
import io.craftgate.exception.CraftgateException;
import io.craftgate.model.RefundDestinationType;
import io.craftgate.request.RefundPaymentRequest;
import io.craftgate.response.PaymentRefundResponse;
import io.craftgate.response.PaymentResponse;
import io.craftgate.response.dto.PaymentError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CraftGatePaymentRestAdapterTest {

    @Mock
    private CraftGateProperties craftGateProperties;

    @Mock
    private Craftgate craftgate;

    @Mock
    private PaymentAdapter paymentService;

    @Mock
    private PaymentResponse paymentResponse;

    @InjectMocks
    private CraftGatePaymentRestAdapter adapter;

    private PaymentCreate paymentCreate;

    @Before
    public void setUp() {
        paymentCreate = PaymentCreate.builder()
                .price(BigDecimal.valueOf(100.0))
                .priceType(PriceType.TRY)
                .transactionId(UUID.randomUUID())
                .creditCard(CreditCard.builder()
                        .cardHolderName("Test User")
                        .cardNumber("5528790000000008")
                        .expireYear("2030")
                        .expireMonth("01")
                        .cvc("123")
                        .build())
                .paymentCreateItems(List.of(PaymentCreateItem.builder()
                        .id("1")
                        .name("Test Item")
                        .price(BigDecimal.valueOf(100.0))
                        .build()))
                .build();
    }


    @Test
    public void testPay_Success() {
        when(craftGateProperties.getApiKey()).thenReturn("sandbox-ocNNqeLVOsMquyqOVSKmYYgplaSZhhls");
        when(craftGateProperties.getSecretKey()).thenReturn("sandbox-encbFWAefCdnbkmLtPkiNWHPKknjSoQp");
        when(craftGateProperties.getBaseUrl()).thenReturn("https://sandbox-api.craftgate.io");

        PaymentDto result = adapter.pay(paymentCreate);

        assertNotNull(result);
        assertEquals(Boolean.TRUE, result.isSuccess());
        assertEquals("100.00000000", result.getPaidPrice().toString());
    }

    @Test
    public void testPay_Failed() {
        when(craftGateProperties.getApiKey()).thenReturn("apiKey");
        when(craftGateProperties.getSecretKey()).thenReturn("secretKey");
        when(craftGateProperties.getBaseUrl()).thenReturn("baseUrl");
        PaymentError paymentError = new PaymentError();
        paymentError.setErrorCode("ERR");
        paymentError.setErrorDescription("Error description");


        assertThrows(CraftgateException.class, () -> adapter.pay(paymentCreate));

    }

    @Test
    public void testRefund_Failed() {
        
        PaymentRefund paymentRefund = PaymentRefund.builder().paymentId("1").build();

        RefundPaymentRequest expectedRequest = RefundPaymentRequest.builder()
                .paymentId(12345L)
                .refundDestinationType(RefundDestinationType.PROVIDER)
                .build();

        PaymentRefundResponse refundResponse = new PaymentRefundResponse();
        refundResponse.setId(54321L);
        refundResponse.setCreatedDate(LocalDateTime.now());
        refundResponse.setRefundPrice(BigDecimal.valueOf(100));

        when(craftGateProperties.getApiKey()).thenReturn("sandbox-ocNNqeLVOsMquyqOVSKmYYgplaSZhhls");
        when(craftGateProperties.getSecretKey()).thenReturn("sandbox-encbFWAefCdnbkmLtPkiNWHPKknjSoQp");
        when(craftGateProperties.getBaseUrl()).thenReturn("https://sandbox-api.craftgate.io");

        assertThrows(CraftgateException.class, () -> adapter.refund(paymentRefund));

    }
}