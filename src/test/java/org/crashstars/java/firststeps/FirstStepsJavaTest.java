package org.crashstars.java.firststeps;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by navidad on 7/10/15.
 */
public class FirstStepsJavaTest {

    private static final Logger LOG = LoggerFactory.getLogger(FirstStepsJavaTest.class);

    @Test
    public void testMock() {
        LOG.debug("JAVA: TestMock begin");
        //Strict checking
        Assert.assertNull(null);
    }

    @Test
    public void testUserFileExits() {
        LOG.debug("JAVA-TEST: TestUserFileExits begin");
        String path = getClass().getResource("/data/users.csv").getPath();
        boolean exists = new File(path).exists();
        //Strict checking
        Assert.assertTrue(exists);
    }
}
