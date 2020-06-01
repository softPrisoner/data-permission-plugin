package com.rainbow.datapermission.plugin.interceptor;

import com.rainbow.datapermission.plugin.annotations.DataPermission;
import com.rainbow.datapermission.plugin.concat.SQLConcat;
import com.rainbow.datapermission.plugin.enums.SQLOpsEnum;
import com.rainbow.datapermission.plugin.example.ListSQLConcat;
import com.rainbow.datapermission.plugin.example.MyExecutor;
import com.rainbow.datapermission.plugin.example.enums.ConstraintTypeEnum;
import com.rainbow.datapermission.plugin.parser.Parser;
import com.rainbow.datapermission.plugin.parser.mysql.ParserDelegate;
import com.rainbow.datapermission.plugin.parser.mysql.SelectParser;
import com.rainbow.datapermission.plugin.register.BaseConcatRegister;
import com.rainbow.datapermission.plugin.register.BaseParserRegister;
import com.rainbow.datapermission.plugin.register.Register;
import com.rainbow.datapermission.plugin.request.SQLRequest;
import com.rainbow.datapermission.plugin.servlet.DispatcherServlet;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * 数据权限拦截器
 *
 * @author tony
 * @description DataPermissionInterceptor
 * @copyright rainbow
 * @date 2020/03/27
 */
@Intercepts({@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
        , @Signature(type = Executor.class, method = "query", args = {
        MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
)})
@Component
public class DataPermissionMysqlInterceptor implements Interceptor {
    private final Auth auth;
    private final Map<String, String> authParams;


    public DataPermissionMysqlInterceptor(Auth auth, Map<String, String> authParams) {
        this.auth = auth;
        this.authParams = authParams;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        // 获取方法上的数据权限注解，如果没有注解，则直接通过
        DataPermission annotation = getPermissionByDelegate(mappedStatement);
        if (Objects.isNull(annotation)) {
            return invocation.proceed();
        }
        String[] ignoreChildren = annotation.ignoreChildren();
        String[] ignoreParams = annotation.ignoreParams();

        Map<String, String> params = this.auth.auth(authParams);
        BoundSql boundSql;

        if (args.length == 4) {
            boundSql = mappedStatement.getBoundSql(args[1]);
        } else {
            boundSql = (BoundSql) args[5];
        }

        String originSql = boundSql.getSql();
        String sql = "SELECT * FROM table s INNER join user on table.uid=user.id where s.name in (select name from table_b) group by id having user.age>10";
        com.rainbow.datapermission.plugin.executor.Executor executor = new MyExecutor();

        Register<SQLConcat> concatRegister = BaseConcatRegister.getInstance();

        concatRegister.registry(ConstraintTypeEnum.LIST.name, new ListSQLConcat());

        ParserDelegate parserDelegate = new ParserDelegate(concatRegister, executor);
        SelectParser selectParser = new SelectParser(parserDelegate);
        Register<Parser> parserRegister = BaseParserRegister.getInstance();
        parserRegister.registry(SQLOpsEnum.SELECT.key, selectParser);

        DispatcherServlet servlet = new DispatcherServlet(parserRegister);
        String result = servlet.dispatch(new SQLRequest(sql, params, ignoreChildren, ignoreParams));
        System.out.println("result = " + result);
        String newSql = result;
        if ("".equals(newSql.trim())) {
            return invocation.proceed();
        }
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, newSql);
        return invocation.proceed();
    }

    /**
     * 获取数据权限注解信息
     *
     * @param mappedStatement MappedStatement
     * @return DataPermission
     */
    private DataPermission getPermissionByDelegate(MappedStatement mappedStatement) {
        DataPermission dataAuth = null;
        try {
            String id = mappedStatement.getId();
            String className = id.substring(0, id.lastIndexOf("."));
            String methodName = id.substring(id.lastIndexOf(".") + 1, id.length());
            final Class<?> cls = Class.forName(className);
            final Method[] method = cls.getMethods();

            for (Method me : method) {
                if (me.getName().equals(methodName) && me.isAnnotationPresent(DataPermission.class)) {
                    dataAuth = me.getAnnotation(DataPermission.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataAuth;
    }
}