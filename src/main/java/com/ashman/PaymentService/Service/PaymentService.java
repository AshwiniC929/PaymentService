package com.ashman.PaymentService.Service;

import com.ashman.PaymentService.Model.PaymentRequest;
import com.ashman.PaymentService.Model.PaymentResponse;

public interface PaymentService {

    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrder(String  orderId);
}
