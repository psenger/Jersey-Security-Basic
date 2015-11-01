/**
 * Created by philip a senger on October 22, 2015
 */
package com.sample.domain;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.core.EntityTag;
import java.util.*;
import java.util.concurrent.Executors;

public class BookDao {

    private Map<String, Book> books;
    private ListeningExecutorService service;

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

        service  = MoreExecutors.listeningDecorator( Executors.newFixedThreadPool(10) );
    }

    public Collection<Book> getBooks() {
        return (books.values());
    }

    public Book getBook(String id) throws EntityNotFoundException {
        if ( books.containsKey(id) ) {
            return books.get(id);
        }
        throw new EntityNotFoundException("Book Not Found");
    }

    public ListenableFuture<Book> getBookAsync(final String id ){
        ListenableFuture<Book> future = service.submit(
                () -> getBook(id)
                /**
                 new Callable<Book>() {
                   public Book call(){
                     return getBook(id);
                   }
                 }
                 */
        );
        return future;
    }

    public Book addBook( Book book ) {
        book.setId( UUID.randomUUID().toString() );
        books.put( book.getId(), book );
        return book;
    }
}
