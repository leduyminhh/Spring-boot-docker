package com.leduyminh.filemgtservice.schedule;

import com.leduyminh.gridfs.utils.GridFSUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteTempSchedule {
    private final GridFSUtils gridFSUtils;

    @Scheduled(cron = "${file.collect.garbage.cron}")
    void deleteTempBucket() {
        gridFSUtils.deleteBucket(null,"temp");
    }
}
