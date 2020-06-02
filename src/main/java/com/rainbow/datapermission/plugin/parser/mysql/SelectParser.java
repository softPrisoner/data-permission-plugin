package com.rainbow.datapermission.plugin.parser.mysql;


import com.rainbow.datapermission.plugin.parser.Parser;

import java.util.Map;

import static com.rainbow.datapermission.plugin.utils.SQLUtil.process;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public class SelectParser implements Parser {
    private final ParserDelegate delegate;

    public SelectParser(ParserDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public String parse(String sql, final Map<String, String> params, String[] ignoreChildren, String[] ignoreParams)
            throws Exception {
        return process(delegate.divide(sql, params, ignoreChildren, ignoreParams));
    }
}
