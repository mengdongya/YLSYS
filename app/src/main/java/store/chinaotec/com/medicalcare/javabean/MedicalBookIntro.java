package store.chinaotec.com.medicalcare.javabean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wjc on 2018/3/1 0001.
 */

public class MedicalBookIntro  implements Serializable{
    private Data data;
    private String msg;
    private String responseCode;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public static class Data implements Serializable{
        private MedicalBookDetailDto medicalBookDetailDto;

        public MedicalBookDetailDto getMedicalBookDetailDto() {
            return medicalBookDetailDto;
        }

        public void setMedicalBookDetailDto(MedicalBookDetailDto medicalBookDetailDto) {
            this.medicalBookDetailDto = medicalBookDetailDto;
        }

        public static class MedicalBookDetailDto implements Serializable{
            private String name;
            private String img;
            private String detail;
            private String author;
            private List<MedicalBookLists> medicalBookLists;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public List<MedicalBookLists> getMedicalBookLists() {
                return medicalBookLists;
            }

            public void setMedicalBookLists(List<MedicalBookLists> medicalBookLists) {
                this.medicalBookLists = medicalBookLists;
            }

            public String getName() {

                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public static class MedicalBookLists implements Serializable{
                private int id;
                private int bookId;
                private String title;
                private String img;
                private String url;
                private int impact;
                private int type;
                private long createTime;
                private long updateTime;
                private String body;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getBookId() {
                    return bookId;
                }

                public void setBookId(int bookId) {
                    this.bookId = bookId;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public int getImpact() {
                    return impact;
                }

                public void setImpact(int impact) {
                    this.impact = impact;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public long getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(long updateTime) {
                    this.updateTime = updateTime;
                }

                public String getBody() {
                    return body;
                }

                public void setBody(String body) {
                    this.body = body;
                }
            }
        }
    }
}
