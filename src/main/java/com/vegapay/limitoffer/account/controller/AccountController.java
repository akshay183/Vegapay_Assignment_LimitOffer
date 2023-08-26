package com.vegapay.limitoffer.account.controller;

import com.vegapay.limitoffer.account.entity.AccountEntity;
import com.vegapay.limitoffer.account.model.CreateAccountRequest;
import com.vegapay.limitoffer.account.service.AccountService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/account")
@Validated
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping()
    ResponseEntity<String> createAccount (@RequestBody CreateAccountRequest createAccountRequest) {

        return new ResponseEntity<>(accountService.createAccount(createAccountRequest), HttpStatus.OK);
    }

    @GetMapping()
    ResponseEntity<AccountEntity> getAccountDetails
            (@RequestParam(name = "account_id") @NotNull Integer accountId) {

        return new ResponseEntity<>(accountService.getAccountDetails(accountId), HttpStatus.OK);
    }
}
