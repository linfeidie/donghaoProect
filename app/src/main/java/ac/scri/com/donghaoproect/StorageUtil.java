package ac.scri.com.donghaoproect;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by admin on 2017/8/23.
 *
 * 管理文件存储路径
 */

public class StorageUtil {

    /*
    *   图片存储路径为： storage/sdcard/Android/data/包名/files/Pictures
    * */
    public static String getImageStoragePath(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
    }

    /*
   *   返回头像文件存储文件
   * */
    public static File getAvatarStorageFile(Context context, String userId) {
        return new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +"/"+userId+ "head.jpg");
    }






    /*
   *   下载文件存储路径为： storage/sdcard/Android/data/包名/files/Download
   * */
    public static String getDownloadFileStoragePath(Context context) {
        String path = "";
        if (context != null && context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) != null) {
             path = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath();
        }
        return path;
    }
    /*
   *  音频存储路径为： storage/sdcard/Android/data/包名/files/Pictures
   *
   *  /storage/emulated/0/Android/data/cn.com.countrygarden.pcc/files/Audio/a0cf25a3-d7c2-44a5-aeab-b22471f44339.amr
   * */
    static String AUDIOPATH="Audio";
    public static String getAudioStoragePath(Context context) {
        return context.getExternalFilesDir(AUDIOPATH).getPath();
    }
    static String PCMPATH="Pcm";
    public static String getPcmStoragePath(Context context) {
        return context.getExternalFilesDir(PCMPATH).getPath();
    }


    static String MEDIAPATH="Media";
    public static String getMediaStoragePath(Context context) {
        return context.getExternalFilesDir(MEDIAPATH).getPath();
    }
}
