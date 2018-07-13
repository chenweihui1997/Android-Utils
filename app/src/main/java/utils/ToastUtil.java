package utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created on 2017/9/8 15:32.
 * Author as cwh
 * Function description : 优化后的Toast
 */

public class ToastUtil {

    private static Toast toast;

    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                toast.setText(content);
            }else {
                toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            }
        }
        toast.show();
    }

    public static void showToast(Context context, int content) {
        if (toast == null) {
            toast = Toast.makeText(context, context.getResources().getString(content), Toast.LENGTH_SHORT);
        } else {
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                toast.setText(content);
            }else {
                toast = Toast.makeText(context, context.getResources().getString(content), Toast.LENGTH_SHORT);
            }
        }
        toast.show();
    }


}
