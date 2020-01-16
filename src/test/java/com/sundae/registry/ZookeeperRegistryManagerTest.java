package com.sundae.registry;

import org.apache.zookeeper.KeeperException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ZookeeperRegistryManagerTest
 *
 * @author daijiyuan
 * @date 2020/1/16
 * @comment
 */
class ZookeeperRegistryManagerTest extends ZookeeperRegistryManager{

    @Test
    public void testZookeeper() throws IOException, InterruptedException, KeeperException {
        connectZooKeeper("localhost", 2181);
        System.out.println(getChildren("/"));
//        createNode("/SimpleRPC", "service");
//        createNode("/SimpleRPC/service", "test%test1");
        System.out.println(createNode("/test/node/service", "service1"));
        System.out.println(isExist("/SimpleRPC"));
        System.out.println(getNodeData("/SimpleRPC/service"));
//        System.out.println(Arrays.asList("/SIMPLE/TEST".split("/")));
        closeConnection();
    }

}