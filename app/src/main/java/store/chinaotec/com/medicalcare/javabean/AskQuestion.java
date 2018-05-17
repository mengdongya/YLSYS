package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by wjc on 2018/3/9 0009.
 */

public class AskQuestion {

    private String responseCode;
    private String msg;
    private DataBean data;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private String finish;
        private String action;
        private String conclusion;
        private String question;

        public String getFinish() {
            return finish;
        }

        public void setFinish(String finish) {
            this.finish = finish;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getConclusion() {
            return conclusion;
        }

        public void setConclusion(String conclusion) {
            this.conclusion = conclusion;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        @Override
        public String toString() {
            return "AskQuestion{" +
                    "finish='" + finish + '\'' +
                    ", action='" + action + '\'' +
                    ", conclusion='" + conclusion + '\'' +
                    ", question='" + question + '\'' +
                    '}';
        }
    }
}
