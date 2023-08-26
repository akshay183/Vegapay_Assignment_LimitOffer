package com.vegapay.limitoffer.offer.service;

import com.vegapay.limitoffer.account.entity.AccountEntity;
import com.vegapay.limitoffer.account.repository.AccountRepository;
import com.vegapay.limitoffer.account.service.AccountService;
import com.vegapay.limitoffer.offer.common.LimitType;
import com.vegapay.limitoffer.offer.common.Status;
import com.vegapay.limitoffer.offer.entity.OfferEntity;
import com.vegapay.limitoffer.offer.model.CreateLimitOfferRequest;
import com.vegapay.limitoffer.offer.model.UpdateStatusRequest;
import com.vegapay.limitoffer.offer.repository.LimitOfferRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class LimitOfferService {
    private final AccountRepository accountRepository;

    private final LimitOfferRepository limitOfferRepository;

    private final AccountService accountService;

    public LimitOfferService(LimitOfferRepository customQueryService, AccountService accountService,
                             AccountRepository accountRepository) {
        this.limitOfferRepository = customQueryService;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    public List<OfferEntity> findActiveLimitOffers(Integer accountId, LocalDate activeDate) {

        try{
            List<OfferEntity> offerEntities;

            if(ObjectUtils.isEmpty(activeDate)) {
                offerEntities = limitOfferRepository.findActiveLimitOffers(accountId);
            }
            else {
                offerEntities = limitOfferRepository.findActiveLimitOffers(accountId,activeDate);
            }

            return offerEntities;
        }
        catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

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

    public void createLimitOffer(CreateLimitOfferRequest createLimitOfferRequest) {

        AccountEntity accountEntity = accountRepository.findById(createLimitOfferRequest.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        if(createLimitOfferRequest.getLimitType() == LimitType.ACCOUNT_LIMIT) {
            if (createLimitOfferRequest.getNewLimit() <= accountEntity.getAccountLimit()) {
                throw new IllegalArgumentException("new limit cant be less than previous one");
            }
        }
        else if (createLimitOfferRequest.getLimitType() == LimitType.PER_TRANSACTION_LIMIT) {
            if (createLimitOfferRequest.getNewLimit() <= accountEntity.getPerTransactionLimit()) {
                throw new IllegalArgumentException("new limit cant be less than previous one");
            }
        }

        OfferEntity limitOffer = OfferEntity.builder()
                .accountId(createLimitOfferRequest.getAccountId())
                .limitType(createLimitOfferRequest.getLimitType())
                .newLimit(createLimitOfferRequest.getNewLimit())
                .offerActivationTime(createLimitOfferRequest.getOfferActivationTime())
                .offerExpiryTime(createLimitOfferRequest.getOfferExpiryTime())
                .status(Status.Pending)
                .build();

        limitOfferRepository.save(limitOffer);
    }
}
