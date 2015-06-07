package com.dean.payapp

class TransactionHistoryController {

	def accountService
	String message
	
    def index() {
		def accounts = accountService.getAccounts()
		[accounts: accounts]
	}
	
	def list() {
		def accSelected = Account.findByAccountName(params.accountName)
		def accountHistory = accountService.getTransactionHistory(accSelected.email)
		[accountHistory: accountHistory]
	}
}
