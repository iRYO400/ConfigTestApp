
package madiyar.almaty.configtestapp.model;


import com.google.gson.annotations.SerializedName;



public class MarkModel {

    @SerializedName("code")
    private String mCode;
    @SerializedName("icon")
    private Icon mIcon;
    @SerializedName("identifier")
    private Double mIdentifier;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("title_ru")
    private String mTitleRu;

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public Icon getIcon() {
        return mIcon;
    }

    public void setIcon(Icon icon) {
        mIcon = icon;
    }

    public Double getIdentifier() {
        return mIdentifier;
    }

    public void setIdentifier(Double identifier) {
        mIdentifier = identifier;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitleRu() {
        return mTitleRu;
    }

    public void setTitleRu(String titleRu) {
        mTitleRu = titleRu;
    }

}
