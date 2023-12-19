package com.leduyminh.elastic.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElasticConfig {
    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String hostNames;

    @NotNull
    @NotEmpty
    private String rootIndex;

    @NotNull
    private Boolean authorization;

    private Integer partitionSize = 5000;
}
