package prochain.com.tronbox.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 避免toast串行多次显示
 *
 * @author Elephant
 * @since 17/8/31 下午5:33
 */
public class ToastUtils {

    private static Toast toast;
    private static int version = android.os.Build.VERSION.SDK_INT;
    private static final int MAX_NEED_CANCEL_VERSION = 10;

    /**
     * 显示toast,Toast.LENGTH_SHORT
     */
    public static void toastShort(Context context, int resId) {
        initToast(context, resId);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    /**
     * 显示toast,Toast.LENGTH_SHORT
     */
    public static void toastShort(Context context, String text) {
        initToast(context, text);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    /**
     * 显示toast,Toast.LENGTH_LONG
     */
    public static void toastLong(Context context, int resId) {
        initToast(context, resId);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * 显示toast,Toast.LENGTH_LONG
     */
    public static void toastLong(Context context, String text) {
        initToast(context, text);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * 隐藏toast
     */
    public static void hide() {
        if (toast == null) {
            return;
        }
        toast.cancel();
    }

    /**
     * 初始化Toast ，替换为统一样式， R.layout.toast
     *
     * @param resId 需要显示的String 资源ID
     */
    private static void initToast(Context context, int resId) {
        initToast(context, context.getResources().getString(resId));
    }

    /**
     * 初始化Toast ，替换为统一样式， R.layout.toast
     *
     * @param text 需要显示的Toast 文字
     */
    @SuppressLint("ShowToast")
    private static void initToast(Context context, String text) {
        if (toast == null) {
            if (context != null) {
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            }
        }
        toast.setText(text);
        if (version <= MAX_NEED_CANCEL_VERSION) {
            toast.cancel();
        }
    }
}
