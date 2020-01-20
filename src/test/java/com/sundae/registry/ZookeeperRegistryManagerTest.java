package com.sundae.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
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
//        if(!isExist("/test/node/service"))
//            System.out.println(createNode("/test/node/service", "test/test"));
//        System.out.println(isExist("/SimpleRPC"));
//        System.out.println(getNodeData("/SimpleRPC/service"));

        createNodeIfNotExist("/test/node/service/aaa1", "aaaaaaa");
        createNodeIfNotExist("/test/node/service/aaa2", "aaaaaaa");
        createNodeIfNotExist("/test/node/service/aaa3", "aaaaaaa");
//        deleteNodeIfExist("/test");

//        registerWatcherNodeChanged("/test/nod123e/service", () -> {
//                System.out.println("nodeChanged() ---> ");
//                System.out.println(getNodeData("/test/node/service"));
//        });
        registerWatcherTreeChanged("/test/node/service", new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                if(treeCacheEvent.getType() == TreeCacheEvent.Type.NODE_UPDATED){
                    System.out.println("childEvent  ---> " + treeCacheEvent.getData().getPath() );
                    System.out.println("childEvent  ---> " + new String(treeCacheEvent.getData().getData()) );
                }
                System.out.println(treeCacheEvent.getType().name());        //NODE_REMOVED 删除类型
             }
        });
        TimeUnit.SECONDS.sleep(300);  //TODO Test Listener
//        System.out.println(Arrays.asList("/SIMPLE/TEST".split("/")));
//        closeConnection();
    }

}