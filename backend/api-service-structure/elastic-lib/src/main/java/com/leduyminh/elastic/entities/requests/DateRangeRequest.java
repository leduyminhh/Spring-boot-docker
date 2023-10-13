package com.leduyminh.elastic.entities.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DateRangeRequest extends BaseIndexRequest {
    private Date fromDate;

    private Date toDate;

    private String dateFieldName;

    private Boolean override;
}
