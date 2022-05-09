package pay.payment.service;

import java.util.Map;

/**
 * @author Chen
 */
public interface PaymentInfoService {

    /**
     * createPaymentInfo
     *
     * @param plainText /
     */
    void createPaymentInfo(String plainText);

    /**
     * createPaymentInfoForAliPay
     *
     * @param params /
     */
    void createPaymentInfoForAliPay(Map<String, String> params);
}
