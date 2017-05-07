package cn.ifengkou.hestia.test.countDown;

import java.util.concurrent.CountDownLatch;

/**
 * Description:
 *
 * @author shenlongguang
 * @date: 2017/4/26 下午5:22.
 */
public class CacheHealthChecker extends BaseHealthChecker {
    public CacheHealthChecker(CountDownLatch latch) {
        super("Cache Health Service", latch);
    }

    @Override
    public void verifyService() {
        System.out.println("Checking " + this.getServiceName());
        try
        {
            Thread.sleep(4000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is UP");
    }
}
