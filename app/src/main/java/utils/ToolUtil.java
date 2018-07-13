package utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.GridView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.regex.Pattern;

public class ToolUtil {

    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();

    public static void autoListHeight(ListView listView) {
        int totalHeight = 0;
        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            View listItem = listView.getAdapter().getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listView.getAdapter().getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static void autoGridHeight(GridView girdView, int n) {
        int totalHeight = 0;

        for (int i = 0; i < ((girdView.getAdapter().getCount() % n == 0) ? girdView.getAdapter().getCount() / n : girdView.getAdapter().getCount() / n + 1); i++) {
            View listItem = girdView.getAdapter().getView(i, null, girdView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = girdView.getLayoutParams();
        params.height = totalHeight + (girdView.getHeight() * (((girdView.getAdapter().getCount() % n == 0) ? girdView.getAdapter().getCount() / n : girdView.getAdapter().getCount() / n + 1) - 1));
        girdView.setLayoutParams(params);
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String mobiles) {
        /*
		移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		*/
        String telRegex = "[1][3456789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (mobiles == null || mobiles.trim().length() == 0) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
    }

    /**
     * 除法
     *
     * @param
     * @param
     * @return
     */
    public static String subtract(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static int comapreTo(String v1, String v2) {
        int result;
        return result = v1.compareTo(v2);
    }


    public static String changeObject2String(Object value) {
        String result = "";
        if (value != null && !value.equals("")) {
            result = value.toString();
        }
        return result;
    }

    public static String changeDoubleString(Double value) {
        String result = "";
        if (value != null && !value.equals("")) {
            DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
            result = decimalFormat.format(value);
        }
        return result;
    }

    public static String changeObject3String(Object value) {
        String result = "0";
        if (value != null && !value.equals("")) {
            result = value.toString();
        }

        return result;
    }

    public static Integer changeObjectInteger(Object value) {
        int result = 0;
        if (value != null && !value.equals("")) {
            Double str = changeObject2Double(value);
            result = str.intValue();
        }
        return result;
    }

    public static Boolean changeObjectBoolean(Object value) {
        Boolean result = false;
        if (value != null && !value.equals("") && !value.equals("0.0")&& !value.equals("false")){
            result = true;
        }

        return result;
    }

    public static Double changeObject2Double(Object value) {
        String result = changeObject2String(value);
        if (result != null && result.trim().length() != 0) {
            return Double.valueOf(result);
        } else {
            return null;
        }
    }

    public static Double changeObject3Double(Object value) {
        String result = changeObject3String(value);
        if (result != null && result.trim().length() != 0) {
            return Double.valueOf(result);
        } else {
            return null;
        }
    }

    public static Float changeObject2Float(Object value) {
        //     System.out.println("value="+value);
        String result = changeObject2String(value);
        if (result != null && result.trim().length() != 0) {
            return Float.valueOf(result);
        } else {
            return null;
        }
    }

    public static Integer changeObject2Integer(Object value) {
        Double result = changeObject2Double(value);
        if (result != null) {
            return result.intValue();
        } else {
            return null;
        }
    }

    public static Integer changeObject3Integer(Object value) {
        Double result = changeObject3Double(value);
        if (result != null) {
            return result.intValue();
        } else {
            return null;
        }
    }

    public static Date changString2Date(String strTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date changString2DateD(String strTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date changString2DateD(String strTime, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String changDate2String(Date date) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.format(date);
        } else {
            return "";
        }
    }

    public static String changDate2String3(Date date) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(date);
        } else {
            return "";
        }
    }


    public static String changDate2String4(Date date) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            return format.format(date);
        } else {
            return "";
        }
    }

    public static Integer changDate2String5(Date date, String str) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat(str);
            return Integer.parseInt(format.format(date));
        } else {
            return null;
        }
    }

    public static String changDate2String6(Date date, String str) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat(str);
            return format.format(date);
        } else {
            return "";
        }
    }

    //根据date取到2017 年 11 月 17 日 星期五
    public static String changDate2String6(Date date) {
        String str = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
        switch (date.getDay()) {
            case 0:
                str = format.format(date) + "   星期天";
                break;
            case 1:
                str = format.format(date) + "   星期一";
                break;
            case 2:
                str = format.format(date) + "   星期二";
                break;
            case 3:
                str = format.format(date) + "   星期三";
                break;
            case 4:
                str = format.format(date) + "   星期四";
                break;
            case 5:
                str = format.format(date) + "   星期五";
                break;
            case 6:
                str = format.format(date) + "   星期六";
                break;
        }
        return str;
    }


    public static boolean hasPhoneDevice(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int type = telephony.getPhoneType();
        if (type == TelephonyManager.PHONE_TYPE_NONE) {
            ToastUtil.showToast(context, "你的设备不具备电话或短信等手机功能！");
            return false;
        } else {
            return true;
        }
    }

    public static String formatStringLength(String oldString, char fillChar, Integer length, int position) {
        StringBuffer prefix = new StringBuffer();
        for (int i = 0; i < length; i++) {
            prefix.append(fillChar);
        }

        String result = "";

        if (position == 0) {
            result = (prefix.toString() + oldString).substring((prefix.toString() + oldString).length() - length, (prefix.toString() + oldString).length());
        } else if (position == 1) {
            result = (oldString + prefix.toString()).substring(0, length);
        } else {
            result = (oldString + prefix.toString()).substring(0, length);
        }
        return result;
    }

    public static Date calProdOrMatuDate(Number shelfLife, Date prodDate, Date matuDate, String calType) {
        if (calType.equals("prodDate")) {
            return calProdDate(shelfLife, matuDate, prodDate);
        } else if (calType.equals("matuDate")) {
            return calMatuDate(shelfLife, prodDate, matuDate);
        } else {
            return null;
        }
    }

    public static Date calMatuDate(Number shelfLife, Date prodDate, Date defaultMatuDate) {
        if (shelfLife == null) {
            return defaultMatuDate;
        } else {
            if (prodDate != null) {
                Calendar cal = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
                cal.setTime(prodDate);
                cal.add(Calendar.DAY_OF_YEAR, shelfLife.intValue());
                return cal.getTime();
            } else {
                return defaultMatuDate;
            }
        }
    }

    public static Date calProdDate(Number shelfLife, Date matuDate, Date defaultProdDate) {
        if (shelfLife == null) {
            return defaultProdDate;
        } else {
            if (matuDate != null) {
                Calendar cal = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
                cal.setTime(matuDate);
                cal.add(Calendar.DAY_OF_YEAR, -shelfLife.intValue());
                return cal.getTime();
            } else {
                return defaultProdDate;
            }
        }
    }



    /**
     * 对spinner组件，根据id计算得到在下拉选框中的位置，
     * 便于给spinner赋值,即spinner调用setSelection(int position)选中
     *
     * @param list
     * @param idKey
     * @param idValue
     * @return
     */
    public static int calPositionOfSpinnerValue(List<Map<String, Object>> list, String idKey, Object idValue) {
        int position = -1;
        for (int i = 0; i < list.size(); i++) {
            Object tmp = list.get(i).get(idKey);
            if (tmp != null) {
                if (tmp instanceof Integer) {
                    if (((Integer) tmp).intValue() == ((Integer) idValue).intValue()) {
                        position = i;
                        break;
                    }
                } else if (tmp instanceof String) {
                    if (((String) tmp).equals((String) idValue)) {
                        position = i;
                        break;
                    }
                } else {
                    if (tmp.toString().equals(idValue.toString())) {
                        position = i;
                        break;
                    }
                }
            }
        }
        return position;
    }


    public static String checkImageUrlIsEmpty(Object result) {
        String uri = "";
        if (result != null && !result.toString().trim().equals("")) {
            if (result.toString().trim().length() > 0) {
                if (!result.toString().contains("remoteImage://")) {
                    uri = "remoteImage://" + result;
                } else {
                    uri = result.toString();
                }
            } else {
                uri = "";
            }
        }
        return uri;
    }

    public static Uri getImageUrl(Object imageUrl) {
        if (imageUrl != null && !changeObject2String(imageUrl).isEmpty()) {
            return Uri.parse(ToolUtil.checkImageUrlIsEmpty(imageUrl));
        }
        return null;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }


    /**
     * 保留 newScale 位小数 create at 2016/10/19 17:09
     */
    public static double chang2BigDecimal(Object value, int newScale) {
        return new BigDecimal(value.toString()).setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * Function description : 设置屏幕半透明
     * create at 2016/10/26 16:35
     */
    public static void setBackgroundAlpha(float alpha, Activity context) {
        Window mWindow = context.getWindow();
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = alpha;
        mWindow.setAttributes(lp);
    }

    public static int spToPx(int sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static ViewGroup.LayoutParams setViewMargin(View view, int left, int right, int top, int bottom) {
        if (view == null)
            return null;

        ViewGroup.LayoutParams params = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginParams = null;
        //获取View的margin设置参数
        if (params instanceof ViewGroup.MarginLayoutParams) {
            marginParams = (ViewGroup.MarginLayoutParams) params;
        } else {
            //不存在时新建一个
            marginParams = new ViewGroup.MarginLayoutParams(params);
        }
        marginParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(marginParams);

        return marginParams;
    }


    public static void callPhone(String phoneNum, Activity context) {
        if (!ToolUtil.isNull(phoneNum)) {
            if (ToolUtil.hasPhoneDevice(context)) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                context.startActivity(intent);
            }
        } else {
            ToastUtil.showToast(context, "电话号码为空!");
        }
    }

    public static void sendMsg(String receiver, String msg, Activity context) {
        if (!ToolUtil.isNull(receiver)) {
            if (ToolUtil.hasPhoneDevice(context)) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + receiver));
                intent.putExtra("sms_body", msg);
                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                context.startActivity(intent);
            }
        } else {
            ToastUtil.showToast(context, "电话号码为空!");
        }
    }


    // 先隐藏键盘
    public static void hiddenSoft(Activity activity) {
        ((InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static String getPhotoTitle(int index) {
        String title = "";
        switch (index) {
            case -1:
                title = "示例图";
                break;
            case 0:
                title = "本人正面照";
                break;
            case 1:
                title = "身份证正面照";
                break;
            case 2:
                title = "身份证反面照";
                break;
            case 3:
                title = "户口本户主照";
                break;
            case 4:
                title = "户口本本人照";
                break;

        }
        return title;
    }

    public static boolean isNull(Object value) {
        if (value != null && !changeObject2String(value).trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }


    public static void popupInputMethodWindow(final Activity activity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Service.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 0);
    }

    public static String changeStr2IntStr(Object value) {
        if (isNull(value)) {
            return "0";
        } else {
            return ToolUtil.changeObject2String(ToolUtil.changeObject2Integer(value));
        }
    }

    // 判断当前的activity是否在前台显示 className 实体名称 
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static String getPartnerStatus(String status) {
        switch (status) {
            case "失效":
                return "-1";
            case "正常":
                return "1";
            case "签约":
                return "2";
        }
        return "";
    }

    //低于10月的加0
    public static String getMonthStr(int pyear) {
        String pmonthSTtr = "";
        if (pyear < 10) {
            pmonthSTtr = "0" + pyear;
        } else {
            pmonthSTtr = pyear + "";
        }

        return pmonthSTtr;
    }

    //求最大的数
    public static String maxiMum(List<String> list) {
        String max = list.get(0);
        BigDecimal a = new BigDecimal(max);
        for (int i = 1; i < list.size(); i++) {
            if (a.compareTo(new BigDecimal(list.get(i))) < 0) {
                max = list.get(i);
            }
            a = new BigDecimal(max);
        }
        return max;
    }

    /**
     * 获取两个日期之间的间隔天数
     *
     * @return
     */
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    //按最大的来算比率画图
    public static List<String> calculateRatio(List<String> list) {
        String max = maxiMum(list);
        String ratio = "";
        List<String> lists = new ArrayList<String>();
        if (!max.equals("0")) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals("0")) {
                    ratio = "0";
                } else {
                    ratio = ToolUtil.subtract(list.get(i), max);
                }
                lists.add(ratio);
            }
        }
        return lists;
    }

    public static String getShipStatus(int status) {
        switch (status) {
            case 1:
                return "草稿";
            case 2:
                return "已配货";
            case 3:
                return "已签收";
            default:
                return "错误";
        }
    }

    public static String getRedStatus(int status) {
        switch (status) {
            case 1:
                return "原始单据";
            case 2:
                return "被冲单据    ";
            case 3:
                return "红冲单据";
            default:
                return "错误";
        }
    }

    /**
     * 当浮点型数据位数超过10位之后，数据变成科学计数法显示。用此方法可以使其正常显示。
     *
     * @param value
     * @return Sting
     */
    public static String formatFloatNumber(double value) {
        if (value != 0.00) {
            DecimalFormat df = new DecimalFormat("########.00");
            return df.format(value);
        } else {
            return "0.00";
        }

    }

    public static String formatFloatNumber(Double value) {
        if (value != null) {
            if (value.doubleValue() != 0.00) {
                DecimalFormat df = new DecimalFormat("########.00");
                return df.format(value.doubleValue());
            } else {
                return "0.00";
            }
        }
        return "";
    }

    public static int compareDate(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() >= dt2.getTime()) {
                return 1;
            } else {
                return -1;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static String changDate2String5(Date date) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            return format.format(date);
        } else {
            return "";
        }
    }

    public static String getUserLecturingtype(int status) {
        switch (status) {
            case 1:
                return "草稿";
            case 2:
                return "已确认";
            case 3:
                return "未付完";
        }
        return "";
    }

    /**
     * 判断一个字符串小数点后面第一位数，如果是0就截取小数点前面的返回，反则返回src
     */
    public static String getApplyDate(String src) {
        if ("0".equals(src.substring(src.lastIndexOf(".") + 1, src.length()))) {
            return src.substring(0, src.lastIndexOf("."));
        } else {
            return src;
        }
    }

    public static String changDate3String5(Date date) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            return format.format(date);
        } else {
            return "";
        }
    }

    //设置会员级别对应文字
    public static String getMemberGradenoTex(String gradeno) {
        switch (gradeno) {
            case "0001":
                return "会员";
            case "0002":
                return "金卡会员";
            case "0003":
                return "VIP会员";
            case "0004":
                return "至尊会员";
        }
        return "会员";
    }

    public static String getStatusNo(String status) {
        switch (status) {
            case "生效中":
                return "2";
            case "待生效":
                return "1";
            case "已失效":
                return "-1";
        }
        return "-1";
    }

    //获取这个月的第一天日期
    public static String getMonthFirst(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        return ToolUtil.changDate2String3(c.getTime());
    }

    //获取这个月的最后一天日期
    public static String getMonthFinally(){
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ToolUtil.changDate2String3(ca.getTime());
    }

    //获取这周第一天日期
    public static String getWeekFirst(){
        Calendar d = Calendar.getInstance();
        int day_of_week = d.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        d.add(Calendar.DATE, -day_of_week + 1);
        return ToolUtil.changDate2String3(d.getTime());
    }

    //获取这周最后一天日期
    public static String getWeekFinally(){
        Calendar e = Calendar.getInstance();
        int day_of_weeke = e.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_weeke == 0)
            day_of_weeke = 7;
        e.add(Calendar.DATE, -day_of_weeke + 7);
        return ToolUtil.changDate2String3(e.getTime());
    }

    public static String changDate3String1(Date date) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return format.format(date);
        } else {
            return "";
        }
    }

    public static String getMembertype(int status) {
        switch (status) {
            case 1:
                return "草稿";
            case 2:
                return "已确认";
            case 3:
                return "已完结";
        }
        return "";
    }

    public static String getStatustype(int status) {
        switch (status) {
            case 1:
                return "申请";
            case 2:
                return "申请通过";
            case -1:
                return "申请不通过";
        }
        return "";
    }

    public static String[] setmosno(ArrayList<Map<String, Object>> mMoments) {
        String[] mosno = new String[mMoments.size()];
        for (int i = 0; i < mMoments.size(); i++) {
            mosno [i] = (String) mMoments.get(i).get("mosno");
        }
        return mosno;
    }

    public static String nowDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
    public static String getStar(String status) {
        if (status != null) {
            switch (status) {
                case "0":
                    return "店长";
                case "1":
                    return "一星店长";
                case "2":
                    return "二星店长";
                case "3":
                    return "三星店长";
                case "4":
                    return "四星店长";
                case "5":
                    return "五星店长";
            }
        }
        return "";
    }

    public static boolean uninstallSoftware(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
            if (packageInfo != null) {
                return true;
            }
        }
        catch (PackageManager.NameNotFoundException e) { e.printStackTrace();
        }
        return false;
    }

    public static String changeObject2StringTheme(Object value) {
        String result = "暂无新消息";
        if (value != null && !value.equals("")) {
            result = value.toString();
        }

        return result;
    }

    //根据两个date判断是否同一年或同一天
    public static String getHomePageThemeDate(Date date,Date todayDate){
        if(date!=null&&todayDate!=null){
            if(date.getYear()<todayDate.getYear()){
                return ToolUtil.changDate2String5(date);
            }else if(date.getYear()==todayDate.getYear() && date.getMonth()==todayDate.getMonth() && date.getDate()==todayDate.getDate()){
                return changDate2String7(date);
            }else {
                return changDate2String8(date);
            }
        }else {
            return "";
        }
    }

    public static boolean getTodayBoolean(Date date,Date todayDate) {
        if(date.getYear()==todayDate.getYear() && date.getMonth()==todayDate.getMonth() && date.getDate()==todayDate.getDate()){
            return true;
        }else {
            return false;
        }
    }

    public static String changDate2String9(Date date) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd");
            return format.format(date);
        } else {
            return "";
        }
    }

    public static String changDate2String8(Date date) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
            return format.format(date);
        } else {
            return "";
        }
    }

    public static String changDate2String7(Date date) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            return format.format(date);
        } else {
            return "";
        }
    }

    //根据城市名称获取聚合天气数据
    public static String getWeather(final String cityName) {
        final StringBuilder sb = new StringBuilder();
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                BufferedReader br = null;
                InputStreamReader isr = null;
                URLConnection conn;
                try {
                    URL geturl = new URL("http://v.juhe.cn/weather/index?cityname="+cityName+"&key=b2958be8b6f3f596d38b04dbc8aca83e");
                    conn = geturl.openConnection();//创建连接
                    conn.connect();//get连接
                    isr = new InputStreamReader(conn.getInputStream());//输入流
                    br = new BufferedReader(isr);
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);//获取输入流数据
                    }
                    System.out.println(sb.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {//执行流的关闭
                    if (br != null) {
                        try {
                            if (br != null) {
                                br.close();
                            }
                            if (isr != null) {
                                isr.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } }}
                return sb.toString();
            }
        });
        new Thread(task).start();
        String s = null;
        try {
            s = task.get();//异步获取返回值
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static void showKeyboard(Activity activity, boolean isShow) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            if (activity.getCurrentFocus() == null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } else {
                imm.showSoftInput(activity.getCurrentFocus(), 0);
            }
        } else {
            if (activity.getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static String changDate3String(String date) {
        if (changString2DateD(date) != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            return format.format(changString2DateD(date));
        } else {
            return "";
        }
    }
    /**
     * @param day  今天往前的天数
     */
    static Calendar ca = Calendar.getInstance(); // 得到一个Calendar的实例
    public static Date getFrontDate(int day,Date date){
        ca.setTime(date); // 设置时间为当前时间
       // ca.set(2011, 11, 17);// 月份是从0开始的，所以11表示12月
       // ca.add(Calendar.YEAR, -1); // 年份减1
       // ca.add(Calendar.MONTH, -1);// 月份减1
        ca.add(Calendar.DATE, -day);// 日期减1
       // Date resultDate = ca.getTime(); // 结果
        return ca.getTime();
    }

    /**
     * 获取两个日期之间的所有日期（yyyy-MM-dd）
     * @Description TODO
     * @param begin
     * @param end
     */
    public static List<String> getBetweenDates(Date begin, Date end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        List<String> result = new ArrayList<String>();
        ca.setTime(begin);
        while(begin.getTime()<=end.getTime()){
            result.add(sdf.format(ca.getTime()));
            ca.add(Calendar.DAY_OF_YEAR, 1);
            begin = ca.getTime();
        }
        return result;
    }

    public static Process clearAppUserData(String packageName) {
        Process p = execRuntimeProcess("pm clear " + packageName);
        if (p == null) {
        } else {
        }
        return p;
    }
    public static Process execRuntimeProcess(String commond) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(commond);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

    public static void deleteFile(File file) {
        if (file.exists() == false) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    deleteFile(f);
                }
                file.delete();
            }
        }
    }

    //广播通知更新相册
    public static void updateAlbum(Context context,String localPath){
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(localPath));
        intent.setData(uri);
        context.sendBroadcast(intent); // 发送广播通知相册
    }

    //清空所有Cookie
    public static void clearCookie(Context context, WebView webView){
        //清空所有Cookie
        CookieSyncManager.createInstance(context);  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now

        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.clearCache(true);
    }


    public static boolean checkUrlDNSIsFengXin58(String url){
        if (!ToolUtil.isNull(url)) {
            int beginIndex = url.indexOf("fengxin58.com");
            int lastIndex = url.indexOf("com");
            if (beginIndex != -1 && lastIndex != -1){
                String urlDNS = url.substring(beginIndex, lastIndex + 3);
                return "fengxin58.com".equals(urlDNS);
            } else {
                return false;
            }
        }
        return false;
    }
    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    public static String changeObject4String(Object value) {
        String result = "0.0";
        if (value != null && !value.equals("")) {
            result = value.toString();
        }

        return result;
    }
}
