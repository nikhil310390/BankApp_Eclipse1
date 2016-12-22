package com.capgemini.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.exceptions.InsufficientBalanceExcption;
import com.capgemini.exceptions.InsufficientInitialBalanceExcption;
import com.capgemini.exceptions.InvalidAccountNumberExcption;
import com.capgemini.model.Account;
import com.capgemini.repo.AccountRepositoty;
import com.capgemini.servic.impl.AccountServiceImpl;
import com.capgemini.service.AccountService;

import static org.mockito.Mockito.when;

public class AccountTest {

	@Mock
	AccountRepositoty accountRepositoty;
	
	AccountService accountservice;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		accountservice = new AccountServiceImpl(accountRepositoty); 
	}
	//createAccount
	@Test(expected = InsufficientInitialBalanceExcption.class)
	public void whenAmmountlessthan500throwException() throws InsufficientInitialBalanceExcption {
		accountservice.createAccount(123, 123);
	}
	//createAccount
	@Test
	public void whenValidAmountCreateAccountSuccessfully() throws InsufficientInitialBalanceExcption {
		//Account account = accountservice.createAccount(123, 600);
		
		Account account = new Account(123, 600);
		
		when(accountRepositoty.save(account)).thenReturn(true);
		
		assertEquals(account, accountservice.createAccount(123, 600));
	}
	//showBalance
	@Test
	public void whenValidAccountNoShowAmountSuccessfully() throws InvalidAccountNumberExcption {
		//Account account = accountservice.createAccount(123, 600);
		
		int accountNo = 123;
		Account account = new Account(123, 600);
		
		when(accountRepositoty.searchAccount(accountNo)).thenReturn(account);
		
		assertEquals(account.getAmount(), accountservice.showBalance(123));
	}
	//showBalance
	@Test
	public void whenInValidAccountNumberthrowException() throws InvalidAccountNumberExcption {
		//Account account = accountservice.createAccount(123, 600);
		
		int accountNo = 999;
		Account account = new Account(123, 600);
		
		when(accountRepositoty.searchAccount(accountNo)).thenReturn(null);
	}
	//Withdraw
	@Test(expected = InvalidAccountNumberExcption.class)
	public void whenWithdrawinvalidAccountNumberthrowException() throws InsufficientBalanceExcption,InvalidAccountNumberExcption {
		//Account account = accountservice.createAccount(123, 600);
		
		int accountNo = 999;
		Account account = new Account(123, 600);
		
		when(accountRepositoty.searchAccount(999)).thenReturn(null);
		
		accountservice.withdrawAmount(accountNo, 123);
		
	}
	//Withdraw
	@Test(expected = InsufficientBalanceExcption.class)
	public void whenWithdrawInsufficientbalancethrowException() throws InsufficientBalanceExcption,InvalidAccountNumberExcption {
		//Account account = accountservice.createAccount(123, 600);
		
		int accountNo = 123;
		Account account = new Account(123, 200);
		
		when(accountRepositoty.searchAccount(accountNo)).thenReturn(account);
		
		accountservice.withdrawAmount(accountNo, 500);
		
	}
	//Withdraw
	@Test
	public void whenWithdrawallGoodReturnFinalAmount() throws InsufficientBalanceExcption,InvalidAccountNumberExcption {
		//Account account = accountservice.createAccount(123, 600);
		
		int accountNo = 123;
		Account account = new Account(123, 600);
		
		when(accountRepositoty.searchAccount(accountNo)).thenReturn(account);
		
		assertEquals(accountservice.withdrawAmount(accountNo, 500),100);
	}
	
	//Deposit
		@Test(expected = InvalidAccountNumberExcption.class)
		public void whenDepositinvalidAccountNumberthrowException() throws InvalidAccountNumberExcption {
			//Account account = accountservice.createAccount(123, 600);
			
			int accountNo = 999;
			Account account = new Account(123, 600);
			
			when(accountRepositoty.searchAccount(999)).thenReturn(null);
			
			accountservice.depositAmount(accountNo, 123);
		}
		//Deposit
		@Test
		public void whenDepositlGoodReturnFinalAmount() throws InvalidAccountNumberExcption {
			//Account account = accountservice.createAccount(123, 600);
			
			int accountNo = 123;
			Account account = new Account(123, 600);
			
			when(accountRepositoty.searchAccount(accountNo)).thenReturn(account);
			
			assertEquals(accountservice.depositAmount(accountNo, 500),1100);
		}
		
		
		//Trasnfer
		@Test(expected = InvalidAccountNumberExcption.class)
		public void whenTransferinvalidAccountNumberthrowException() throws InsufficientBalanceExcption,InvalidAccountNumberExcption {
			//Account account = accountservice.createAccount(123, 600);
			
			int accountNo = 999;
			Account donorAccount = new Account(123, 600);
			Account receiverAccount = new Account(567, 600);
			
			when(accountRepositoty.searchAccount(123)).thenReturn(donorAccount);
			when(accountRepositoty.searchAccount(567)).thenReturn(receiverAccount);
			when(accountRepositoty.searchAccount(accountNo)).thenReturn(null);
			
			accountservice.fundTransfer(123, 999, 50);
			
		}
		//Trasnfer
		@Test(expected = InsufficientBalanceExcption.class)
		public void whenTransferInsufficientbalancethrowException() throws InsufficientBalanceExcption,InvalidAccountNumberExcption {
			//Account account = accountservice.createAccount(123, 600);
			
			int accountNo = 999;
			Account donorAccount = new Account(123, 600);
			Account receiverAccount = new Account(567, 600);
			
			when(accountRepositoty.searchAccount(123)).thenReturn(donorAccount);
			when(accountRepositoty.searchAccount(567)).thenReturn(receiverAccount);
			
			accountservice.fundTransfer(123, 567, 10000);
			
		}
		//Trasnfer
		@Test
		public void whenTransferGoodReturnListOfAccounts() throws InsufficientBalanceExcption,InvalidAccountNumberExcption {
			//Account account = accountservice.createAccount(123, 600);
			
			int accountNo = 999;
			Account donorAccount = new Account(123, 600);
			Account receiverAccount = new Account(567, 600);
			
			when(accountRepositoty.searchAccount(123)).thenReturn(donorAccount);
			when(accountRepositoty.searchAccount(567)).thenReturn(receiverAccount);
			
			assertNotNull(accountservice.fundTransfer(123, 567, 50));
		}
		
	
	
}
