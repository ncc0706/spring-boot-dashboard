package io.arukas.endpoint;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA. <br/>
 *
 * @Auther: 牛玉贤 <br/>
 * @Date: 2018/10/25 11:03 <br/>
 * @Email: ncc0706@gmail.com <br/>
 */
@RestController
public class NotifyEndpoint {


    @PostMapping(value = "demo")
    public String demo(@RequestBody Result result) {
        return "success";
    }


}

class Result {

    private String mid;
    private String respMsg;
    private String respCode;
    private String orderCode;
    private String orderTime;
    private String totalAmount;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}