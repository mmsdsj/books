package com.lj.dao;

import com.lj.pojo.Book;

import java.util.List;

public interface BookMapper {
    int deleteByPrimaryKey(Integer bid);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(Integer bid);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);

    List<Book> bookList();

    int delete(Integer bIsbn);

    int updateBook(Book book);

    int addBooks(Book book);

    List<Book> findBook(Integer bisbn);

    List<Book> findBookName(String bname);

    List<Book> findBookWriter(String bwriter);

    List<Book> findBooks(String bsearch);

    int updateBnumberAndBOutNumber(int bisbn);

    int updateBackNumber(int bisbn);


}