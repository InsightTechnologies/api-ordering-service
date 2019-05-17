package com.miracle.ordering.bean;

import java.util.List;

import org.springframework.stereotype.Component;

import com.miracle.common.api.bean.Feature;
@Component
public class UnorderedFeaturesBean {
	private List<Feature> features;
	private String filterType;
	
	public List<Feature> getFeatures() {
		return features;
	}
	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	
	
}
