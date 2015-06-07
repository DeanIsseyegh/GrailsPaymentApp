package com.dean.payapp

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Transaction)
class TransactionSpec extends Specification {

    void "should have an amount and date"() {
		given:
		def trans = new Transaction(amount: amount, date: date).save()
		
		when:
		def result = Transaction.findByAmountAndDate(amount, date)
		
		then:
		result != null
		
		where:
		amount = 50
		date = new Date()
    }
	
	void "should set date by default"() {
		given:
		def trans = new Transaction(amount: amount).save()
		
		when:
		def result = Transaction.findByAmount(amount)
		
		then:
		result.date != null
		
		where:
		amount = 50
	}
}
