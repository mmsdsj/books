package com.lj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lj.common.ServerResponse;
import com.lj.dao.BookMapper;
import com.lj.dao.RecordMapper;
import com.lj.pojo.Record;
import com.lj.service.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iRecordService")
public class RecordServiceImpl implements IRecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private BookMapper bookMapper;

    public ServerResponse borrow(Record record){
        int result = recordMapper.borrow(record);
        if(result > 0){
            int updateResult = bookMapper.updateBnumberAndBOutNumber(record.getBisbn());
            if(updateResult > 0) {
                return ServerResponse.createBySuccessMessage("借书成功");
            }
            return ServerResponse.createByErrorMessage("更新图书失败");
        }
        return ServerResponse.createByErrorMessage("结束失败");
    }

    public ServerResponse<PageInfo> reader_record(int pageNum1,int pageSize1,String rName){
        PageHelper.startPage(pageNum1,pageSize1);
        List<Record> records = recordMapper.reader_record(rName);
        PageInfo<Record> pageInfo = new PageInfo<>(records);
        return ServerResponse.createBySuccess("success",pageInfo);



    }

}