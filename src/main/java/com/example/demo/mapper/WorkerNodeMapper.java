package com.example.demo.mapper;
/*
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserve.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.Storage;
import com.example.demo.model.WorkerNode;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * DAO for M_WORKER_NODE
 *
 * @author yutianbao
 */
@Repository
public interface WorkerNodeMapper extends BaseMapper<WorkerNode>  {

    @Select("SELECT " +
            " ID," +
            " HOST_NAME," +
            " PORT," +
            " TYPE," +
            " LAUNCH_DATE," +
            " MODIFIED," +
            " CREATED" +
            " FROM" +
            " WORKER_NODE" +
            " WHERE" +
            " HOST_NAME = #{host,jdbcType=VARCHAR} AND PORT = #{port,jdbcType=VARCHAR} limit 1")
    WorkerNode getWorkerNodeByHostPort(@Param("host") String host, @Param("port") String port);


    @Insert("INSERT INTO WORKER_NODE" +
            "(HOST_NAME," +
            "PORT," +
            "TYPE," +
            "LAUNCH_DATE," +
            "MODIFIED," +
            "CREATED)" +
            "VALUES (" +
            "#{hostName}," +
            "#{port}," +
            "#{type}," +
            "#{launchDate}," +
            "NOW()," +
            "NOW())")
    @Options(useGeneratedKeys=true,keyProperty = "id", keyColumn = "id")
    void addWorkerNode(WorkerNode workerNodeEntity);

}
