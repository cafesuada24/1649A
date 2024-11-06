package com.hahsm.cart;

import java.sql.Date;
import java.sql.Time;

import com.hahsm.book.model.Book;
import com.hahsm.common.type.Pair;
import com.hahsm.datastructure.HashMap;
import com.hahsm.order.model.Order;
import com.hahsm.order.model.OrderBook;

public class BookCart {
    private HashMap<Integer, Pair<Integer, Book>> cart = new HashMap<>();
    private String customerName;
    private String customerAddress;
    private String customerPhone;

    public void addItem(int id, Book book, int qty) {
        if (cart.containsKey(id)) {
            final var cartItem = cart.get(id);
            cartItem.setFirst(cartItem.getFirst() + qty);
        } else {
            cart.put(id, new Pair<>(qty, book));
        }
    }

    @Override
    public String toString() {
        if (cart.isEmpty()) {
            return "\tempty";
        }
        StringBuilder builder = new StringBuilder();
        cart.forEach((key, val) -> {
            builder.append("\t").append(val.getSecond().toString());
            builder.append("\n\t\tqty: ").append(val.getFirst()).append('\n');
        });
        return builder.toString();
    }

    public void setCustomerInformation(String customerName, String customerAddress, String customerPhone) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
    }

    public Order toOrder() {
        final Order order = new Order();
        final long currentTimeMillis = System.currentTimeMillis(); 
        order.setOrderDate(new Date(currentTimeMillis)); 
        order.setOrderTime(new Time(currentTimeMillis));
        order.setCustomerName(customerName);
        order.setCustomerAddress(customerAddress);
        order.setCustomerPhone(customerPhone);

        for (final var entry : cart) {
            final Book book = entry.getValue().getSecond();
            order.getOrderBooks().add(new OrderBook(entry.getKey(), book.getID(), entry.getValue().getFirst()));
        }

        return order;
    }
}
