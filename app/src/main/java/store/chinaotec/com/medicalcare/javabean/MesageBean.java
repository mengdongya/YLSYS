package store.chinaotec.com.medicalcare.javabean;

import android.net.Uri;

/**
 * Created by hxk on 2017/7/27 0027 10:35
 * 自主诊疗沟通信息类
 */

public class MesageBean {
    public static int RECEIVE_TYPE = 10;
    public static int SEND_TYPE = 20;
    public static int PHOTO_TYPE = 30;
    public int type;
    public String content;
    public Uri uri;

    public MesageBean(int type, String content, Uri uri) {
        this.type = type;
        this.content = content;
        this.uri = uri;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
