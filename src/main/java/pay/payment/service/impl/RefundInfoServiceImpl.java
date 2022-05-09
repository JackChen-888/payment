package pay.payment.service.impl;

import pay.payment.entity.OrderInfo;
import pay.payment.entity.RefundInfo;
import pay.payment.enums.wxpay.WxRefundStatus;
import pay.payment.mapper.RefundInfoMapper;
import pay.payment.service.OrderInfoService;
import pay.payment.service.RefundInfoService;
import pay.payment.util.OrderNoUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chen
 */
@Service
public class RefundInfoServiceImpl extends ServiceImpl<RefundInfoMapper, RefundInfo> implements RefundInfoService {

    private static final String STATUS = "status";

    private static final String REFUND_STATUS = "refund_status";

    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 根据订单号创建退款订单
     *
     * @param orderNo /
     * @return /
     */
    @Override
    public RefundInfo createRefundByOrderNo(String orderNo, String reason) {

        //根据订单号获取订单信息
        OrderInfo orderInfo = orderInfoService.getOrderByOrderNo(orderNo);

        //根据订单号生成退款订单
        RefundInfo refundInfo = new RefundInfo();
        //订单编号
        refundInfo.setOrderNo(orderNo);
        //退款单编号
        refundInfo.setRefundNo(OrderNoUtils.getRefundNo());
        //原订单金额(分)
        refundInfo.setTotalFee(orderInfo.getTotalFee());
        //退款金额(分)
        refundInfo.setRefund(orderInfo.getTotalFee());
        //退款原因
        refundInfo.setReason(reason);

        //保存退款订单
        baseMapper.insert(refundInfo);

        return refundInfo;
    }


    /**
     * 记录退款记录
     *
     * @param content /
     */
    @Override
    public void updateRefund(String content) {

        //将json字符串转换成Map
        Gson gson = new Gson();
        Map<String, String> resultMap = gson.fromJson(content, HashMap.class);

        //根据退款单编号修改退款单
        QueryWrapper<RefundInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("refund_no", resultMap.get("out_refund_no"));

        //设置要修改的字段
        RefundInfo refundInfo = new RefundInfo();

        //微信支付退款单号
        refundInfo.setRefundId(resultMap.get("refund_id"));

        //查询退款和申请退款中的返回参数
        if (resultMap.get(STATUS) != null) {
            //退款状态
            refundInfo.setRefundStatus(resultMap.get("status"));
            //将全部响应结果存入数据库的content字段
            refundInfo.setContentReturn(content);
        }
        //退款回调中的回调参数
        if (resultMap.get(REFUND_STATUS) != null) {
            //退款状态
            refundInfo.setRefundStatus(resultMap.get("refund_status"));
            //将全部响应结果存入数据库的content字段
            refundInfo.setContentNotify(content);
        }

        //更新退款单
        baseMapper.update(refundInfo, queryWrapper);
    }

    /**
     * 找出申请退款超过minutes分钟并且未成功的退款单
     *
     * @param minutes /
     * @return /
     */
    @Override
    public List<RefundInfo> getNoRefundOrderByDuration(int minutes) {

        //minutes分钟之前的时间
        Instant instant = Instant.now().minus(Duration.ofMinutes(minutes));

        QueryWrapper<RefundInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("refund_status", WxRefundStatus.PROCESSING.getType());
        queryWrapper.le("create_time", instant);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 根据订单号创建退款订单
     *
     * @param orderNo /
     * @return /
     */
    @Override
    public RefundInfo createRefundByOrderNoForAliPay(String orderNo, String reason) {

        //根据订单号获取订单信息
        OrderInfo orderInfo = orderInfoService.getOrderByOrderNo(orderNo);

        //根据订单号生成退款订单
        RefundInfo refundInfo = new RefundInfo();
        //订单编号
        refundInfo.setOrderNo(orderNo);
        //退款单编号
        refundInfo.setRefundNo(OrderNoUtils.getRefundNo());

        //原订单金额(分)
        refundInfo.setTotalFee(orderInfo.getTotalFee());
        //退款金额(分)
        refundInfo.setRefund(orderInfo.getTotalFee());
        //退款原因
        refundInfo.setReason(reason);

        //保存退款订单
        baseMapper.insert(refundInfo);

        return refundInfo;
    }

    /**
     * 更新退款记录
     *
     * @param refundNo     /
     * @param content      /
     * @param refundStatus /
     */
    @Override
    public void updateRefundForAliPay(String refundNo, String content, String refundStatus) {

        //根据退款单编号修改退款单
        QueryWrapper<RefundInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("refund_no", refundNo);

        //设置要修改的字段
        RefundInfo refundInfo = new RefundInfo();
        //退款状态
        refundInfo.setRefundStatus(refundStatus);
        //将全部响应结果存入数据库的content字段
        refundInfo.setContentReturn(content);

        //更新退款单
        baseMapper.update(refundInfo, queryWrapper);

    }

}
