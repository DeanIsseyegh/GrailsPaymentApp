package com.dean.payapp

import java.util.List;

import grails.transaction.Transactional

/**
 * This class exists to handle interactions with the domain objects. We put it here instead
 * of a controller to allow for greater modularity and re-usability. We also want account transfers
 * to be 'transactional' meaning if a deposit or withdrawal fails then we do a roll-back.
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
	
	/**
	 * Given there is enough money in the 'fromAccount, will withdraw money from the fromAccount' and deposit
	 * it into the 'toAccount' given
	 *
	 * @param accFromEmail
	 * @param accToEmail
	 * @param amount
	 *
	 * @return if there is enough money in 'fromAccount'
	 */
	boolean transfer(String accFromEmail, String toAccEmail, long amount) {
		if (isEnoughMoney(toAccEmail, amount)) {
			Account fromAcc = Account.findByEmail(accFromEmail)
			Account toAcc = Account.findByEmail(toAccEmail)
			addTransaction(fromAcc, toAcc, amount)
			return true
		} 
		false
	}
	
	/**
	 * Convenience method to add transactions to accounts
	 *
	 * @param fromAcc
	 * @param toAcc
	 * @param transaction
	 */
	private void addTransaction(Account fromAcc, Account toAcc, long amount) {
		Withdraw withdraw = new Withdraw(account: fromAcc)
		Deposit deposit = new Deposit(account: toAcc)
		Transaction transaction = new Transaction(amount: amount, withdraw: withdraw, deposit: deposit).save()
		
		fromAcc.balance -= transaction.amount
		toAcc.balance += transaction.amount
	}
	
	private boolean isEnoughMoney(String accEmail, long amount) {
		Account account = Account.findByEmail(accEmail)
		if (account.balance >= amount) {
			return true
		}
		false
	}
}
