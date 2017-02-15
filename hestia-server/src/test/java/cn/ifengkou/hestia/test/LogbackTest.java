package cn.ifengkou.hestia.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * logback 单元测试
 * Created by LongGuang on 2017/2/15.
 */
public class LogbackTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogbackTest.class);
    @Test
    public void testLogback(){
        LOGGER.trace("======trace");
        LOGGER.debug("======debug");
        LOGGER.info("======info");
        LOGGER.warn("======warn");
        LOGGER.error("======error");
    }
}
