package com.ada.blog.service.impl;

import com.ada.blog.entity.Config;
import com.ada.blog.mapper.ConfigMapper;
import com.ada.blog.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Ada
 * @ClassName :ConfigServiceImpl
 * @date 2019/6/24 23:20
 * @Description:
 */

@Service("configService")
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;


    public static final String websiteName = "Ada个人网站";
    public static final String websiteDescription = "一个分享知识的Java学习网站";
    public static final String websiteLogo = "/admin/img/logo.png";
    public static final String websiteIcon = "/admin/img/favicon.png";

    public static final String yourAvatar = "/admin/dist/user.png";
    public static final String yourEmail = "1355948107@qq.com";
    public static final String yourName = "Ada";

    public static final String footerAbout = "Ada的个人网站";
    public static final String footerICP = "粤ICP备 xxxxxx-x号";
    public static final String footerCopyRight = "@2019 Ada";
    public static final String footerPoweredBy = "个人网站";
    public static final String footerPoweredByURL = "##";

    /**
     * 查询配置
     */
    @Override
    public Map<String, String> getAllConfigs() {
        /**获取所有的map并封装为map*/
        List<Config> configList = configMapper.selectAll();
        /**list转化为map*/
        Map<String, String> map = configList.stream().collect(Collectors.toMap(Config::getConfigName, Config::getConfigValue));
        for (Map.Entry<String, String> config : map.entrySet()) {
            if ("websiteName".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(websiteName);
            }
            if ("websiteDescription".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(websiteDescription);
            }
            if ("websiteLogo".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(websiteLogo);
            }
            if ("websiteIcon".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(websiteIcon);
            }
            if ("yourAvatar".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(yourAvatar);
            }
            if ("yourEmail".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(yourEmail);
            }
            if ("yourName".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(yourName);
            }
            if ("footerAbout".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(footerAbout);
            }
            if ("footerICP".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(footerICP);
            }
            if ("footerCopyRight".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(footerCopyRight);
            }
            if ("footerPoweredBy".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(footerPoweredBy);
            }
            if ("footerPoweredByURL".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(footerPoweredByURL);
            }
        }
        return map;
    }


    /**
     * 更新配置
     */
    @Override
    public int updateConfig(String configName, String configValue) {
        Config config = configMapper.selectByPrimaryKey(configName);
        if (config != null) {
            config.setConfigValue(configValue);
            config.setUpdateTime(new Date());
            return configMapper.updateByPrimaryKey(config);
        }
        return 0;
    }
}
