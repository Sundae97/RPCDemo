package com.sundae.registry;

import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.zookeeper.KeeperException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

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
        if(!isExist("/test/node/service"))
            System.out.println(createNode("/test/node/service", "test/test"));
        System.out.println(isExist("/SimpleRPC"));
        System.out.println(getNodeData("/SimpleRPC/service"));

        registerWatcherNodeChanged("/test/node/service", new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("nodeChanged()");
            }
        });
//        TimeUnit.SECONDS.sleep(300);  //TODO Test Listener
//        System.out.println(Arrays.asList("/SIMPLE/TEST".split("/")));
//        closeConnection();
    }

}