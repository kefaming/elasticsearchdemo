package com.example.demo.controller;

import com.baidu.fsg.uid.UidGenerator;
import com.example.demo.dto.CommodityDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Controller
public class UidGeneratorController {

    @Resource
    private UidGenerator defaultUidGenerator;

    /**
     * http://localhost:19222/uid
     */
    @RequestMapping("/uid")
    public String uid(HashMap<String, Object> map) {
        // Generate UID
        long uid = defaultUidGenerator.getUID();

        // Parse UID into [Timestamp, WorkerId, Sequence]
        // {"UID":"450795408770","timestamp":"2019-02-20 14:55:39","workerId":"27","sequence":"2"}
        System.out.println(uid);
        map.put("uid", uid);
        return "uid-genetator";
    }


    /**
     * http://localhost:19222/getuid
     */
    @RequestMapping("/getuid")
    public String getuid(HashMap<String, Object> map, CommodityDTO commodityDTO) {
        // 创建一个可缓存线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10000; i++) {
//            try {
//                // sleep可明显看到使用的是线程池里面以前的线程，没有创建新的线程
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            cachedThreadPool.execute(new Runnable() {
                public void run() {
                    // 打印正在执行的缓存线程信息
//                    System.out.println(Thread.currentThread().getName() + "正在被执行");

                    long uid = defaultUidGenerator.getUID();

                    // Parse UID into [Timestamp, WorkerId, Sequence]
                    // {"UID":"450795408770","timestamp":"2019-02-20 14:55:39","workerId":"27","sequence":"2"}
                    System.out.println(String.valueOf(uid));

//                        Thread.sleep(1000);
                }
            });
        }
        return "hello";
    }

}
