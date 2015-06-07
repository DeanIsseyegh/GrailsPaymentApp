package com.dean.payapp

import grails.test.mixin.TestFor
import spock.lang.Specification
import grails.validation.ValidationException

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Deposit)
class DepositSpec extends Specification {

        void "Deposit should not be able to save without a transaction and account"() {
		given:
		def deposit = new Deposit()
		
		when:
		deposit.save()
		
		then:
		thrown ValidationException 
    }
	
	void "Deposit should be able to save with a transaction and account"() {
		given:
		def deposit = new Deposit(transaction: new Transaction(amount:50), 
			account: new Account(accountName: "Bob", email: "bob@gmail.com"))
		
		when:
		deposit.save()
		
		then:
		notThrown ValidationException
		deposit.validate() == true
	}
}
