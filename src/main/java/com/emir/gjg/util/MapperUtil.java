package com.emir.gjg.util;

import lombok.experimental.UtilityClass;
import org.mapstruct.Named;

import java.util.Locale;

/**
 * Created by Emir Gökdemir
 * on 23 Kas 2020
 */

@UtilityClass
public class MapperUtil {

    @Named("toLocale")
    public Locale toLocale(String isoCode) {
        return new Locale(isoCode);
    }
}
