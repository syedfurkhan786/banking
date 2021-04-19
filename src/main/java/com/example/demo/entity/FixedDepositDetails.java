package com.example.demo.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixedDepositDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rateId;

	private int customerId;
	
	private long accountNumber;

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
