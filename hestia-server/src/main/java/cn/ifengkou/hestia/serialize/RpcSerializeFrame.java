package cn.ifengkou.hestia.serialize;

import io.netty.channel.ChannelPipeline;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @Description RpcSerializeFrame
 * @package cn.ifengkou.hestia.serialize
 * @date 2017/2/22 14:24
 */
public interface RpcSerializeFrame {
    void select(RpcSerializeProtocol protocol, ChannelPipeline pipeline);
}
