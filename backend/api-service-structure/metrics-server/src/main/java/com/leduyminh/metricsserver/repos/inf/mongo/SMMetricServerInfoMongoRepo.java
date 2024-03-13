package com.leduyminh.metricsserver.repos.inf.mongo;

import com.leduyminh.metricsserver.entities.document.SMMetricsServerInfoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.Transactional;

@EnableMongoRepositories
@Transactional
public interface SMMetricServerInfoMongoRepo extends MongoRepository<SMMetricsServerInfoDocument, String> {

    SMMetricsServerInfoDocument findFirstByServer(String server);
}
