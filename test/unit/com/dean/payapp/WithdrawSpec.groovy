package com.dean.payapp

import grails.test.mixin.TestFor
import spock.lang.Specification
import grails.validation.ValidationException;

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Withdraw)
class WithdrawSpec extends Specification {

    void "Withdraw should not be able to save without a transaction and account"() {
		given:
		def withdraw = new Withdraw()
		
		when:
		withdraw.save()
		
		then:
		thrown ValidationException 
    }
	
	void "Withdraw should be able to save with a transaction and account"() {
		given:
		def withdraw = new Withdraw(transaction: new Transaction(amount:50), 
			account: new Account(accountName: "Bob", email: "bob@gmail.com"))
		
		when:
		withdraw.save()
		
		then:
		notThrown ValidationException
		withdraw.validate() == true
	}
}
