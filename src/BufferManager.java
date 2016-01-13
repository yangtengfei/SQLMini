import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BufferManager {
	private static final int pageNum = 500000;

	static int ioNum = 0;
	static long time = System.currentTimeMillis();
	static int hitTimes = 0;

	static int pageSize = 4;
	static int initBufferSize = 1024;
	static int currentBufferSize = 1024;

	static List<Integer> bufferList = new ArrayList<Integer>(); // pageId代表page 
	static Map<Integer, BCB> bCBMap = new HashMap<Integer, BCB>();

	public static void main(String[] args) {
		for (int i = 0; i < (initBufferSize/pageSize); i++) { // bufferList >= 缓冲区中页面数
			bufferList.add(0);
		}

		Page page = null;
		// 读取页面
		try {
			FileManager.openFile();
			for (int i = 0; i < pageNum; i++) {
//				System.out.println("=====" + i + "=====");
				
				page = FileManager.readPage(i);
				// 判断页面是否存在于缓存中(命中)
				if (bufferList.contains(page.getPageId())) {
//					System.out.println("----------------------------------");
					hitTimes++;
					updateBCB(page);
					updateLRUManager(page.getPageId());
					
				} else if (0 < currentBufferSize) { // 缓冲区未满
//					System.out.println("----------------------------------");
					ioNum++;
					System.out.println("currentBufferSize: " + currentBufferSize);
					insertIntoLRUManager(page.getPageId());
					currentBufferSize = currentBufferSize - pageSize;
					insertBCB(page);
					
				} else {
//					System.out.println("----------------------------------");
					ioNum++;
					insertBCB(page);
					BCB removedBCB = getLatestPage(page.getPageId());
					if (removedBCB.isDirty()) {
						FileManager.pageWrite(removedBCB.getPageID());
						ioNum++;
					}

					bCBMap.remove(removedBCB.getPageID());
				}
			}
		} finally {
			System.out.println();System.out.println();
			System.out.println("ioNum: " + ioNum);
			System.out.println("hitTimes: " + hitTimes);
			System.out.println("time: " + (System.currentTimeMillis() - time) + "ms");
		}

	}

	/**
	 * 将新页面插入LRUManager
	 * 
	 * @param pageId
	 */
	private static void insertIntoLRUManager(int pageId) {
		int point = (initBufferSize-currentBufferSize)/4; // 已存放数据的位置
//		System.out.println(point + "==insertIntoLRUManager==");
		LRUManager(point, pageId);
	}

	/**
	 * 将命中的页面放到首位
	 * 
	 * @param pageId
	 */
	private static void updateLRUManager(int pageId) {
		int point = bufferList.indexOf(pageId);
//		System.out.println(point + "==updateLRUManager==");
		LRUManager(point, pageId);
	}

	/**
	 * 插入新页面，获取旧页面
	 * 
	 * @param pageId
	 * @return
	 */
	private static BCB getLatestPage(int pageId) {
		int latestPageId = bufferList.get(bufferList.size() - 1);
		BCB bcb = bCBMap.get(latestPageId);
		
//		System.out.println(bufferList.size() - 1 + "==getLatestPage==");
		LRUManager(bufferList.size() - 1, pageId);

		return bcb;
	}

	/**
	 * LRU管理器，通过控制point指针，在各种情况下插入数据
	 * 
	 * @param point
	 * @param pageId
	 */
	private static void LRUManager(int point, int pageId) {
		for (int i = point; i > 0; i--) {
			bufferList.set(i, bufferList.get(i - 1));
		}
		bufferList.set(0, pageId);
//		System.out.println(bufferList.toString());
	}

	/**
	 * 在bCBMap中插入新的BCB
	 * 
	 * @param page
	 */
	private static void insertBCB(Page page) {
		BCB bcb = new BCB();
		bcb.setPageID(page.getPageId());
		bcb.setCount(0);
		bcb.setDirty(false);
		if (page.getOperator() == 1) {
			bcb.setDirty(true);
		}
		bCBMap.put(page.getPageId(), bcb);
	}

	/**
	 * 修改对应的BCB
	 * 
	 * @param pageId
	 * @param page
	 */
	private static void updateBCB(Page page) {
		BCB bcb = bCBMap.get(page.getPageId());
		int hitCount = bcb.getCount();
		bcb.setCount(++hitCount);
		if (1 == page.getOperator()) {
			bcb.setDirty(true);
		}
	}

}
