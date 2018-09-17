package omsk.turinfo;

import android.content.Context;
import android.graphics.Typeface;

public class SingletonFonts {
    private static Typeface font1, font2, font3, font4;
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
            setFont2(Typeface.createFromAsset(activity.getAssets(), "fonts/Lato-Bold.ttf"));
            setFont3(Typeface.createFromAsset(activity.getAssets(), "fonts/Lato-Regular.ttf"));
            setMainFont(Typeface.createFromAsset(activity.getAssets(), "fonts/Rubik-Light.ttf"));
        }
        return localInstance;
    }

    public Typeface getFont1() {
        return font1;
    }
    public Typeface getFont2() {
        return font2;
    }
    public Typeface getFont3() {
        return font3;
    }
    public Typeface getMainFont(){return font4;}

    private static void setFont1(Typeface font1) {
        SingletonFonts.font1 = font1;
    }

    private static void setFont2(Typeface font2) {
        SingletonFonts.font2 = font2;
    }

    private static void setFont3(Typeface font3) {
        SingletonFonts.font3 = font3;
    }

    private static void setMainFont(Typeface font4){
        SingletonFonts.font4 = font4;
    }
}
