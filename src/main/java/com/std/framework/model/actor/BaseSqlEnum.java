package com.std.framework.model.actor;

public enum BaseSqlEnum {

    Save(1), Remove(2), Update(3), FindByPK(4), FindAll(5);

    // ����˽�б���
    private int nCode;

    // ���캯����ö������ֻ��Ϊ˽��
    BaseSqlEnum (int _nCode) {
        this.nCode = _nCode;
    }

    @Override
    public String toString () {
        String rtnStr = null;
        switch (nCode) {
            case 1:
                rtnStr = "Save";
                break;
            case 2:
                rtnStr = "Remove";
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
