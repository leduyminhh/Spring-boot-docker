package com.leduyminh.metricsserver.service.inf;


import com.leduyminh.metricsserver.entities.SMMetricsServerInfo;
import com.leduyminh.metricsserver.entities.document.SMMetricsServerInfoDocument;
import com.leduyminh.commons.dtos.SMMetricsServerInfoDTO;

public interface SMMetricServerInfoService {
    SMMetricsServerInfo createOrUpdate(SMMetricsServerInfoDTO dto);
    SMMetricsServerInfoDocument createOrUpdateDocument(SMMetricsServerInfoDTO dto);
}
