package com.hahsm.orderprocesssingsystem;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import com.hahsm.common.type.Observer;
import com.hahsm.common.type.Repository;
import com.hahsm.datastructure.PriorityQueue;
import com.hahsm.order.model.Order;

import de.vandermeer.asciitable.AsciiTable;

public class OrderProcessingSystem implements Observer<Order> {
    private final Repository<Order, Integer> orderRepo;

    private final PriorityQueue<Order> pq;

    public OrderProcessingSystem(
            final Repository<Order, Integer> orderRepo
    ) {
        assert orderRepo != null;
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
        final AsciiTable at = new AsciiTable();

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
