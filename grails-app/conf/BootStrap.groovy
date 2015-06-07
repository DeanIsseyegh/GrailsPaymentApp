import com.dean.payapp.Account
import com.dean.payapp.Deposit
import com.dean.payapp.Transaction
import com.dean.payapp.Withdraw

class BootStrap {

    def init = { servletContext ->
		def account1 = new Account(accountName: "Bob", email: "bob@gmail.com").save()
		def account2 = new Account(accountName: "George", email: "george@gmail.com").save()
		def account3 = new Account(accountName: "Rodger", email: "rodger@gmail.com").save()
		def account4 = new Account(accountName: "Felicity", email: "felicity@gmail.com").save()
		
		addTransaction(account1, account2, 50)
		addTransaction(account2, account1, 33)
		addTransaction(account1, account4, 22)
    }
	
	/**
	 * Convenience method to add transactions to accounts
	 *
	 * @param fromAcc
	 * @param toAcc
	 * @param transaction
	 */
	void addTransaction(Account fromAcc, Account toAcc, long amount) {
		Withdraw withdraw = new Withdraw(account: fromAcc)
		Deposit deposit = new Deposit(account: toAcc)
		Transaction transaction = new Transaction(amount: amount, withdraw: withdraw, deposit: deposit).save()
		
		fromAcc.balance -= transaction.amount
		toAcc.balance += transaction.amount
	}
	
    def destroy = {
    }
}
