package com.capgemini.servic.impl;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.exceptions.InsufficientBalanceExcption;
import com.capgemini.exceptions.InsufficientInitialBalanceExcption;
import com.capgemini.exceptions.InvalidAccountNumberExcption;
import com.capgemini.model.Account;
import com.capgemini.repo.AccountRepositoty;
import com.capgemini.service.AccountService;

public class AccountServiceImpl implements AccountService{

	AccountRepositoty accountRepositoty;
	
	public AccountServiceImpl(AccountRepositoty accountRepositoty) {
		super();
		this.accountRepositoty = accountRepositoty;
	}

	@Override
	public Account createAccount(int accountNo, int amount) throws InsufficientInitialBalanceExcption {
		if(amount<500){
			throw new InsufficientInitialBalanceExcption();
		}
				Account account = new Account(accountNo, amount);
		if(accountRepositoty.save(account)){
		return account;
		}
	return null;
	}

	@Override
	public int showBalance(int accountNo) throws InvalidAccountNumberExcption{
		 Account account = accountRepositoty.searchAccount(accountNo) ;
		 if(account !=null)
		 return account.getAmount();
		 else
			 throw new InvalidAccountNumberExcption();
	}

	@Override
	public int withdrawAmount(int accountNo,int amount)throws InvalidAccountNumberExcption,InsufficientBalanceExcption {
		Account account = accountRepositoty.searchAccount(accountNo) ;
		if(account != null){
			if(account.getAmount()>=amount){
		int finalAmount = account.getAmount()-amount;
		return finalAmount;
		}else{
			throw new InsufficientBalanceExcption();
			}}
		else{
			throw new InvalidAccountNumberExcption();
		}
			
		}
	


	@Override
	public int depositAmount(int accountNo, int amount) throws InvalidAccountNumberExcption{
		Account account = accountRepositoty.searchAccount(accountNo) ;
		if(account != null){
			
		int finalAmount = account.getAmount()+amount;
		return finalAmount;
		}
		else{
			throw new InvalidAccountNumberExcption();
		}
			
		}
	

	@Override
	public List<Account> fundTransfer(int donorAccountNo, int receiverAccountNo, int amount) throws InvalidAccountNumberExcption,InsufficientBalanceExcption{
		Account donorAccount = accountRepositoty.searchAccount(donorAccountNo) ;
		Account receiverAccount = accountRepositoty.searchAccount(receiverAccountNo) ;
		List<Account> accountList;
		if(donorAccount!=null && receiverAccount!=null){
			
			if(accountRepositoty.searchAccount(donorAccountNo).getAmount()>=amount){
				donorAccount.setAmount(donorAccount.getAmount()-amount);
				receiverAccount.setAmount(receiverAccount.getAmount()-amount);
				accountList = new ArrayList<>();
				accountList.add(donorAccount);
				accountList.add(receiverAccount);
			}
			else{
				
				throw new InsufficientBalanceExcption();
			}
			
			
		}
		else{
			throw new InvalidAccountNumberExcption();
			
		}
		
		return accountList;	
		
	}

}
