package com.hahsm.order.model;

import java.sql.Date;
import java.sql.Time;

import com.hahsm.datastructure.adt.List;
import com.hahsm.datastructure.ArrayList;

public class Order implements Comparable<Order> {
    private int id;
    private int userId;
    private Date orderDate;
    private Time orderTime;

    private List<OrderBook> orderBooks;

    public Order() {
        this.orderBooks = new ArrayList<>();
    }

    public Order(int id, int userid, Date orderDate, Time orderTime, List<OrderBook> orderBooks) {
        setId(id);
        setUserId(userid);
        setOrderDate(orderDate);
        setOrderTime(orderTime);
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

	public List<OrderBook> getOrderBooks() {
		return orderBooks;
	}
}
