package com.example.demo.controller;

import com.example.demo.dto.CommodityDTO;
import com.github.kefaming.uid.UidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;

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

}
