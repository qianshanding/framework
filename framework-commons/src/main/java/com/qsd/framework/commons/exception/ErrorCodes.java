package com.qsd.framework.commons.exception;

/**
 * @Description : 关于异常码定义
 */
public class ErrorCodes {

	//Rest:90000~99999
	public static final ErrorCode CONSUMER_FALLBACK = new ErrorCode(90000, "服务内部异常{0}");
	public static final ErrorCode RPC_EXCEPTION = new ErrorCode(90001, "RPC异常");


	//公共异常范围:100000~199999
	public static final ErrorCode DATA_IS_NULL = new ErrorCode(100001, "{0}为空");
	public static final ErrorCode OBJECT_IS_NULL = new ErrorCode(100002, "{0} is null");
	public static final ErrorCode RESULT_IS_NULL = new ErrorCode(100003, "查询结果{0}为空");
	public static final ErrorCode ACCESS_DENIED = new ErrorCode(100004, "{0}");
	public static final ErrorCode DATA_FORMAT_ERROR = new ErrorCode(100005, "{0}");
	public static final ErrorCode DATA_BASE_ERROR = new ErrorCode(100006, "{0}");

	//默认参数校验异常码
	public static final ErrorCode PARAM_CHECK_COMMON_ERROR = new ErrorCode(100007, "参数校验异常");
	public static final ErrorCode DEFAULT_CHECK_COMMON_ERROR = new ErrorCode(100008, "参数异常");
	public static final ErrorCode PARAM_INVALID = new ErrorCode(100009, "{0} 参数不合法");
	public static final ErrorCode OTHER_EXCEPTION = new ErrorCode(100010, "未知异常");

	//BI义务异常范围:  200000~299999
	public static final ErrorCode BI_ITEM_ID_IS_NULL = new ErrorCode(200001, "itemId is null.");
	public static final ErrorCode BI_ITEM_NOT_EXIST = new ErrorCode(200002, "ID={0},监控项信息不存在");
	public static final ErrorCode BI_ROWKEY_ID_IS_NULL = new ErrorCode(200003, "rowkey id is null");


	//monitor业务异常范围: 300000~399999

	//log业务异常范围  400000~499999
	public static final ErrorCode LOG_ID_NOT_EXIST = new ErrorCode(400001, "logIndexId={0}的日志表不存在.");
	public static final ErrorCode CONDITON_UPDATE_FAIL = new ErrorCode(400002, "搜索条件{0}更新失败.");
	public static final ErrorCode CONDITON_DELETE_FAIL = new ErrorCode(400003, "搜索条件{0}删除失败.");
	public static final ErrorCode COMPRESS_ERROR = new ErrorCode(400004, "压缩数据出错");
	public static final ErrorCode UNCOMPRESS_ERROR = new ErrorCode(400005, "解压缩数据出错");
	public static final ErrorCode LOG_PARSE_ERROR = new ErrorCode(400006, "解析日志错误");

	//streaming业务异常范围  500000~599999
	public static final ErrorCode AGGRE_VALUE_FAILED = new ErrorCode(500001, "获取AGGRE值异常.");
	public static final ErrorCode PARSE_BLOT_WHERE_FAILED = new ErrorCode(500002, "where name={0}, msg={1}异常.");

	//schedule业务异常范围  600000~699999
	public static final ErrorCode HIVE_SQL_EXIST= new ErrorCode(600000, "HIVE SQL已经存在.");
	public static final ErrorCode HIVE_CREATE_TABLE_ERROR = new ErrorCode(600001, "创建Hive表异常");

	//alarm业务异常范围   700000~799999
	public static final ErrorCode NOTIFY_RULE_DELETE_FAIL = new ErrorCode(700001, "告警通知规则{0}删除失败.");

}
