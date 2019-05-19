package com.paleteja.br.paleteja.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class CurrencyUtils {
    public static DecimalFormat getInstance() {
        DecimalFormat currencyInstance = (DecimalFormat) NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = currencyInstance.getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        currencyInstance.setDecimalFormatSymbols(decimalFormatSymbols);

        return currencyInstance;
    }
}
