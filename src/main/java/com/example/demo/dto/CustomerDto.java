package com.example.demo.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int customerId;

	@NotNull
	@Column(unique = true)
	private long accountNumber;

	private String customerName;

	private LocalDate dateOfBirth;
	
}
