package cn.ifengkou.hestia.test;

import org.junit.Test;

/**
 * Description: {一句话描述类是干什么的}
 *
 * @author shenlongguang
 * @version 1.0
 * @date: 2017/2/21 14:53
 */
public class CommonTest
{
    @Test
    public void testAvailableProcessors(){
        int numb = Runtime.getRuntime().availableProcessors();
        System.out.println(String.valueOf(numb));
    }

}
