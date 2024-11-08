package com.hahsm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

import de.vandermeer.asciitable.AsciiTable;

import com.hahsm.algorithm.MergeSort;
import com.hahsm.algorithm.Search;
import com.hahsm.algorithm.SortStrategy;
import com.hahsm.book.model.Book;
import com.hahsm.book.repository.BookRepository;
import com.hahsm.cart.BookCart;
import com.hahsm.common.ConfigLoader;
import com.hahsm.common.Util;
import com.hahsm.common.database.DatabaseConstants;
import com.hahsm.common.ioconsole.ConsoleHelper;
import com.hahsm.common.ioconsole.MenuBuilder;
import com.hahsm.common.type.Pair;
import com.hahsm.common.type.Repository;
import com.hahsm.database.DatabaseConnectionManager;
import com.hahsm.datastructure.adt.List;
import com.hahsm.datastructure.adt.Queue;
import com.hahsm.frame.Frame;
import com.hahsm.frame.FrameManager;
import com.hahsm.order.model.Order;
import com.hahsm.order.model.OrderBook;
import com.hahsm.order.repository.OrderBookRepository;
import com.hahsm.order.repository.OrderRepository;
import com.hahsm.orderprocesssingsystem.OrderProcessingSystem;
import com.hahsm.datastructure.ArrayList;
import com.hahsm.datastructure.PriorityQueue;
import com.hahsm.datastructure.HashMap;

public class App {

    public static void main(String[] args) throws SQLException {
        init();
        final DatabaseConnectionManager connectionManager = new DatabaseConnectionManager("main");
        final Repository<Book, Integer> bookRepo = new BookRepository(connectionManager);
        final Repository<Order, Integer> orderRepo = new OrderRepository(connectionManager, bookRepo);
        final OrderProcessingSystem ops = new OrderProcessingSystem(orderRepo);
        final Scanner sc = new Scanner(System.in);
        final FrameManager fm = new FrameManager();
        final SortStrategy sorter = new MergeSort();

        fm.addFrame(mainMenu(sc, orderRepo, bookRepo, ops, sorter));

        sc.close();
    }

    private static void init() {
        try {
            ConfigLoader.loadProperties("database.properties");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static Frame mainMenu(final Scanner sc, final Repository<Order, Integer> orderRepo,
            final Repository<Book, Integer> bookRepo, final OrderProcessingSystem ops, final SortStrategy sorter) {
        return new Frame((FrameManager fm) -> {
            final int option = ConsoleHelper.getFromMenu(
                    sc,
                    "Please enter your option",
                    new ArrayList<>("Create Order", "Show all orders", "Track Order", "Exit"),
                    1);

            switch (option) {
                case 1:
                    fm.addFrame(createOder(sc, orderRepo, bookRepo, sorter));
                    break;
                case 2:
                    fm.addFrame(showAllOrders(ops));
                    break;
                case 3:
                    fm.addFrame(trackOrder(sc, orderRepo, sorter));
                    break;
                case 4:
                    System.out.println("Good bye!");
                    System.exit(0);
                    return;
            }
        });
    }

    private static Frame showAllOrders(final OrderProcessingSystem ops) {
        return new Frame((FrameManager fm) -> {
            System.out.println(ops.toString());
        });
    }

    private static Frame trackOrder(final Scanner sc, final Repository<Order, Integer> orderRepo, final SortStrategy sorter) {
        return new Frame((fm) -> {
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
                System.out.println("Cannot find your order");
                return;
            }

            System.out.println("Found " + searchResult.size() + " orders");
            ConsoleHelper.printSeparator();
            for (Order ord : searchResult) {
                sorter.sort(ord.getOrderBooks(), (x, y) -> x.getBook().compareTo(y.getBook()));
                System.out.println(ord);
                ConsoleHelper.printSeparator();
            }
        });
    }

    private static Frame createOder(
            final Scanner sc,
            final Repository<Order, Integer> orderRepo,
            final Repository<Book, Integer> bookRepo,
            final SortStrategy sorter) {
        return new Frame((fm) -> {
            final String table = bookRepo.toString();
            BookCart cart = new BookCart(sorter);

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

            final String customerName = ConsoleHelper.getString(sc, "Enter your name: ");
            final String customerAddress = ConsoleHelper.getString(sc, "Enter your address: ");
            final String customerPhone = ConsoleHelper.getString(sc, "Enter your phone number: ");

            cart.setCustomerInformation(customerName, customerAddress, customerPhone);

            final Order order = cart.toOrder();
            orderRepo.insert(order);

            ConsoleHelper.printSeparator();

            System.out.println("Your order is created with id: " + order.getId());
        });
    }
}
