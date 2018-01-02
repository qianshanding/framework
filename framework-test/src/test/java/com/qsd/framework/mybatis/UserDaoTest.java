package com.qsd.framework.mybatis;

import com.alibaba.fastjson.JSON;
import com.qsd.framework.mybatis.dataobject.UserDO;
import com.qsd.framework.mybatis.mapper.UserMapper;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by fish on 2017/1/21.
 */
public class UserDaoTest extends BasicSpringTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void insert() {
        UserDO userDO = new UserDO();
        userDO.setUserName("fish");
        userDO.setPassWord("123123123");
        userDO.setGmtUpdate(new Date());
        userDO.setGmtCreate(new Date());
        userMapper.insert(userDO);

        System.out.println(JSON.toJSONString(userMapper.getById(1)));
    }
}
