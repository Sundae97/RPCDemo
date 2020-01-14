package com.sundae.server;

import io.netty.bootstrap.ServerBootstrap;

/**
 * Main
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public class ServerMain {

    public static void main(String[] args) {
        //TODO NETTY 通讯
        new ServerBootStrap().doBootStrap();
        //TODO 序列化
        //TODO 注解扫描
    }

}
