package com.leduyminh.metricsserver.controller;

import com.leduyminh.metricsserver.entities.SMMetricsServerInfo;
import com.leduyminh.metricsserver.entities.document.SMMetricsServerInfoDocument;
import com.leduyminh.metricsserver.service.inf.SMMetricServerInfoService;
import com.leduyminh.commons.dtos.DataResponse;
import com.leduyminh.commons.dtos.SMMetricsServerInfoDTO;
import com.leduyminh.commons.utils.BusinessCommon;
import com.leduyminh.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "metrics", produces = MediaType.APPLICATION_JSON_VALUE)
public class MetricsController extends BaseController{

    @Autowired
    SMMetricServerInfoService metricServerInfoService;

    @Value("${ismongo}")
    private String isMongo;

    @PostMapping(value = "")
    public ResponseEntity<DataResponse> createOrUpdate(@RequestBody SMMetricsServerInfoDTO dto) {
        if(!StringUtils.isNullOrEmpty(isMongo) && isMongo.equals("true")){
            SMMetricsServerInfoDocument result = metricServerInfoService.createOrUpdateDocument(dto);
            return BusinessCommon.createResponse(result, super.getMessage("message.action.success.commons"), HttpStatus.OK);
        }else{
            SMMetricsServerInfo result = metricServerInfoService.createOrUpdate(dto);
            return BusinessCommon.createResponse(result, super.getMessage("message.action.success.commons"), HttpStatus.OK);
        }
    }
}
