package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by hxk on 2017/9/14 0014 15:35
 * sentence合成问题接口返回的数据
 */

public class QuestionBean {
    /**
     * action : question
     * question : 请问您是否右腹部触痛？
     */

    private String action;
    private String question;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
