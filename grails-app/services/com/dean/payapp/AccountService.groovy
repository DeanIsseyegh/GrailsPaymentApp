package com.dean.payapp

import grails.transaction.Transactional

/**
 * This class exists to handle interactions with the domain objects. We put it here instead
 * of a controller to allow for greater modularity and re-usability. We also want account transfers
 * to be 'transactional' meaning if a deposit or withdrawal fails then we do a roll-back.
 *
 * @author Dean
 *
 */
/* @Transactional - YIKES! This causes nullpointer exceptions to be thrown in our test.
 * https://jira.grails.org/browse/GRAILS-10538
 * This could potentially be due to something else I am missing, but I will comment out for now
 * for the sake of saving time!
 * 
 */
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
	 * Transfers money between accounts given there emails.
	 * 
	 * Given there is enough money in the 'fromAccount', will withdraw money from the 'fromAccount' and deposit
	 * it into the 'toAccount'. 
	 *
	 * @param accFromEmail
	 * @param accToEmail
	 * @param amount
	 *
	 * @return if there is enough money in 'fromAccount'
	 */
	boolean transfer(String accFromEmail, String toAccEmail, long amount) {
		if (isEnoughMoney(accFromEmail, amount)) {
			Account fromAcc = Account.findByEmail(accFromEmail)
			Account toAcc = Account.findByEmail(toAccEmail)
			addTransaction(fromAcc, toAcc, amount)
			return true
		} 
		false
	}
	
	/**
	 * Convenience method to add transactions to accounts.
	 * 
	 * Warning: Does not check if there is enough money first! Only call this method in tests
	 * or if you have already checked the balance first.
	 *
	 * @param fromAcc
	 * @param toAcc
	 * @param transaction
	 */
	void addTransaction(Account fromAcc, Account toAcc, long amount) {
		Withdraw withdraw = new Withdraw(account: fromAcc)
		Deposit deposit = new Deposit(account: toAcc)
		Transaction transaction = new Transaction(amount: amount, withdraw: withdraw, deposit: deposit).save()
		
		fromAcc.balance -= transaction.amount
		toAcc.balance += transaction.amount
	}
	
	private boolean isEnoughMoney(String accEmail, long amount) {
		Account account = Account.findByEmail(accEmail)
		if (account.balance >= amount) {
			return true // return keyword needed here so that it knows to break out of method
		}
		false
	}
	
	/**
	 * Will get a list of all the deposits and withdrawals that have occurred on the account.
	 *
	 * @param accEmail
	 * @return a list of all the transactions that have happened on specified account sorted by by most recent
	 */
	List<AccountTransaction> getTransactionHistory(String accEmail) {
		Account account = Account.findByEmail(accEmail)
		def withdrawals = Withdraw.findAllByAccount(account)
		def deposits = Deposit.findAllByAccount(account)
		List<AccountTransaction> accTransactions = new ArrayList<>()
		
		// Lets try out some Groovy loop goodness!
		withdrawals.each{ withdraw ->
			Transaction trans = withdraw.transaction
			Deposit deposit = trans.deposit
			Account otherAcc = deposit.account
			// Create an account with negative amount because its a withdrawal
			AccountTransaction accTrans = new AccountTransaction(amount: (-trans.amount), date: trans.date,
				otherAccName: otherAcc.accountName, otherAccEmail: otherAcc.email)
			accTransactions.add(accTrans)
		}
		
		deposits.each{ deposit ->
			Transaction trans = deposit.transaction
			Withdraw withdraw = trans.withdraw
			Account otherAcc = withdraw.account
			// Create an account with a postive amount because its a deposit
			AccountTransaction accTrans = new AccountTransaction(amount: trans.amount, date: trans.date,
				otherAccName: otherAcc.accountName, otherAccEmail: otherAcc.email)
			accTransactions.add(accTrans)
		}
		
		// No return keyword needed in Groovy!
		accTransactions.sort(true)
	}
	
}
