package com.std.framework.annotation;

public enum PointCut {

	Before {
		public String toString(){
			return "Before";
		}
	},
	After{
		public String toString(){
			return "After";
		}
	}
	
}
