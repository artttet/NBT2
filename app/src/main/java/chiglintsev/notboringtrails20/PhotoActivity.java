package chiglintsev.notboringtrails20;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

public class PhotoActivity extends AppCompatActivity {
    private final static String KEY_FROM_PLACE_RESID = "resID_key";
    private final static String KEY_FROM_PLACE_TITLE = "title_key";
    private int res;
    private TextView titleV;
    private PhotoView image;
    private String title;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        title = getIntent().getStringExtra(KEY_FROM_PLACE_TITLE);
        res = getIntent().getIntExtra(KEY_FROM_PLACE_RESID, -1);

        titleV = findViewById(R.id.photo_title);
        titleV.setText(title);
        titleV.setTypeface(SingletonFonts.getInstance(this).getFont3());

        image = findViewById(R.id.photo);
        image.setImageResource(res);

        ImageButton back = findViewById(R.id.photo_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //красивый цвет статус бара
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBarForPhotoView));
        }
    }
}
