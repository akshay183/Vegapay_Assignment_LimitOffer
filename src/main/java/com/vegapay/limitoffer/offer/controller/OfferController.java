package com.vegapay.limitoffer.offer.controller;

import com.vegapay.limitoffer.offer.model.UpdateStatusRequest;
import com.vegapay.limitoffer.offer.service.LimitOfferService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/limit-offers")
@Log4j2
public class OfferController {

    private LimitOfferService limitOfferService;

    public OfferController(LimitOfferService limitOfferService) {
        this.limitOfferService = limitOfferService;
    }

    @PutMapping("/{limitOfferId}/status")
    ResponseEntity<String> updateLimitOfferStatus(@PathVariable Integer limitOfferId,
                                                  @RequestBody UpdateStatusRequest updateStatusRequest) {

        log.info("to the start of updateLimitOfferStatus");
        return new ResponseEntity<>(limitOfferService.updateLimitOfferStatus(limitOfferId, updateStatusRequest),
                HttpStatus.OK);
    }
}
