package com.rainbow.datapermission.plugin.register;

import com.rainbow.datapermission.plugin.concat.SQLConcat;
import com.rainbow.datapermission.plugin.exception.RegistryFailedException;
import com.rainbow.datapermission.plugin.exception.UnRegisterException;
import com.rainbow.datapermission.plugin.utils.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
abstract class AbstractSQLConcatRegister implements Register<SQLConcat> {
    protected static final Map<String, SQLConcat> SQL_CONCAT_MAP = new ConcurrentHashMap<>(16);

    @Override
    public void registry(String key, SQLConcat element) throws RegistryFailedException {
        if (StringUtils.isEmpty(key) || Objects.isNull(element)) {
            throw new RegistryFailedException("Registry failed.Key or element is null");
        }
        SQL_CONCAT_MAP.put(key, element);
    }

    @Override
    public SQLConcat lookFor(String type) throws Exception {
        if (SQL_CONCAT_MAP.size() == 0) {
            throw new UnRegisterException("UnRegister Exception:No concat has been registry");
        }
        return SQL_CONCAT_MAP.get(type);
    }


}
