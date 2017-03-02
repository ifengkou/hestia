package cn.ifengkou.hestia.server;

import cn.ifengkou.hestia.config.SpringConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Description: 程序入口
 *
 * @author shenlongguang
 * @version 1.0
 * @date: 2017/2/21 9:55
 */
public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    //private static final Object WAIT_OBJECT = new Object();

    public static void main(String[] args) {
        LOGGER.info("Start Application");

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        context.registerShutdownHook();

        /*synchronized (WAIT_OBJECT){
            try {
                LOGGER.info("Main Thread Wait");
                WAIT_OBJECT.wait();
            } catch (InterruptedException e) {
                LOGGER.error("Main Thread interrupted",e);
            }
        }*/
        context.destroy();
    }

}
