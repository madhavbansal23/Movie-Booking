package com.maddy.moviebooking.util;

import androidx.annotation.StringDef;

public class Constants {

    @StringDef({BOOKINGSTATUS.BOOKED, BOOKINGSTATUS.VACANT, BOOKINGSTATUS.SELECTED})
    public @interface BOOKINGSTATUS {
        String BOOKED = "BOOKED", VACANT = "VACANT", SELECTED = "SELECTED";
    }

}
