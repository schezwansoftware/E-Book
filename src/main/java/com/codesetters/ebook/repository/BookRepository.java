package com.codesetters.ebook.repository;

import com.codesetters.ebook.config.SparkConfiguration;
import com.codesetters.ebook.domain.Book;
import com.datastax.spark.connector.cql.CassandraConnector;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapRowTo;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

/**
 * Cassandra repository for the Book entity.
 */
@Repository
public class BookRepository {
    private transient SparkConf conf;
    private static final String TABLENAME = Book.class.getSimpleName().toLowerCase();

    public BookRepository(SparkConfiguration configuration) {
        this.conf=configuration.getDefaultSparkConfiguration();

    }


    public List<Book> findAll() {
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<Book> booksRdd = null;
        try {
            booksRdd = javaFunctions(sc).cassandraTable(SparkConfiguration.KEYSPACENAME, TABLENAME, mapRowTo(Book.class));
        } catch (Exception e) {
            sc.stop();
        }
        List<Book> books = booksRdd != null ? booksRdd.collect() : null;
        sc.stop();
        return books;
    }

    public Book findOne(UUID id) {
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<Book> booksRdd = null;
        try {
            booksRdd = javaFunctions(sc).cassandraTable(SparkConfiguration.KEYSPACENAME, TABLENAME, mapRowTo(Book.class)).where("id="+id);
        } catch (Exception e) {
            sc.stop();
        }
        Book book = booksRdd != null ? booksRdd.first() : null;
        sc.stop();
        return book;
    }

    public Book save(Book book) {
        if (book.getId()==null){
            book.setId(UUID.randomUUID());
        }
        System.out.println("DEBUGGING"+book);
        List<Book> books = new ArrayList<>();
        books.add(book);
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<Book> bookJavaRDD = sc.parallelize(books);
        try {
            javaFunctions(bookJavaRDD).writerBuilder(SparkConfiguration.KEYSPACENAME, TABLENAME, mapToRow(Book.class)).saveToCassandra();
        }catch (Exception e){
            sc.stop();
        }
        sc.stop();
        return book;
    }

    public void delete(UUID id) {
        JavaSparkContext sc = new JavaSparkContext(conf);
        String deleteStatement="DELETE FROM "+SparkConfiguration.KEYSPACENAME+"."+TABLENAME+" where id=" + id + ";";
        CassandraConnector connector=CassandraConnector.apply(sc.getConf());
        Session session=connector.openSession();
        session.execute(deleteStatement);
        sc.stop();
    }

    public void deleteAll() {

    }
}
