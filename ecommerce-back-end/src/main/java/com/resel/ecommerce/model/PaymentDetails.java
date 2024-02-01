package com.resel.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDetails {

    private String paymentMethod;

    private String paymentDetails;
    private String paymentId;
    private String razorpayPaymentLinkId;

    private String razorpayPaymentLinkReferenceId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentId;
    private String status;








}
