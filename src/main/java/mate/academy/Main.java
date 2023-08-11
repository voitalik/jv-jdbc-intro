package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        BookDao bookDaoImpl = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book();
        book1.setTitle("test book1");
        book1.setPrice(new BigDecimal(500));

        Book book2 = bookDaoImpl.create(book1);
        System.out.println(book2);

        book1 = new Book();
        book1.setTitle("test book2");
        book1.setPrice(new BigDecimal(300));
        Book book3 = bookDaoImpl.create(book1);
        System.out.println(book3);

        book3.setPrice(new BigDecimal(900));
        book3 = bookDaoImpl.update(book3);
        System.out.println(book3);

        System.out.println(bookDaoImpl.findById(2L));

        bookDaoImpl.findAll().forEach(System.out::println);
        boolean deleted = bookDaoImpl.deleteById(2L);
        System.out.println(deleted);
        System.out.println(bookDaoImpl.findById(2L));

        bookDaoImpl.findAll().forEach(System.out::println);
    }
}
