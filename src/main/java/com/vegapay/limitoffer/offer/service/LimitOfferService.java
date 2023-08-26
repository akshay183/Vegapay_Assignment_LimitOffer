package com.vegapay.limitoffer.offer.service;

import com.vegapay.limitoffer.account.service.AccountService;
import com.vegapay.limitoffer.offer.common.Status;
import com.vegapay.limitoffer.offer.entity.OfferEntity;
import com.vegapay.limitoffer.offer.model.UpdateStatusRequest;
import com.vegapay.limitoffer.offer.repository.LimitOfferRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class LimitOfferService {

    private final LimitOfferRepository limitOfferRepository;

    private final AccountService accountService;

    public LimitOfferService(LimitOfferRepository customQueryService, AccountService accountService) {
        this.limitOfferRepository = customQueryService;
        this.accountService = accountService;
    }
    public String updateLimitOfferStatus(Integer limitOfferId, UpdateStatusRequest updateStatusRequest) {

        try{

            String status = updateStatusRequest.getStatus().toString();

            Optional<OfferEntity> optionalLimitOffer = limitOfferRepository.findById(limitOfferId);

            if(optionalLimitOffer.isPresent()) {
                OfferEntity limitOffer = optionalLimitOffer.get();

                if(limitOffer.getStatus()==Status.Pending){
                    if(updateStatusRequest.getStatus() == Status.Accepted) {

                        accountService.updateAccountLimits(limitOffer.getAccountId(), limitOffer.getLimitType(), limitOffer.getNewLimit());
                        limitOffer.setStatus(Status.Accepted);

                        limitOfferRepository.save(limitOffer);
                    } else if (updateStatusRequest.getStatus() == Status.Rejected) {
                        limitOffer.setStatus(Status.Rejected);
                    }
                    else{
                        throw new RuntimeException("Unidentified Status Value");
                    }
                }
                else{
                    throw new Exception("Offer not in Pending state");
                }
            }
            else{
                throw new Exception("Offer id not present");
            }
            return "Success";

        }
        catch (Exception e) {
            log.info(e.getMessage());
            return (e.getMessage());
        }
    }
}
