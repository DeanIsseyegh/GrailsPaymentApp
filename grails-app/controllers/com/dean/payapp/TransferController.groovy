package com.dean.payapp

class TransferController {

	def accountService
	def mailService

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
		} else {
			sendSuccessEmail(fromAcc, toAcc, Long.valueOf(params.amount))
		}
		redirect(action: "index", params: [message: message])
	}

	def sendSuccessEmail(Account fromAcc, Account toAcc, long amount) {
		mailService.sendMail {
			to fromAcc.email, toAcc.email
			subject "Money Transferred"
			body "We are pleased to inform that " + fromAcc.accountName + " sent " + toAcc.accountName + " a payment of " + amount + " pounds"
		}
	}
}
