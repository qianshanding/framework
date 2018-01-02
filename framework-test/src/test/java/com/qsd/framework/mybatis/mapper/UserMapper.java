package com.qsd.framework.mybatis.mapper;

import com.qsd.framework.mybatis.dataobject.UserDO;
import org.apache.ibatis.annotations.Param;

/**
 * Created by fish on 2016/11/11.
 */
public interface UserMapper {
    public UserDO getById(@Param("id") Integer id);

    public Integer insert(UserDO userDO);
}
