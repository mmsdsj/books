package com.lj.controller;

import com.github.pagehelper.PageInfo;
import com.lj.common.Const;
import com.lj.common.ServerResponse;
import com.lj.pojo.Reader;
import com.lj.pojo.Record;
import com.lj.service.IBookService;
import com.lj.service.IReaderService;
import com.lj.service.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Controller
public class ReaderController {

    @Autowired
    private IReaderService iReaderService;

    @Autowired
    private IBookService iBookService;

    @Autowired
    private IRecordService iRecordService;
    //例子（可删除）
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){

        return "register";
    }


    @RequestMapping(value = "register.do", method = RequestMethod.GET)
    @ResponseBody
    public String register(com.lj.pojo.Reader reader) {

        return iReaderService.register(reader);
    }
//例子（可删除）
    @RequestMapping(value = "loginHtml", method = RequestMethod.GET)
    public String loginHtml(){

        return "login";
    }
//分界
    //读者登录
    @RequestMapping(value = "login.do", method = RequestMethod.GET)
    //@ResponseBody
    public String login(String rName, String rPwd, HttpSession session, Model model) {//ServerResponse<Reader>

        ServerResponse<com.lj.pojo.Reader> reader = iReaderService.login(rName, rPwd);
        if(reader.isSuccess()){
            session.setAttribute(Const.Reader.CURRENT_READER,reader.getData());
            model.addAttribute("username",reader.getData().getRname());
            model.addAttribute("rAge",reader.getData().getRage());
            model.addAttribute("rpwd",reader.getData().getRpwd());
            /*session.getSession().setAttribute("username",rName);*/
            ServerResponse<PageInfo> response = iBookService.listBook(1,5);
            model.addAttribute("bookList", response.getData());
            model.addAttribute("totalPages",response.getData().getPages());
            //读者借阅记录
            iRecordService.penalty();//罚金
            ServerResponse<PageInfo> response1 = iRecordService.reader_record(1,15,reader.getData().getRname());
            model.addAttribute("recordList", response1.getData());


/*            ServerResponse<com.lj.pojo.Record> record = iRecordService.reader_record(reader.getData().getRname());
            List<Data> time1=  (List<Data>)(record.getData().getIndate());
            model.addAttribute("recordList", record.getData());
            model.addAttribute("timeList", record.getData());*/



/*            ServerResponse<PageInfo> records = iRecordService.reader_record(pageNum,pageSize,rName);
            model.addAttribute("recordList",records.getData().getList());
            model.addAttribute("totalPages1",records.getData().getPages());*/

/*//获取分页数据
            model.addAttribute("ServerResponse",response1);
            model.addAttribute("pageNum1",pageNum);
            model.addAttribute("totalPages1",response1.getData().getPages());*/

            //return response;
            return "reader";
        }
        //return response;
        return "error";
    }

    //此为读者页面的图书分页
    @RequestMapping(value = "/listBook_reader.do",method = RequestMethod.GET)
    //pageNum是第几页，pageSize是每页显示几条数据
    public String getAllBook(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5") int pageSize, Model model, HttpSession session){
        Reader reader = (Reader) session.getAttribute(Const.Reader.CURRENT_READER);
        if (null == reader){
            return "error";
        }

        ServerResponse<PageInfo> response1 = iBookService.listBook(pageNum,pageSize);
        //获取数据
        model.addAttribute("bookList", response1.getData());
        model.addAttribute("username",reader.getRname());
        model.addAttribute("rAge",reader.getRage());
        model.addAttribute("rpwd",reader.getRpwd());

        ServerResponse<PageInfo> response2 = iRecordService.reader_record(1,15,reader.getRname());
        model.addAttribute("recordList", response2.getData());

        //获取分页数据
        model.addAttribute("ServerResponse",response1);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("totalPages",response1.getData().getPages());
        return "reader";
        /*return iBookService.listBook(pageNum,pageSize);*/
    }

/*
    @RequestMapping(value = "reader_listbook",method = RequestMethod.GET)
    //@ResponseBody
    public String listBook1(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5") int pageSize, Model model){
        ServerResponse<PageInfo> response = iBookService.listBook(pageNum,pageSize);
        model.addAttribute("bookList", response.getData().getList());
        return "listbook";
    }*/


    @RequestMapping(value = "updateReaderHtml.do",method = RequestMethod.GET)
    public String updateReaderHtml(){
        return "updateReader";
    }

    @RequestMapping(value = "updateReader.do",method = RequestMethod.POST)
    @ResponseBody
    public String updateReader(com.lj.pojo.Reader reader) {

        return iReaderService.updateReader(reader);
    }

    @RequestMapping(value = "deleteReaderHtml.do",method = RequestMethod.GET)
    public String deleteReaderHtml(){
        return "deleteReader";
    }

    @RequestMapping(value = "deleteReader.do",method = RequestMethod.GET)
    @ResponseBody
    public String deleteReader(String rname) {

        return iReaderService.deleteReader(rname);
    }

    @RequestMapping(value = "findReaderHtml.do",method = RequestMethod.GET)
    public String findReaderHtml(){
        return "findReader";
    }

    @RequestMapping(value = "fineReader.do",method = RequestMethod.GET)
    @ResponseBody
    public List<com.lj.pojo.Reader> findReader(String rname) {

        return iReaderService.findReader(rname);
    }

    @RequestMapping(value = "get_reader_info.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse  getReaderInfo(HttpSession session){//ServerResponse
        com.lj.pojo.Reader reader = (com.lj.pojo.Reader)session.getAttribute("rName");
        if(reader!=null){
           return ServerResponse.createBySuccess("获取成功",reader);
        }
        return ServerResponse.createByErrorMessage("请登录");
    }


    /*@RequestMapping(value = "readerHtml",method = RequestMethod.GET)
    //@ResponseBody
    public String listBook(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5") int pageSize, Model model){
        ServerResponse<PageInfo> response = iBookService.listBook(pageNum,pageSize);
        model.addAttribute("bookList", response.getData().getList());
        return "reader";
    }*/
}
