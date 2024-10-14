package com.ecommerce.modal;

import com.ecommerce.user.domain.PaymentMethod;
import com.ecommerce.user.domain.PaymentStatus;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {

    private String razorpayAccountId;
    private String razorpayPaymentId;
    private PaymentMethod razorpayPaymentMethod;
    private String razorpayPaymentDescription;
    private PaymentStatus razorpayPaymentStatus;

    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkUrl;

}
