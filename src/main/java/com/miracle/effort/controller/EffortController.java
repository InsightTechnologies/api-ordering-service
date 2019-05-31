package com.miracle.effort.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miracle.common.api.bean.Story;
import com.miracle.common.api.bean.StoryID;
import com.miracle.common.controller.APIMicroService;
import com.miracle.effort.bean.StoryMetadataBean;
import com.miracle.effort.constants.EffortConstants;
import com.miracle.effort.exception.EffortErrorCode;
import com.miracle.effort.exception.EffortException;
import com.miracle.exception.GatewayServiceException;

@RequestMapping(value = "/masterBot/project/stories")
@RestController
public class EffortController extends APIMicroService {
	public static final Logger logger = LoggerFactory.getLogger(EffortController.class);

	// API_M5
	@PostMapping("/estimateEffort")
	public Double runService(@RequestBody StoryMetadataBean storyMetadataBean) throws Exception {
		String iceScrumURLPrefix = null;				
		double totalEffort = 0;
		try {
			iceScrumURLPrefix = getIceScrumURLPrefix();
			String projectName = storyMetadataBean.getProjectName();
			List<String> states = storyMetadataBean.getStoryStates();
			List<StoryID> stories_ids = storyMetadataBean.getStories_ids();
			if (stories_ids != null && stories_ids.size() > 0) {
				for (StoryID storyID : stories_ids) {
					StringBuilder urlBuilder = new StringBuilder("");
					urlBuilder.append(iceScrumURLPrefix).append(projectName).append(EffortConstants.STORY_PATH)
							.append("/").append(storyID.getId());
					logger.info("Getting StoryDetails URL " + urlBuilder.toString());
					Map<String, String> headerDetails = commonUtil.getHeaderDetails();
					List<MediaType> acceptableMediaTypes = commonUtil.getAcceptableMediaTypes();
					String storyDetails = commonUtil.getDetails(urlBuilder.toString(), headerDetails,
							acceptableMediaTypes);
					logger.info("Story Details :: " + storyDetails);
					Story story = new ObjectMapper().readValue(storyDetails, Story.class);
					if (states != null && states.size() > 0) {
						for (String state : states) {
							if (Integer.parseInt(state) == story.getState()) {
								logger.info("storyID :: " + storyID.getId() + "\t uid:: " + story.getUid()
										+ "\t Name :: " + story.getName() + "\t State ::" + story.getState()
										+ "\t Effort :: " + story.getEffort());
								totalEffort += story.getEffort();
								break;
							}
						}
					} else {
						logger.error("Story states are empty in request");
						throw new EffortException("Story states are empty in request",
								EffortErrorCode.EMPTY_STORY_STATES);
					}

				}
			} else {
				logger.error("No Story Ids found in feature, so unable to estimate the effort");
				throw new EffortException("No Story Ids found in feature, so unable to estimate the effort",
						EffortErrorCode.EMPTY_STORY_IDS);
			}

		} catch (EffortException effortException) {
			throw effortException;
		} catch (GatewayServiceException gatewayServiceException) {
			throw gatewayServiceException;
		} catch (Exception exception) {
			logger.error("Unable to estimate effort from stories , Exception Description :: " + exception.getMessage(),
					exception);
			throw new EffortException(
					"Unable to estimate effort from stories , Exception Description :: " + exception.getMessage(),
					exception, EffortErrorCode.EFFORT_CONTROLLER_UNKNOWN_EXCEPTION,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Double effort = new Double(totalEffort);
		logger.info("Estimated Effort From Stories is " + totalEffort);
		return effort;
	}
}
