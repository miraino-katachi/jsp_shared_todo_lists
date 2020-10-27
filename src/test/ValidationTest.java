package test;

import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.FloatValidator;
import org.apache.commons.validator.routines.ISBNValidator;
import org.apache.commons.validator.routines.IntegerValidator;
import org.apache.commons.validator.routines.RegexValidator;
import org.apache.commons.validator.routines.TimeValidator;

public class ValidationTest {

	public static void main(String[] args) {
		// ISBNコード
		ISBNValidator isbn = ISBNValidator.getInstance(true);
		System.out.println("ISBN : ISBN978-4-7980-5258-8 : " + isbn.isValid("ISBN978-4-7980-5258-8"));
		System.out.println("ISBN : 978-4-7980-5258-8 : " + isbn.isValid("978-4-7980-5258-8"));
		System.out.println("ISBN : 9784798052588 :" + isbn.isValid("9784798052588"));

		// 日付形式
		DateValidator date = DateValidator.getInstance();
		System.out.println("Date: 2020/02/29 : " + date.isValid("2020/02/29"));
		System.out.println("Date: 2019/02/29 : " + date.isValid("2019/02/29"));
		System.out.println("Date: 2020-02-28 : " + date.isValid("2020-02-28"));
		System.out.println("Date: 2020-02-28, yyyy-MM-dd : " + date.isValid("2020-02-28", "yyyy-MM-dd"));
		System.out.println("Date: 2020年02月28日, yyyy年MM月dd日 : " + date.isValid("2020年02月28日", "yyyy年MM月dd日"));

		System.out.println("Date: 2020/02/29 12:59:59 : " + date.isValid("2020/02/29 12:59:59"));
		System.out.println("Date: 2020/02/29 12:59:59, yyyy/MM/dd hh:mm:ss : "
				+ date.isValid("2020/02/29 12:59:59", "yyyy/MM/dd hh:mm:ss"));
		System.out.println("Date: 2020/02/29 12:61:62, yyyy/MM/dd hh:mm:ss : "
				+ date.isValid("2020/02/29 12:61:62", "yyyy/MM/dd hh:mm:ss"));

		// 時刻形式
		TimeValidator time = TimeValidator.getInstance();
		System.out.println("Time: 12:56 : " + time.isValid("12:56"));
		System.out.println("Time: 12:56:12 : " + time.isValid("12:56:12"));
		System.out.println("Time: 12:56:12 ,hh:mm:ss : " + time.isValid("12:56:12", "hh:mm:ss"));
		System.out.println("Time: 12:67 : " + time.isValid("12:67"));

		// 整数
		IntegerValidator integer = IntegerValidator.getInstance();
		System.out.println("Integer: \"1\" : " + integer.isValid("1"));
		System.out.println("Integer: \"あ\" : " + integer.isValid("あ"));
		System.out.println("Integer: \"01\" : " + integer.isValid("01"));
		System.out.println("Integer: \"０１\" : " + integer.isValid("０１"));

		// 少数
		FloatValidator floatValid = FloatValidator.getInstance();
		System.out.println("float: \"1\" : " + floatValid.isValid("1"));
		System.out.println("float: \"あ\" : " + floatValid.isValid("あ"));
		System.out.println("float: \"01\" : " + floatValid.isValid("01"));
		System.out.println("float: \"０１\" : " + floatValid.isValid("０１"));
		System.out.println("float: \"1.00\" : " + floatValid.isValid("1"));
		System.out.println("float: \"01.01\" : " + floatValid.isValid("01"));
		System.out.println("float: \"０１．０１\" : " + floatValid.isValid("０１．０１"));
		System.out.println("float: \"０１.０１\" : " + floatValid.isValid("０１.０１"));

		// E-mail形式
		EmailValidator email = EmailValidator.getInstance();
		System.out.println("email: e@e : " + email.isValid("e@e"));
		System.out.println("email: e@e.com : " + email.isValid("e@e.com"));
		System.out.println("email: .e@e.com : " + email.isValid(".e@e.com"));
		System.out.println("email: e.@e.com : " + email.isValid("e.@e.com"));
		System.out.println("email: e.f@e.com : " + email.isValid("e.f@e.com"));
		System.out.println("email: e..f@e.com : " + email.isValid("e..f@e.com"));

		// 正規表現
		RegexValidator regex = new RegexValidator("^[0-9a-zA-Z]{8,20}$");
		String str = "123456AaBc12345678901";
		System.out.println("パスワード : " + regex.isValid(str));

		RegexValidator regex2 = new RegexValidator("^[0-9]+$");
		String num = "１２３４５";
		System.out.println("整数 : １２３４５ : " + regex2.isValid(num));
		String num2 = "12345";
		System.out.println("整数 : 12345 : " + regex2.isValid(num2));

		RegexValidator regex3 = new RegexValidator("^[0-9]+\\.[0-9]+$");
		String num3 = "12.34";
		System.out.println("少数 : 12.34 : " + regex3.isValid(num3));
	}

}
