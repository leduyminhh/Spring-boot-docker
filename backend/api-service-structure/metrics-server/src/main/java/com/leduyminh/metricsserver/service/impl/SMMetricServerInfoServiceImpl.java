package com.leduyminh.metricsserver.service.impl;

import com.leduyminh.metricsserver.entities.SMMetricsLog;
import com.leduyminh.metricsserver.entities.SMMetricsServerInfo;
import com.leduyminh.metricsserver.entities.document.SMMetricsLogDocument;
import com.leduyminh.metricsserver.entities.document.SMMetricsServerInfoDocument;
import com.leduyminh.metricsserver.enums.MetricLogStatusEnum;
import com.leduyminh.metricsserver.repos.inf.SMMetricLogRepo;
import com.leduyminh.metricsserver.repos.inf.SMMetricServerInfoRepo;
import com.leduyminh.metricsserver.repos.inf.mongo.SMMetricLogMongoRepo;
import com.leduyminh.metricsserver.repos.inf.mongo.SMMetricServerInfoMongoRepo;
import com.leduyminh.metricsserver.service.inf.SMMetricServerInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SMMetricServerInfoServiceImpl implements SMMetricServerInfoService {

    @Autowired
    SMMetricServerInfoRepo metricServerInfoRepo;

    @Autowired
    SMMetricServerInfoMongoRepo metricServerInfoMongoRepo;

    @Autowired
    SMMetricLogRepo smMetricLogRepo;

    @Autowired
    SMMetricLogMongoRepo smMetricLogMongoRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public SMMetricsServerInfo createOrUpdate(SMMetricsServerInfoDTO dto) {
        SMMetricsServerInfo metric = null;
        SMMetricsLog metricsLog = new SMMetricsLog();
        metricsLog.setServer(dto.getServer());
        metricsLog.setContent(new Gson().toJson(dto));

        try {
            SMMetricsServerInfo oldMetric = metricServerInfoRepo.findFirstByServer(dto.getServer());
            metric = modelMapper.map(dto, SMMetricsServerInfo.class);
            if (oldMetric == null) {
                metricsLog.setStatus(MetricLogStatusEnum.CREATE.getValue());
            } else {
                metric.setId(oldMetric.getId());
                metricsLog.setStatus(MetricLogStatusEnum.UPDATE.getValue());
            }

            metric = metricServerInfoRepo.save(metric);
            System.out.print("================ " + metricsLog.getStatus() + " metric [ "+ metric.getServer() +"] successfully. Time: "  + new Date() + "  ================\n");
        } catch (Exception ex) {
            metricsLog.setStatus(MetricLogStatusEnum.ERROR.getValue());
            metricsLog.setErrorMessage(ex.getMessage());
            ex.printStackTrace();
        } finally {
            smMetricLogRepo.save(metricsLog);
        }
        return metric;
    }
    @Override
    public SMMetricsServerInfoDocument createOrUpdateDocument(SMMetricsServerInfoDTO dto) {
        SMMetricsServerInfoDocument metric = null;
        SMMetricsLogDocument metricsLog = new SMMetricsLogDocument();
        metricsLog.setServer(dto.getServer());
        metricsLog.setContent(new Gson().toJson(dto));

        try {
            SMMetricsServerInfoDocument oldMetric = metricServerInfoMongoRepo.findFirstByServer(dto.getServer());
            metric = modelMapper.map(dto, SMMetricsServerInfoDocument.class);
            if (oldMetric == null) {
                metricsLog.setStatus(MetricLogStatusEnum.CREATE.getValue());
                metric.setCreatedDate(new Date());
                metricsLog.setCreatedDate(new Date());
            } else {
                metric.setId(oldMetric.getId());
                metric.setLastModifiedDate(new Date());
                metricsLog.setLastModifiedDate(new Date());
                metricsLog.setStatus(MetricLogStatusEnum.UPDATE.getValue());
            }
            metric = metricServerInfoMongoRepo.save(metric);
            System.out.print("================ " + metricsLog.getStatus() + " metric [ "+ metric.getServer() +"] successfully. Time: "  + new Date() + "  ================\n");
        } catch (Exception ex) {
            metricsLog.setStatus(MetricLogStatusEnum.ERROR.getValue());
            metricsLog.setErrorMessage(ex.getMessage());
            ex.printStackTrace();
        } finally {
            smMetricLogMongoRepo.save(metricsLog);
        }
        return metric;
    }
}
