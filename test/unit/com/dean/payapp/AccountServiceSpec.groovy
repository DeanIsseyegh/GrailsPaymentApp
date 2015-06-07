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
	
	def setup() {
		mockDomain(Account)
		mockDomain(Withdraw)
		mockDomain(Deposit)
		mockDomain(Transaction)
		def account1 = new Account(accountName: "Bob", email: "bob@gmail.com").save()
		def account2 = new Account(accountName: "George", email: "george@gmail.com").save()
		def account3 = new Account(accountName: "Rodger", email: "rodger@gmail.com").save()
		def account4 = new Account(accountName: "Felicity", email: "felicity@gmail.com").save()
		testAccounts = new ArrayList<>()
		testAccounts.add(account1)
		testAccounts.add(account2)
		testAccounts.add(account3)
		testAccounts.add(account4)
		addTransaction(account1, account2, 50)
		addTransaction(account2, account1, 33)
		addTransaction(account1, account4, 22)
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
	
	/**
	 * Convenience method to add transactions to accounts
	 *
	 * @param fromAcc
	 * @param toAcc
	 * @param transaction
	 */
	private void addTransaction(Account fromAcc, Account toAcc, long amount) {
		Withdraw withdraw = new Withdraw(account: fromAcc)
		Deposit deposit = new Deposit(account: toAcc)
		Transaction transaction = new Transaction(amount: amount, withdraw: withdraw, deposit: deposit).save()
		
		fromAcc.balance -= transaction.amount
		toAcc.balance += transaction.amount
	}
}
