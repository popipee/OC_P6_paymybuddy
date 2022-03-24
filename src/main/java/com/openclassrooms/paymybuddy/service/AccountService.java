package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Account;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.utils.CurrencyCode;
import com.openclassrooms.paymybuddy.model.utils.layout.Paged;
import com.openclassrooms.paymybuddy.repository.AccountRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.transaction.Transactional;

@Service
@Transactional
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private TransactionService transactionService;
  @Autowired
  private BankTransactionService bankTransactionService;

  /**
   * This method creates a new account with a specified currency code   *
   *
   * @param currencyCode
   * @return
   */

  public Account createNewAccount(CurrencyCode currencyCode) {
    Account newAccount = new Account();
    newAccount.setBalance(0);
    newAccount.setCurrencyCode(currencyCode);
    return newAccount;
  }

  public Transaction makeATransaction(
    Account fromAccount,
    Account toAccount,
    @Nullable String description,
    float amount,
    Account feesAccount
  ) {
    return transactionService
      .makeATransaction(fromAccount, toAccount, description, amount, feesAccount);
  }

  public BankTransaction makeABankTransaction(Account account,
                                              String iban,
                                              String swiftCode,
                                              String description,
                                              float amount) {

    return bankTransactionService.makeABankTransaction(account, iban, swiftCode, description, amount);
  }

  public Paged<Transaction> getAllPagedTransaction(int pageNumber, int size, Account account) {
    return transactionService.getPageByAccount(pageNumber, size, account);
  }
}
