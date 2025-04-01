package com.vitira.itreasury.helpers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

public class DateTimeUtils {

	public static LocalDateTime calendarToLocalDateTime(Calendar calendar) {
		System.out.println(calendar);
		if (calendar == null) {
			return null;
		}
		return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
	}
}
