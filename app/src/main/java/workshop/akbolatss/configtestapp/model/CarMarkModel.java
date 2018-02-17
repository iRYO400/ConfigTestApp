
package workshop.akbolatss.configtestapp.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarMarkModel {

    @SerializedName("command")
    private String mCommand;

    @SerializedName("list")
    private List<MarkModel> mList;

    @SerializedName("status")
    private Long mStatus;

    public String getCommand() {
        return mCommand;
    }


    public List<MarkModel> getList() {
        return mList;
    }

    public Long getStatus() {
        return mStatus;
    }
}
