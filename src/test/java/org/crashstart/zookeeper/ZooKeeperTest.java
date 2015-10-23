package org.crashstart.zookeeper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.test.TestingServer;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by anavidad on 21/10/15.
 */
public class ZooKeeperTest {

    TestingServer zkTestServer;
    private static CuratorFramework cli;

    @Before
    public void startZookeeper() throws Exception {
        zkTestServer = new TestingServer(2181);
        cli = CuratorFrameworkFactory.newClient(zkTestServer.getConnectString(), new RetryOneTime(2000));
    }

    @After
    public void stopZookeeper() throws IOException {
        cli.close();
        zkTestServer.stop();
    }

    @Test
    public void testInsertZNode() throws Exception {
        cli.start();
        String testValue = "testvalue";
        cli.create().forPath("/a1", testValue.getBytes());
        Stat stat = cli.checkExists().forPath("/a1");
        Assert.assertNotNull(stat);
        byte[] bytes = cli.getData().forPath("/a1");
        String testValueString = StringUtils.toEncodedString(bytes, StandardCharsets.UTF_8);
        Assert.assertEquals(testValue, testValueString);
    }

    @Test
    public void testIsPosibleGetPreviousVersion() throws Exception {
        cli.start();
        String testValue = "testvalue";
        cli.create().forPath("/a1", testValue.getBytes());
        Stat stat = cli.checkExists().forPath("/a1");
        Assert.assertNotNull(stat);
        byte[] bytes = cli.getData().forPath("/a1");
        String testValueString = StringUtils.toEncodedString(bytes, StandardCharsets.UTF_8);
        Assert.assertEquals(testValue, testValueString);
    }
}
