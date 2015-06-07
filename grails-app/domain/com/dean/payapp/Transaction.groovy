package com.dean.payapp

class Transaction {
	
	long amount
	Date date = new Date()
	
	static hasOne = [withdraw:Withdraw, deposit:Deposit]
	
    static constraints = {
    }
}
