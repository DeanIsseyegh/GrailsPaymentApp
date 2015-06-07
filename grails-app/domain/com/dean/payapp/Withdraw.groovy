package com.dean.payapp

class Withdraw {
	
	static belongsTo = [transaction:Transaction, account:Account]
	
    static constraints = {
    }
}
