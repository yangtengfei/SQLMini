package com.tengfeiyang.supply;

import java.util.List;

public class SchduleAlgorithm {
	/**
	 * LRU管理器，管理缓存，通过控制point指针，在各种情况下插入数据
	 * 
	 * @param point
	 * @param pageId
	 * @return 
	 */
	public static List<Integer> LRUManager(List<Integer> bufferList, int point, int pageId) {
		for (int i = point; i > 0; i--) {
			bufferList.set(i, bufferList.get(i - 1));
		}
		bufferList.set(0, pageId);
		return bufferList;
	}
}
