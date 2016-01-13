
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
