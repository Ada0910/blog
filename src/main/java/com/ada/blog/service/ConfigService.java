package com.ada.blog.service;

import java.util.Map;

/**
 * @author Ada
 * @ClassName :ConfigService
 * @date 2019/6/24 23:20
 * @Description:
 */
public interface ConfigService {

    Map<String, String> getAllConfigs();

    int updateConfig(String configName, String configValue);
}

