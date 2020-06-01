package com.rainbow.datapermission.plugin.servlet;

import com.rainbow.datapermission.plugin.request.SQLRequest;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 * @description Servlet
 */
public interface Servlet {
    public String dispatch(SQLRequest request) throws Exception;
}
