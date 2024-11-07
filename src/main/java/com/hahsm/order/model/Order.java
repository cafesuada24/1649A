package com.hahsm.order.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.hahsm.datastructure.adt.List;
import com.hahsm.datastructure.ArrayList;

public class Order implements Comparable<Order> {
    public static enum Status {
        // PENDING,
        PROCESSING,
        SHIPPED,
        DELIVERED,
        // CANCELLED
    }

    // public static enum Status {
    // WAITING,
    // };
    private int id;
    // private int userId = 0;
    // private Date orderDate;
    // private Time orderTime;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private LocalDateTime orderTime;
    private LocalDateTime estimatedDeliveryTime;
    private Status status;
    // private String status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatus(String status) {
        setStatus(Status.valueOf(status));
    }

    private List<OrderBook> orderBooks;

    public Order() {
        this.orderBooks = new ArrayList<>();
    }

    // public Order(int id, LocalDateTime orderDate, LocalDateTime
    // estimatedDeliveryTime, String customerName,
    // String customerAddress,
    // String customerPhone) {
    // this(id, orderDate, estimatedDeliveryTime, customerName, customerAddress,
    // customerPhone, null,
    // new ArrayList<>());
    // }

    public Order(int id, long orderTime, long estimatedDeliveryTime, String customerName,
            String address, String phone, String status) {
        this(id, orderTime, estimatedDeliveryTime, customerName, address, phone, status, new ArrayList<>());
    }

    public Order(int id, long orderTime, long estimatedDeliveryTime, String customerName,
            String address, String phone, String status, List<OrderBook> orderBooks) {
        setId(id);
        // setUserId(userId);
        // setOrderDate(orderDate);
        // setOrderTime(orderTime);
        setOrderTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(orderTime), ZoneOffset.UTC));
        setEstimatedDeliveryTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(estimatedDeliveryTime), ZoneOffset.UTC));
        setCustomerName(customerName);
        setCustomerAddress(customerAddress);
        setCustomerPhone(customerPhone);
        setStatus(status);
        // this.orderBooks = orderBooks;
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

    // public int getUserId() {
    // return userId;
    // }
    //
    // public void setUserId(int userId) {
    // this.userId = userId;
    // }

    // public Date getOrderDate() {
    // return orderDate;
    // }
    //
    // public void setOrderDate(Date orderDate) {
    // this.orderDate = orderDate;
    // }
    //
    // public Time getOrderTime() {
    // return orderTime;
    // }
    //
    // public void setOrderTime(Time orderTime) {
    // this.orderTime = orderTime;
    // }

    @Override
    public int compareTo(Order o) {
        // if (getOrderDate().before(o.getOrderDate())) {
        // return -1;
        // } else if (getOrderDate().after(o.getOrderDate())) {
        // return 1;
        // }
        //
        // if (getOrderTime().before(o.getOrderTime())) {
        // return -1;
        // } else if (getOrderTime().after(o.getOrderTime())) {
        // return 1;
        // }
        
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

        // Format the orderTime and estimatedDeliveryTime
        String formattedOrderTime = (orderTime != null) ? orderTime.format(formatter) : "N/A";
        String formattedDeliveryTime = (estimatedDeliveryTime != null) ? estimatedDeliveryTime.format(formatter) : "N/A";

        // Handle null values for customerName, address, and phone
        String formattedCustomerName = (customerName != null && !customerName.isEmpty()) ? customerName : "No name provided";
        String formattedAddress = (customerAddress != null && !customerAddress.equals("null")) ? customerAddress : "No address provided";
        String formattedPhone = (customerPhone != null && !customerPhone.equals("null")) ? customerPhone : "No phone provided";

        return String.format("Order Details:\n" +
                "Customer Name: %s\n" +
                "Address: %s\n" +
                "Phone: %s\n" +
                "Order Time: %s\n" +
                "Estimated Delivery Time: %s\n" +
                "Status: %s",
                formattedCustomerName, formattedAddress, formattedPhone, formattedOrderTime, formattedDeliveryTime, status);
    }

}
