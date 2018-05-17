package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by wjc on 2017/9/23 0023.
 */
public class KnowledgeResponse {
    public String msg;
    public String responseCode;
    public List<Knowledge> data;


    public class Knowledge {
        private String lexiconName;
        private String contentUrl;
        private int id;

        public String getLexiconName() {
            return lexiconName;
        }

        public void setLexiconName(String lexiconName) {
            this.lexiconName = lexiconName;
        }

        public String getContentUrl() {
            return contentUrl;
        }

        public void setContentUrl(String contentUrl) {
            this.contentUrl = contentUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
