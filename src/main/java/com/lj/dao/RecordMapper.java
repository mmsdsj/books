package com.lj.dao;

import com.lj.pojo.Record;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Record record);

    int insertSelective(Record record);

    Record selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Record record);

    int updateByPrimaryKey(Record record);

    int borrow(Record record);

//    Record reader_record(String rName);

    List<Record> reader_record(String rName);

    int penalty();

    int back(Integer id);


}