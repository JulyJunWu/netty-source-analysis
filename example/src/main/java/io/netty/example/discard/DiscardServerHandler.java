/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.example.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.DefaultChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends SimpleChannelInboundHandler<Object> {

    static final InternalLogger logger = InternalLoggerFactory.getInstance(DiscardServerHandler.class);

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // discard
        ByteBuf byteBuf = (ByteBuf) msg;
        String message = byteBuf.toString(Charset.defaultCharset());
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String hostName = socketAddress.getHostName();
        int port = socketAddress.getPort();
        logger.info("客户端IP:{}:{} | 数据:{}",hostName,port,message );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
