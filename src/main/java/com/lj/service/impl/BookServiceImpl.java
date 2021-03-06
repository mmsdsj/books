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
        Book result1 = bookMapper.checkBisbn(books.getBisbn());
        if (result1 !=null) {
            return ServerResponse.createByErrorMessage("新增失败,该isbn已经存在！");
        }
        int result = bookMapper.insert(books);
        if (result > 0) {
            return ServerResponse.createBySuccessMessage("新增成功");
        }
        return ServerResponse.createByErrorMessage("新增失败");

    }

    public ServerResponse updateBook(Book books){
        Book result1 = bookMapper.checkBisbn(books.getBisbn());
        if (result1 ==null) {
            return ServerResponse.createByErrorMessage("修改失败,查询不到该isbn号！");
        }
        int result = bookMapper.updateBook(books);
        if(result > 0){
            return ServerResponse.createBySuccessMessage("修改成功");

        }else {
            return ServerResponse.createByErrorMessage("修改失败");
        }

    }

    public String delete(Integer bIsbn){
        int result = bookMapper.delete(bIsbn);
        if(result > 0){
            return "success";
        }else {
            return "删除失败，这本书可能已经被删除啦！";
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
  /*  public List<Book> findBooks(String bsearch){
        return bookMapper.findBooks(bsearch);
    }*/
    public ServerResponse<PageInfo> findBooks(int pageNum, int pageSize,String bsearch){
        PageHelper.startPage(pageNum,pageSize);
        List<Book> books = bookMapper.findBooks(bsearch);
        //用PageInfo对结果进行包装
        PageInfo<Book> pageInfo = new PageInfo<>(books);
        return ServerResponse.createBySuccess("success",pageInfo);
    }

}
