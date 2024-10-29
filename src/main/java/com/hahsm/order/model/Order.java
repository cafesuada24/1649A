package com.hahsm.order.model;

import java.time.LocalDateTime;

import com.hahsm.common.type.Pair;


public class Order {
    private int userId;
    private int bookId;
    private LocalDateTime createdAt;

    public Order() {}

    public Order(int userId, int bookId, LocalDateTime createdAt) {
        setUserId(userId);
        setBookId(bookId);
        setCreatedAt(createdAt);
    }

    public Order(int userId, int bookId, String createdAt) {
        setUserId(userId);
        setBookId(bookId);
        setCreatedAt(LocalDateTime.parse(createdAt));
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
