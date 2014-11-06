package you.ctrip.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {

	/**
	 * 判断字符串长度(字符串全为空格的为false)
	 * @param str
	 * @param min
	 * @param max
	 * @return
	 */
	public static Boolean checkStrLength(String str, Integer min, Integer max) {
		return (!isEmpty(str) && str.length() >= min && str.length() <= max);
	}

	/**
	 * 判断字符串是不是数字
	 * @param str
	 * @return
	 */
	public static Boolean checkNumber(String str) {
		return !isEmpty(str) && str.matches("^\\d+$");
	}

	/**
	 * 判断数组是否全部为空
	 * @param o
	 * @return
	 */
	public static Boolean isAllEmpty(Object[] o) {
		if (o == null)
			return true;

		for (Object tmp : o) {
			if (!isEmpty(tmp)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断字符串是不是null或无字符（trim后）
	 * @param o
	 * @return
	 */
	public static Boolean isEmpty(String o) {
		return (o == null || o.trim().length() == 0);
	}

	/**
	 * 判断整形是否为null或0
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isEmpty(Integer value) {
		return (value == null || value == 0);
	}

    /**
     * 判断整形是否为null或0
     *
     * @param value
     * @return
     */
    public static Boolean isEmpty(Long value) {
        return (value == null || value == 0);
    }

	/**
	 * 判断List是否为空
	 *
	 * @param list
	 * @return
	 */
	public static Boolean isEmpty(List<?> list) {
		return (list == null || list.size() == 0);
	}

	/**
	 * 判断Map是否为空
	 * @param map
	 * @return
	 */
	public static Boolean isEmpty(Map<?, ?> map) {
		return (map == null || map.size() == 0);
	}

	/**
	 * 判断Set是否为空
	 * @param set
	 * @return
	 */
	public static Boolean isEmpty(Set<?> set) {
		return (set == null || set.size() == 0);
	}

	/**
	 * 判断Object是否为空
	 * @param o
	 * @return
	 */
	public static Boolean isEmpty(Object o) {
//		return o == null;
		if (o == null) {
			return true;
		}
		boolean isEmpty = false;
		if (o instanceof String) {
			isEmpty = isEmpty((String) o);
		} else if (o instanceof Integer) {
			isEmpty = isEmpty((Integer) o);
		} else if (o instanceof List<?>) {
			isEmpty = isEmpty((Integer) o);
		} else if (o instanceof Map<?, ?>) {
			isEmpty = isEmpty((Integer) o);
		} else if (o instanceof Set<?>) {
			isEmpty = isEmpty((Integer) o);
		}

		return isEmpty;
	}

	/**
	 * 判断数组是否为空
	 * @param o
	 * @return
	 */
	public static Boolean isEmpty(Object[] o) {
		return (o == null || o.length == 0);
	}
	
	/**
	 * 正则验证是否为英文单词，也就是纯大小写A-z字母组成
	 * @param word
	 * @return
	 */
	public static Boolean checkEnglishWord(String word) {
		if(isEmpty(word)) {
			return false;
		}
		return checkRegex("[a-zA-Z]+", word);
	}

	/**
	 * 根据指定的正则表达式验证字符串
	 * @param regex 正则表达式
	 * @param str 检验内容
	 * @return
	 */
	public static Boolean checkRegex(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 验证正整数
	 * @param str 检验内容
	 * @param min 最小长度
	 * @param max 最大长度
	 * @return
	 */
	public static Boolean checkPositive(String str, int min, int max) {
		return checkRegex("^\\d{" + min + "," + max + "}$", str);
	}

	/**
	 * 验证正整数
	 * @param str 检验内容
	 * @param length 整数的长度
	 * @return
	 */
	public static Boolean checkPositive(String str, int length) {
		return checkRegex("^\\d{" + length + "}$", str);
	}

	/**
	 * 判断Object数组中的值是否为空,只要其中有一个为空就返回true<br/>
	 * Integer为0会判断为空
	 * @param o
	 * @return
	 */
	public static boolean oneMoreEmpty(Object[] o) {
		boolean b = false;

		for (int i = 0; i < o.length; i++)
			if (isEmpty(o[i])) {
				b = true;
				break;
			}

		return b;
	}
	
	/**
	 * 判断时间是否是今天
	 * 是今天时间，返回true
	 * 非今天时间，返回false
	 * @param date
	 * @return
	 */
	public static boolean timeIsTodayTime(Date date){
		if(date==null){
			return false;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String paramDate=sdf.format(date);
		String nowDate=sdf.format(new Date());
		return paramDate.equals(nowDate);
	}

	public static void main(String[] args) {
//		System.out.println(checkPositive("123121", 5));
//		System.out.println(checkPositive("121", 5,8));
		//"[a-zA-Z]+"
		//"^\\[A-Za-z\\]+$"
		System.out.println(checkRegex("[a-zA-Z]+", "fsfsa san sasa"));
	}
	/**
	 * 根据指定的正则表达式校验字符串
	 *
	 * @param reg
	 *            正则表达式
	 * @param string
	 *            拼配的字符串
	 * @return
	 */
	public static boolean startCheck(String reg, String string) {
		if (isEmpty(string)) {
			return false;
		}
		boolean tem = false;

		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(string);

		tem = matcher.matches();
		return tem;
	}
	
	public static final String REGEX_MOBILE = "^0?1(?:3[0-9]|4[457]|5[0-35-9]|8[0-35-9])\\d{8}$";
	/**
	 * 手机号码验证,11位 13 号段0-9 14 号段 5,7 15 号段除4以外 18 号段 6, 7, 8, 9
	 * */
	public static boolean checkCellPhone(String cellPhoneNr) {
		// String reg = "^(13[0-9]|14[57]|15[^4]|18[6-9])\\d{8}$";
		return startCheck(REGEX_MOBILE, cellPhoneNr);
	}
	
	public static final String REGEX_PHONE = "^(0[0-9]{2,3})?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$|(^400[0-9]{7}$)";
	public static boolean checkTel(String phone) {
		return startCheck(REGEX_PHONE, phone);
	}
}
