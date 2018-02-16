
package madiyar.almaty.configtestapp.model;

import com.google.gson.annotations.SerializedName;


public class Icon {

    @SerializedName("big")
    private String mBig;
    @SerializedName("code")
    private String mCode;
    @SerializedName("filtered")
    private String mFiltered;
    @SerializedName("normal")
    private String mNormal;
    @SerializedName("small")
    private String mSmall;

    public String getBig() {
        return mBig;
    }

    public void setBig(String big) {
        mBig = big;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getFiltered() {
        return mFiltered;
    }

    public void setFiltered(String filtered) {
        mFiltered = filtered;
    }

    public String getNormal() {
        return mNormal;
    }

    public void setNormal(String normal) {
        mNormal = normal;
    }

    public String getSmall() {
        return mSmall;
    }

    public void setSmall(String small) {
        mSmall = small;
    }

}
