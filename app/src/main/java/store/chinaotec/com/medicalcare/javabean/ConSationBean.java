package store.chinaotec.com.medicalcare.javabean;


/**
 * Created by hxk on 2017/7/25 0025 15:36
 * 用户和后台沟通数据解析
 */

public class ConSationBean {
    public int finish;
    public String nextquestion;
    public String name;
    public boolean tag;
    public String question;
    public String answer;

    public ConSationBean(int finish, String nextquestion, String name, boolean tag, String question, String answer) {
        this.finish = finish;
        this.nextquestion = nextquestion;
        this.name = name;
        this.tag = tag;
        this.question = question;
        this.answer = answer;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public String getNextquestion() {
        return nextquestion;
    }

    public void setNextquestion(String nextquestion) {
        this.nextquestion = nextquestion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "ConSationBean{" +
                "finish=" + finish +
                ", nextquestion='" + nextquestion + '\'' +
                ", name='" + name + '\'' +
                ", tag=" + tag +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
