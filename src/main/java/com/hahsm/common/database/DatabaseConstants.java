package com.hahsm.common.database;

public class DatabaseConstants {

    private DatabaseConstants() {}

    public static final String USER_TABLE = "users";
    public static final String BOOK_TABLE = "books";
    public static final String ORDER_TABLE = "orders";


    public static final class UserColumns {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String ADDRESS = "address";
    }

    public static final class BookColumns {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String AUTHOR = "author";
    }
    
    public static final class OrderColumns {
        public static final String USER_ID = "user_id";
        public static final String BOOK_ID = "book_id";
        public static final String QUANTITY = "quantity";
        public static final String ORDER_DATE = "order_date";
        public static final String ORDER_TIME = "order_time";
    }
}
