package com.rainbow.datapermission.plugin.parser;


import java.util.Map;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public interface Parser {
    String parse(String sql, final Map<String, String> params, String[] ignoreChildren, String[] ignoreParams) throws Exception;
}
