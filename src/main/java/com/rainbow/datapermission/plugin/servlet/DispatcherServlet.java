package com.rainbow.datapermission.plugin.servlet;

import com.alibaba.druid.sql.SQLUtils;
import com.rainbow.datapermission.plugin.exception.SQLParserNotFoundException;
import com.rainbow.datapermission.plugin.exception.UnRegisterException;
import com.rainbow.datapermission.plugin.parser.Parser;
import com.rainbow.datapermission.plugin.register.Register;
import com.rainbow.datapermission.plugin.request.SQLRequest;
import com.rainbow.datapermission.plugin.utils.FormatUtil;
import com.rainbow.datapermission.plugin.utils.SQLUtil;

import java.util.Map;
import java.util.Objects;

import static com.alibaba.druid.util.JdbcConstants.MYSQL;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public class DispatcherServlet implements Servlet {

    private final Register<Parser> parserRegister;

    public DispatcherServlet(Register<Parser> parserRegister) {
        this.parserRegister = parserRegister;
    }

    @Override
    public String dispatch(SQLRequest request) throws Exception {
        if (Objects.isNull(request)) {
            throw new NullPointerException("SQL request is null");
        }

        String sql = request.getSql();
        Map<String, String> params = request.getParams();
        String[] ignoreChildren = request.getIgnoreChildren();
        String[] ignoreParams = request.getIgnoreParams();

        try {
            SQLUtils.parseStatements(sql, MYSQL);
        } catch (Exception e) {
            throw new UnRegisterException("Dispatch sql syntax is illegal");
        }

        String opsKey = SQLUtil.opsKey(sql);

        Parser parser = parserRegister.lookFor(opsKey);

        if (Objects.isNull(parser)) {
            throw new SQLParserNotFoundException("No parser found");
        }

        return parser.parse(sql, params, FormatUtil.formatIgnoreChildren(ignoreChildren),
                FormatUtil.formatIgnoreParams(ignoreParams));
    }
}
