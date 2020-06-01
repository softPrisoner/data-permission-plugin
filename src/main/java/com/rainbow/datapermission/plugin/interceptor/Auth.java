package com.rainbow.datapermission.plugin.interceptor;

import java.util.Map;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 * @description Auth
 * @date 2020-06-01
 */
public interface Auth {
    Map<String, String> auth(Map<String, String> params);
}
