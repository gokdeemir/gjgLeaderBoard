package com.emir.gjg.model.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2020
 */

@Getter
@Setter
@Resource
@AllArgsConstructor
@NoArgsConstructor
public class ScoreResource {
    private UUID userId;

    private Long timestamp;

    private Double scoreWorth;

    private Double totalScore;
}
