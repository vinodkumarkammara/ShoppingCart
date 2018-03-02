package com.shoppingcart.dao.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockData.generate.DataGenerator;
import com.mockData.generate.utils.DateUtils;
import com.mockData.generate.utils.RandomUtil;
import com.shoppingcart.dao.OrderDAO;
import com.shoppingcart.domain.Order;
import com.shoppingcart.persist.PersistLayer;
import com.shoppingcart.util.ExceptionUtils;
import com.shoppingcart.util.exception.ShoppingCartException;

public class OrderDAOImpl implements OrderDAO {

	private DataGenerator dataGenerator = new DataGenerator();
	private final static Logger log = LoggerFactory.getLogger(OrderDAOImpl.class);
	private PersistLayer persistLayer = new PersistLayer();

	@Override
	public Order get(String orderNumber) {
		int randomNo = RandomUtil.getRandomNumberInRange(1, 3);
		Order order = null;
		switch (randomNo) {
			case 1: order = dataGenerator.generateOrder1(orderNumber);break;
			case 2: order = dataGenerator.generateOrder2(orderNumber);break;
			default: throw new ShoppingCartException("Unable to find order generator for randomNo: " + randomNo);
		}
		return order;
	}

	@Override
	public Order create(String orderNumber) {
		int randomNo = RandomUtil.getRandomNumberInRange(1, 2);
		Order order;
		switch (randomNo) {
			case 1: order = dataGenerator.generateOrder1(orderNumber);
			case 2: order = dataGenerator.generateOrder2(orderNumber);
			default: order = null;
		}
		return order;
	}

	@Override
	public Order update(Order order) {
		log.info("Updating Order");
		order.setLastUpdated(DateUtils.getNow());
		boolean success = persistLayer.persist(order);
		if (success)
			return order;
		throw new ShoppingCartException("Unable to persist order " + order.getOrderNumber());
	}
	
	@Override
	public void updateOrderDate(Order order, String orderDate) {
		try {
			persistLayer.updateOrderDate(order, orderDate);
			order.setLastUpdated(DateUtils.getNow());
		} catch (ParseException e) {
			log.error(ExceptionUtils.convertStackTraceToString(e));
		}
	}

	@Override
	public boolean delete(Order order) {
		log.info("Deleting Order");
		order.setLastUpdated(DateUtils.getNow());
		boolean success = persistLayer.persist(order);
		if (success)
			return true;
		throw new ShoppingCartException("Unable to delete order " + order.getOrderNumber());
	}

}