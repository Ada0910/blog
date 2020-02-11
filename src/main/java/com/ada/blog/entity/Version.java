package com.ada.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Ada
 * @ClassName :Version
 * @date 2020/2/11 22:32
 * @Description:
 */
@Getter
@Setter
@ToString
public class Version {

    private Integer versionId;

    private String versionSerialNum;

    private Integer versionType;

    private String versionDescription;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date versionCreateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date versionUpdateTime;


}
