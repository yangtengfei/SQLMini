import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件管理模块，从trace文件中读取文件
 * 
 * @author yangtf
 *
 */
public class FileManager {

	private static final int pageNum = 500000;
	
	static List<Page> pages = new ArrayList<Page>();
	/**
	 * 打开文件
	 * @param fileName
	 * @return
	 */
	public static void openFile() {
		String fileUrl = "C:\\Users\\yangtf\\Desktop\\folder\\HomeWork\\高级数据库\\data-5w-50w-zipf.txt";
		Page page;
		try {
			FileReader fileReader = new FileReader(fileUrl);
			BufferedReader br = new BufferedReader(fileReader);
			String linePage = null;
			for (int i = 0; i < pageNum; i++) {
				linePage = br.readLine();
				page = new Page();
				String[] str = linePage.split(",");
				page.setOperator(Integer.parseInt(str[0]));
				page.setPageId(Integer.parseInt(str[1]));
				pages.add(page);
			}
			closeFile(fileReader, br);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 实际的流程应该是在openFile的时候，将PAGE读取到某一个页面数组中，在readPage()中，读取数组中的页
	 * @param br 
	 * @param fileReader 
	 * 
	 * @return
	 */
	private static boolean closeFile(FileReader fr, BufferedReader br) {
		try {
			fr.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 读取页面
	 * 
	 * @param pagePosition
	 *            文件读取到第几个page了
	 * @return page
	 */
	public static Page readPage(int pagePosition) {
		Page page = pages.get(pagePosition);
		return page;
	}

	/**
	 * 将页面写回
	 * 
	 * @param pageId
	 * @return
	 */
	public static boolean pageWrite(int pageId) {
		Page page = new Page();
		page.setPageId(pageId);
		page.setOperator(0);
		return true;
	}
}
