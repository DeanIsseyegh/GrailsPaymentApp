package com.dean.payapp

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.dean.payapp.Account
/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(AccountService)
class AccountServiceSpec extends Specification {
	
	List<Account> testAccounts;
	def acc1;
	def acc2;
	def acc3;
	def acc4;
	
	def setup() {
		mockDomain(Account)
		mockDomain(Withdraw)
		mockDomain(Deposit)
		mockDomain(Transaction)
		
		acc1 = new Account(accountName: "Bob", email: "bob@gmail.com").save()
		acc2 = new Account(accountName: "George", email: "george@gmail.com").save()
		acc3 = new Account(accountName: "Rodger", email: "rodger@gmail.com").save()
		acc4 = new Account(accountName: "Felicity", email: "felicity@gmail.com").save()
		
		testAccounts = new ArrayList<>()
		testAccounts.add(acc1)
		testAccounts.add(acc2)
		testAccounts.add(acc3)
		testAccounts.add(acc4)
	}
	
    void "should retrieve list of accounts"() {
		given:
		def expectedNumOfAccs = testAccounts.size()
		
		when:
		def accsFound = service.getAccounts()
		
		then:
		accsFound.size() == expectedNumOfAccs
    }
	
	void "should be transactional"() {
		expect:
		service.transactional == true
	}
	
	void "should transfer money if account has enough balance"() {
		given:
		def initialFromAccBalance = acc1.balance
		def initialToAccBalance = acc2.balance
		
		when:
		def result = service.transfer(acc1.email, acc2.email, amountToTransfer)
		
		then:
		println "Acc1 balance from " + initialFromAccBalance + " => " + acc1.balance 
		println "Acc2 balance from " + initialToAccBalance + " => " + acc2.balance
		acc1.balance == initialFromAccBalance - amountToTransfer
		acc2.balance == initialToAccBalance + amountToTransfer
		result == true
		
		where:
		amountToTransfer = 50
	}
	
	void "should not transfer money and return false given the account does not have enough balance"() {
		given:
		def initialFromAccBalance = acc1.balance
		def initialToAccBalance = acc2.balance
		
		when:
		def result = service.transfer(acc1.email, acc2.email, (acc1.balance + 1))
		
		then:
		println "Acc1 balance from " + initialFromAccBalance + " => " + acc1.balance
		println "Acc2 balance from " + initialToAccBalance + " => " + acc2.balance
		acc1.balance == initialFromAccBalance
		acc2.balance == initialToAccBalance
		result == false
	}

}
