package hush.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;


public class ValidateUtil {

    public final static String DATE_TIME_DEFAUL_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 判断所给参数是否为手机号码
     *
     * @param mobiles 数据字符串
     * @return true，是手机号码; false,不是手机号码
     */
    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^(\\+86|)(|0)1[35847]\\d{9}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

    /**
     * 判断所给对象是否为0或1
     *
     * @param value String 数据
     * @return boolean 0或1返回true
     */
    public static boolean isCheckValue(String value) {
        if ("0".equals(value) || "1".equals(value)) {
            return true;
        }
        return false;
    }

    /**
     * 判断所给对象是否为浮点数
     *
     * @param value String 数据
     * @return boolean
     */
    public static boolean isFloat(String value) {
        Pattern pattern = Pattern.compile("^(\\d+(\\.\\d+)?|0*(\\.\\d+)?)$");
        Matcher matcher = pattern.matcher(StringUtils.trim(value));
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 判断所给对象是否为整数
     *
     * @param value String 数据
     * @return boolean
     */
    public static boolean isInteger(String value) {
        Pattern pattern = Pattern.compile("^\\d+(\\.0*)?$");
        Matcher matcher = pattern.matcher(StringUtils.trim(value));
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 验证是否是邮政编码
     *
     * @param value
     * @return
     */
    public static boolean isZipCode(String value) {

        Pattern pattern = Pattern.compile("\\p{Digit}{6}");
        Matcher matcher = pattern.matcher(StringUtils.trim(value));
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 与当前时间比，在当前时间之前视为过期
     *
     * @param dateTime 时间
     * @return 是否过期
     */
    public static boolean isExpired(String dateTime) {
        if (StringUtils.isEmpty(dateTime)) {
            throw new IllegalArgumentException("parameter dateTime must be set"); }
        DateFormat df = new SimpleDateFormat(DATE_TIME_DEFAUL_FORMAT);
        try {
            Date d1 = df.parse(dateTime);
            Date now = new Date();
            return d1.before(now);
        } catch (ParseException e) {
            throw new IllegalArgumentException("fail to parse date " + dateTime);
        }
    }

    /**
     * 检查是否是合法的银行卡号(Luhm校验)
     *
     * @param bankCardNo
     * @return
     * @throws Exception
     */
    public static boolean checkBankCardNo (String bankCardNo)  {

        if (bankCardNo == null || bankCardNo.trim().length() == 0
                || !bankCardNo.matches("\\d+")) {
            return false;
        }
        return true;
    }

    /**
     * 检查是否是合法的身份证号码
     *
     * @param idNumber
     * @return
     * @throws Exception
     */
    public static boolean checkIdNumber(String idNumber) {
        String  Ai="";
        if (idNumber.length() != 15 && idNumber.length() != 18) {
            return false;
        }
        if (idNumber.length() == 18) {
            Ai = idNumber.substring(0, 17);
        } else if (idNumber.length() == 15) {
            Ai = idNumber.substring(0, 6) + "19" + idNumber.substring(6, 15);
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(Ai);
        if (!isNum.matches()) {
            return false;
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("11", "北京");
        map.put("12", "天津");
        map.put("13", "河北");
        map.put("14", "山西");
        map.put("15", "内蒙古");
        map.put("21", "辽宁");
        map.put("22", "吉林");
        map.put("23", "黑龙江");
        map.put("31", "上海");
        map.put("32", "江苏");
        map.put("33", "浙江");
        map.put("34", "安徽");
        map.put("35", "福建");
        map.put("36", "江西");
        map.put("37", "山东");
        map.put("41", "河南");
        map.put("42", "湖北");
        map.put("43", "湖南");
        map.put("44", "广东");
        map.put("45", "广西");
        map.put("46", "海南");
        map.put("50", "重庆");
        map.put("51", "四川");
        map.put("52", "贵州");
        map.put("53", "云南");
        map.put("54", "西藏");
        map.put("61", "陕西");
        map.put("62", "甘肃");
        map.put("63", "青海");
        map.put("64", "宁夏");
        map.put("65", "新疆");
        map.put("71", "台湾");
        map.put("81", "香港");
        map.put("82", "澳门");
        map.put("91", "国外");
        if (map.get(Ai.substring(0, 2)) == null) {
            return false;
        }

        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2" };
        String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4",
                "3", "2" };
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;
        if (idNumber.length() == 18 && !(Ai.equals(idNumber))) {
            return false;
        }

        return true;
    }

    /**
     * 检查是否是合法的银行编码
     *
     * @param bankCardCode
     * @return
     */
    public static boolean checkBankCardCode(String bankCardCode) {
        if (bankCardCode == null || bankCardCode.trim().length() == 0
                || !bankCardCode.matches("^[A-Z]+$")) {
            return false;
        }
        return true;
    }


    /**
     * 字符串是否为空
     * @param value
     * @return
     */
    public static boolean isStringBlank(String value) {
        return StringUtils.isBlank(value);
    }

    /**
     * 字符串是否是数字
     * @param value
     * @return
     */
    public static boolean isNumeric(String value) {

        return StringUtils.isNumeric(value);
    }
}
