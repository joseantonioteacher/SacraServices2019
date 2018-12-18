package com.sacra.ecommerce.util;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * Utilidades para calculo temporal, que facilitan
 * el paso de la eterna java.util.Date a las nuevas java.time.* 
 * de Java 8, habida cuenta que: 
 * "The equivalent class to java.util.Date in JSR-310 is Instant, 
 * thus there is a convenient method  toInstant() to provide the conversion".
 * 
 * @author https://www.linkedin.com/in/joseantoniolopezperez
 * @version 0.2
 */
public class DateTimeUtils {
	
	/**
	 * Calcula diferencia entre dos fechas, expresadas como java.util.Date
	 * y retorna como la nueva java.time.Duration, sobre la cual...
	 * toMillis(), toMinutes(), etc.
	 */
	public static Duration between(Date d1, Date d2) {
		Instant i1 = d1.toInstant();
		Instant i2 = d2.toInstant();
		Duration d = Duration.between(i1, i2);
		return d;
	}
	
}
