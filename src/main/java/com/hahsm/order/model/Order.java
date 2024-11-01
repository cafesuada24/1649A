package com.hahsm.order.model;

import java.sql.Date;
import java.sql.Time;

import com.hahsm.common.type.Pair;


public class Order implements Comparable<Order> {

    private int userId;
    private int bookId;
    private int quantity;
    private Date orderDate;
    private Time orderTime;

	public Order() {}

    public Order(int userId, int bookId, int quantity, Date orderDate, Time orderTime) {
        setUserId(userId);
        setBookId(bookId);
        setQuantity(quantity);
        setOrderDate(orderDate);
        setOrderTime(orderTime);
    }

    public Pair<Integer, Integer> getID() {
        return new Pair<>(userId, bookId);
    }

    public void setId(Pair<Integer, Integer> key) {
        setUserId(key.getFirst());
        setBookId(key.getSecond());
    }

    public void setId(int userId, int bookId) {
        setUserId(userId);
        setBookId(bookId);
    }

	public int getUserId() {
        return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

    public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
}
