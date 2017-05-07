package cn.ifengkou.hestia.test.countDown;

import java.util.concurrent.CountDownLatch;

/**
 * Description:
 *
 * @author shenlongguang
 * @date: 2017/4/26 下午5:18.
 */
public class NetworkHealthChecker extends BaseHealthChecker {

    public NetworkHealthChecker (CountDownLatch latch)  {
        super("Network Service", latch);
    }

    @Override
    public void verifyService() {
        System.out.println("Checking " + this.getServiceName());
        try
        {
            Thread.sleep(7000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is UP");
    }
}
