package cn.ifengkou.hestia.test;

import cn.ifengkou.hestia.test.service.HelloService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * 原生proto 测试
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/23 17:19
 */
public class ProtoTest {

    public static final byte[] REQUEST = new byte[] { 0x0A, 0x03, 'i', 'f', 'k' };
    public static final byte[] RESPONSE = new byte[] { 0x0A, 0x0A, 'H', 'e', 'l', 'l', 'o', ',', ' ', 'i', 'f', 'k' };

    private HelloService service;

    @Before
    public void setUp() throws Exception {
        service = new HelloService();
    }

    @Test
    public void testSearch() throws Exception {
        byte[] responseData = service.hello(REQUEST);
        Assert.assertArrayEquals(RESPONSE, responseData);
    }
}
