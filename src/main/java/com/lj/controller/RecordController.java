package com.lj.controller;

import com.github.pagehelper.PageInfo;
import com.lj.common.ServerResponse;
import com.lj.pojo.Record;
import com.lj.service.IRecordService;
import com.sun.org.apache.xpath.internal.operations.Mod;
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

    //查询读者借阅记录
    @RequestMapping(value = "fineReaderRecord.do", method = RequestMethod.POST)
    public String record_back(String rName, Model model){
    if(rName!=null){

        iRecordService.penalty();//罚金
    ServerResponse<PageInfo> response1 = iRecordService.reader_record(1,15,rName);
    model.addAttribute("recordList", response1.getData());
    return "record/reader_record";
    }
    return "error";
    }
}
