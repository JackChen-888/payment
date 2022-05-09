package pay.payment.service;

import pay.payment.entity.RefundInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Chen
 */
public interface RefundInfoService extends IService<RefundInfo> {

    /**
     * createRefundByOrderNo
     *
     * @param orderNo /
     * @param reason  /
     * @return /
     */
    RefundInfo createRefundByOrderNo(String orderNo, String reason);

    /**
     * updateRefund
     *
     * @param content /
     */
    void updateRefund(String content);

    /**
     * getNoRefundOrderByDuration
     *
     * @param minutes /
     * @return /
     */
    List<RefundInfo> getNoRefundOrderByDuration(int minutes);

    /**
     * createRefundByOrderNoForAliPay
     *
     * @param orderNo /
     * @param reason  /
     * @return /
     */
    RefundInfo createRefundByOrderNoForAliPay(String orderNo, String reason);

    /**
     * updateRefundForAliPay
     *
     * @param refundNo     /
     * @param content      /
     * @param refundStatus /
     */
    void updateRefundForAliPay(String refundNo, String content, String refundStatus);
}
