package com.shoppingcart.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockData.generate.DataGenerator;
import com.shoppingcart.dao.CustomerDAO;
import com.shoppingcart.domain.Customer;
import com.shoppingcart.domain.Order;
import com.shoppingcart.persist.PersistLayer;
import com.shoppingcart.util.exception.ShoppingCartException;

public class CustomerDAOImpl implements CustomerDAO {

	private DataGenerator dataGenerator = new DataGenerator();
	private PersistLayer persistLayer = new PersistLayer();
	private final static Logger log = LoggerFactory.getLogger(CustomerDAOImpl.class);


	@Override
	public Customer getByCustomerNumber(String customerNumber) {
		try {
			Customer customer = dataGenerator.generateCustomer(customerNumber);
			return customer;
		} catch (Exception e) {
			log.error("Unable to create customer");
			throw new ShoppingCartException(e);
		}
	}

	@Override
	public Customer create() {
		try {
			return dataGenerator.createCustomer1();
		} catch (Exception e) {
			log.error("Unable to create customer");
			throw new ShoppingCartException(e);
		}
	}

	@Override
	public Customer update(Customer customer) {
		log.info("Updating Customer");
		boolean success = persistLayer.persist(customer);
		if (success)
			return customer;
		throw new ShoppingCartException("Unable to persist customer " + customer.getAccountNumber());
	}

	@Override
	public boolean delete(Customer customer) {
		log.info("Deleting Customer");
		boolean success = persistLayer.persist(customer);
		if (success)
			return true;
		throw new ShoppingCartException("Unable to delete customer " + customer.getAccountNumber());

	}

	@Override
	public List<Order> getAllOrders(String customerNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}