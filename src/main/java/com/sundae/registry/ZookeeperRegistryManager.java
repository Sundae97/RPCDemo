package com.sundae.registry;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
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
        connectZooKeeper(host, port, null);
    }

    public void connectZooKeeper(String host, int port, String namespace) {
        client = CuratorFrameworkFactory.builder()
                .connectString(host+":"+port)
                .namespace(namespace)
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(3000)
                .build();
        client.start();
        System.out.println("zookeeper connect success");
    }

    /**
     * 创建节点，如果节点存在就会抛出 NodeExistsException
     * @param path
     * @param data
     * @return
     */
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

    /**
     * 当节点不存在时创建节点 如果存在直接返回true
     * @param path
     * @param data
     * @return
     */
    public boolean createNodeIfNotExist(String path,String data) {
        if(isExist(path))
            return true;
        return createNode(path, data);
    }

    /**
     * 判断节点是否存在
     * @param path
     * @return
     */
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


    /**
     * 获取path下所有节点
     * @param path
     * @return
     */
    public List<String> getChildren(String path) {
        List<String> list = null;
        try {
            list = client.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取节点数据
     * @param path
     * @return
     */
    public String getNodeData(String path) {
        try {
            byte[] data = client.getData().forPath(path);
            return new String(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 更新节点数据
     * @param path
     * @param newValue
     * @return
     */
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

    /**
     * 删除节点 如果节点不存在抛出 NoNodeException
     * @param path
     * @return
     */
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
     * 删除节点(包括子节点) 如果存在删除，否则直接返回true
     * @param path
     * @return
     */
    public boolean deleteNodeIfExist(String path){
        if(isExist(path))
            return deleteChildrenIfNeededNode(path);
        return true;
    }

    /**
     * 递归删除子节点(包括目录下的节点)
     * @param path
     * @return
     */
    public boolean deleteChildrenIfNeededNode(String path) {
        try {
            client.delete()
                    .deletingChildrenIfNeeded()
                    .forPath(path);
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
        try {
            NodeCache nodeCache = new NodeCache(client, path, false);
            nodeCache.getListenable().addListener(nodeCacheListener);
            nodeCache.start(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerWatcherTreeChanged(String path, TreeCacheListener treeCacheListener){
        try {
            TreeCache treeCache = new TreeCache(client, path);
            treeCache.getListenable().addListener(treeCacheListener);
            treeCache.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 关闭连接
     * @throws InterruptedException
     */
    public void closeConnection() throws InterruptedException {
        if(client != null)
            client.close();
    }
}
