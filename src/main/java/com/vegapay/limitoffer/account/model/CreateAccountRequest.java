package com.vegapay.limitoffer.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {

    @JsonProperty(value = "customer_id")
    private Integer customerId;

    @JsonProperty(value = "account_limit")
    private Integer accountLimit;

    @JsonProperty(value = "per_transaction_limit")
    private Integer perTransactionLimit;

}
