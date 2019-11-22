package ac.scri.com.donghaoproect;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/11/20
 * <p>
 * 版本号：donghaoProect
 */
public class SettingActivity extends AppCompatActivity{
    private EditText et_port;
    private TextView tv_finish;
    private NumberAddSubView linear_speed,angular_speed;
    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        et_port = findViewById(R.id.et_port);
        tv_finish = findViewById(R.id.tv_finish);
        linear_speed = findViewById(R.id.linear_speed);
        angular_speed = findViewById(R.id.angular_speed);

        linear_speed.setValue((float) Contanst.LINEAR_SPEED);
        angular_speed.setValue((float) Contanst.ANGULAR_SPEED);

        linear_speed.setOnNumberClickListener(new NumberAddSubView.OnNumberClickListener() {
            @Override
            public void onButtonSub(View view, float value) {
                Contanst.LINEAR_SPEED = value;
            }

            @Override
            public void onButtonAdd(View view, float value) {
                Contanst.LINEAR_SPEED = value;
            }
        });

        angular_speed.setOnNumberClickListener(new NumberAddSubView.OnNumberClickListener() {
            @Override
            public void onButtonSub(View view, float value) {
                Contanst.ANGULAR_SPEED = value;
            }

            @Override
            public void onButtonAdd(View view, float value) {
                Contanst.ANGULAR_SPEED = value;
            }
        });
        tv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        Contanst.PORT = et_port.getText().toString();
    }

}
