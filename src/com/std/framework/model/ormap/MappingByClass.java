package com.std.framework.model.ormap;

/**
 * @author Luox 驼峰规则，类->表 属性名映射
 */
public class MappingByClass implements MappingRule {

	/**
	 * 表名映射规则
	 */
	public String ObjMapTab(String table) {
		return "T_" + handleHumpString(table);
	}

	/**
	 * 序列映射规则
	 */
	public String ObjMapSeq(String sequence) {
		return "S_" + handleHumpString(sequence);
	}

	/**
	 * 字段映射规则
	 */
	public String ObjMapCol(String column) {
		return handleHumpString(column);
	}

	private String handleHumpString(String string) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			if (Character.isUpperCase(string.charAt(i)) && i > 0) {
				sb.append("_").append(string.charAt(i));
			} else {
				sb.append(string.charAt(i));
			}
		}
		return sb.toString();
	}

}
