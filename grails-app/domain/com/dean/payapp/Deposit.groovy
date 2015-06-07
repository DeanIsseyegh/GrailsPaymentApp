package com.dean.payapp

class Deposit {

	static belongsTo = [transaction:Transaction, account:Account]
	
    static constraints = {
    }
	
}
