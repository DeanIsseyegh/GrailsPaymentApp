package com.dean.payapp

class TransferController {

	def accountService
	
    def index() { 
		def accounts = accountService.getAccounts()
		[accounts: accounts, message: params.message]
	}
	
	def makeTransfer() {
		def fromAcc = Account.findByAccountName(params.fromAcc)
		def toAcc = Account.findByAccountName(params.toAcc)
		boolean wasEnoughBalance = accountService.transfer(fromAcc.email, toAcc.email, Long.valueOf(params.amount))
		String message = "Transfer successful!"
		if (!wasEnoughBalance) {
			message = "Oops, there was not enough balance in that account! Please try again with a smaller amount"
		}
		redirect(action: "index", params: [message: message])
	}
}
