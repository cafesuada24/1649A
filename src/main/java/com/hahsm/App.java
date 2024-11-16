package com.hahsm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

import com.hahsm.algorithm.MergeSort;
import com.hahsm.algorithm.SortStrategy;
import com.hahsm.book.model.Book;
import com.hahsm.book.repository.BookRepository;
import com.hahsm.cart.BookCart;
import com.hahsm.common.ConfigLoader;
import com.hahsm.common.ioconsole.ConsoleHelper;
import com.hahsm.common.type.Repository;
import com.hahsm.common.type.User;
import com.hahsm.database.DatabaseConnectionManager;
import com.hahsm.datastructure.adt.List;
import com.hahsm.frame.Frame;
import com.hahsm.frame.FrameManager;
import com.hahsm.order.model.Order;
import com.hahsm.order.repository.OrderRepository;
import com.hahsm.orderprocesssingsystem.OrderProcessingSystem;
import com.hahsm.datastructure.ArrayList;

public class App {

    public static void main(String[] args) throws SQLException {
        init();
        final DatabaseConnectionManager connectionManager = new DatabaseConnectionManager("main");
        final Repository<Book, Integer> bookRepo = new BookRepository(connectionManager);
        final Repository<Order, Integer> orderRepo = new OrderRepository(connectionManager, bookRepo);
        final OrderProcessingSystem ops = new OrderProcessingSystem(orderRepo);
        final Scanner sc = new Scanner(System.in);
        final FrameManager fm = new FrameManager();
        final SortStrategy sortalgo = new MergeSort();

        final App app = new App(connectionManager, bookRepo, orderRepo, ops, sc, fm, sortalgo);

        app.start();

        sc.close();
    }

    private final DatabaseConnectionManager connectionManager;
    private final Repository<Book, Integer> bookRepo;
    private final Repository<Order, Integer> orderRepo;
    private final OrderProcessingSystem ops;
    private final Scanner sc;
    private final FrameManager fm;
    private final SortStrategy sortAlgo;
    private User currentUser;

    public App(
            final DatabaseConnectionManager connectionManager,
            final Repository<Book, Integer> bookRepo,
            final Repository<Order, Integer> orderRepo,
            final OrderProcessingSystem ops,
            final Scanner sc,
            final FrameManager fm,
            final SortStrategy sortAlgo) {
        this.connectionManager = connectionManager;
        this.bookRepo = bookRepo;
        this.orderRepo = orderRepo;
        this.ops = ops;
        this.sc = sc;
        this.fm = fm;
        this.sortAlgo = sortAlgo;
    }

    public void start() {
        fm.addFrameAndDisplay(createLoginMenuFrame());
    }

