package pay.payment.service.impl;

import pay.payment.entity.OrderInfo;
import pay.payment.entity.Product;
import pay.payment.enums.OrderStatus;
import pay.payment.mapper.OrderInfoMapper;
import pay.payment.mapper.ProductMapper;
import pay.payment.service.OrderInfoService;
import pay.payment.util.OrderNoUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * @author Chen
 */
@Service
@Slf4j
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Resource
    private ProductMapper productMapper;


    @Override
    public OrderInfo createOrderByProductId(Long productId, String paymentType) {

        //查找已存在但未支付的订单
        OrderInfo orderInfo = this.getNoPayOrderByProductId(productId, paymentType);
        if (orderInfo != null) {
            return orderInfo;
        }

        //获取商品信息
        Product product = productMapper.selectById(productId);

        //生成订单
        orderInfo = new OrderInfo();
        orderInfo.setTitle(product.getTitle());
        //订单号
        orderInfo.setOrderNo(OrderNoUtils.getOrderNo());
        orderInfo.setProductId(productId);
        //分
        orderInfo.setTotalFee(product.getPrice());
        //未支付
        orderInfo.setOrderStatus(OrderStatus.NOTPAY.getType());
        orderInfo.setPaymentType(paymentType);
        baseMapper.insert(orderInfo);

        return orderInfo;
    }

    /**
     * 存储订单二维码
     *
     * @param orderNo /
     * @param codeUrl /
     */
    @Override
    public void saveCodeUrl(String orderNo, String codeUrl) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCodeUrl(codeUrl);

        baseMapper.update(orderInfo, queryWrapper);
    }

    /**
     * 查询订单列表，并倒序查询
     *
     * @return /
     */
    @Override
    public List<OrderInfo> listOrderByCreateTimeDesc() {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<OrderInfo>().orderByDesc("create_time");
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 根据订单号更新订单状态
     *
     * @param orderNo     /
     * @param orderStatus /
     */
    @Override
    public void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus) {

        log.info("更新订单状态 ===> {}", orderStatus.getType());

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderStatus(orderStatus.getType());

        baseMapper.update(orderInfo, queryWrapper);
    }

    /**
     * 根据订单号获取订单状态
     *
     * @param orderNo /
     * @return /
     */
    @Override
    public String getOrderStatus(String orderNo) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        OrderInfo orderInfo = baseMapper.selectOne(queryWrapper);
        if (orderInfo == null) {
            return null;
        }
        return orderInfo.getOrderStatus();
    }

    /**
     * 查询创建超过minutes分钟并且未支付的订单
     *
     * @param minutes /
     * @return /
     */
    @Override
    public List<OrderInfo> getNoPayOrderByDuration(int minutes, String paymentType) {

        Instant instant = Instant.now().minus(Duration.ofMinutes(minutes));

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_status", OrderStatus.NOTPAY.getType());
        queryWrapper.le("create_time", instant);
        queryWrapper.eq("payment_type", paymentType);

        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 根据订单号获取订单
     *
     * @param orderNo /
     * @return /
     */
    @Override
    public OrderInfo getOrderByOrderNo(String orderNo) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);

        return baseMapper.selectOne(queryWrapper);
    }


    /**
     * 根据商品id查询未支付订单
     * 防止重复创建订单对象
     *
     * @param productId /
     * @return /
     */
    private OrderInfo getNoPayOrderByProductId(Long productId, String paymentType) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        queryWrapper.eq("order_status", OrderStatus.NOTPAY.getType());
        queryWrapper.eq("payment_type", paymentType);
        return baseMapper.selectOne(queryWrapper);
    }
}