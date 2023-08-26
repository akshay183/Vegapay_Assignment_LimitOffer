package com.vegapay.limitoffer.offer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.vegapay.limitoffer.offer.common.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateStatusRequest {

    @JsonProperty("status")
    private Status status;
}
