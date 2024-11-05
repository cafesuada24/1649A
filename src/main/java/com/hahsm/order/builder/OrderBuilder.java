package com.hahsm.order.builder;

import java.sql.Date;
import java.sql.Time;

import com.hahsm.common.type.Builder;
import com.hahsm.datastructure.adt.List;
import com.hahsm.order.model.Order;
import com.hahsm.order.model.OrderBook;
import com.hahsm.datastructure.ArrayList;

public class OrderBuilder implements Builder<Order> {

    private Order order;

    public OrderBuilder() {
        reset();
    }

    public void setUser(int userId) {
        order.setId(userId);
    }

    public void addBooks(List<Integer> bookIds, List<Integer> quantities) throws IllegalArgumentException {
        if (bookIds.size() != quantities.size()) {
            throw new IllegalArgumentException(
                    "Expected equality in size of 'bookIds' and 'quantities', not bookIds { size = " + bookIds.size()
                            + " }, quantities { size = " + quantities.size() + " }.");
        }

        final List<OrderBook> obs = new ArrayList<>(bookIds.size());
        for (int i = 0; i < bookIds.size(); ++i) {
            final OrderBook ob = new OrderBook();
            ob.setBookId(bookIds.get(i));
            ob.setQuantity(quantities.get(i));
            obs.set(i, ob);
        }
        this.order.getOrderBooks().addAll(obs);

    }

    public void addBook(int bookId, int quantity) {
        final OrderBook ob = new OrderBook();
        ob.setBookId(bookId);
        ob.setQuantity(quantity);
        this.order.getOrderBooks().add(ob);
    }

    public void setCustomerInformation(String customerName, String customerAddress, String customerPhone) {
        order.setCustomerName(customerName);
        order.setCustomerAddress(customerAddress);
        order.setCustomerPhone(customerPhone);
    }

    @Override
    public Order getProduct() {
        final long currentTimeMillis = System.currentTimeMillis(); 
        order.setOrderDate(new Date(currentTimeMillis)); 
        order.setOrderTime(new Time(currentTimeMillis));

        final Order order = this.order;
        reset();
        return order;
    }

    @Override
    public void reset() {
        this.order = new Order();
    }
}
