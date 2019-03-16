package fontysmultipurposelibrary.dataacces.helpers;

import java.util.Date;

public class DateHelper {

    private DateHelper() {
    }

    public static Date addMinutesToDate(int minutes, Date beforeTime) {
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

        long curTimeInMs = beforeTime.getTime();
        return new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
    }
}