    private static void init() {
        try {
            ConfigLoader.loadProperties("database.properties");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private Frame createLoginMenuFrame() {
        return new Frame((fm) -> {
            final int option = ConsoleHelper.getFromMenu(
                    sc,
                    "What role are you?",
                    new ArrayList<>("User", "Admin", "Exit"),
                    1);
            ConsoleHelper.printSeparator();
            switch (option) {
                case 1:
                    fm.addFrame(createUserMainMenuFrame());
                    fm.addFrameAndDisplay(createUserInfoEditFrame());
                    break;
                case 2:
                    fm.addFrameAndDisplay(createAdminMainMenu());
                    break;
                case 3:
                    System.out.println("Good bye!");
                    System.exit(0);
                    return;
            }
        });
    }

    // USER

    private Frame createUserInfoEditFrame() {
        return new Frame((_) -> {
            if (currentUser == null) {
                final String customerName = ConsoleHelper.getString(sc, "Enter your name: ");
                final String customerAddress = ConsoleHelper.getString(sc, "Enter your address: ");
                final String customerPhone = ConsoleHelper.getString(sc, "Enter your phone number: ");
                currentUser = new User(customerName, customerAddress, customerPhone);
                return;
            }

            final String customerName = ConsoleHelper.getOptionalString(sc,
                    "Enter your name (press enter if no change): ");
            final String customerAddress = ConsoleHelper.getOptionalString(sc,
                    "Enter your address (press enter if no change): ");
            final String customerPhone = ConsoleHelper.getOptionalString(sc,
                    "Enter your phone number (press enter if no change): ");
            if (customerName != null && !customerName.isBlank()) {
                currentUser.name = customerName;
            }
            if (customerAddress != null && !customerAddress.isBlank()) {
                currentUser.address = customerAddress;
            }
            if (customerPhone != null && !customerPhone.isBlank()) {
                currentUser.phone = customerPhone;
            }

        });
    }

    private Frame createUserMainMenuFrame() {
        return new Frame((FrameManager fm) -> {
            final int option = ConsoleHelper.getFromMenu(
                    sc,
                    "Please enter your option",
                    new ArrayList<>("Create Order", "Show your orders", "Search by ID", "Change your information",
                            "Exit"),
                    1);

            switch (option) {
                case 1:
                    fm.addFrameAndDisplay(createCreateOrderFrame());
                    break;
                case 2:
                    fm.addFrameAndDisplay(createShowUserOrdersFrame());
                    break;
                case 3:
                    fm.addFrameAndDisplay(createSearchOrderByIdFrame());
                    break;
                case 4:
                    fm.addFrameAndDisplay(createUserInfoEditFrame());
                    break;
                default:
                    System.out.println("Good bye!");
                    System.exit(0);
                    return;
            }
        });
    }

    private Frame createShowUserOrdersFrame() {
        return new Frame((_) -> {
            final List<Order> orders = orderRepo
                    .getByFilter((order) -> order.getCustomerName().equals(currentUser.name) &&
                            order.getCustomerAddress().equals(currentUser.address) &&
                            order.getCustomerPhone().equals(currentUser.phone));

            System.out.println("You have " + orders.size() + " orders");
            printOrders(orders);
        });
    }

    private Frame createSearchOrderByIdFrame() {
        return new Frame((_) -> {
            final int id = ConsoleHelper.getInteger(sc, "Please enter order id: ");

            final var res = orderRepo.getByID(id);
            if (res.isEmpty()) {
                System.out.println("No order with id " + id + " found!");
            } else {
                System.out.println(res);
            }
        });
    }

    private Frame createCreateOrderFrame() {
        return new Frame((_) -> {
            final String table = bookRepo.toString();
            final BookCart cart = new BookCart(sortAlgo, currentUser);

            boolean loop = true;
            while (loop) {
                ConsoleHelper.Clear();
                System.out.println(table);
                ConsoleHelper.printSeparator();

                System.out.println("Your current card: ");
                System.out.println(cart.toString());

                final int id = ConsoleHelper.getInteger(sc, "Please enter book id: ");
                final Optional<Book> book = bookRepo.getByID(id);

                if (book.isEmpty()) {
                    System.err.println("Invalid id");
                    continue;
                }

                final int qty = ConsoleHelper.getInteger(sc, "Enter quantity: ", 0, Integer.MAX_VALUE);
                cart.addItem(id, book.get(), qty);

                System.out.println("Your current card: ");
                System.out.println(cart.toString());

                loop = ConsoleHelper.getYesNoInput(sc,
                        "Continue to add? Enter 'y' for yes or 'n' for no: ");
            }

            System.out.println("=".repeat(100));

            final Order order = cart.toOrder();
            orderRepo.insert(order);

            ConsoleHelper.printSeparator();

            System.out.println("Your order is created with id: " + order.getId());
        });
    }

    // ADMIN

    private Frame createAdminMainMenu() {
        return new Frame((fm) -> {
            final int option = ConsoleHelper.getFromMenu(
                    sc,
                    "Please enter your option",
                    new ArrayList<>("List all order", "Filter order", "Process order", "Exit"),
                    1);

            switch (option) {
                case 1:
                    fm.addFrameAndDisplay(createListAllOrdersFrame());
                    break;
                case 2:
                    fm.addFrameAndDisplay(createSearchOrderFrame());

                case 3:
                    fm.addFrameAndDisplay(createProcessOrderFrame());
                default:
                    System.out.println("Good bye!");
                    System.exit(0);
                    return;
            }

        });
    }

    private Frame createListAllOrdersFrame() {
        return new Frame((_) -> {
            System.out.println(ops);
        });
    }

    private Frame createSearchOrderFrame() {
        return new Frame((_) -> {
            ConsoleHelper.printSeparator();
            final int id = ConsoleHelper.getInteger(sc, "Please enter order id, type 0 to ignore: ");

            List<Order> searchResult = new ArrayList<>();
            if (id != 0) {
                final var res = orderRepo.getByID(id);
                if (res.isPresent()) {
                    searchResult.add(res.get());
                }
            } else {
                final String customerName = ConsoleHelper.getOptionalString(sc, "Enter your name: ");
                final String customerAddress = ConsoleHelper.getOptionalString(sc, "Enter your address: ");
                final String customerPhone = ConsoleHelper.getOptionalString(sc, "Enter your phone number: ");

                searchResult = orderRepo.getByFilter((order) -> {
                    if (order.getCustomerName() == null) {
                        return customerName == null || customerName.isEmpty();
                    }
                    return order.getCustomerName().startsWith(customerName);
                }).filter((order) -> {
                    if (order.getCustomerAddress() == null) {
                        return customerAddress == null || customerAddress.isEmpty();
                    }
                    return order.getCustomerAddress().startsWith(customerAddress);
                }).filter((order) -> {
                    if (order.getCustomerPhone() == null) {
                        return customerPhone == null || customerPhone.isEmpty();
                    }
                    return order.getCustomerPhone().startsWith(customerPhone);
                });
            }

            if (searchResult.isEmpty()) {
                System.out.println("Cannot find order matching your information");
                return;
            }

            System.out.println("Found " + searchResult.size() + " orders");
            ConsoleHelper.printSeparator();
            printOrders(searchResult);
        });
    }

    private Frame createProcessOrderFrame() {
        return new Frame((_) -> {
            System.out.println("ORDERS ():");
            System.out.println(ops);

            Order order = null;
            while (true) {
                int id = ConsoleHelper.getInteger(sc, "Enter order id to process: ");

                Optional<Order> result = orderRepo.getByID(id);
                if (result.isPresent()) {
                    order = result.get();
                    break;
                }
                System.out.println("Invalid order id, try again.");
                continue;
            }

            final int statusId = ConsoleHelper.getFromMenu(sc,
                    "Please choose order status to update",
                    new ArrayList<>("Processing", "Shipped", "Delivered", "Cancelled"),
                    1);
            Order.Status status = order.getStatus();
            switch (statusId) {
                case 1:
                    status = Order.Status.PROCESSING; 
                    break;
                case 2:
                    status = Order.Status.SHIPPED; 
                    break;
                case 3:
                    status = Order.Status.DELIVERED; 
                    break;
                case 4:
                    status = Order.Status.CANCELLED; 
                    break;
                default:
                    break;
            }

            order.setStatus(status);            
            orderRepo.update(order);
            System.out.println("Order id " + order.getId() + ", status has been updated to " + status); 
        });
    }

    // -----------------HELPERS----------------------------//

    private void printOrders(final List<Order> orders) {
        ConsoleHelper.printSeparator();
        for (Order ord : orders) {
            sortAlgo.sort(ord.getOrderBooks(), (x, y) -> x.getBook().compareTo(y.getBook()));
            System.out.println(ord);
            ConsoleHelper.printSeparator();
        }
    }
}
