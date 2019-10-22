package ac.scri.com.donghaoproect;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiantao.coordinatormenu.CoordinatorMenu;

import java.util.ArrayList;

/**https://github.com/qiantao94/CoordinatorMenu
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/18
 * <p>
 * 版本号：donghaoProect
 */
public class CehuaActivity extends AppCompatActivity {

    private ImageView mHeadIv;
    private CoordinatorMenu mCoordinatorMenu;
    private ArrayList<Fragment> pages = null;
    private CommnetViewPager viewpager = null;
    private RegionView mRegionView;
    private IPView ipView;

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cehua);
        ipView = findViewById(R.id.ipView);

        mRegionView = (RegionView) findViewById(R.id.regionView);

        mRegionView.setListener(new RegionView.RegionViewClickListener() {

            @Override
            public void clickTop() {
                showToast("view clickTop");
                Log.e("linfd",ipView.getBytesWithIP().toString());
                Log.e("linfd",new String(ipView.getBytesWithIP()));;
            }

            @Override
            public void clickRight() {
                //  showToast("view clickRight");
            }

            @Override
            public void clickRightDown() {
                showToast("view clickRightwdown");
            }

            @Override
            public void clickLeft() {
                showToast("view clickLeft");
            }

            @Override
            public void clickCenter() {
                showToast("view clickCenter");
            }

            @Override
            public void clickBottom() {
                showToast("view clickBottom");
            }
        });
        pages = new ArrayList<Fragment>();
        mCoordinatorMenu = (CoordinatorMenu) findViewById(R.id.menu);
        viewpager = findViewById(R.id.viewpager);

        // create new fragments
        pages.add(new MyFragment("tab1", Color.BLUE));
        pages.add(new MyFragment("tab2", Color.RED));
        pages.add(new MyFragment("tab3", Color.CYAN));

        // set adapter
        viewpager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        //viewpager.setOnPageChangeListener(this);
        viewpager.setCurrentItem(0);
        //titles.get(0).setSelected(true);

//        mHeadIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mCoordinatorMenu.isOpened()) {
//                    mCoordinatorMenu.closeMenu();
//                } else {
//                    mCoordinatorMenu.openMenu();
//                }
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        if (mCoordinatorMenu.isOpened()) {
            mCoordinatorMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    // fragment
    @SuppressLint("ValidFragment")
    public static class MyFragment extends Fragment {

        private String text;
        private int color;

        public MyFragment(String text, int color) {
            this.text = text;
            this.color = color;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            TextView tv = new TextView(DonghaoApplication.getApplication());
            tv.setBackgroundColor(color);
            tv.setText(text);
            return tv;
        }
    }

    // adapter
    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return pages.get(arg0);
        }
    }

    private void showToast(String message) {
        if (mToast == null) {
            mToast = Toast.makeText(CehuaActivity.this, message,
                    Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }
}
