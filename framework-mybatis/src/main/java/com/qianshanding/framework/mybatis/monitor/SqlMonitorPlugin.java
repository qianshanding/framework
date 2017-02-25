package com.qianshanding.framework.mybatis.monitor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * 数据库时延监控
 * Created by Fish on 2016/10/19.
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class,
                CacheKey.class, BoundSql.class})})
public class SqlMonitorPlugin implements Interceptor {

    private static final Logger logger = LogManager.getLogger(SqlMonitorPlugin.class);

    private static int MAX_ALLOW_TIME = 1000;

    private boolean sqlMonitor = true;
    private boolean sqlShow = false;
    //超过一定时间答应Error日志
    private int maxAllowTime = MAX_ALLOW_TIME;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (!sqlMonitor) {
            return invocation.proceed();
        }

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

        if (mappedStatement == null) {
            return invocation.proceed();
        }

        Object result = null;
        int code = 0;
        long start = System.currentTimeMillis();

        try {

            result = invocation.proceed();
        } catch (Exception e) {

            code = 1;
            throw e;
        } finally {

            long end = System.currentTimeMillis();
            // 执行SQL所花费时间
            long cost = end - start;

            String sql = null;
            if (sqlShow) {
                sql = getSql(invocation, cost);
                logger.info(sql);
            }
            if (cost >= maxAllowTime) {
                //接口加方法名mappedStatement.getId()
                logger.error(mappedStatement.getId() + ",code=" + code + ",cost=" + cost);
            } else if (cost > 30) {
                logger.warn(mappedStatement.getId() + ",code=" + code + ",cost=" + cost);
            } else {
                logger.info(mappedStatement.getId() + ",code=" + code + ",cost=" + cost);
            }
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        if (properties == null) {
            return;
        }
        if (properties.containsKey("sql_monitor")) {
            String value = properties.getProperty("sql_monitor");
            if (Boolean.TRUE.toString().equals(value)) {
                this.sqlMonitor = true;
            }
        }
        if (properties.containsKey("sql_show")) {
            String value = properties.getProperty("sql_show");
            if (Boolean.TRUE.toString().equals(value)) {
                this.sqlShow = true;
            }
        }
        if (properties.containsKey("max_allow_time")) {
            String value = properties.getProperty("max_allow_time");

            if (value == null || "".equals(value.trim())) {
                maxAllowTime = MAX_ALLOW_TIME;
            }
            try {
                maxAllowTime = Integer.parseInt(value.trim());
            } catch (Exception ex) {
                maxAllowTime = MAX_ALLOW_TIME;
            }
        }
    }

    private String getSql(Invocation invocation, long cost) {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        String sql = showSql(mappedStatement.getConfiguration(), mappedStatement.getBoundSql(parameter));
        StringBuilder str = new StringBuilder(100);
        str.append(mappedStatement.getId());
        str.append(":");
        str.append(sql);
        str.append(":");
        //获取SQL类型
        str.append(mappedStatement.getSqlCommandType());
        str.append(":");
        str.append(cost);
        str.append("ms");
        return str.toString();
    }

    /**
     * 参数值
     *
     * @param obj
     * @return
     */
    private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

    private String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));

            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }
}