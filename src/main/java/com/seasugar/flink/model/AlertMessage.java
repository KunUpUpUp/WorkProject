/*
 * CopyRight (c) 2012-2020 Ezviz Co, Ltd. All rights reserved. Filename:
 * AlertMessage.java Create-User: joe.zhao(zhaohaolin@hikvision.com.cn)
 * Create-Date: 2021年3月4日 下午5:16:40
 */
package com.seasugar.flink.model;

/**
 * TODO
 * 
 * @author joe.zhao(zhaohaolin@hikvision.com.cn)
 * @version $Id: AlertMessage, v 0.1 Exp $
 */
public class AlertMessage {
	
	private String	id;
	private String	app;
	private String	type;
	private String	title;
	private String	content;
	private int		level;
	private String	alertKey;
	private String	source;
	private String	triggerid;
	private String	filter;
					
	/**
	 */
	public AlertMessage(
			String id,
				String app,
				String type,
				String title,
				String content,
				int level,
				String alertKey,
				String source,
				String triggerid,
				String filter) {
		this.id = id;
		this.app = app;
		this.type = type;
		this.title = title;
		this.content = content;
		this.level = level;
		this.alertKey = alertKey;
		this.source = source;
		this.triggerid = triggerid;
		this.filter = filter;
	}
	
	/**
	 * @return: long
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param id
	 * @return: long
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return: String
	 */
	public String getApp() {
		return app;
	}
	
	/**
	 * @param app
	 * @return: String
	 */
	public void setApp(String app) {
		this.app = app;
	}
	
	/**
	 * @return: String
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @param type
	 * @return: String
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return: String
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title
	 * @return: String
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return: String
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * @param content
	 * @return: String
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * @return: int
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * @param level
	 * @return: int
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	/**
	 * @return: String
	 */
	public String getAlertKey() {
		return alertKey;
	}
	
	/**
	 * @param alertKey
	 * @return: String
	 */
	public void setAlertKey(String alertKey) {
		this.alertKey = alertKey;
	}
	
	/**
	 * @return: String
	 */
	public String getSource() {
		return source;
	}
	
	/**
	 * @param source
	 * @return: String
	 */
	public void setSource(String source) {
		this.source = source;
	}
	
	/**
	 * @return: String
	 */
	public String getTriggerid() {
		return triggerid;
	}
	
	/**
	 * @param triggerid
	 * @return: String
	 */
	public void setTriggerid(String triggerid) {
		this.triggerid = triggerid;
	}
	
	/**
	 * @return: String
	 */
	public String getFilter() {
		return filter;
	}
	
	/**
	 * @param filter
	 * @return: String
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	/**
	 * @return
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return "AlertMessage [id=" + id + ", app=" + app + ", type=" + type + ", title=" + title + ", content=" + content + ", level="
				+ level + ", alertKey=" + alertKey + ", source=" + source + ", triggerid=" + triggerid + ", filter=" + filter + "]";
	}
	
}
