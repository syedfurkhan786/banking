package com.example.demo.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AccountDao;
import com.example.demo.dto.FixedDepositRates;
import com.example.demo.entity.Customer;
import com.example.demo.entity.FixedDepositDetails;
import com.example.demo.exceptions.CustomerException;
import com.example.demo.exceptions.TenureException;

@Service
public class AccountServiceImple implements AccountService {

	@Autowired
	private AccountDao accountDao;

	@Override
	public FixedDepositRates calculateInterestRate(FixedDepositRates fixedDepositRates, LocalDate dateOfBirth,
			int tenure, double amount) {

		LocalDate currentDate = LocalDate.now();
		Period difference = Period.between(dateOfBirth, currentDate);

		if (difference.getYears() >= 60) {

			fixedDepositRates.setMaturityDate(LocalDate.now().plusDays(tenure));
			fixedDepositRates.setTenure(tenure);
			fixedDepositRates.setAmount(amount);

			fixedDepositRates = calculateSeniorFDRate(fixedDepositRates, tenure, amount);

			if (fixedDepositRates == null) {
				throw new TenureException("Invalid Tenure Period");
			}

			return fixedDepositRates;

		} else if (difference.getYears() < 60) {

			fixedDepositRates.setMaturityDate(LocalDate.now().plusDays(tenure));
			fixedDepositRates.setTenure(tenure);
			fixedDepositRates.setAmount(amount);

			fixedDepositRates = calculateRegularFDRate(fixedDepositRates, tenure, amount);

			if (fixedDepositRates == null) {
				throw new TenureException("Invalid Tenure Period");
			}

			return fixedDepositRates;
		}

		return null;

	}

	private FixedDepositRates calculateRegularFDRate(FixedDepositRates fixedDepositRates, int tenure, double amount) {

		if ((tenure >= 7 && tenure <= 45)) {
			fixedDepositRates.setRateOfInterest(2.9);
			fixedDepositRates.setMaturityAmount(amount + ((amount / 100) * 2.9));

			return fixedDepositRates;
		} else if ((tenure > 45 && tenure <= 179)) {
			fixedDepositRates.setRateOfInterest(3.4);
			fixedDepositRates.setMaturityAmount(amount + ((amount / 100) * 3.4));

			return fixedDepositRates;
		} else if ((tenure > 179 && tenure <= 364)) {
			fixedDepositRates.setRateOfInterest(3.9);
			fixedDepositRates.setMaturityAmount(amount + ((amount / 100) * 3.9));

			return fixedDepositRates;
		} else if ((tenure > 364 && tenure <= 729)) {
			fixedDepositRates.setRateOfInterest(4.4);
			fixedDepositRates.setMaturityAmount(amount + ((amount / 100) * 4.4));

			return fixedDepositRates;
		} else if ((tenure > 729 && tenure <= 1094)) {
			fixedDepositRates.setRateOfInterest(4.9);
			fixedDepositRates.setMaturityAmount(amount + ((amount / 100) * 4.9));

			return fixedDepositRates;
		}

		return null;
	}

	private FixedDepositRates calculateSeniorFDRate(FixedDepositRates fixedDepositRates, int tenure, double amount) {

		if ((tenure >= 7 && tenure <= 45)) {
			fixedDepositRates.setRateOfInterest(3.4);
			fixedDepositRates.setMaturityAmount(amount + ((amount / 100) * 3.4));

			return fixedDepositRates;
		} else if ((tenure > 45 && tenure <= 179)) {
			fixedDepositRates.setRateOfInterest(3.9);
			fixedDepositRates.setMaturityAmount(amount + ((amount / 100) * 3.9));

			return fixedDepositRates;
		} else if ((tenure > 179 && tenure <= 364)) {
			fixedDepositRates.setRateOfInterest(4.4);
			fixedDepositRates.setMaturityAmount(amount + ((amount / 100) * 4.4));

			return fixedDepositRates;
		} else if ((tenure > 364 && tenure <= 729)) {
			fixedDepositRates.setRateOfInterest(4.9);
			fixedDepositRates.setMaturityAmount(amount + ((amount / 100) * 4.9));

			return fixedDepositRates;
		} else if ((tenure > 729 && tenure <= 1094)) {
			fixedDepositRates.setRateOfInterest(5.4);
			fixedDepositRates.setMaturityAmount(amount + ((amount / 100) * 5.4));

			return fixedDepositRates;
		}

		return null;
	}

	@Override
	public FixedDepositDetails confirmation(Customer customer, FixedDepositDetails fixedDepositDetails,
			FixedDepositRates fixedDepositRates, long accountNumber, LocalDate dateOfBirth, int tenure, double amount) {

		LocalDate currentDate = LocalDate.now();
		Period difference = Period.between(dateOfBirth, currentDate);

		if (difference.getYears() >= 60) {
			fixedDepositRates.setMaturityDate(LocalDate.now().plusDays(tenure));
			fixedDepositRates.setTenure(tenure);
			fixedDepositRates.setAmount(amount);

			fixedDepositRates = calculateSeniorFDRate(fixedDepositRates, tenure, amount);

			if (fixedDepositRates == null) {
				throw new TenureException("Invalid Tenure Period");
			}

			BeanUtils.copyProperties(fixedDepositRates, fixedDepositDetails);
			fixedDepositDetails.setAccountNumber(accountNumber);

			fixedDepositDetails = accountDao.confirmation(customer, fixedDepositDetails, accountNumber, dateOfBirth);

			if (fixedDepositDetails == null) {
				throw new CustomerException("Credentials mismatch");
			}

			return fixedDepositDetails;

		} else if (difference.getYears() < 60) {

			fixedDepositRates.setMaturityDate(LocalDate.now().plusDays(tenure));
			fixedDepositRates.setTenure(tenure);
			fixedDepositRates.setAmount(amount);

			fixedDepositRates = calculateRegularFDRate(fixedDepositRates, tenure, amount);

			if (fixedDepositRates == null) {
				throw new TenureException("Invalid Tenure Period");
			}

			BeanUtils.copyProperties(fixedDepositRates, fixedDepositDetails);
			fixedDepositDetails.setAccountNumber(accountNumber);

			fixedDepositDetails = accountDao.confirmation(customer, fixedDepositDetails, accountNumber, dateOfBirth);

			if (fixedDepositDetails == null) {

				throw new CustomerException("Credentials mismatch");
			}

			return fixedDepositDetails;
		}

		return null;

	}

	@Override
	public Collection<?> fetchAccountDetails(int rateId) {

		Collection<?> data = accountDao.fetchAccountDetails(rateId);

		if (data.isEmpty()) {
			throw new CustomerException("Invalid Details");
		}

		return data;
	}

}
