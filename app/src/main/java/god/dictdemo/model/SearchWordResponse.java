package god.dictdemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchWordResponse {

    /**
     * translation : ["a."]
     * basic : {"us-phonetic":"eɪ","phonetic":"eɪ","uk-phonetic":"eɪ","explains":["art. 一；任一；每一"]}
     * query : a
     * errorCode : 0
     * web : [{"value":["无伴奏合唱","阿卡贝拉","走在大街的女子"],"key":"a cappella"},{"value":["一点","一些","一点儿"],"key":"A little"},{"value":["双酚A","双酚","酚甲烷"],"key":"Bisphenol A"}]
     */

    private BasicBean basic;
    private String query;
    private int errorCode;
    private List<String> translation;
    private List<WebBean> web;

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public List<WebBean> getWeb() {
        return web;
    }

    public void setWeb(List<WebBean> web) {
        this.web = web;
    }

    @Override
    public String toString() {
        return "SearchWordResponse{" +
                "basic=" + basic +
                ", query='" + query + '\'' +
                ", errorCode=" + errorCode +
                ", translation=" + translation +
                ", web=" + web +
                '}';
    }

    public static class BasicBean {
        /**
         * us-phonetic : eɪ
         * phonetic : eɪ
         * uk-phonetic : eɪ
         * explains : ["art. 一；任一；每一"]
         */

        @SerializedName("us-phonetic")
        private String usphonetic;
        private String phonetic;
        @SerializedName("uk-phonetic")
        private String ukphonetic;
        private List<String> explains;

        public String getUsphonetic() {
            return usphonetic;
        }

        public void setUsphonetic(String usphonetic) {
            this.usphonetic = usphonetic;
        }

        public String getPhonetic() {
            return phonetic;
        }

        public void setPhonetic(String phonetic) {
            this.phonetic = phonetic;
        }

        public String getUkphonetic() {
            return ukphonetic;
        }

        public void setUkphonetic(String ukphonetic) {
            this.ukphonetic = ukphonetic;
        }

        public List<String> getExplains() {
            return explains;
        }

        public void setExplains(List<String> explains) {
            this.explains = explains;
        }

        @Override
        public String toString() {
            return "BasicBean{" +
                    "usphonetic='" + usphonetic + '\'' +
                    ", phonetic='" + phonetic + '\'' +
                    ", ukphonetic='" + ukphonetic + '\'' +
                    ", explains=" + explains +
                    '}';
        }
    }

    public static class WebBean {
        /**
         * value : ["无伴奏合唱","阿卡贝拉","走在大街的女子"]
         * key : a cappella
         */

        private String key;
        private List<String> value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "WebBean{" +
                    "key='" + key + '\'' +
                    ", value=" + value +
                    '}';
        }
    }
}
