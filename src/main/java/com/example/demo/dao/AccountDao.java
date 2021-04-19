package com.example.demo.dao;

import java.time.LocalDate;
import java.util.Collection;

import com.example.demo.entity.Customer;
import com.example.demo.entity.FixedDepositDetails;

public interface AccountDao {

	public FixedDepositDetails confirmation(Customer customer, FixedDepositDetails rates,long accountNumber, LocalDate dateOfBirth);

	public Collection<?> fetchAccountDetails(int rateId);

	

}
