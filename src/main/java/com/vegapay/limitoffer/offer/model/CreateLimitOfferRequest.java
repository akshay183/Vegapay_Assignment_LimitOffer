package com.vegapay.limitoffer.offer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vegapay.limitoffer.offer.common.LimitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLimitOfferRequest {

    @JsonProperty(value = "account_id")
    private Integer accountId;

    @JsonProperty(value = "limit_type")
    private LimitType limitType;

    @JsonProperty(value = "new_limit")
    private Integer newLimit;

    @JsonProperty("offer_activation_time")
    private LocalDate offerActivationTime;

    @JsonProperty("offer_expiry_time")
    private LocalDate offerExpiryTime;

}
