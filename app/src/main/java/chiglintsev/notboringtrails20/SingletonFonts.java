package chiglintsev.notboringtrails20;

import android.content.Context;
import android.graphics.Typeface;

public class SingletonFonts {
    private static Typeface font1;
    private static volatile SingletonFonts instance;

    private SingletonFonts() {
    }

    public static SingletonFonts getInstance(Context activity) {
        SingletonFonts localInstance = instance;
        if (localInstance == null) {
            synchronized (SingletonFonts.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SingletonFonts();
                }
            }
            setFont1(Typeface.createFromAsset(activity.getAssets(), "fonts/lato-light.ttf"));
        }
        return localInstance;
    }

    public Typeface getFont1() {
        return font1;
    }

    private static void setFont1(Typeface font1) {
        SingletonFonts.font1 = font1;
    }
}
