package com.lj.controller;


import com.github.pagehelper.PageInfo;
import com.lj.common.Const;
import com.lj.common.ServerResponse;
import com.lj.pojo.Manager;
import com.lj.service.IBookService;
import com.lj.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class ManagerController {

    @Autowired
    private IManagerService iManagerService;

    @Autowired
    private IBookService iBookService;

  /*  @RequestMapping(value = "managerHtml.do", method = RequestMethod.GET)
    public String loginHtml(){

        return "manager";
    }*/
//管理员登录
    @RequestMapping(value = "manager.do", method = RequestMethod.GET)
    //@ResponseBody
    public String manager(String mname, String mpwd, Model model, HttpSession session) {
/*
        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
*/
        ServerResponse<com.lj.pojo.Manager> manager = iManagerService.mlogin(mname, mpwd);
        if (manager.isSuccess()) {
            session.setAttribute(Const.Manager.CURRENT_MANAGER, manager.getData());
            model.addAttribute("mname", manager.getData().getMname());
            ServerResponse<PageInfo> response = iBookService.listBook(1, 5);
            model.addAttribute("bookList", response.getData());
            model.addAttribute("totalPages",response.getData().getPages());
            model.addAttribute("pageNum",1);

            /*model.addAttribute("mname", mname);*/
            return "manager";
        }
        model.addAttribute("registerError","账号或密码错误");
        return "reader/register_error";

    }
}
