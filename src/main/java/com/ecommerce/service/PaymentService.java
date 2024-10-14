package com.ecommerce.service;

import com.ecommerce.exception.OrderException;
import com.ecommerce.exception.UserException;
import com.ecommerce.response.PaymentLinkResponse;
import com.razorpay.RazorpayException;

public interface PaymentService {

    PaymentLinkResponse createPaymentLink(Long orderId, String jwt) throws RazorpayException, UserException, OrderException;

    void processPaymentWebhook(String payload) throws Exception;

}
