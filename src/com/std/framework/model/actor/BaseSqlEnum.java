package com.std.framework.model.actor;

public enum BaseSqlEnum {

    SAVE(1), DELETE(2), UPDATE(3), GET(4), LIST_All(5), COUNT(6);

    // 定义私有变量
    private int nCode;

    // 构造函数，枚举类型只能为私有
    BaseSqlEnum(int _nCode) {
        this.nCode = _nCode;
    }

    @Override
    public String toString() {
        String rtnStr = null;
        switch (nCode) {
            case 1:
                rtnStr = "SAVE";
                break;
            case 2:
                rtnStr = "DELETE";
                break;
            case 3:
                rtnStr = "UPDATE";
                break;
            case 4:
                rtnStr = "GET";
                break;
            case 5:
                rtnStr = "LIST_All";
                break;
            case 6:
                rtnStr = "COUNT";
                break;
            default:
                break;
        }
        return rtnStr;
    }
}