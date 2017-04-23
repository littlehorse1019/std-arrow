package com.std.framework.annotation;

/**
 * 枚举类型
 * 用于配合{@link Advisor}注解使用
 */
public enum PointCut {

    Before {
        public String toString () {
            return "Before";
        }
    },
    After {
        public String toString () {
            return "After";
        }
    }

}
