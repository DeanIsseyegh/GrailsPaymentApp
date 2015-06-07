package com.dean.payapp

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Account)
class AccountSpec extends Specification {

    void "should have a name, balance and email address"() {
		given:
		new Account(accountName: accountName, email: email, balance: amount).save()
		
		when:
		def result = Account.findByAccountNameAndEmailAndBalance(accountName, email, amount)
		
		then:
		result != null
		
		where:
		accountName = "Bob"
		email = "bob@gamil.com"
		amount = 50
    }
	
	void "should have a default balance of 200 pounds"() {
		given:
		new Account(accountName: accountName, email: email).save()
		
		when:
		def result = Account.findByAccountNameAndEmail(accountName, email)
		
		then:
		result.balance == 200
		
		where:
		accountName = "Bob"
		email = "bob@gamil.com"
		amount = 50
	}
	
	void "should have constraints on name and email being blank"() {
		given:
		def acc = new Account(accountName: accountName, email: email, balance: amount)
		
		when:
		acc.validate()
		
		then:
		acc.hasErrors() == true
		
		where:
		accountName = ""
		email = ""
		amount = 50
	}
	
	void "should have constraint on balance going into overdraft)"() {
		given:
		def acc = new Account(accountName: accountName, email: email, balance: amount)
		
		when:
		acc.validate()
		
		then:
		acc.hasErrors() == true
		
		where:
		accountName = "Bob"
		email = "bob@gmail.com"
		amount = -50L
	}
	
	void "should have constraint on email being unique"() {
		given:
		def acc1 = new Account(accountName: accountName2, email: email, balance: amount).save()
		
		when:
		def acc2 = new Account(accountName: accountName, email: email, balance: amount)
		mockForConstraintsTests(Account, [acc1, acc2])
		
		then:
		acc2.hasErrors() == false
		
		where:
		accountName = "Bob"
		accountName2 = "Charles"
		email = "bob@gmail.com"
		amount = 50
	}
}
