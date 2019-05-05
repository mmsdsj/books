package com.lj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lj.common.ServerResponse;
import com.lj.dao.BookMapper;
import com.lj.dao.ReaderMapper;
import com.lj.dao.RecordMapper;
import com.lj.pojo.Book;
import com.lj.pojo.Reader;
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

    @Autowired
    private ReaderMapper readerMapper;
    //办理借阅
    public ServerResponse borrow(Record record){
        Reader result1 = readerMapper.checkName(record.getRname());
        if(result1 ==null){
            return ServerResponse.createByErrorMessage("借书失败,读者账号不存在");
        }
        Book result2 = bookMapper.checkBisbn(record.getBisbn());
        if(result2 ==null){
            return ServerResponse.createByErrorMessage("借书失败,图书isbn不存在");
        }
        int result = recordMapper.borrow(record);
        if(result > 0){
            int updateResult = bookMapper.updateBnumberAndBOutNumber(record.getBisbn());
            if(updateResult > 0) {
                return ServerResponse.createBySuccessMessage("借书成功");
            }
            return ServerResponse.createByErrorMessage("借书失败");
        }
        return ServerResponse.createByErrorMessage("借书失败");
    }
    //罚金设置
    public Integer penalty(){
        return recordMapper.penalty();

    }
//读者页面借阅记录显示
    public ServerResponse<PageInfo> reader_record(int pageNum,int pageSize,String rName){
        PageHelper.startPage(pageNum,pageSize);
        List<Record> records = recordMapper.reader_record(rName);
        PageInfo<Record> pageInfo = new PageInfo<>(records);
        return ServerResponse.createBySuccess("success",pageInfo);
    }

        //办理归还
        public ServerResponse back(int id,int bisbn){
           // int result = recordMapper.back(record);
            //if(result > 0){
                int updateResult = bookMapper.updateBackNumber(bisbn);
                if(updateResult > 0) {

                    int result = recordMapper.back(id);
                        if(result > 0){
                            return ServerResponse.createBySuccessMessage("还书成功");
                        }
                        return ServerResponse.createByErrorMessage("还书失败");
                }
            return ServerResponse.createByErrorMessage("还书失败");
        }

        //办理丢失
        public ServerResponse lost(int id,int bisbn){

            int updateResult = bookMapper.updateBackNumberLost(bisbn);
            if( updateResult > 0) {
                int result = recordMapper.back(id);
                if(result > 0){
                    return ServerResponse.createBySuccessMessage("还书成功！");
                }
                return ServerResponse.createByErrorMessage("还书失败！");
            }
            return ServerResponse.createByErrorMessage("还书失败！");
        }







/*    public ServerResponse<Record> reader_record(String rname){
    Record record = recordMapper.reader_record(rname);
        if (record!=null){
            return ServerResponse.createBySuccess("登录成功",record);
        }
        return ServerResponse.createByErrorMessage("暂无借阅记录");
    }*/
}
