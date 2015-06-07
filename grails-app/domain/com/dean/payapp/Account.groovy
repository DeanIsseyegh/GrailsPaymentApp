package com.dean.payapp

class Account {

	String accountName
	long balance = 200
	String email
	
	static hasMany = [withdraws:Withdraw, deposits:Deposit]
	
    static constraints = {
		balance(defaultValue: 200, min: 0L)
		email unique: true
    }
}
