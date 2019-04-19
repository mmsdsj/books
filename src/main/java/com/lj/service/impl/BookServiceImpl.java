package com.lj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lj.common.ServerResponse;
import com.lj.dao.BookMapper;
import com.lj.pojo.Book;
import com.lj.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iBookService")
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookMapper bookMapper;

    public ServerResponse  addBooks(Book books) {
        int result = bookMapper.insert(books);
        if (result > 0) {
            return ServerResponse.createBySuccessMessage("新增成功");
        }
        return ServerResponse.createByErrorMessage("新增失败");

    }

    public String updateBook(Book books){
        int result = bookMapper.updateBook(books);
        if(result > 0){
            return "success";
        }else {
            return "fail";
        }

    }

    public String delete(Integer bIsbn){
        int result = bookMapper.delete(bIsbn);
        if(result > 0){
            return "success";
        }else {
            return "fail";
        }
    }
//首页
   public ServerResponse<PageInfo> listBook(int pageNum,int pageSize){
       PageHelper.startPage(pageNum,pageSize);
       List<Book> books = bookMapper.bookList();
       //用PageInfo对结果进行包装
       PageInfo<Book> pageInfo = new PageInfo<>(books);
       return ServerResponse.createBySuccess("success",pageInfo);
   }

   public List<Book> findBook(Integer bisbn){
    return bookMapper.findBook(bisbn);
   }
    public List<Book> findBookName(String bname){
        return bookMapper.findBookName(bname);
    }
    public List<Book> findBookWriter(String bwriter){
        return bookMapper.findBookWriter(bwriter);
    }
    public List<Book> findBooks(String bsearch){
        return bookMapper.findBooks(bsearch);
    }


}
