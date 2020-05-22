package cn.hcnet2006.mmall.mmall.controller.portal;
import cn.hcnet2006.mmall.mmall.common.ServerResponse;
import cn.hcnet2006.mmall.mmall.service.IProductService;
import cn.hcnet2006.mmall.mmall.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 前台商品
 */
@Api(tags = "前台商品接口")
@Controller
@RequestMapping("/product/")
public class productController {
    @Autowired
    private IProductService productService;

    @RequestMapping(value = "detail.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        return productService.getProductDetail(productId);
    }
    @RequestMapping(value = "list.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword",required = false) String keyword, @RequestParam(value = "categoryId",required = false) Integer categoryId
            ,@RequestParam("orderBy") String orderBy ,@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10")int pageSize){
        return productService.getProductByKeywordCategory(keyword,categoryId,orderBy,pageNum,pageSize);
    }

}
