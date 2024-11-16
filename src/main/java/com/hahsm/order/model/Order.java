package com.hahsm.order.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.hahsm.datastructure.adt.List;
import com.hahsm.datastructure.ArrayList;

public class Order implements Comparable<Order> {
    public static enum Status {
        PROCESSING(0),
        SHIPPED(1),
        DELIVERED(2),
        CANCELLED(3);

        private final int value;

        private Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
        @Override
        public String toString() {
            switch (value) {
                case 1:
                    return "Processing";
                case 2:
                    return "Shipping";
                case 3:
                    return "Completed";
                case 4:
                    return "Cancelled";
            }
            return "Undefined";
        }


    }

    private int id;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private LocalDateTime orderTime;
    private LocalDateTime estimatedDeliveryTime;
    private Status status;

    private List<OrderBook> orderBooks;

    public Order() {
        this.orderBooks = new ArrayList<>();
    }

    public Order(int id, long orderTime, long estimatedDeliveryTime, String customerName,
            String customerAddress, String customerPhone, String status) {
        this(id, orderTime, estimatedDeliveryTime, customerName, customerAddress, customerPhone, status, new ArrayList<>());
    }

    public Order(int id, long orderTime, long estimatedDeliveryTime, String customerName,
            String customerAddress, String customerPhone, String status, List<OrderBook> orderBooks) {
        setId(id);
        setOrderTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(orderTime), ZoneOffset.UTC));
        setEstimatedDeliveryTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(estimatedDeliveryTime), ZoneOffset.UTC));
        setCustomerName(customerName);
        setCustomerAddress(customerAddress);
        setCustomerPhone(customerPhone);
        setStatus(status);
    }

    public List<OrderBook> getOrderBooks() {
        return orderBooks;
    }

    public void setOrderBooks(List<OrderBook> orderBooks) {
        this.orderBooks = orderBooks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatus(String status) {
        setStatus(Status.valueOf(status));
    }

    @Override
    public int compareTo(Order o) {
        if (!getOrderTime().isEqual(o.getOrderTime())) {
            return getOrderTime().isBefore(o.getOrderTime()) ? 1 : -1;
        }

        if (getEstimatedDeliveryTime().isEqual(o.getEstimatedDeliveryTime())) {
            return getEstimatedDeliveryTime().isBefore(o.getEstimatedDeliveryTime()) ? -1 : 1;
        }

        if (getId() != o.getId()) {
            return getId() < o.getId() ? -1 : 1;
        }

        return 0;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String cutstomerName) {
        this.customerName = cutstomerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String cutstomerAddress) {
        this.customerAddress = cutstomerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String cutstomerPhone) {
        this.customerPhone = cutstomerPhone;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public LocalDateTime getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(LocalDateTime estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedOrderTime = (orderTime != null) ? orderTime.format(formatter) : "N/A";
        String formattedDeliveryTime = (estimatedDeliveryTime != null) ? estimatedDeliveryTime.format(formatter) : "N/A";

        String formattedCustomerName = (customerName != null && !customerName.isEmpty()) ? customerName : "No name provided";
        String formattedAddress = (customerAddress != null && !customerAddress.equals("null")) ? customerAddress : "No address provided";
        String formattedPhone = (customerPhone != null && !customerPhone.equals("null")) ? customerPhone : "No phone provided";

        String str = String.format("Order Details:\n" +
                "------------------------------------\n" +
                "Customer Name: %s\n" +
                "Address: %s\n" +
                "Phone: %s\n" +
                "Order Time: %s\n" +
                "Estimated Delivery Time: %s\n" +
                "Status: %s\n" +
                "Books (%d):\n ",
                formattedCustomerName, formattedAddress, formattedPhone, formattedOrderTime, formattedDeliveryTime, status, getOrderBooks().size());

                for (final var ob : getOrderBooks()) {
                    str += "\t" + ob.getBook() + "\n";
                }
        return str;
    }

}
