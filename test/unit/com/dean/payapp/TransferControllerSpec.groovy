package com.dean.payapp

import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import spock.lang.Specification
import grails.test.mixin.domain.DomainClassUnitTestMixin

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(TransferController)
@TestMixin(DomainClassUnitTestMixin)
@Mock([Transaction, Deposit, Withdraw, Account])
class TransferControllerSpec extends Specification {

	AccountService accountService
	Account acc1
	Account acc2
	
    def setup() {
		accountService = new AccountService()
		controller.accountService = accountService
		acc1 = new Account(accountName: "Bob", email: "bob@gmail.com")
		acc2 = new Account(accountName: "George", email: "george@gmail.com")
		mockDomain(Account, [acc1, acc2])
    }

    def cleanup() {
    }

    void "index should return a map containing a list of accounts"() {
		given:
		int numOfAccs = 2
		
		when:
		def accounts = controller.index()
		
		then:
		def key = accounts.keySet().iterator().next()
		def value = accounts.get(key)
		value.size() == numOfAccs
		value.get(0) == acc1
		value.get(1) == acc2
    }
	
	void "makeTransfer should transfer money and show a success message given there is enough balance"() {		
		when:
		params.fromAcc = acc1.accountName
		params.toAcc = acc2.accountName
		params.amount = "50"
		controller.makeTransfer()
		
		then:
		accountService.getTransactionHistory(acc1.email).size() == 1
		String redirectURL = response.redirectedUrl
		// e.g. /transfer/index?message=Oops%2C+there+was+not+enough+balance+in+that+account%21+Please+try+again+with+a+smaller+amount
		redirectURL.toLowerCase().contains("successful") == true
	}
	
	void "makeTransfer should not transfer money and show error message given balance is too low"() {
		when:
		params.fromAcc = acc1.accountName
		params.toAcc = acc2.accountName
		params.amount = "9999"
		controller.makeTransfer()
		
		then:
		accountService.getTransactionHistory(acc1.email).size() == 0
		//response.redirectedUrl.toString().containsIgnoreCase("oops")
		String redirectURL = response.redirectedUrl
		// e.g. /transfer/index?message=Oops%2C+there+was+not+enough+balance+in+that+account%21+Please+try+again+with+a+smaller+amount
		redirectURL.toLowerCase().contains("oops") == true
	}
}
