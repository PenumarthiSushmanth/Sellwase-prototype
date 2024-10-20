package com.sellwase.Sellwase.Service;

import com.sellwase.Sellwase.Model.Filters;
import com.sellwase.Sellwase.Model.Products;
import com.sellwase.Sellwase.Model.WishList;
import com.sellwase.Sellwase.Repository.ProductsRepo;
import com.sellwase.Sellwase.Repository.WishListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsService {

    @Autowired
    ProductsRepo productsRepo;

    @Autowired
    WishListRepo wishListRepo;

    public static String uploadProductDir = System.getProperty("user.dir") + "/src/main/Images/ProductImages";


    public static String uploadDirec = System.getProperty("user.dir") + "/src/main/Images/ProductImages";
    public void addProduct(String pname, int price, boolean isGiveAway,
                           String prodCategory, String condition,
                           String prodDescription, boolean isDelivery, int uid,
                           MultipartFile pic1, MultipartFile pic2, MultipartFile pic3)
    {
        Date date = new Date();

        Products p = new Products();
        p.setProdName(pname);
        p.setPrice(price);
        p.setGiveAway(isGiveAway);
        p.setProdCategory(prodCategory);
        p.setCondition(condition);
        p.setProdDescription(prodDescription);
        p.setDelivery(isDelivery);
        p.setUid(uid);
        p.setDate(date);


        Products prod = productsRepo.save(p);
        String[] imgNames = savingProdImages(prod.getProdID(), prod.getUid(), pic1, pic2, pic3);

        productsRepo.updateProdImage(imgNames[0], imgNames[1], imgNames[2], p.getProdID());

    }


    public String[] savingProdImages(int prodID, int uid, MultipartFile pic1, MultipartFile pic2, MultipartFile pic3)
    {

        String pic1orginalName = uid + "_" + prodID +"_1.png";
        String pic2orginalName = uid + "_" + prodID +"_2.png";
        String pic3orginalName = uid + "_" + prodID +"_3.png";

        Path file1NameAndPath = Paths.get(uploadDirec, pic1orginalName);
        Path file2NameAndPath = Paths.get(uploadDirec, pic2orginalName);
        Path file3NameAndPath = Paths.get(uploadDirec, pic3orginalName);


        try {
            Files.write(file1NameAndPath, pic1.getBytes());
            Files.write(file2NameAndPath, pic2.getBytes());
            Files.write(file3NameAndPath, pic3.getBytes());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(file1NameAndPath);
        System.out.println(file2NameAndPath);
        System.out.println(file3NameAndPath);

        return new String[]{pic1orginalName, pic2orginalName, pic3orginalName};

    }


    public void updateProductDetails(int prodId, String pname, int price, boolean isGiveAway,
                                     String prodCategory, String condition,
                                     String prodDescription, boolean isDelivery, int uid,
                                     MultipartFile pic1, MultipartFile pic2, MultipartFile pic3)
    {

        String[] imgNames = savingProdImages(prodId, uid, pic1, pic2, pic3);

//        System.out.println(imgNames[0] + imgNames[1] + imgNames[2]);

        productsRepo.updateProdDetails(prodId, pname, price, isGiveAway, prodCategory, condition, prodDescription, isDelivery);


    }
    public List<Products> getAllProducts()
    {
        return productsRepo.findAll();
    }


    public List<Products> getMyProducts(int uid) {

        List<Products> myProds = productsRepo.getProductsById(uid);
        return myProds;
    }

    public void addToWishList(WishList wishList) {
        wishListRepo.save(wishList);
    }

    public List<Products> getMyWishList(int uid) {
        List<Products> myWishLst = wishListRepo.getMyWishList(uid);

        return myWishLst;
    }

    public void deleteProduct(int prodId) {

        Products myProd = productsRepo.findById(prodId).orElse(null);

        if(myProd != null)
        {

            File pic1 = new File(uploadProductDir + "/" + myProd.getProdImage1());
            File pic2 = new File(uploadProductDir + "/" + myProd.getProdImage2());
            File pic3 = new File(uploadProductDir + "/" + myProd.getProdImage3());

            System.out.println(pic3);

            pic1.delete();
            pic2.delete();
            pic3.delete();

            productsRepo.deleteById(prodId);
            wishListRepo.deleteProduct(prodId);
        }
    }

    public void deleteFromWishList(int prodId, int uid) {
        wishListRepo.deleteProductFromWishLst(prodId, uid);
    }


    public List<Products> getFilteredProds(Filters filter) {

        List<Products> allProds = getAllProducts();

        List<Products> filteredProducts = new ArrayList<>();

        for(Products prod : allProds)
        {
            if(filter.isGiveAway())
            {
                if(prod.isGiveAway())
                {
                    filteredProducts.add(prod);
                }
            }
            else{
                if(prod.getPrice() >= filter.getMinPrice() && prod.getPrice() <= filter.getMaxPrice() && !prod.isGiveAway())
                {
                    filteredProducts.add(prod);
                }
            }
        }

        if (!"All".equals(filter.getCategory())) {
            filteredProducts = filteredProducts.stream()
                    .filter(prod -> filter.getCategory().equals(prod.getProdCategory()))
                    .collect(Collectors.toList());
        }

        Iterator<Products> iterator = filteredProducts.iterator();
        while (iterator.hasNext()) {
            Products prod = iterator.next();
            if (prod.isDelivery() != filter.isDelivery()) {
                iterator.remove();
            }
        }

        return filteredProducts;

    }

}


