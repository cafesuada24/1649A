package com.hahsm.orderprocesssingsystem;

import com.hahsm.datastructure.adt.List;
import com.hahsm.datastructure.adt.Queue;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;

import com.hahsm.algorithm.LinearSearch;
import com.hahsm.algorithm.MergeSort;
import com.hahsm.algorithm.Search;
import com.hahsm.algorithm.SortStrategy;
import com.hahsm.book.model.Book;
import com.hahsm.common.type.Observer;
import com.hahsm.common.type.Pair;
import com.hahsm.common.type.Repository;
import com.hahsm.datastructure.ArrayList;
import com.hahsm.datastructure.LinkedList;
import com.hahsm.datastructure.PriorityQueue;
import com.hahsm.order.model.Order;
import com.hahsm.order.model.OrderBook;

import de.vandermeer.asciitable.AsciiTable;

public class OrderProcessingSystem implements Observer<Order> {
    // private Queue<Order> orderQueue;
    private final Repository<Order, Integer> orderRepo;
    // private final Repository<OrderBook, Pair<Integer, Integer>> orderBookRepo;
    // private final Repository<Book, Integer> bookRepo;
    // private final SortStrategy sorter;

    // private final List<Order> orders;
    // private final List<OrderBook> orderBooks;
    // private final List<Book> books;

    private final PriorityQueue<Order> pq;

    public OrderProcessingSystem(
            final Repository<Order, Integer> orderRepo
    // final Repository<OrderBook, Pair<Integer, Integer>> orderBookRepo,
    // final Repository<Book, Integer> bookRepo) {
    ) {
        assert orderRepo != null;
        // this(orderRepo, orderBookRepo, bookRepo, new MergeSort());
        this.orderRepo = orderRepo;
        this.orderRepo.registerObserver(this);
        pq = new PriorityQueue<Order>(Comparator.naturalOrder());
        for (Order order : orderRepo.getAll()) {
            pq.add(order);
        }
    }

    @Override
    public void update(Order data) {
        pq.add(data);
    }

    @Override
    public String toString() {
        AsciiTable at = new AsciiTable();

        // Define the header row
        at.addRule();
        at.addRow("ID", "Customer Name", "Customer Address", "Customer Phone", "Order Time", "Estimated Delivery",
                "Status");
        at.addRule();

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Order order : pq) {
            at.addRow(
                    order.getId(),
                    order.getCustomerName(),
                    order.getCustomerAddress(),
                    order.getCustomerPhone(),
                    order.getOrderTime().format(formatter),
                    order.getEstimatedDeliveryTime().format(formatter),
                    order.getStatus().name());
            at.addRule();
        }

        return at.render();
    }
}
