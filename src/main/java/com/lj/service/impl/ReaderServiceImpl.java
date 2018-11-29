package com.lj.service.impl;

import com.lj.dao.ReaderMapper;
import com.lj.pojo.Reader;
import com.lj.service.IReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Reader login(String rname, String rpwd) {
        Reader reader = readerMapper.login(rname, rpwd);
        if (!reader.equals(null)){
            return reader;
        }
        return null;
    }
}