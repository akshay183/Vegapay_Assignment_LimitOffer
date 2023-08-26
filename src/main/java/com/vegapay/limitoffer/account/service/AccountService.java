package com.vegapay.limitoffer.account.service;

import com.vegapay.limitoffer.account.entity.AccountEntity;
import com.vegapay.limitoffer.account.repository.AccountRepository;
import com.vegapay.limitoffer.offer.common.LimitType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
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
            throw new RuntimeException(e.getMessage());
        }
    }
}
