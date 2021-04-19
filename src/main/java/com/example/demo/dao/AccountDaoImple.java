package com.example.demo.dao;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Customer;
import com.example.demo.entity.FixedDepositDetails;

@Repository
public class AccountDaoImple implements AccountDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public FixedDepositDetails confirmation(Customer customer,FixedDepositDetails rates, long accountNumber, LocalDate dateOfBirth) {
		
		Query query = entityManager.createQuery(
				"select c from Customer c where c.accountNumber=:accountNumber and c.dateOfBirth=:dateOfBirth");
		query.setParameter("accountNumber", accountNumber);
		query.setParameter("dateOfBirth", dateOfBirth);
		
		Query queryTwo = entityManager.createQuery("select c.customerId from Customer c where c.accountNumber=:accountNumber and c.dateOfBirth=:dateOfBirth");
		queryTwo.setParameter("accountNumber", accountNumber);
		queryTwo.setParameter("dateOfBirth", dateOfBirth);
		
		int cust = (int) queryTwo.getSingleResult();
		rates.setCustomerId(cust);

		if (!query.getResultList().isEmpty()) {
			entityManager.persist(rates);
			return rates;
		}

		entityManager.close();
		return null;

	}

	@Override
	public Collection<?> fetchAccountDetails(int rateId) {

		Query query = entityManager.createQuery(
				"select c,fd from Customer c inner join FixedDepositDetails fd on c.customerId=fd.customerId where fd.rateId=:rateId");
		query.setParameter("rateId", rateId);

		entityManager.close();
		return query.getResultList();

	}

}
