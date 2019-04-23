package com.lj.controller;

import com.github.pagehelper.PageInfo;
import com.lj.common.Const;
import com.lj.common.ServerResponse;
import com.lj.pojo.Book;
import com.lj.pojo.Manager;
import com.lj.pojo.Reader;
import com.lj.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;


@Controller

public class BookController {
    @Autowired
    private IBookService iBookService;
/*
    @RequestMapping(value = "indexHtml1", method = RequestMethod.GET)
    public String indexHtml(){ return "index"; }

    @RequestMapping(value = "headerHtml.do", method = RequestMethod.GET)
    public String headerHtml(){ return "header"; }*/

    @RequestMapping(value = "bookHtml.do", method = RequestMethod.GET)
    public String bookHtml(){ return "book"; }

    @RequestMapping(value = "book.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse  addBooks(Book book, MultipartFile file){
        if (file != null) {
            String newFileName = file.getOriginalFilename();
            String path = "src/main/resources/static/image";
            File fileRoot = new File(path);
            if (!fileRoot.exists()) {
                fileRoot.mkdir();
            }
            File targetFile = new File(fileRoot.getAbsolutePath(), newFileName);
            try {
                file.transferTo(targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String savePath = "/image/" + newFileName;
            book.setBpic(savePath);

        }
        return iBookService.addBooks(book);
    }

//    @RequestMapping(value = "updateBookHtml.do",method = RequestMethod.GET)
//    public String updateBookHtml(){return "updateBook";}

    @RequestMapping(value = "updateBook.do",method = RequestMethod.POST)
    @ResponseBody
    public String updateBook(Book book){return  iBookService.updateBook(book);}

    @RequestMapping(value = "deleteHtml.do",method = RequestMethod.GET)
    public String deleteHtml(){return "delete";}

    @RequestMapping(value = "delete.do",method = RequestMethod.GET)
    @ResponseBody
    public String delete(Integer bIsbn){return iBookService.delete(bIsbn);
    }

    @RequestMapping(value = "listBookHtml.do", method = RequestMethod.GET)
    public String listBookHtml(){return "listBook";}

    //分页显示,此为首页分页
    @RequestMapping(value = "/listBook.do",method = RequestMethod.GET)
    //pageNum是第几页，pageSize是每页显示几条数据
    public String listBook(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5") int pageSize, Model model){
        ServerResponse<PageInfo> response = iBookService.listBook(pageNum,pageSize);
        model.addAttribute("bookList", response.getData().getList());

    //获取分页数据
        model.addAttribute("ServerResponse",response);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("totalPages",response.getData().getPages());
        return "index";
        /*return iBookService.listBook(pageNum,pageSize);*/
    }



    //此为图书管理员图书浏览页面分页
    @RequestMapping(value = "/listBook_manager.do",method = RequestMethod.GET)
    //pageNum是第几页，pageSize是每页显示几条数据
    public String getAllBook_manager(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5") int pageSize, Model model, HttpSession session){
        Manager manager = (Manager) session.getAttribute(Const.Manager.CURRENT_MANAGER);
        if (null == manager){
            return "error";
        }

        ServerResponse<PageInfo> response = iBookService.listBook(pageNum,pageSize);
        //获取数据
        model.addAttribute("bookList", response.getData());
        model.addAttribute("mname",manager.getMname());


        /*model.addAttribute("rName",reader.getRname());*/

        //获取分页数据
        model.addAttribute("ServerResponse",response);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("totalPages",response.getData().getPages());
        return "manager";
        /*return iBookService.listBook(pageNum,pageSize);*/
    }

/*    @RequestMapping(value = "findBookHtml.do", method = RequestMethod.GET)
    public String findBookHtml(){return "findBook";}

    @RequestMapping(value = "findBook.do",method = RequestMethod.GET)
    @ResponseBody
    public List<Book> findBook(Integer bisbn){
        return iBookService.findBook(bisbn);

    }

    @RequestMapping(value = "findBookNameHtml.do", method = RequestMethod.GET)
    public String findBookNameHtml(){return "findBookName";}

    @RequestMapping(value = "findBookName.do",method = RequestMethod.GET)
    @ResponseBody
    public List<Book> findBookName(String bname){
        return iBookService.findBookName(bname);

    }

    @RequestMapping(value = "findBookWriterHtml.do", method = RequestMethod.GET)
    public String findBookWriterHtml(){return "findBookWriter";}

    @RequestMapping(value = "findBookWriter.do",method = RequestMethod.GET)
    @ResponseBody
    public List<Book> findBookWriter(String bwriter){
        return iBookService.findBookWriter(bwriter);

    }*/

   /* @RequestMapping(value = "findBooks.do",method = RequestMethod.GET)
    @ResponseBody
    public List<Book> findBooks(String bsearch){
        return iBookService.findBooks(bsearch);

    }*/


/*    @RequestMapping(value = "/findBooks.do", method = RequestMethod.GET)
    public String searchBook( Model model,String bsearch,HttpSession session) {

        session.setAttribute(Const.Book.CURRENT_BOOK,bsearch);
        ServerResponse<PageInfo> response = iBookService.listBook(1,5);
        model.addAttribute("bookSearchList", response.getData());
        //获取分页尾页数据
        model.addAttribute("totalPages",response.getData().getPages());
        return "booksearch";
    }*/

    //查询图书
    @RequestMapping(value = "/findBooks.do",method = RequestMethod.GET)
    //pageNum是第几页，pageSize是每页显示几条数据
    public String searchBook(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5") int pageSize, Model model,String bsearch,HttpSession session){

        ServerResponse<PageInfo> response = iBookService.findBooks(pageNum,pageSize,bsearch);
        model.addAttribute("bookSearchList", response.getData());

        //获取分页数据
        model.addAttribute("ServerResponse",response);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("totalPages",response.getData().getPages());
        return "booksearch";
        //return iBookService.listBook(pageNum,pageSize);
    }

}
