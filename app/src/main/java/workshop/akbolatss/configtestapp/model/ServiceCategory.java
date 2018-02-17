
package workshop.akbolatss.configtestapp.model;


import com.google.gson.annotations.SerializedName;


public class ServiceCategory {

    @SerializedName("code")
    private String mCode;
    @SerializedName("count")
    private Long mCount;
    @SerializedName("icon")
    private Icon mIcon;
    @SerializedName("identifier")
    private Long mIdentifier;
    @SerializedName("thumbnail")
    private Thumbnail mThumbnail;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("unreadResponses")
    private Long mUnreadResponses;

    private boolean isSelected;

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public Long getCount() {
        return mCount;
    }

    public void setCount(Long count) {
        mCount = count;
    }

    public Icon getIcon() {
        return mIcon;
    }

    public void setIcon(Icon icon) {
        mIcon = icon;
    }

    public Long getIdentifier() {
        return mIdentifier;
    }

    public void setIdentifier(Long identifier) {
        mIdentifier = identifier;
    }

    public Thumbnail getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        mThumbnail = thumbnail;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Long getUnreadResponses() {
        return mUnreadResponses;
    }

    public void setUnreadResponses(Long unreadResponses) {
        mUnreadResponses = unreadResponses;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
