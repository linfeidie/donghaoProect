package ac.scri.com.donghaoproect;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.qiantao.coordinatormenu.CoordinatorMenu;

import java.util.ArrayList;

/**https://github.com/qiantao94/CoordinatorMenu
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/18
 * <p>
 * 版本号：donghaoProect
 */
public class CehuaActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ImageView mHeadIv;
    private CoordinatorMenu mCoordinatorMenu;
    private ArrayList<Fragment> pages = null;
    private CommnetViewPager viewpager = null;
    private RegionView mRegionView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cehua);

        initView();
        ControlSendManager.init(this, new PackagesHandleCallback() {
            @Override
            public void messageCallback(TypeEntity typeEntity, String message) {
                EntityHandlerManager.handerEntity(typeEntity, message);

            }
        });
        ControlSendManager.connect();
    }

    private void initView() {
        mRegionView = (RegionView) findViewById(R.id.regionView);

        mRegionView.setListener(new RegionView.RegionViewClickListener() {

            @Override
            public void clickTop() {
               // showToast("view clickTop");
            }

            @Override
            public void clickRight() {
                //  showToast("view clickRight");
            }

            @Override
            public void clickRightDown() {
                //showToast("view clickRightwdown");
            }

            @Override
            public void clickLeft() {
              //  showToast("view clickLeft");
            }

            @Override
            public void clickCenter() {
             //   showToast("view clickCenter");
            }

            @Override
            public void clickBottom() {
              //  showToast("view clickBottom");
            }
        });
        pages = new ArrayList<Fragment>();
        mCoordinatorMenu = (CoordinatorMenu) findViewById(R.id.menu);
        viewpager = findViewById(R.id.viewpager);

        // create new fragments
        pages.add(new BaiduFragment());
        pages.add(new IndoorFragment());

        // set adapter
        viewpager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewpager.setOnPageChangeListener(this);
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

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

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


}
