package test.util;

import java.text.DateFormat;
import java.util.Date;

public class DateTest {
	public static void main(String args[]){
		Date date=new Date(System.currentTimeMillis());
		DateFormat format=DateFormat.getDateInstance();
		System.out.println(date.toString());
		System.out.println(System.currentTimeMillis());
		System.out.println(format.format(date));
	}
}
