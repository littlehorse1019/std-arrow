package com.std.framework.model.actor;

public enum BaseSqlEnum {

	Save(1), Delete(2), Update(3), FindByPK(4), FindAll(5);

	// 定义私有变量
	private int nCode;

	// 构造函数，枚举类型只能为私有
	private BaseSqlEnum(int _nCode) {
		this.nCode = _nCode;
	}

	@Override
	public String toString() {
		String rtnStr = null;
		switch (nCode) {
		case 1:
			rtnStr = "Save";
			break;
		case 2:
			rtnStr = "Delete";
			break;
		case 3:
			rtnStr = "Update";
			break;
		case 4:
			rtnStr = "FindByPK";
			break;
		case 5:
			rtnStr = "FindAll";
			break;
		}
		return rtnStr;
	}
}