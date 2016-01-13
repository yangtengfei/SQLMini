package com.tengfeiyang.entities;

public class BCB {
	private int pageID;
	private int frameId;
	private int count;
	private boolean dirty;
	
	
	
	
	public int getPageID() {
		return pageID;
	}
	public void setPageID(int pageID) {
		this.pageID = pageID;
	}
	public int getFrameId() {
		return frameId;
	}
	public void setFrameId(int frameId) {
		this.frameId = frameId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public boolean isDirty() {
		return dirty;
	}
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	@Override
	public String toString() {
		return "BCB [pageID=" + pageID + ", frameId=" + frameId + ", count="
				+ count + ", dirty=" + dirty + "]";
	}
	
	
}
