package com.itheima.controller;

import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception{
        List<Product> products = productService.findAll();
        ModelAndView mv = new ModelAndView();
        mv.addObject("products",products);
        mv.setViewName("product-list");
        return mv;
    }
    @RequestMapping("/save.do")
    public String addProduct(Product product){

        productService.addProduct(product);
        return "redirect:/product/findAll.do";
    }
}
