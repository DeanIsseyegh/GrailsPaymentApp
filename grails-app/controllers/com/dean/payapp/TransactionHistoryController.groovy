package com.dean.payapp

class TransactionHistoryController {

	def accountService
	String message
	
	/**
	 * Returns a map containing a list of accounts.
	 * 
	 * @return map containing list of accounts
	 */
    def index() {
		def accounts = accountService.getAccounts()
		[accounts: accounts]
	}
	
	/**
	 * Returns a map containing a list of all transactions that have occurred on the account selected.
	 * 
	 * @return map containing transactions
	 */
	def list() {
		def accSelected = Account.findByAccountName(params.accountName)
		def accountHistory = accountService.getTransactionHistory(accSelected.email)
		[accountHistory: accountHistory]
	}
}
