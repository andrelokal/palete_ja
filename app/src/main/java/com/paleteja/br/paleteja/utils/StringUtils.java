package com.paleteja.br.paleteja.utils;

public class StringUtils {
    public static String strOrNull(Object o) {
        return (o != null)? o.toString() : null;
    }
}
