package cn.qhb.haiv.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * sql过滤器
 */
public class SqlFilter {

    /**
     * 目标键
     */
    private String key;
    /**
     * 目标值
     */
    private Object val;
    /**
     * 操作符的字符串表示
     */
    private String operatorStr;

    /**
     * 操作符
     */
    private Operator operator;

    /**
     * 是否有下一个
     */
    private boolean next;

    /**
     * 是否添加前括号
     */
    private boolean preBracket;

    /**
     * 是否添加后括号
     */
    private boolean afterBracket;

    /**
     * 条件连接符
     */
    private FilterLinkStr linkStr;

    /**
     * 操作符
     */
    public enum Operator {
        /**
         * 等于
         */
        eq,
        /**
         * 大于
         */
        gt,
        /**
         * 小于
         */
        lt,
        /**
         * 大于等于
         */
        ge,
        /**
         * 小于等于
         */
        le,
        /**
         * 模糊
         */
        like,
        /**
         * 范围
         */
        in;
        public static Map<Operator, String> operatorMap = new HashMap<>();

        static {
            operatorMap.put(Operator.eq, "=");
            operatorMap.put(Operator.ge, ">=");
            operatorMap.put(Operator.le, "<=");
            operatorMap.put(Operator.gt, ">");
            operatorMap.put(Operator.lt, "<");
            operatorMap.put(Operator.like, "like");
            operatorMap.put(Operator.in, "in");
        }
    }

    /**
     * 过滤器之间的连接符号
     */
    public enum FilterLinkStr {
        /**
         * and连接
         */
        and,
        /**
         * or连接
         */
        or,
        /**
         * 左连接
         */
        left_join,
        /**
         * 右连接
         */
        right_join;
        public static Map<FilterLinkStr, String> linkStrMap = new HashMap<>();

        static {
            linkStrMap.put(FilterLinkStr.and, "and");
            linkStrMap.put(FilterLinkStr.or, "or");
            linkStrMap.put(FilterLinkStr.left_join, "left join");
            linkStrMap.put(FilterLinkStr.right_join, "right join");
        }
    }

    /**
     * 默认过滤构造器
     *
     * @param preBracket   是否添加前括号
     * @param key          字段
     * @param operator     操作符
     * @param val          值
     * @param next         是否有下一个条件
     * @param linkStr      下一个条件的连接符（如果有下一个条件）
     * @param afterBracket 是否添加后括号
     * @throws Exception
     */
    public SqlFilter(boolean preBracket, String key, Operator operator, Object val, boolean next, FilterLinkStr linkStr, boolean afterBracket) throws Exception {
        /*Annotation annotation = field.getAnnotation(Column.class);
        if (annotation != null) {
            this.key = ((Column) annotation).name();
        } else {
            this.key = field.getName();
        }
        String fieldType = field.getGenericType().toString();
        String type = val.getClass().toString();
        if (!fieldType.trim().equals(type.trim())) {
            throw new TypeNotSameException("字段过滤器目标类型与给出的类型不一致！");
        }*/
        this.key = key.trim();
        this.preBracket = preBracket;
        this.val = val;
//        this.operator = operator;
        this.operatorStr = Operator.operatorMap.get(operator);
        this.next = next;
        if (this.next) {
            this.linkStr = linkStr;
        }
        this.afterBracket = afterBracket;
    }

    /**
     * 获取键
     *
     * @return
     */
    public String getKey() {
        return key;
    }

    /**
     * 获取值
     *
     * @return
     */
    public Object getVal() {
        return val;
    }

    /**
     * 获取当前操作符
     *
     * @return
     */
    public Operator getOperator() {
        return operator;
    }

    public String getOperatorStr() {
        return operatorStr;
    }

    public static void main(String... args) throws Exception {
//        SqlFilter sqlFilter = new SqlFilter(Device.class.getDeclaredField("name"), Operator.eq, "hello");
//        System.out.println("key: " + sqlFilter.getKey() + " value: " + sqlFilter.getVal());
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public FilterLinkStr getLinkStr() {
        return linkStr;
    }

    public void setLinkStr(FilterLinkStr linkStr) {
        this.linkStr = linkStr;
    }

    public boolean isPreBracket() {
        return preBracket;
    }

    public void setPreBracket(boolean preBracket) {
        this.preBracket = preBracket;
    }

    public boolean isAfterBracket() {
        return afterBracket;
    }

    public void setAfterBracket(boolean afterBracket) {
        this.afterBracket = afterBracket;
    }
}
