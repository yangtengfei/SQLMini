package com.tengefeiyang.managerTest;

import com.tengfeiyang.entities.Page;
import com.tengfeiyang.manager.FileManager;

public class TestFileManager {
	public static void main(String[] args) {
			readFile(0);
	}
	
	// 测试文件读取
	public static void readFile(int pageId){
		Page page = FileManager.readPage(pageId);
		System.out.println(page);
	}
	
	
}
