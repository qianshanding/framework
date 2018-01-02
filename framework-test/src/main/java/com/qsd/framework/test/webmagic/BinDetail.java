package com.qsd.framework.test.webmagic;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by zhengyu on 2017/7/16.
 */
@Data
@AllArgsConstructor
public class BinDetail {
    private Double nowPrice;
    private Double minPrice;
    private Double maxPrice;
    private String chinaName;
    private String enName;

}
