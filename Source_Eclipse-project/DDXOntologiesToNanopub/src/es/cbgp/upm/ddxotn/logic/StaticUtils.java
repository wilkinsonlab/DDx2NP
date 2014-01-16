package es.cbgp.upm.ddxotn.logic;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class StaticUtils {

	public final static char GUION = '-';
	public final static char COMMA = ',';
	public final static char DOUBLE_DOT = ':';
	public final static char UNDERSCORE = '_';
	public final static char STRANGE_CHAR = (char) 0;
	public final static String BLANK = "";
	public final static String CLASSES = "CLASSES";
	public final static String CLASS = "CLASS";
	public final static String DAY = "DAY";
	public final static String MONTH = "MONTH";
	public final static String YEAR = "YEAR";
	public final static String HOUR = "HOUR";
	public final static String MINUTE = "MINUTE";
	public final static String SECOND = "SECOND";
	public final static String START  = "START";
	public final static String END = "END";
	public final static String DURATION = "DURATION";
	
	
	/**
	 * Method which receives the date in format XX-YY-ZZ or XX:YY:ZZ and return
	 * a three size array with a[0] = XX, a[1] = YY, a[2] = ZZ
	 * 
	 * @param d
	 *            The string
	 * @return The value
	 **/
	public static int[] getArrayDate(String d, char sep) {
		int ret[] = new int[3];
		String parts[] = d.split(Character.toString(sep));
		if (parts.length == 3) {
			for (int i = 0; i < parts.length; i++) {
				try {
					ret[i] = Integer.parseInt(parts[i].trim());
				} catch (Exception e) {
					/*
					 * If we received a non numeric value, we exit.
					 */
					return null;
				}
			}
			return ret;
		}
		return null;
	}

	public static boolean isEmpty(String t) {
		return (t == null) || (t.equalsIgnoreCase(""));
	}

	public static void printGC(String s, GregorianCalendar rsd) {
		System.out.println(s + rsd.get(Calendar.DAY_OF_MONTH) + "/"
				+ rsd.get(Calendar.MONTH) + "/" + rsd.get(Calendar.YEAR)
				+ " - " + rsd.get(Calendar.HOUR_OF_DAY) + ":"
				+ rsd.get(Calendar.MINUTE) + ":" + rsd.get(Calendar.SECOND));

	}

	public static String formatAddingAdditionalZeros(int i) {
		String number = Integer.toString(i);
		if (number.length() == 1) {
			return "0" + number;
		}
		return number;
	}

	public static boolean isInteger(String i) {
		try {
			Integer.parseInt(i);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean areAllIntValues(String[] values) {
		for (int i = 0; i < values.length; i++) {
			try {
				Integer.parseInt(values[i]);
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}
}
