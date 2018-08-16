package cn.qhb.haiv.core.util;

import java.util.HashMap;
import java.util.Map;


public class FlowConstants {
	
	@SuppressWarnings("rawtypes")
	public static Map<Object, Class> FIELD_TYPE_MAP = new HashMap<Object, Class>();
	static {
		FIELD_TYPE_MAP.put("java.lang.String", String.class);
		FIELD_TYPE_MAP.put("java.lang.Long", Long.class);
		FIELD_TYPE_MAP.put("java.lang.Integer", Integer.class);
		FIELD_TYPE_MAP.put("java.lang.Short", Short.class);
		FIELD_TYPE_MAP.put("java.lang.Float", Float.class);
		FIELD_TYPE_MAP.put("java.lang.Double", Double.class);
		FIELD_TYPE_MAP.put("java.util.Date", java.util.Date.class);

		FIELD_TYPE_MAP.put("String", String.class);
		FIELD_TYPE_MAP.put("Long", Long.class);
		FIELD_TYPE_MAP.put("Integer", Integer.class);
		FIELD_TYPE_MAP.put("Short", Short.class);
		FIELD_TYPE_MAP.put("Float", Float.class);
		FIELD_TYPE_MAP.put("Double", Double.class);
		FIELD_TYPE_MAP.put("Date", java.util.Date.class);

		// FIELD_TYPE_MAP.put("String", java.lang.String.class);
		FIELD_TYPE_MAP.put("long", Long.class);
		FIELD_TYPE_MAP.put("int", Integer.class);
		FIELD_TYPE_MAP.put("short", Short.class);
		FIELD_TYPE_MAP.put("float", Float.class);
		FIELD_TYPE_MAP.put("double", Double.class);

		FIELD_TYPE_MAP.put("tinyint", Integer.class);
		FIELD_TYPE_MAP.put("smallint", Integer.class);
		FIELD_TYPE_MAP.put("mediumint", Integer.class);
		FIELD_TYPE_MAP.put("int", Integer.class);
		FIELD_TYPE_MAP.put("bigint", Long.class);

		FIELD_TYPE_MAP.put("float", Float.class);
		FIELD_TYPE_MAP.put("double", Double.class);

		FIELD_TYPE_MAP.put("char", String.class);
		FIELD_TYPE_MAP.put("varchar", String.class);
		FIELD_TYPE_MAP.put("tinytext ", String.class);
		FIELD_TYPE_MAP.put("text", String.class);
		FIELD_TYPE_MAP.put("mediumtext", String.class);
		FIELD_TYPE_MAP.put("longtext", String.class);

		FIELD_TYPE_MAP.put("clob", java.sql.Clob.class);
		FIELD_TYPE_MAP.put("blob", String.class);
		FIELD_TYPE_MAP.put("longblob", String.class);

		FIELD_TYPE_MAP.put("date", java.util.Date.class);
		FIELD_TYPE_MAP.put("time", java.util.Date.class);
		FIELD_TYPE_MAP.put("datetime", java.util.Date.class);
		FIELD_TYPE_MAP.put("timestamp", java.util.Date.class);

		FIELD_TYPE_MAP.put("TINYINT", Integer.class);
		FIELD_TYPE_MAP.put("SMALLINT", Integer.class);
		FIELD_TYPE_MAP.put("MEDIUMINT", Integer.class);
		FIELD_TYPE_MAP.put("INT", Integer.class);
		FIELD_TYPE_MAP.put("BIGINT", Long.class);

		FIELD_TYPE_MAP.put("FLOAT", Float.class);
		FIELD_TYPE_MAP.put("DOUBLE", Double.class);

		FIELD_TYPE_MAP.put("CHAR", String.class);
		FIELD_TYPE_MAP.put("VARCHAR", String.class);
		FIELD_TYPE_MAP.put("TINYTEXT ", String.class);
		FIELD_TYPE_MAP.put("TEXT", String.class);
		FIELD_TYPE_MAP.put("MEDIUMTEXT", String.class);
		FIELD_TYPE_MAP.put("LONGTEXT", String.class);

		FIELD_TYPE_MAP.put("CLOB", java.sql.Clob.class);
		FIELD_TYPE_MAP.put("BLOB", String.class);
		FIELD_TYPE_MAP.put("LONGBLOB", String.class);

		FIELD_TYPE_MAP.put("DATE", java.util.Date.class);
		FIELD_TYPE_MAP.put("TIME", java.util.Date.class);
		FIELD_TYPE_MAP.put("DATETIME", java.util.Date.class);
		FIELD_TYPE_MAP.put("TIMESTAMP", java.util.Date.class);

		FIELD_TYPE_MAP.put("decimal", Double.class);
		FIELD_TYPE_MAP.put("DECIMAL", Double.class);

	}

	
	public final static String SIGN_VOTE_TYPE = "signVoteType";
	
	public final static String TO_PARENT_PATH = "toParentPath";
	
	public final static String DUE_DATE = "dueDate";
}
