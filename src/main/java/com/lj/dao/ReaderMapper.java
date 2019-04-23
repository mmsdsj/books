package com.lj.dao;

import com.lj.pojo.Reader;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReaderMapper {
    int deleteByPrimaryKey(Integer rid);

    int insert(Reader record);

    int insertSelective(Reader record);

    Reader selectByPrimaryKey(Integer rid);

    int updateByPrimaryKeySelective(Reader record);

    int updateByPrimaryKey(Reader record);

    Reader login(@Param("rName")String rName,@Param("rPwd")String rPwd);

    int register(@Param("rName")String rName,@Param("rPwd")String rPwd,@Param("rAge")Integer rAge,@Param("rSex")String rSex);

    int updateReader(Reader reader);

    int updateReaderPassword(String rname);

    int deleteReader(String rname);

    List<Reader> findReader(String rname);

    String record_name(@Param("rName")String rName);

    List<Reader> readerList(String rname);

    Reader checkName(@Param("rName")String rName);


}