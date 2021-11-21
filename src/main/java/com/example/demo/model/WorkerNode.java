package com.example.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@TableName(value = "t_storage")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WorkerNode extends Model<WorkerNode> implements Serializable {

    private static final long serialVersionUID = 55711502851875007L;

    /**
     * auto increment id
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * host name
     */
    @TableField("HOST_NAME")
    private String hostName;

    /**
     * port
     */
    @TableField("PORT")
    private String port;

    /**
     * node type: ACTUAL or CONTAINER
     */
    @TableField("TYPE")
    private Integer type;

    /**
     * launch date
     */
    @TableField("LAUNCH_DATE")
    private LocalDate launchDate;

    /**
     * modified time
     */
    @TableField("MODIFIED")
    private LocalDateTime modified;

    /**
     * created time
     */
    @TableField("CREATED")
    private LocalDateTime created;


}
