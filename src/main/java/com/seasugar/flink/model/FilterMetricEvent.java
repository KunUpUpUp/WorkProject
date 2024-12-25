/*
 * CopyRight (c) 2012-2020 Ezviz Co, Ltd. All rights reserved. Filename:
 * MergeMetricEvent.java Create-User: joe.zhao(zhaohaolin@hikvision.com.cn)
 * Create-Date: 2021年3月4日 下午4:04:05
 */
package com.seasugar.flink.model;


public class FilterMetricEvent {
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long	serialVersionUID	= 1L;

	private String cluster;
	private String namespace;
	private String				alertTitle;
	private String				alertCondition;
	private String				reAlertCondition;
	private int					alertLevel;
	private String				alertId;
	private String				key;
								
	public FilterMetricEvent() {
		//
	}
	
	public FilterMetricEvent(
			String endpoint,
				String serviceName,
				String productId,
				String metricName,
				String metric,
				String cluster,
				String namespace,
				String monitorType,
				float value,
				int step,
				String counterType,
				long timestamp,
				String timeFormat,
				String alertId,
				String alertTitle,
				String alertCondition,
				String reAlertCondition,
				int alertLevel) {
		this.cluster=cluster;
		this.namespace=namespace;
		this.alertId = alertId;
		this.alertTitle = alertTitle;
		this.alertCondition = alertCondition;
		this.reAlertCondition = reAlertCondition;
		this.alertLevel = alertLevel;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * @return: String
	 */
	public String getAlertTitle() {
		return alertTitle;
	}
	
	/**
	 * @param alertTitle
	 * @return: String
	 */
	public void setAlertTitle(String alertTitle) {
		this.alertTitle = alertTitle;
	}
	
	/**
	 * @return: String
	 */
	public String getAlertCondition() {
		return alertCondition;
	}
	
	/**
	 * @param alertCondition
	 * @return: String
	 */
	public void setAlertCondition(String alertCondition) {
		this.alertCondition = alertCondition;
	}
	
	/**
	 * @return: String
	 */
	public String getReAlertCondition() {
		return reAlertCondition;
	}
	
	/**
	 * @param reAlertCondition
	 * @return: String
	 */
	public void setReAlertCondition(String reAlertCondition) {
		this.reAlertCondition = reAlertCondition;
	}
	
	/**
	 * @return: int
	 */
	public int getAlertLevel() {
		return alertLevel;
	}
	
	/**
	 * @param alertLevel
	 * @return: int
	 */
	public void setAlertLevel(int alertLevel) {
		this.alertLevel = alertLevel;
	}
	
	/**
	 * @return: String
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * @param key
	 * @return: String
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * @return: String
	 */
	public String getAlertId() {
		return alertId;
	}
	
	/**
	 * @param alertId
	 * @return: String
	 */
	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}
	
}
