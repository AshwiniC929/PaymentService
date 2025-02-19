package com.ashman.PaymentService.Service;

import com.ashman.PaymentService.Entity.TransactionDetails;
import com.ashman.PaymentService.Model.PaymentMode;
import com.ashman.PaymentService.Model.PaymentRequest;
import com.ashman.PaymentService.Model.PaymentResponse;
import com.ashman.PaymentService.Repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;
    @Override
    public Long doPayment(PaymentRequest paymentRequest) {

        log.info("Recording Payment Details: {}", paymentRequest);

        TransactionDetails transactionDetails = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();

        transactionDetailsRepository.save(transactionDetails);

        log.info("Transaction Completed with Id: {}", transactionDetails.getId());

        return transactionDetails.getId();
    }


    @Override
    public PaymentResponse getPaymentDetailsByOrder(String  orderId) {

        log.info("Getting Payment details for the Order Id: {}",orderId);

        TransactionDetails transactionDetails = transactionDetailsRepository.findByOrderId(Long.valueOf(orderId));

        PaymentResponse paymentResponse =
                PaymentResponse.builder()
                        .paymentId(transactionDetails.getId())
                        .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                        .paymentDate(transactionDetails.getPaymentDate())
                        .orderId(transactionDetails.getOrderId())
                        .status(transactionDetails.getPaymentStatus())
                        .amount(transactionDetails.getAmount())
                        .build();
        return paymentResponse;
    }
}
