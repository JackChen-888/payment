package pay.payment.service;

import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * @author Chen
 */
public interface WxPayService {
    /**
     * nativePay
     *
     * @param productId /
     * @return /
     * @throws Exception /
     */
    Map<String, Object> nativePay(Long productId) throws Exception;

    /**
     * processOrder
     *
     * @param bodyMap /
     * @throws GeneralSecurityException /
     */
    void processOrder(Map<String, Object> bodyMap) throws GeneralSecurityException;

    /**
     * cancelOrder
     *
     * @param orderNo /
     * @throws Exception /
     */
    void cancelOrder(String orderNo) throws Exception;

    /**
     * queryOrder
     *
     * @param orderNo /
     * @return /
     * @throws Exception /
     */
    String queryOrder(String orderNo) throws Exception;

    /**
     * checkOrderStatus
     *
     * @param orderNo /
     * @throws Exception /
     */
    void checkOrderStatus(String orderNo) throws Exception;

    /**
     * refund
     *
     * @param orderNo /
     * @param reason  /
     * @throws Exception /
     */
    void refund(String orderNo, String reason) throws Exception;

    /**
     * queryRefund
     *
     * @param orderNo/
     * @return /
     * @throws Exception /
     */
    String queryRefund(String orderNo) throws Exception;

    /**
     * checkRefundStatus
     *
     * @param refundNo /
     * @throws Exception /
     */
    void checkRefundStatus(String refundNo) throws Exception;

    /**
     * processRefund
     *
     * @param bodyMap /
     * @throws Exception /
     */
    void processRefund(Map<String, Object> bodyMap) throws Exception;

    /**
     * queryBill
     *
     * @param billDate /
     * @param type     /
     * @return /
     * @throws Exception /
     */
    String queryBill(String billDate, String type) throws Exception;

    /**
     * downloadBill
     *
     * @param billDate /
     * @param type     /
     * @return /
     * @throws Exception /
     */
    String downloadBill(String billDate, String type) throws Exception;

    /**
     * nativePayV2
     *
     * @param productId  /
     * @param remoteAddr /
     * @return /
     * @throws Exception /
     */
    Map<String, Object> nativePayV2(Long productId, String remoteAddr) throws Exception;
}
