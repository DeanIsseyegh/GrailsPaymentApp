package com.dean.payapp

import java.util.List;

import grails.transaction.Transactional

/**
 * This class exists to handle interactions with the domain objects. We put it here instead
 * of a controller to allow for greater modularity and re-usability. We also want account transfers
 * to be 'transactional' meaning if a deposit or withdrawal fails then we do a rollback.
 *
 * @author Dean
 *
 */
@Transactional
class AccountService {
	
	static transactional = true
	
  	/**
	 * Retrieves all the accounts.
	 * 
	 * @return a list of all the accounts
	 */
	List<Account> getAccounts() {
		Account.findAll()
	}
}
