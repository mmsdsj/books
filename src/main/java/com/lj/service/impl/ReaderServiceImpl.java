package com.lj.service.impl;

import com.lj.common.ServerResponse;
import com.lj.dao.ReaderMapper;
import com.lj.pojo.Reader;
import com.lj.service.IReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iReaderService")
public class ReaderServiceImpl implements IReaderService {

    @Autowired
    private ReaderMapper readerMapper;

    public String register(Reader reader) {
        int result = readerMapper.register(reader.getRname(),reader.getRpwd(),reader.getRage(),reader.getRsex());
        if (result > 0) {
            return "success";
        }
        return "fail";
    }

    public ServerResponse<Reader> login(String rName, String rPwd) {
        Reader reader = readerMapper.login(rName, rPwd);
        if (reader!=null){
            return ServerResponse.createBySuccess("登录成功",reader);
        }
        return ServerResponse.createByErrorMessage("登录失败");
    }

    public String updateReader(Reader reader){
        int result = readerMapper.updateReader(reader);
        if(result > 0){
            return "success";
        }
            return "fail";

    }

    public String deleteReader(String rname){
        int result = readerMapper.deleteReader(rname);
        if(result > 0){
            return "success";
        }
        return "fail";
    }

    public List<Reader> findReader(String rname){
        return readerMapper.findReader(rname);
    }
}
