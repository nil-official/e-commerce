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

    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkReferenceId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentId;

}
