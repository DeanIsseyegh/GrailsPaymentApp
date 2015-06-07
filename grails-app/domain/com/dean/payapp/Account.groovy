package com.dean.payapp

class Account {

	String accountName
	long balance = 200
	String email
	
    static constraints = {
		balance min: 0L
		email unique: true
    }
}
