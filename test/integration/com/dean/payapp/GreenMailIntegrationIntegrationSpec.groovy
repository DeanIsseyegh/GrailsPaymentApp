package com.dean.payapp

import grails.test.spock.IntegrationSpec

import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.GreenMailUtil;

class GreenMailIntegrationIntegrationSpec extends IntegrationSpec {
	def greenMail

	def setup() {
	}

	def cleanup() {
	}

	void "makeTransfer should send an email notifaction to both account holders given successful transaction"() {
		setup:
		TransferController controller = new TransferController()
		def accs = Account.findAll()
		// Insert two random accounts incase bootstrap wasn't called properly and/Was changed
		new Account(accountName: "Bob", email: "bobRAND@gmail.com").save()
		new Account(accountName: "George", email: "georgeRAND@gmail.com").save()
		def acc1 = accs.get(0)
		def acc2 = accs.get(1)
		controller.params.fromAcc = acc1.accountName
		controller.params.toAcc = acc2.accountName
		controller.params.amount = "50"
		
		when:
		controller.makeTransfer()

		then:
		println "Assert two emails were sent"
		greenMail.getReceivedMessages().length == 2
		
		def message1 = greenMail.getReceivedMessages()[0]
		message1.subject == "Money Transferred"
		
		def message2 = greenMail.getReceivedMessages()[1]
		message2.subject == "Money Transferred"
		
		//We are pleased to inform that Bob sent George a payment of 50 pounds
		String expectedBody = "We are pleased to inform that " + acc1.accountName + 
			" sent " + acc2.accountName + " a payment of " + controller.params.amount + " pounds"
		GreenMailUtil.getBody(message1).equals(expectedBody)
		GreenMailUtil.getBody(message2).equals(expectedBody)
		
		String toField = getToField(GreenMailUtil.getWholeMessage(message1))
		toField.contains(acc1.email)
		toField.contains(acc2.email)
	}
	
	private String getToField(String msg) {
		String toField = msg.substring(msg.indexOf("To: "), msg.indexOf("Message-ID:")).toLowerCase()
	}
}
