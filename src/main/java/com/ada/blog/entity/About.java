package com.ada.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Ada
 * @ClassName :About
 * @date 2019/8/9 22:50
 * @Description: 关于实体类
 */
@Getter
@Setter
@ToString
public class About {

    private Integer aboutId;

    private String aboutName;

    private Byte aboutType;

    private String aboutUrl;

    private String aboutDescription;

    private String aboutImage ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
