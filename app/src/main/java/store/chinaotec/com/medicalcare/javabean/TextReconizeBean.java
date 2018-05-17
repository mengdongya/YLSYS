package store.chinaotec.com.medicalcare.javabean;


/**
 * Created by hxk on 2017/9/13 0013 19:19
 * 添加图片宽,高,ocr解析图片返回的json字符串的java对象
 */

public class TextReconizeBean {
    private int width;
    private int height;
    private String ocr_result;

    public TextReconizeBean(int width, int height, String ocr_result) {
        this.width = width;
        this.height = height;
        this.ocr_result = ocr_result;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getOcr_result() {
        return ocr_result;
    }

    public void setOcr_result(String ocr_result) {
        this.ocr_result = ocr_result;
    }

    @Override
    public String toString() {
        return "TextReconizeBean{" +
                "width=" + width +
                ", height=" + height +
                ", ocr_result='" + ocr_result + '\'' +
                '}';
    }
}
