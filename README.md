## 基础框架
----

##目录
* [framework-mybatis](#framework-mybatis)
* [framework-redis](#framework-redis)
* [framework-thrid](#framework-thrid)

framework-mybatis
-----------
framework-mybatis主要实现打印sql语句(包含参数值)和sql执行阀值超过多少后打印错误日志。在项目中使用分两步  
第一、引入依赖
```xml
        <dependency>
            <groupId>com.qianshanding.framework</groupId>
            <artifactId>framework-mybatis</artifactId>
            <version>1.0.0</version>
        </dependency>
```
第二、在mybaits加入plugin
```xml
    <plugins>
        <plugin interceptor="com.qianshanding.framework.mybatis.monitor.SqlMonitorPlugin">
            <!--是否监控-->
            <property name="sql_monitor" value="true"/>
            <!--是否打印SQL语句，默认为false -->
            <property name="sql_show" value="true"/>
            <!--sql时延的阈值,超过会打印error日志数据   -->
            <property name="overtime_print_error" value="300"/>
        </plugin>
    </plugins>
```
framework-redis
-----------
framework-redis组件是Redis的客户端，在项目中使用分三步  
第一、引入依赖
```xml
        <dependency>
            <groupId>com.qianshanding.framework</groupId>
            <artifactId>framework-redis</artifactId>
            <version>1.0.0</version>
        </dependency>
```
第二、spring初始化framework-redis
```xml
    <bean id="propertyPlaceholderConfigurer"
          class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="ignoreResourceNotFound" value="true"/>
        <property name="ignoreUnresolvablePlaceholders" value="true"/>
        <property name="locations">
            <list>
                <value>classpath*:cache.properties</value>
            </list>
        </property>
    </bean>

    <import resource="classpath*:framework-redis.xml"/>
```
cached.properties:
```xml
    #redis
    jedis.ip=127.0.0.1
    jedis.port=6379
    jedis.pool.maxActive=60
    jedis.pool.maxIdle=10
    jedis.pool.maxWait=10000
    jedis.pool.testOnBorrow=false
```
第三、项目当中使用
```java
    @Resource
    RedisClient redisClient;

    public void testGet() {
        redisClient.set("fish", "value");
        System.out.println(redisClient.get("fish"));
        redisClient.del("fish");
    }
```
framework-third
-----------
framework-third是一些第三方扩展。  
第一、引入依赖
```xml
        <dependency>
            <groupId>com.qianshanding.framework</groupId>
            <artifactId>framework-third</artifactId>
            <version>1.0.0</version>
        </dependency>
```
第二、在mybaits加入plugin
```xml
    <bean id="configPropertyPlaceholderConfigurer"
          class="com.qianshanding.framework.spring.ConfigPropertyPlaceholderConfigurer">
        <property name="ignoreResourceNotFound" value="true"/>
        <property name="ignoreUnresolvablePlaceholders" value="true"/>
        <property name="locations">
            <list>
                <value>classpath*:cache.properties</value>
            </list>
        </property>
    </bean>
```
功能：可以通过System.getProperty(key)获取cache.properties中的配置信息。