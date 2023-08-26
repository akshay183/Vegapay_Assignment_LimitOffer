package com.vegapay.limitoffer.offer.entity;

import com.vegapay.limitoffer.offer.common.LimitType;
import com.vegapay.limitoffer.offer.common.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "offer")
public class OfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "limit_offer_id")
    private Integer limitOfferId;

    @Column(name = "account_id")
    private Integer accountId;

    @Enumerated(EnumType.STRING)
    @Column(name = "limit_type", columnDefinition = "VARCHAR")
    private LimitType limitType;

    @Column(name = "new_limit")
    private Integer newLimit;

    @Column(name = "offer_activation_time")
    private LocalDate offerActivationTime;

    @Column(name = "offer_expiry_time")
    private LocalDate offerExpiryTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR")
    private Status status;
}
