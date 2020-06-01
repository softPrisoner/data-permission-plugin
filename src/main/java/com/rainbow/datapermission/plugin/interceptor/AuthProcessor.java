package com.rainbow.datapermission.plugin.interceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 * @description AuthProcessor
 * @date 2020-06-01
 */
public class AuthProcessor implements Auth {
    @Override
    public Map<String, String> auth(Map<String, String> params) {
        return new HashMap<>();
    }
}
