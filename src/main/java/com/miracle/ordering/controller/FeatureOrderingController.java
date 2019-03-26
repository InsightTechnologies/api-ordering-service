package com.miracle.ordering.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miracle.feature.response.FeatureJson;
import com.miracle.feature.response.FeatureResponse;

@RestController
public class FeatureOrderingController {

	@GetMapping(value = "/orderedFeatures")
	public ResponseEntity<FeatureResponse> buildFeatures(@RequestParam(value = "filter") String filter,
			@RequestParam(value = "featureJsonList") FeatureJson featureJson) {
		// Order features based on filter type
		FeatureResponse response = new FeatureResponse();
		// send ordered FeatureJson object using FeatureResponse
		return new ResponseEntity<FeatureResponse>(response, HttpStatus.OK);
	}

}
