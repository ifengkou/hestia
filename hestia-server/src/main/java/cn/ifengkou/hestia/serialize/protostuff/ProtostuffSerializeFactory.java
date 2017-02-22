package cn.ifengkou.hestia.serialize.protostuff;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @Description 一句话描述类是干什么的
 * @package cn.ifengkou.hestia.serialize.protostuff
 * @date 2017/2/22 14:56
 */
public class ProtostuffSerializeFactory extends BasePooledObjectFactory<ProtostuffSerialize> {
    private ProtostuffSerialize createProtostuff() {
        return new ProtostuffSerialize();
    }

    @Override
    public ProtostuffSerialize create() throws Exception {
        return createProtostuff();
    }
    @Override
    public  PooledObject<ProtostuffSerialize> wrap(ProtostuffSerialize serialize) {
        return new DefaultPooledObject<ProtostuffSerialize>(serialize);
    }
}
