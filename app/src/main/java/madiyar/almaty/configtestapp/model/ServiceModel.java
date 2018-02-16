
package madiyar.almaty.configtestapp.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ServiceModel {

    @SerializedName("command")
    private String mCommand;
    @SerializedName("serviceCategories")
    private List<ServiceCategory> mServiceCategories;
    @SerializedName("status")
    private Long mStatus;

    public String getCommand() {
        return mCommand;
    }

    public void setCommand(String command) {
        mCommand = command;
    }

    public List<ServiceCategory> getServiceCategories() {
        return mServiceCategories;
    }

    public void setServiceCategories(List<ServiceCategory> serviceCategories) {
        mServiceCategories = serviceCategories;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}
