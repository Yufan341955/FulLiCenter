package ucai.cn.fulicenter.bean;

/**
 * Created by Administrator on 2016/10/13.
 */
public class Result {

    /**
     * retCode : 0
     * retMsg : true
     * retData : null
     */

    private int retCode;
    private boolean retMsg;
    private RetData retData;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public boolean isRetMsg() {
        return retMsg;
    }

    public void setRetMsg(boolean retMsg) {
        this.retMsg = retMsg;
    }

    public RetData getRetData() {
        return retData;
    }

    public void setRetData(RetData retData) {
        this.retData = retData;
    }

    public Result() {
    }

    @Override
    public String toString() {
        return "Result{" +
                "retCode=" + retCode +
                ", retMsg=" + retMsg +
                ", retData=" + retData +
                '}';
    }
}
