package com.ecommerce.modal;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInformation {

    private String cardholderName;
    private String cardNumber;
    private LocalDate expirationDate;
    private String cvv;

}


