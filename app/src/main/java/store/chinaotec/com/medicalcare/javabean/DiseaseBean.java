package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by Administrator on 2017/3/30 0030.
 */

public class DiseaseBean {
    public String diseaseName;
    public String[] diseaseArry;
    public int[] diseaseLogo;

    public DiseaseBean(String diseaseName, String[] diseaseList, int[] diseaseLogoList) {
        this.diseaseName = diseaseName;
        this.diseaseArry = diseaseList;
        this.diseaseLogo = diseaseLogoList;
    }
}
