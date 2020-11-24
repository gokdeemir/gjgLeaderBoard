package com.emir.gjg.model.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

/**
 * Created by Emir Gökdemir
 * on 23 Kas 2020
 */
@Getter
@Setter
@AllArgsConstructor
public class LeaderboardResource {

//    private int rank;

    private Double points;

    private String displayName;

    private Locale country;

}
