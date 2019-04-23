package com.lj.controller;

import com.github.pagehelper.PageInfo;
import com.lj.common.Const;
import com.lj.common.ServerResponse;
import com.lj.pojo.Reader;
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

import javax.servlet.http.HttpSession;
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
   /* @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){

        return "reader/reader_left";
    }*/


    @RequestMapping(value = "register.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse register(Reader reader) {
        if (reader.getRname() == null || reader.getRname().isEmpty()) {
            return ServerResponse.createByErrorMessage("用户名或密码不能为空");
        }
        if (reader.getRpwd().length() < 6) {
            return ServerResponse.createByErrorMessage("密码不能少于6位");
        }
        return iReaderService.register(reader);
    }

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
            model.addAttribute("rsex",reader.getData().getRsex());

            /*session.getSession().setAttribute("username",rName);*/
            ServerResponse<PageInfo> response = iBookService.listBook(1,5);
            model.addAttribute("bookList", response.getData());

            //读者借阅记录
            iRecordService.penalty();//罚金
            ServerResponse<PageInfo> response1 = iRecordService.reader_record(1,15,reader.getData().getRname());
            model.addAttribute("recordList", response1.getData());


            //获取分页尾页数据
            model.addAttribute("totalPages",response.getData().getPages());
            model.addAttribute("pageNum",1);

            return "reader";
        }
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
        model.addAttribute("rsex",reader.getRsex());
        model.addAttribute("rAge",reader.getRage());
        model.addAttribute("rpwd",reader.getRpwd());

        ServerResponse<PageInfo> response2 = iRecordService.reader_record(1,15,reader.getRname());
        model.addAttribute("recordList", response2.getData());

        //获取分页数据
        model.addAttribute("ServerResponse",response1);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("totalPages",response1.getData().getPages());
        return "reader";
    }

    //更改读者信息
    @RequestMapping(value = "updateReader.do",method = RequestMethod.POST)
    @ResponseBody
    public String updateReader(com.lj.pojo.Reader reader) {
        return iReaderService.updateReader(reader);
    }


    //更改读者密码
    @RequestMapping(value = "updateReaderPassword.do",method = RequestMethod.POST)
    @ResponseBody
    public String updateReader(String rname) {
        return iReaderService.updateReaderPassword(rname);
    }


    //删除读者
    @RequestMapping(value = "deleteReader.do",method = RequestMethod.GET)
    @ResponseBody
    public String deleteReader(String rname) {

        return iReaderService.deleteReader(rname);
    }



    //查询读者信息
    @RequestMapping(value = "fineReader.do",method = RequestMethod.POST)
    /*@ResponseBody
    public List<com.lj.pojo.Reader> findReader(String rname) {

        return iReaderService.findReader(rname);
    }*/
    public String getAllReader(Model model,String rname){

        List<Reader> response1 = iReaderService.listReader(rname);
        model.addAttribute("readerList", response1);



        return "readerlist";
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



















    //修改后
    //读者登录
    @RequestMapping(value = "reader_login.do", method = RequestMethod.GET)
    //@ResponseBody
    public String reader_login(String rName, String rPwd, HttpSession session, Model model) {//ServerResponse<Reader>

        ServerResponse<com.lj.pojo.Reader> reader = iReaderService.login(rName, rPwd);
        if(reader.isSuccess()){
            session.setAttribute(Const.Reader.CURRENT_READER,reader.getData());
            model.addAttribute("username",reader.getData().getRname());

            return "reader/reader_left";
        }
        return "error";
    }

    //分页显示,此为读者页面的图书浏览
    @RequestMapping(value = "/reader_listbook.do",method = RequestMethod.GET)
    //pageNum是第几页，pageSize是每页显示几条数据
    public String reader_listBook(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5") int pageSize, Model model,HttpSession session){
        Reader reader = (Reader) session.getAttribute(Const.Reader.CURRENT_READER);
        if (null == reader){
            return "error";
        }
        ServerResponse<PageInfo> response1 = iBookService.listBook(pageNum,pageSize);
        //获取数据
        model.addAttribute("bookList", response1.getData());
        model.addAttribute("username",reader.getRname());
        model.addAttribute("rpwd",reader.getRpwd());

        //获取分页数据
        model.addAttribute("ServerResponse",response1);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("totalPages",response1.getData().getPages());
        return "reader/reader_listbook";

    }
}
