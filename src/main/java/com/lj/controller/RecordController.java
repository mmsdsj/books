package com.lj.controller;

import com.lj.common.ServerResponse;
import com.lj.pojo.Record;
import com.lj.service.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RecordController {

    @Autowired
    private IRecordService iRecordService;

    @RequestMapping(value = "recordHtml.do", method = RequestMethod.GET)
    public String recordHtml() {

        return "record";
    }
//管理员办理借阅
    @RequestMapping(value = "record.do", method = RequestMethod.GET)
    public String borrow(Record record, Model model) {
        ServerResponse response = iRecordService.borrow(record);
        if (response.isSuccess()){
            model.addAttribute("isbn", record.getBisbn());
            model.addAttribute("rname", record.getRname());
           // model.addAttribute("indate",record.getIndate());
            return "record/borrow";
        }
        return "error";
    }
    //管理员办理归还
    @RequestMapping(value = "record_back.do", method = RequestMethod.GET)
    public String back(int id,int bisbn) {
        ServerResponse response = iRecordService.back(id,bisbn);
        if (response.isSuccess()){
            //model.addAttribute("isbn", record.getBisbn());
           // model.addAttribute("rname", record.getRname());
            return "record/back";
        }
        return "error";
    }

}
