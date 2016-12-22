package com.capgemini.service;

import java.util.List;

import com.capgemini.exceptions.InsufficientBalanceExcption;
import com.capgemini.exceptions.InsufficientInitialBalanceExcption;
import com.capgemini.exceptions.InvalidAccountNumberExcption;
import com.capgemini.model.Account;

public interface AccountService {

	Account createAccount(int accountNo,int amount) throws InsufficientInitialBalanceExcption;
	int showBalance(int accountNo) throws InvalidAccountNumberExcption;
	int withdrawAmount(int accountNo,int amount)throws InvalidAccountNumberExcption,InsufficientBalanceExcption;
	int depositAmount(int accountNo,int amount)throws InvalidAccountNumberExcption;
	List<Account> fundTransfer(int donorAccountNo,int receiverAccountNo,int amount)throws InvalidAccountNumberExcption,InsufficientBalanceExcption;
}
