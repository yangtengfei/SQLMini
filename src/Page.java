
public class Page {
	private int operator;
	private int pageId;
	
	public int getOperator() {
		return operator;
	}
	public void setOperator(int operator) {
		this.operator = operator;
	}
	public int getPageId() {
		return pageId;
	}
	public void setPageId(int pageId) {
		this.pageId = pageId;
	}
	
	@Override
	public String toString() {
		return "Page [operator=" + operator + ", pageId=" + pageId + "]";
	}
	
}
