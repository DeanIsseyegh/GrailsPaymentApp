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
    }

    def cleanup() {
    }

    void "index should return a map containing a list of accounts"() {
		given:
		int numOfAccs = 2
		acc1 = new Account(accountName: "Bob", email: "bob@gmail.com")
		acc2 = new Account(accountName: "George", email: "george@gmail.com")
		mockDomain(Account, [acc1, acc2])
		
		when:
		def accounts = controller.index()
		
		then:
		def key = accounts.keySet().iterator().next()
		def value = accounts.get(key)
		value.size() == numOfAccs
		value.get(0) == acc1
		value.get(1) == acc2
    }
	
	void "makeTransfer should transfer money given a valid from and to acc, and there is enough balance"() {
		given:
		int numOfAccs = 2
		acc1 = new Account(accountName: "Bob", email: "bob@gmail.com")
		acc2 = new Account(accountName: "George", email: "george@gmail.com")
		mockDomain(Account, [acc1, acc2])
		
		
		when:
		params.fromAcc = acc1.accountName
		params.toAcc = acc2.accountName
		params.amount = "50"
		controller.makeTransfer()
		
		then:
		accountService.getTransactionHistory(acc1.email).size() == 1
	}
}
