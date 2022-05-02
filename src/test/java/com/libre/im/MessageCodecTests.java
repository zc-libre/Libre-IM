package com.libre.im;

import com.libre.im.core.codec.ProtobufMessageDecoder;
import com.libre.im.core.codec.ProtobufMessageEncoder;
import com.libre.im.core.proto.TextMessageProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @author: Libre
 * @Date: 2022/5/1 9:16 AM
 */
public class MessageCodecTests {

   public static void test() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                new ProtobufMessageDecoder(),
                new ProtobufMessageEncoder(),
                new ProtobufDecoder(TextMessageProto.TextMessage.getDefaultInstance()),
                new SimpleChannelInboundHandler<TextMessageProto.TextMessage>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, TextMessageProto.TextMessage msg) throws Exception {
                        System.out.println(msg);
                    }
                }
        );

        TextMessageProto.TextMessage.Builder builder = TextMessageProto.TextMessage.newBuilder();
        TextMessageProto.TextMessage message = builder.setBody("124").build();
        embeddedChannel.writeInbound(message);
    }

    public static void main(String[] args) {
        test();
    }
}
