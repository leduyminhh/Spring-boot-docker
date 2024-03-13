package com.leduyminh.metricsserver.repos.inf.mongo;

import com.leduyminh.metricsserver.entities.document.SMMetricsLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SMMetricLogMongoRepo extends MongoRepository<SMMetricsLogDocument, Long> {
}
