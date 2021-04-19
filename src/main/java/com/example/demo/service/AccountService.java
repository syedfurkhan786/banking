package com.example.demo.service;

import java.time.LocalDate;
import java.util.Collection;

import com.example.demo.dto.FixedDepositRates;
import com.example.demo.entity.Customer;
import com.example.demo.entity.FixedDepositDetails;

public interface AccountService {

	public FixedDepositRates calculateInterestRate(FixedDepositRates fixedDepositRates, LocalDate dateOfBirth,
			int tenure, double amount);

	public FixedDepositDetails confirmation(Customer customer, FixedDepositDetails fixedDepositDetails,
			FixedDepositRates fixedDepositRates, long accountnumber, LocalDate dateOfBirth, int tenure, double amount);

	public Collection<?> fetchAccountDetails(int rateId);

}
