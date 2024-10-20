package com.sellwase.Sellwase.Controllers;

import com.sellwase.Sellwase.Model.Filters;
import com.sellwase.Sellwase.Model.Products;
import com.sellwase.Sellwase.Model.WishList;
import com.sellwase.Sellwase.Service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ProductsController {

    @Autowired
    ProductsService productsService;

    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestParam("prodName") String pname,
                                             @RequestParam("price") int price,
                                             @RequestParam("isGiveAway") boolean isGiveAway,
                                             @RequestParam("prodCategory") String prodCategory,
                                             @RequestParam("condition") String condition,
                                             @RequestParam("prodDescription") String prodDescription,
                                             @RequestParam("isDelivery") boolean isDelivery,
                                             @RequestParam("uid") int uid,
                                             @RequestParam("prodImage1") MultipartFile pic1,
                                             @RequestParam("prodImage2") MultipartFile pic2,
                                             @RequestParam("prodImage3") MultipartFile pic3)
    {

        productsService.addProduct(pname, price, isGiveAway, prodCategory, condition, prodDescription, isDelivery, uid, pic1, pic2, pic3);

        return new ResponseEntity<>("Product added succesfully!", HttpStatus.OK);
    }


    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Products>> getAllProducts()
    {
        List<Products> prodLst = productsService.getAllProducts();
        return new ResponseEntity<>(prodLst, HttpStatus.OK);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<String> updateProduct(@RequestParam("prodID") int prodId,
                                             @RequestParam("prodName") String pname,
                                             @RequestParam("price") int price,
                                             @RequestParam("isGiveAway") boolean isGiveAway,
                                             @RequestParam("prodCategory") String prodCategory,
                                             @RequestParam("condition") String condition,
                                             @RequestParam("prodDescription") String prodDescription,
                                             @RequestParam("isDelivery") boolean isDelivery,
                                             @RequestParam("uid") int uid,
                                             @RequestParam("prodImage1") MultipartFile pic1,
                                             @RequestParam("prodImage2") MultipartFile pic2,
                                             @RequestParam("prodImage3") MultipartFile pic3)
    {

        productsService.updateProductDetails(prodId, pname, price, isGiveAway, prodCategory, condition, prodDescription, isDelivery, uid, pic1, pic2, pic3);

        return new ResponseEntity<>("Product Updated succesfully!", HttpStatus.OK);
    }


    @GetMapping("/myProducts/{uid}")
    public ResponseEntity<List<Products>> getMyProducts(@PathVariable("uid") int uid)
    {
        List<Products> myProds = productsService.getMyProducts(uid);
        return new ResponseEntity<>(myProds, HttpStatus.OK);
    }


    @DeleteMapping("/deleteProduct/{prodId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("prodId") int prodId)
    {
        productsService.deleteProduct(prodId);
        return new ResponseEntity<>("Product deleted Successfully!", HttpStatus.OK);
    }



    @PostMapping("/addToMyWishList")
    public ResponseEntity<String> addToWishList(@RequestBody WishList wishList)
    {
        productsService.addToWishList(wishList);
        return new ResponseEntity<>("Product added to my wishlist", HttpStatus.OK);
    }


    @GetMapping("/myWishList/{uid}")
    public ResponseEntity<List<Products>> myWishList(@PathVariable("uid") int uid)
    {
        List<Products> myWishList = productsService.getMyWishList(uid);
        return new ResponseEntity<>(myWishList, HttpStatus.OK);
    }

    @DeleteMapping("/removeFromWishList/{prodId}/{uid}")
    public ResponseEntity<String> deleteFromWishList(@PathVariable("prodId") int prodId, @PathVariable("uid") int uid)
    {
        productsService.deleteFromWishList(prodId, uid);
        return new ResponseEntity<>("Product Removed Successfully!", HttpStatus.OK);
    }


    @PostMapping("/filteredProducts")
    public ResponseEntity<List<Products>> getFilteredProd(@RequestBody Filters filters)
    {
        List<Products> filteredProdLst = productsService.getFilteredProds(filters);
        return new ResponseEntity<>(filteredProdLst, HttpStatus.OK);
    }
}
