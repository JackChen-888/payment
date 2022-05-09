package pay.payment.service.impl;

import pay.payment.entity.Product;
import pay.payment.mapper.ProductMapper;
import pay.payment.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Chen
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
