package com.example.demo.service.impl;

import com.baidu.fsg.uid.UidGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.WorkerNodeMapper;
import com.example.demo.model.WorkerNode;
import com.example.demo.service.IWorkerNodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WorkerNodeServiceImpl extends ServiceImpl<WorkerNodeMapper, WorkerNode> implements IWorkerNodeService {

    @Resource
    private UidGenerator uidGenerator;

    @Override
    public long genUid() {
        return uidGenerator.getUID();
    }

}

