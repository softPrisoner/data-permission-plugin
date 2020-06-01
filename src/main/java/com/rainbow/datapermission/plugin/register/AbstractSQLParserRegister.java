package com.rainbow.datapermission.plugin.register;

import com.rainbow.datapermission.plugin.exception.RegistryFailedException;
import com.rainbow.datapermission.plugin.exception.UnRegisterException;
import com.rainbow.datapermission.plugin.parser.Parser;
import com.rainbow.datapermission.plugin.utils.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
abstract class AbstractSQLParserRegister implements Register<Parser> {

    protected static final Map<String, Parser> PARSER_MAP = new ConcurrentHashMap<>();

    @Override
    public void registry(String key, Parser element) throws Exception {
        if (StringUtils.isEmpty(key) || Objects.isNull(element)) {
            throw new RegistryFailedException("Registry failed.Key or element is null");
        }
        PARSER_MAP.put(key, element);
    }

    @Override
    public Parser lookFor(String opsKey) throws Exception {
        if (PARSER_MAP.size() == 0) {
            throw new UnRegisterException("UnRegister Exception:No parser has been registry");
        }

        return PARSER_MAP.get(opsKey);
    }

}
