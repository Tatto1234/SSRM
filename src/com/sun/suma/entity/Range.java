package com.sun.suma.entity;

/**
 * 上传文件的起始位置及大小
 * @author Administrator
 *
 */
public class Range {
	
	private long from;
	
	private long to;
	
	private  long size;

	public long getFrom() {
		return from;
	}
	
	public Range(long from, long to, long size) {
		this.from = from;
		this.to = to;
		this.size = size;
	}

	public void setFrom(long from) {
		this.from = from;
	}

	public long getTo() {
		return to;
	}

	public void setTo(long to) {
		this.to = to;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	

}
