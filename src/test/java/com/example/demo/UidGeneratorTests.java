//package com.example.demo;
//
//import com.github.kefaming.uid.UidGenerator;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//
//import javax.annotation.Resource;
//import java.util.HashSet;
//import java.util.Set;
//
//@SpringBootTest
//@Slf4j
//public class UidGeneratorTests {
//
//    @Resource
//    private UidGenerator defaultUidGenerator;
////
////    @Resource
////    private UidGenerator cachedUidGenerator;
//
//    private static final int SIZE = 100; // 10w
//
//
//    @Test
//    public void testSerialGenerate() {
//        // Generate UID
//        long uid = defaultUidGenerator.getUID();
//
//        // Parse UID into [Timestamp, WorkerId, Sequence]
//        // {"UID":"450795408770","timestamp":"2019-02-20 14:55:39","workerId":"27","sequence":"2"}
//        System.out.println(uid);
//
////
////        Set<Long> uidSet = new HashSet<>(SIZE);
////        for (int i = 0; i < SIZE; i++) {
////            long uid = defaultUidGenerator.getUID();
////
////            // Parse UID into [Timestamp, WorkerId, Sequence]
////            // {"UID":"450795408770","timestamp":"2019-02-20 14:55:39","workerId":"27","sequence":"2"}
////            log.debug(String.valueOf(uid));
////        }
//
//
//
//    }
//
////    @Test
////    public void testSerialGenerate2() {
////        // Generate UID
////        long uid = cachedUidGenerator.getUID();
////
////        // Parse UID into [Timestamp, WorkerId, Sequence]
////        // {"UID":"450795408770","timestamp":"2019-02-20 14:55:39","workerId":"27","sequence":"2"}
////        System.out.println(cachedUidGenerator.parseUID(uid));
////    }
//
//}
