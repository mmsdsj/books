package com.lj.service;

import com.github.pagehelper.PageInfo;
import com.lj.common.ServerResponse;
import com.lj.pojo.Book;

import java.util.List;

public interface IBookService {
    ServerResponse  addBooks(Book book);
    String updateBook(Book book);
    String delete(Integer bIsbn);
    ServerResponse<PageInfo> listBook(int pageNum, int pageSize);

    /*单个查询，已合并
    List<Book> findBook(Integer bisbn);
    List<Book> findBookName(String bname);
    List<Book> findBookWriter(String bwriter);*/
    List<Book> findBooks(String bsearch);
}
