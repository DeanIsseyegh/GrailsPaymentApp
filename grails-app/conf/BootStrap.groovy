import com.dean.payapp.Account
import com.dean.payapp.Deposit
import com.dean.payapp.Transaction
import com.dean.payapp.Withdraw

class BootStrap {

	def accountService
	
    def init = { servletContext ->
		def account1 = new Account(accountName: "Bob", email: "bob@gmail.com").save()
		def account2 = new Account(accountName: "George", email: "george@gmail.com").save()
		def account3 = new Account(accountName: "Rodger", email: "rodger@gmail.com").save()
		def account4 = new Account(accountName: "Felicity", email: "felicity@gmail.com").save()
		
		accountService.addTransaction(account1, account2, 50)
		accountService.addTransaction(account2, account1, 33)
		accountService.addTransaction(account1, account4, 22)
    }
	
    def destroy = {
    }
}
