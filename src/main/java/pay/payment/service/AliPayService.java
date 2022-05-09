package pay.payment.service;

import java.util.Map;

/**
 * @author Chen
 */
public interface AliPayService {
    /**
     * tradeCreate
     *
     * @param productId /
     * @return /
     */
    String tradeCreate(Long productId);

    /**
     * processOrder
     *
     * @param params /
     */
    void processOrder(Map<String, String> params);

    /**
     * cancelOrder
     *
     * @param orderNo /
     */
    void cancelOrder(String orderNo);

    /**
     * queryOrder
     *
     * @param orderNo /
     * @return /
     */
    String queryOrder(String orderNo);

    /**
     * checkOrderStatus
     *
     * @param orderNo /
     */
    void checkOrderStatus(String orderNo);

    /**
     * refund
     *
     * @param orderNo /
     * @param reason  /
     */
    void refund(String orderNo, String reason);

    /**
     * queryRefund
     *
     * @param orderNo /
     * @return /
     */
    String queryRefund(String orderNo);

    /**
     * queryBill
     *
     * @param billDate /
     * @param type     /
     * @return /
     */
    String queryBill(String billDate, String type);

}
