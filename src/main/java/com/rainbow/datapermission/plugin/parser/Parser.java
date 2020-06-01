package com.rainbow.datapermission.plugin.parser;


import java.util.Map;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 * @description
 */
public interface Parser {
    String parse(String sql, final Map<String, String> params) throws Exception;
}
