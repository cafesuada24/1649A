package com.hahsm.order.model;

import com.hahsm.book.model.Book;
import com.hahsm.common.type.Pair;

public class OrderBook {
    private int orderId;
    private int bookId;
    private int quantity;

    private Book book;
    private Order order;

    public OrderBook() {}

    public OrderBook(int orderId, int bookId, int quantity) {
        setOrderId(orderId);
        setBookId(bookId);
        setQuantity(quantity);
    }

    public Pair<Integer, Integer> getId() {
        return new Pair<>(orderId, bookId);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setBookId(int bookId) {
        this.bookId = bookId;
   }

	public Book getBook() {
		return book;
	}

	public Order getOrder() {
		return order;
	}

}
