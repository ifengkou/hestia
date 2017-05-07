package cn.ifengkou.hestia.test.countDown;

import org.junit.Test;

/**
 * Description:
 *
 * @author shenlongguang
 * @date: 2017/4/26 下午5:34.
 */
public class CountDownTest {
    @Test
    public void testCountDownLatch(){
        boolean result = false;
        try {
            result = ApplicationStartupUtil.getInstance().checkExternalServices();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("External services validation completed !! Result was :: "+ result);

    }
}
