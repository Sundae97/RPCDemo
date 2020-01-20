package com.sundae.registry;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * ZookeeperRegistryManager
 *
 * @author daijiyuan
 * @date 2020/1/16
 * @comment
 */
public class ZookeeperRegistryManager {

    private CuratorFramework client;
    private RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

//    public ZookeeperRegistryManager(String host, int port) {
//        connectZooKeeper(host, port);
//    }

    public void connectZooKeeper(String host, int port) {
        client = CuratorFrameworkFactory.builder()
                .connectString(host+":"+port)
                .namespace("sundae")
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(3000)
                .build();
        client.start();
        System.out.println("zookeeper connect success");
    }

    public boolean createNode(String path,String data) {
        try {
            client.create()
                    .creatingParentsIfNeeded()  // 如果不存在目录就会遍历创建
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(path, data.getBytes());
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean isExist(String path) {
        try {
            Stat stat = client.checkExists().forPath(path);
            return stat != null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<String> getChildren(String path) {
        List<String> list = null;
        try {
            list = client.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getNodeData(String path) {
        try {
            byte[] data = client.getData().forPath(path);
            return new String(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean updateNodeData(String path, String newValue) {
        //判断节点是否存在
        if (!isExist(path)) {
            return false;
        }
        try {
            client.setData().forPath(path, newValue.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteNode(String path){
        try {
            client.delete().forPath(path);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递归删除子节点(包括目录下的节点)
     * @param path
     * @return
     */
    public boolean deleteChildrenIfNeededNode(String path) {
        try {
            client.delete().deletingChildrenIfNeeded().forPath(path);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 注册节点数据变化事件
     * @param path              节点路径
     * @param nodeCacheListener 监听事件
     * @return 注册结果
     */
    public boolean registerWatcherNodeChanged(String path, NodeCacheListener nodeCacheListener) {
        NodeCache nodeCache = new NodeCache(client, path, false);
        try {
            nodeCache.getListenable().addListener(nodeCacheListener);
            nodeCache.start(true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void closeConnection() throws InterruptedException {
        if(client != null)
            client.close();
    }
}
