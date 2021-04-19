package com.example.demo.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class FixedDepositRates {

	private int rateId;
	
	@NotNull
	private double rateOfInterest;

	@NotNull
	private LocalDate maturityDate;

	@NotNull
	private int tenure;

	@NotNull
	private double maturityAmount;

	@NotNull
	private double amount;
}
