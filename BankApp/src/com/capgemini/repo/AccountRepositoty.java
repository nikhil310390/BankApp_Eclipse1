package com.capgemini.repo;

import com.capgemini.model.Account;

public interface AccountRepositoty {

	boolean save(Account account);
	Account searchAccount(int accountNumber);
}
