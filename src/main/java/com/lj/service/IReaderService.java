package com.lj.service;

import com.github.pagehelper.PageInfo;
import com.lj.common.ServerResponse;
import com.lj.pojo.Reader;

import java.util.List;

public interface IReaderService {

    String register(Reader reader);

    ServerResponse<Reader> login(String rName, String rPwd);

    String updateReader(Reader reader);

    String updateReaderPassword(String  rname);

    String deleteReader(String rname);

    List<Reader> findReader(String rname);

    List<Reader> listReader(String rname);
}
