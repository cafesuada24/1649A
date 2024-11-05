package com.hahsm.order.model;

import java.sql.Date;
import java.sql.Time;

import com.hahsm.datastructure.adt.List;
import com.hahsm.datastructure.ArrayList;

public class Order implements Comparable<Order> {
    private int id;
    private int userId = 0;
    private Date orderDate;
    private Time orderTime;
    private String customerName;
    private String customerAddress;
    private String customerPhone;

    private List<OrderBook> orderBooks;

    public Order() {
        this.orderBooks = new ArrayList<>();
    }

    public Order(int id, int userId, Date orderDate, Time orderTime, String customerName, String customerAddress, String customerPhone) {
        this(id, userId, orderDate, orderTime, customerName, customerAddress, customerPhone, new ArrayList<>());
    }

    public Order(int id, int userId, Date orderDate, Time orderTime, String customerName, String customerAddress, String customerPhone, List<OrderBook> orderBooks) {
        setId(id);
        setUserId(userId);
        setOrderDate(orderDate);
        setOrderTime(orderTime);
        setCustomerName(customerName);
        setCustomerAddress(customerAddress);
        setCustomerPhone(customerPhone);
        this.orderBooks = orderBooks;
    }

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Time getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Time orderTime) {
        this.orderTime = orderTime;
    }

	public List<OrderBook> getOrderBooks() {
		return orderBooks;
	}

    @Override
    public int compareTo(Order o) {
        if (getOrderDate().before(o.getOrderDate())) {
            return -1;
        } else if (getOrderDate().after(o.getOrderDate())) {
            return 1;
        }

        if (getOrderTime().before(o.getOrderTime())) {
            return -1;
        } else if (getOrderTime().after(o.getOrderTime())) {
            return 1;
        }

        return 0;
    }

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String cutstomerName) {
		this.customerName = cutstomerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String cutstomerAddress) {
		this.customerAddress = cutstomerAddress;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String cutstomerPhone) {
		this.customerPhone = cutstomerPhone;
	}
}
