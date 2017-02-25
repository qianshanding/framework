package com.qianshanding.framework.test.kafka9;

import java.util.List;

/**
 * @Author : jianghengwei
 * @Date :Created by 2017/2/6 上午10:49.
 * @Description : 消费回调函数
 */
public interface ConsumerCallback {

	void callback(String topic, List<String> dataList);

}
