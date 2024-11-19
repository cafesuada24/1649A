package com.hahsm.orderprocesssingsystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import com.hahsm.algorithm.UniformCostSearch;
import com.hahsm.common.type.Graph;
import com.hahsm.common.type.Observer;
import com.hahsm.common.type.Repository;
import com.hahsm.datastructure.PriorityQueue;
import com.hahsm.order.model.Order;
import com.hahsm.order.model.Order.Status;

import de.vandermeer.asciitable.AsciiTable;

public class OrderProcessingSystem implements Observer<Order> {
    private static final double speedKmH = 60;
    private static final String storageLocation = "Hanoi";

    private final Repository<Order, Integer> orderRepo;

    private final PriorityQueue<Order> pq;
    private final Graph cityGraph;

    public OrderProcessingSystem(
            final Repository<Order, Integer> orderRepo,
            final Graph cityGraph
    ) {
        assert orderRepo != null;
        this.orderRepo = orderRepo;
        this.orderRepo.registerObserver(this);
        this.cityGraph = cityGraph;
        pq = new PriorityQueue<Order>(Comparator.naturalOrder());
        for (Order order : orderRepo.getAll()) {
            if (order.getStatus() == Status.PROCESSING) {
                pq.add(order);
            }
        }
    }

    @Override
    public void update(Order data) {
        pq.add(data);
    }

    public void updateOrderStatus(Order order, Order.Status status) {
        assert pq != null;
        assert order != null;
        assert orderRepo != null;

        order.setStatus(status);
        if (status == Order.Status.SHIPPED) {
           order.setEstimatedDeliveryTime(calculateEstimatedDeliveryTime(order.getCustomerAddress()));
        } else if (status == Order.Status.DELIVERED) {
            pq.remove(order);
        }
        orderRepo.update(order);
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
                    order.getEstimatedDeliveryTime() != null ? order.getEstimatedDeliveryTime().format(formatter) : "N/A",
                    order.getStatus());
            at.addRule();
        }

        return at.render();
    }

    private LocalDateTime calculateEstimatedDeliveryTime(String dest) {
           final double shortestDistance = UniformCostSearch.search(storageLocation, dest, cityGraph);
           final Long estimatedHour = Math.round(shortestDistance / speedKmH * 3600);
           return LocalDateTime.now().plusSeconds(estimatedHour);
    }
}
