package cn.ifengkou.hestia.test.countDown;

import java.util.concurrent.CountDownLatch;

/**
 * Description:
 *
 * @author shenlongguang
 * @date: 2017/4/26 下午5:20.
 */
public class DatabaseHealthChecker extends BaseHealthChecker {

    public DatabaseHealthChecker (CountDownLatch latch)  {
        super("DatabaseHealth Service", latch);
    }

    @Override
    public void verifyService() {
        System.out.println("Checking " + this.getServiceName());
        try
        {
            Thread.sleep(3000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is UP");
    }
}
