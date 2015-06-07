package com.dean.payapp;

import groovy.transform.Sortable
import groovy.transform.ToString
/**
 * A history of a payment which can be sorted by date.
 * 
 * @author Dean
 */
@ToString
@Sortable(includes = ['date'])
public class AccountTransaction {

	long amount
	String otherAccName
	String otherAccEmail
	Date date
	
}
