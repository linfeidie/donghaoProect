package ac.scri.com.donghaoproect;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/23
 * <p>
 * 版本号：donghaoProect
 */
public class IndoorFragment extends Fragment {

    private PinchImageView iv_bitmap;

    private DataWatcher watcher=new DataWatcher() {
        @Override
        public void notifyUpdata(Object data) {
            if(data instanceof DataEntity) {
                DataEntity dataEntity = (DataEntity)data;
                if(dataEntity.getType().equalsIgnoreCase(Contanst.MAP_DATA)) {
                    Tools.showToast("地图数据");
                    Log.e("linfd","地图数据");
                }
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataChanger.getInstance().addObserver(watcher);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View rootView  = inflater.inflate(R.layout.fragment_indoor,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv_bitmap = view.findViewById(R.id.iv_bitmap);
    }

    @Override
    public void onStart() {
        super.onStart();
        Rect rect = new Rect(1264,220,0,0);
        ComBitmapManager.getInstance().startComposite(rect, new ComBitmapManager.CompositeMapListener() {
            @Override
            public void compositeMapCallBack(Bitmap mapComposite) {
                iv_bitmap.setImageBitmap(mapComposite);
            }
        });
    }
}
