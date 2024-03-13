package com.leduyminh.metricsserver.repos.inf;

import com.leduyminh.metricsserver.entities.SMMetricsServerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SMMetricServerInfoRepo extends JpaRepository<SMMetricsServerInfo, Long> {

    SMMetricsServerInfo findFirstByServer(String server);
}
