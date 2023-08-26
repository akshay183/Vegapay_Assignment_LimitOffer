package com.vegapay.limitoffer.account.service;

import com.vegapay.limitoffer.account.entity.AccountEntity;
import com.vegapay.limitoffer.account.model.CreateAccountRequest;
import com.vegapay.limitoffer.account.repository.AccountRepository;
import com.vegapay.limitoffer.offer.common.LimitType;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Log4j2
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void updateAccountLimits(int accountId, LimitType limitType, Integer newLimit) {

        Optional<AccountEntity> optionalAccount = accountRepository.findById(accountId);

        try {
            if (optionalAccount.isPresent()) {
                AccountEntity account = optionalAccount.get();

                if (limitType == LimitType.ACCOUNT_LIMIT) {
                    account.setLastAccountLimit(account.getAccountLimit());
                    account.setAccountLimit(newLimit);
                    account.setAccountLimitUpdateTime(LocalDate.now());
                } else if (limitType == LimitType.PER_TRANSACTION_LIMIT) {
                    account.setLastPerTransactionLimit(account.getPerTransactionLimit());
                    account.setPerTransactionLimit(newLimit);
                    account.setPerTransactionLimitUpdateTime(LocalDate.now());
                }

                accountRepository.save(account);
            }
            else {
                throw new RuntimeException("account id not found");
            }
        }
        catch (Exception e) {
            log.info (e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public String createAccount(CreateAccountRequest createAccountRequest) {

        try{
            AccountEntity account = AccountEntity.builder().
                    accountLimit(createAccountRequest.getAccountLimit()).
                    customerId(createAccountRequest.getCustomerId()).
                    perTransactionLimit(createAccountRequest.getPerTransactionLimit()).
                    build();

            account = accountRepository.save(account);

            return account.getAccountId().toString();
        }
        catch (Exception e) {
            log.info(e.getMessage());
            return "Exception occurred";
        }

    }

    public AccountEntity getAccountDetails(Integer accountId) {

        try{
            Optional<AccountEntity> optionalAccountEntity = accountRepository.findById(accountId);

            if(optionalAccountEntity.isPresent()) {
                AccountEntity accountEntity = optionalAccountEntity.get();
                return accountEntity;
            }
            else{
                throw new RuntimeException("account id not present");
            }
        }
        catch(Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
