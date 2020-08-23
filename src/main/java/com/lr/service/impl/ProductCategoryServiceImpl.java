package com.lr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lr.entity.ProductCategory;
import com.lr.mapper.ProductCategoryMapper;
import com.lr.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lr.service.ProductService;
import com.lr.vo.ProductCategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr.li
 * @since 2020-07-25
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper mapper;

    @Autowired
    private ProductService productService;
    @Override
    public List<ProductCategoryVo> findAll() {
        //查询出所有的一级，二级，三级分类
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("type",1);
        //一级分类对应实体类数据
        List<ProductCategory> levelOneList = this.mapper.selectList(wrapper);
        //转为VO对象
        List<ProductCategoryVo> levelOneVOList = new ArrayList<>();
        int i = 0;
        for (ProductCategory productCategory : levelOneList) {
            ProductCategoryVo oneVO = new ProductCategoryVo();
            oneVO.setId(productCategory.getId());
            oneVO.setName(productCategory.getName());
            levelOneVOList.add(oneVO);
            //找到属于我的二级分类
            wrapper = new QueryWrapper();
            wrapper.eq("type", 2);
            wrapper.eq("parent_id", oneVO.getId());
            //二级分类对应实体类数据
            List<ProductCategory> levelTwoList = this.mapper.selectList(wrapper);
            //转为VO对象
            List<ProductCategoryVo> levelTwoVOList = new ArrayList<>();
            for (ProductCategory productCategory2 : levelTwoList) {
                ProductCategoryVo twoVO = new ProductCategoryVo();
                twoVO.setId(productCategory2.getId());
                twoVO.setName(productCategory2.getName());
                levelTwoVOList.add(twoVO);
                //三级分类对应实体类数据
                wrapper = new QueryWrapper();
                wrapper.eq("type", 3);
                wrapper.eq("parent_id", twoVO.getId());
                List<ProductCategory> levelThreeList = this.mapper.selectList(wrapper);
                //转为VO对象
                List<ProductCategoryVo> levelThreeVOList = new ArrayList<>();
                for (ProductCategory productCategory3 : levelThreeList) {
                    ProductCategoryVo threeVO = new ProductCategoryVo();
                    threeVO.setId(productCategory3.getId());
                    threeVO.setName(productCategory3.getName());
                    levelThreeVOList.add(threeVO);
                }
                twoVO.setChildren(levelThreeVOList);
            }
            oneVO.setChildren(levelTwoVOList);
            oneVO.setBannerImg("banner"+i+".png");
            oneVO.setTopImg("top"+i+".png");
            i++;
            //查询商品
            oneVO.setProducts(this.productService.findByLevle(1, oneVO.getId()));
        }
        return levelOneVOList;
    }
}
