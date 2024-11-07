-- TABLE
CREATE TABLE books (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  year integer NOT NULL,
  title TEXT NOT NULL,
  author TEXT NOT NULL
  );
CREATE TABLE orders (
  	id integer PRIMARY KEY AUTOINCREMENT,
    order_time integer NOT NULL,
    estimated_delivery_time integer NOT NULL,
    status TEXT NOT NULL,
    customer_name TEXT NOT NULL,
    customer_address TEXT NOT NULL,
    customer_phone TEXT NOT NULL
  );
CREATE TABLE order_books (
 	order_id integer NOT NULL,
    book_id integer NOT NULL,
    quantity integer NOT NULL,
    
    PRIMARY KEY (order_id, book_id),
    FOREIGN KEY (order_id)
    	REFERENCES orders(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE, 
    FOREIGN KEY (book_id)
    	REFERENCES books (id)
        	ON DELETE CASCADE
            ON UPDATE CASCADE     
 );
-- INDEX
 
-- TRIGGER
 
-- VIEW
 
