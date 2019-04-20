package com.lj.service;

import com.github.pagehelper.PageInfo;
import com.lj.common.ServerResponse;
import com.lj.pojo.Record;

import java.util.List;

public interface IRecordService {
    ServerResponse borrow(Record record);
    /*ServerResponse<Record> reader_record(String rname);*/
    ServerResponse<PageInfo> reader_record(int pageNum, int pageSize, String rname);
    Integer penalty();
    ServerResponse back(int id,int bisbn);
}
