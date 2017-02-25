package com.qianshanding.framework.mybatis.dataobject;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhengyu on 2016/11/11.
 */
@Data
public class UserDO implements Serializable {
    private Integer id;
    private String userName;
    private String passWord;
    private Date gmtCreate;
    private Date gmtUpdate;
}
