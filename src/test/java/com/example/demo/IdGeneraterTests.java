package com.example.demo;

import com.baidu.fsg.uid.UidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:uid/cached-uid-spring.xml" })
@SpringBootTest
@Slf4j
public class IdGeneraterTests {

    @Resource
    private UidGenerator defaultUidGenerator;

    @Resource
    private UidGenerator cachedUidGenerator;

    @Test
    public void testSerialGenerate() {
        // Generate UID
        long uid = defaultUidGenerator.getUID();

        // Parse UID into [Timestamp, WorkerId, Sequence]
        // {"UID":"450795408770","timestamp":"2019-02-20 14:55:39","workerId":"27","sequence":"2"}
        System.out.println(uid);
    }

    //    @Test
    public void testSerialGenerate2() {
        // Generate UID
        long uid = cachedUidGenerator.getUID();

        // Parse UID into [Timestamp, WorkerId, Sequence]
        // {"UID":"450795408770","timestamp":"2019-02-20 14:55:39","workerId":"27","sequence":"2"}
        System.out.println(cachedUidGenerator.parseUID(uid));
    }

}
