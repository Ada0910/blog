package com.ada.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @ClassName:Like
 * @author: Ada
 * @date 2020/03/02 15:18
 * @Description:
 */
@Setter
@Getter
@ToString
public class Like {
    public Integer likeId;

    public String likeUserIp;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date likeCreateTime;

    public Integer likeBlogId;

}
