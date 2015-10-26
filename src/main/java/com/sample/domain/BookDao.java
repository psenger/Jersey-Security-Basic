/**
 * Created by philip a senger on October 22, 2015
 */
package com.sample.domain;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BookDao {

    private Map<String, Book> books;

    public BookDao() {

        books = new HashMap<String, Book>();
        Book book1 = new Book();
        book1.setId("1");
        book1.setTitle("The Sharp Sliver");
        book1.setAuthor("Clementine Green");
        book1.setIsbn("1234");
        book1.setPublished(new Date());

        Book book2 = new Book();
        book2.setId("2");
        book2.setTitle("Edge of Darkness");
        book2.setAuthor("Francisco Fry");
        book2.setIsbn("1234");
        book2.setPublished(new Date());


        books.put(book1.getId(), book1);
        books.put(book2.getId(), book2);

    }

    public Collection<Book> getBooks() {
        return (books.values());
    }

    public Book getBook(String id) {
        return (books.get(id));
    }

    public Book addBook( Book book ) {
        book.setId( UUID.randomUUID().toString() );
        books.put( book.getId(), book );
        return book;
    }
}
