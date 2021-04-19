package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.FixedDepositRates;
import com.example.demo.dto.ResponseBean;
import com.example.demo.entity.Customer;
import com.example.demo.entity.FixedDepositDetails;
import com.example.demo.service.AccountService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/customer")
public class CustomerController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private FixedDepositDetails fixedDepositDetails;
	
	@Autowired
	private FixedDepositRates fixedDepositRates; 
	
	@Autowired
	private Customer customer;
	
	@GetMapping("/calculations")
	public ResponseBean calculateInterestRate(@RequestParam String dateOfBirth,
			@RequestParam int tenure,@RequestParam double amount) {
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("d/MM/yyyy");
		
		LocalDate date = LocalDate.parse(dateOfBirth,format);
		
		fixedDepositRates = accountService.calculateInterestRate(fixedDepositRates, date,
				tenure, amount);

		return new ResponseBean(fixedDepositRates, false, "Fixed Deposit Rate Calculated");
	}

	@PostMapping("/confirmation")
	public ResponseBean confirmation(@RequestParam String dateOfBirth,
	@RequestParam int tenure,@RequestParam double amount,@RequestParam long accountNumber) {
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("d/MM/yyyy");
		
		LocalDate date = LocalDate.parse(dateOfBirth,format);
		
		fixedDepositDetails = accountService.confirmation(customer,fixedDepositDetails, fixedDepositRates, accountNumber,
				date, tenure, amount);

		return new ResponseBean(fixedDepositDetails, false, "Confirmation Success");
	}

	@GetMapping("/details")
	public ResponseBean fetchAccountDeatils(@RequestParam int rateId) {
		Collection<?> data = accountService.fetchAccountDetails(rateId);

		return new ResponseBean(data, false, "Details Fetched");
	}

}
