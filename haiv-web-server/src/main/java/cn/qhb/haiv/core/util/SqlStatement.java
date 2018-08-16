package cn.qhb.haiv.core.util;

import cn.qhb.haiv.core.exception.SqlStatementException;

import java.util.HashMap;
import java.util.Map;

/**
 * sql语句操作串
 */
public class SqlStatement {

    /**
     * where语句
     */
    public static final String WHERE = " where ";
    /**
     * from语句
     */
    public static final String FROM = " from ";

    /**
     * sql语句集
     */
    public enum Statement {
        /**
         * 插入
         */
        insert,
        /**
         * 更新
         */
        update,
        /**
         * 查询
         */
        select,
        /**
         *删除
         */
        delete;
        /**
         * 语句集映射
         */
        public static Map<Statement, String> statementMap = new HashMap<>();

        static {
            statementMap.put(Statement.insert, "insert into");
            statementMap.put(Statement.update, "update");
            statementMap.put(Statement.select, "select");
            statementMap.put(Statement.delete,"delete");
        }

        /**
         * 获取sql语句串
         *
         * @param statement
         * @return
         * @throws SqlStatementException
         */
        public static String value(Statement statement) throws SqlStatementException {
            String sqlSta = statementMap.get(statement);
            if (sqlSta == null || sqlSta.trim().equals("")) {
                throw new SqlStatementException("无效的sql语句操作！");
            }
            return sqlSta;
        }
    }

}
