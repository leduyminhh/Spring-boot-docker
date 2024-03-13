package com.leduyminh.metricsserver.repos.inf;

import com.leduyminh.metricsserver.entities.SMMetricsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SMMetricLogRepo extends JpaRepository<SMMetricsLog, Long> {
}
