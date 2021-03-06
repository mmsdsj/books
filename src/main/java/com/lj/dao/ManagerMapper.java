package com.lj.dao;

import com.lj.pojo.Manager;
import org.apache.ibatis.annotations.Param;

public interface ManagerMapper {
    int deleteByPrimaryKey(Integer mid);

    int insert(Manager record);

    int insertSelective(Manager record);

    Manager selectByPrimaryKey(Integer mid);

    int updateByPrimaryKeySelective(Manager record);

    int updateByPrimaryKey(Manager record);

    Manager mlogin(@Param("mname")String mname,@Param("mpwd")String mpwd);
}