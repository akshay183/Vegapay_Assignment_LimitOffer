package com.vegapay.limitoffer.account.entity;

import com.vegapay.limitoffer.offer.entity.OfferEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;

    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "account_limit")
    private double accountLimit;

    @Column(name = "per_transaction_limit")
    private double perTransactionLimit;

    @Column(name = "last_account_limit")
    private double lastAccountLimit;

    @Column(name = "last_per_transaction_limit")
    private double lastPerTransactionLimit;

    @Column(name = "account_limit_update_time")
    private LocalDate accountLimitUpdateTime;

    @Column(name = "per_transaction_limit_update_time")
    private LocalDate perTransactionLimitUpdateTime;

}
