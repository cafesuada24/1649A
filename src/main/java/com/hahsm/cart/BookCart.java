package com.hahsm.cart;

import java.time.LocalDateTime;

import com.hahsm.algorithm.SortStrategy;
import com.hahsm.book.model.Book;
import com.hahsm.common.type.Pair;
import com.hahsm.common.type.User;
import com.hahsm.datastructure.HashMap;
import com.hahsm.order.model.Order;
import com.hahsm.order.model.OrderBook;

public class BookCart {
    private HashMap<Integer, Pair<Integer, Book>> cart = new HashMap<>();
    private User user;
    private SortStrategy sorter;

    public BookCart(final SortStrategy sorter, final User user) {
        this.sorter = sorter;
        this.user = user;
    }

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
        final var items = cart.values();
        sorter.sort(items, (a, b) -> a.getSecond().compareTo(b.getSecond()));

        StringBuilder builder = new StringBuilder();
        items.forEach((item) -> {
            builder.append("\t").append(item.getSecond().toString());
            builder.append("\n\t\tqty: ").append(item.getFirst()).append('\n');
        });

        return builder.toString();
    }

    public Order toOrder() {
        if (user == null) {
            throw new IllegalStateException("User must be set");
        }
        final Order order = new Order();
        final var now = LocalDateTime.now();
        order.setOrderTime(now);
        order.setEstimatedDeliveryTime(now.plusSeconds(30));
        order.setCustomerName(user.name);
        order.setCustomerAddress(user.address);
        order.setCustomerPhone(user.phone);
        order.setStatus(Order.Status.PROCESSING);

        for (final var entry : cart) {
            final Book book = entry.getValue().getSecond();
            order.getOrderBooks().add(new OrderBook(entry.getKey(), book.getID(), entry.getValue().getFirst()));
        }

        return order;
    }
}
