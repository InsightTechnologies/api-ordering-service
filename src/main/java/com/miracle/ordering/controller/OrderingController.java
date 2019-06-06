package com.miracle.ordering.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.miracle.api.service.utils.CommonUtil;
import com.miracle.common.api.bean.Feature;
import com.miracle.common.controller.APIMicroService;
import com.miracle.exception.APIFrameworkException;
import com.miracle.ordering.bean.UnorderedFeaturesBean;
import com.miracle.ordering.exception.OrderingErrorCode;
import com.miracle.ordering.exception.OrderingException;
import com.miracle.utility.DataUtility;

@RestController
@RequestMapping("/masterBot/project")
public class OrderingController extends APIMicroService {

	@Autowired
	private DataUtility dataUtility;

	public static final Logger logger = LoggerFactory.getLogger(OrderingController.class);

	@PostMapping(value = { "/orderedFeatures" }, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_PLAIN_VALUE })
	@ResponseBody
	public List<Feature> runService(@RequestBody UnorderedFeaturesBean unorderedFeaturesBean) throws Exception {
		List<Feature> unorderedFeature = null;
		try {
			String filter = getFilter(unorderedFeaturesBean);
			unorderedFeature = unorderedFeaturesBean.getFeatures();
			CommonUtil.sortBeans(unorderedFeature, filter);
			logger.info("Filtered " + unorderedFeaturesBean + " features based on the " + filter + " . Filtered bean "
					+ unorderedFeature);
		} catch (OrderingException orderingException) {
			logger.error(
					"Getting exception in applying fliter on features for feature Ordering, Exception Description :: "
							+ orderingException.getMessage(),
					orderingException);
			throw orderingException;
		} catch (APIFrameworkException gatewayServiceException) {
			logger.error(
					"Getting exception in applying fliter on features for feature Ordering, Exception Description :: "
							+ gatewayServiceException.getMessage(),
					gatewayServiceException);
			throw gatewayServiceException;
		} catch (Exception exception) {
			logger.error(
					"Getting exception in applying fliter on features for feature Ordering, Exception Description :: "
							+ exception.getMessage(),
					exception);
			throw new OrderingException(
					"Getting exception in applying fliter on features  for feature Ordering, Exception Description :: "
							+ exception.getMessage(),
					exception, OrderingErrorCode.FEATURE_ORDERING_CONTROLLER_UNKNOWN_EXCEPTION,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return unorderedFeature;
	}

	/**
	 * 
	 * @param unorderedFeaturesBean
	 * @return
	 * @throws OrderingException
	 */
//	private String getFilter(UnorderedFeaturesBean unorderedFeaturesBean)throws OrderingException
//	{
//		String filter ="";
//		if( unorderedFeaturesBean.getFilterType()!= null && !unorderedFeaturesBean.getFilterType().isEmpty()) {
//			filter = unorderedFeaturesBean.getFilterType();
//		}
//		else {
//			logger.error("Invalid filtername in the request");
//			throw new OrderingException("Invalid filtername in the request",OrderingErrorCode.INVALID_FILTER_TYPE,HttpStatus.BAD_REQUEST);
//		}
//		return filter;
//	}

	private String getFilter(UnorderedFeaturesBean unorderedFeaturesBean) throws OrderingException {
		String filter = "";
		try {
			if (unorderedFeaturesBean.getFilterType() != null && !unorderedFeaturesBean.getFilterType().isEmpty()) {
//				filter = mongoDBUtility.getFilterType(Integer.parseInt(unorderedFeaturesBean.getFilterType()));
				filter = dataUtility.loadFilterWithId(Integer.parseInt(unorderedFeaturesBean.getFilterType()))
						.getFilterType();
			} else {
				logger.error("Invalid filtername in the request");
				throw new OrderingException("Invalid filtername in the request", OrderingErrorCode.INVALID_FILTER_TYPE,
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception exception) {
			throw new OrderingException(
					"Getting exception in applying fliter on features  for feature Ordering, Exception Description :: "
							+ exception.getMessage(),
					exception, OrderingErrorCode.INVALID_FILTER_TYPE, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return filter;
	}
}
