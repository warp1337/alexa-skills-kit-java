/**
 *
 * Author: flier@techfak.uni-bielefeld.de
 *
 * Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use
 * this file except in compliance with the License. A copy of the License is located at
 * <p>
 * http://aws.amazon.com/apache2.0/
 * <p>
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 * for the specific language governing permissions and limitations under the License.
 *
 */

package ubmensaplan;

import java.util.*;
import java.util.Calendar;

public class CalendarUtils {

    Locale locale = Locale.GERMANY;
    TimeZone tz = TimeZone.getTimeZone("Germany");
    Calendar cal = Calendar.getInstance(tz, locale);
    String tzname = tz.getDisplayName();
    String name1 = locale.getDisplayName();

    private String getDayOfWeek(int value) {
        String day = "";
        switch (value) {
            case 1:
                day = "sonntag";
                break;
            case 2:
                day = "montag";
                break;
            case 3:
                day = "dienstag";
                break;
            case 4:
                day = "mittwoch";
                break;
            case 5:
                day = "donnerstag";
                break;
            case 6:
                day = "freitag";
                break;
            case 7:
                day = "samstag";
                break;
        }
        return day;
    }

    public String whatDayIsToday() {
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return getDayOfWeek(dayOfWeek);
    }

}