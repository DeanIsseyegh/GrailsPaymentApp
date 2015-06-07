package com.dean.payapp

import grails.plugin.mail.MailService
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import spock.lang.Specification
import grails.test.mixin.domain.DomainClassUnitTestMixin
import com.icegreen.greenmail.util.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(TransferController)
@TestMixin(DomainClassUnitTestMixin)
@Mock([Transaction, Deposit, Withdraw, Account, MailService])
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
		controller.mailService = Mock(MailService)
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
		given:
		params.fromAcc = acc1.accountName
		params.toAcc = acc2.accountName
		params.amount = "50"
		def initialFromAccAmount = acc1.balance
		def initalToAccAmount = acc2.balance
				
		when:
		controller.makeTransfer()
		
		then:
		accountService.getTransactionHistory(acc1.email).size() == 1
		acc1.balance == initialFromAccAmount - Long.valueOf(params.amount)
		acc2.balance == initalToAccAmount + Long.valueOf(params.amount)
		String redirectURL = response.redirectedUrl
		redirectURL.toLowerCase().contains("successful") == true
	}
	
	void "makeTransfer should not transfer money and show error message given balance is too low"() {
		given:
		params.fromAcc = acc1.accountName
		params.toAcc = acc2.accountName
		params.amount = "9999"
		def initialFromAccAmount = acc1.balance
		def initalToAccAmount = acc2.balance
		
		when:
		controller.makeTransfer()
		
		then:
		accountService.getTransactionHistory(acc1.email).size() == 0
		initialFromAccAmount == acc1.balance
		initalToAccAmount == acc2.balance
		String redirectURL = response.redirectedUrl
		redirectURL.toLowerCase().contains("oops") == true
	}
}
