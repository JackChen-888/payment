package pay.payment.service;

import pay.payment.entity.OrderInfo;
import pay.payment.enums.OrderStatus;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Chen
 */
public interface OrderInfoService extends IService<OrderInfo> {

    /**
     * createOrderByProductId
     *
     * @param productId   /
     * @param paymentType /
     * @return /
     */
    OrderInfo createOrderByProductId(Long productId, String paymentType);

    /**
     * saveCodeUrl
     *
     * @param orderNo /
     * @param codeUrl /
     */
    void saveCodeUrl(String orderNo, String codeUrl);

    /**
     * listOrderByCreateTimeDesc
     *
     * @return /
     */
    List<OrderInfo> listOrderByCreateTimeDesc();

    /**
     * updateStatusByOrderNo
     *
     * @param orderNo     /
     * @param orderStatus /
     */
    void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus);

    /**
     * getOrderStatus
     *
     * @param orderNo /
     * @return /
     */
    String getOrderStatus(String orderNo);

    /**
     * getNoPayOrderByDuration
     *
     * @param minutes     /
     * @param paymentType /
     * @return /
     */
    List<OrderInfo> getNoPayOrderByDuration(int minutes, String paymentType);

    /**
     * getOrderByOrderNo
     *
     * @param orderNo /
     * @return /
     */
    OrderInfo getOrderByOrderNo(String orderNo);
}
