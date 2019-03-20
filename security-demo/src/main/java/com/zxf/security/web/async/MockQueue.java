package com.zxf.security.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Create by Mr.ZXF
 * on 2019-03-20 15:51
 */
@Component
public class MockQueue {

    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 下单信息
     */
    private String placeOrder;

    /**
     * 订单完成消息
     */
    private String completeOrder;

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) {
        new Thread(() -> {
            logger.info("接到下单请求" + placeOrder);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.completeOrder = placeOrder; /*处理订单，给完成订单参数赋值*/
            logger.info("下单请求处理完毕" + placeOrder);
        }).start();
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
