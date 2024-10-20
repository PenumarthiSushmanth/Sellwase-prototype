package com.sellwase.Sellwase.Repository;

import com.sellwase.Sellwase.Model.Products;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Products p SET p.prodImage1 = ?1, p.prodImage2 = ?2, p.prodImage3 = ?3 WHERE p.prodID = ?4")
    void updateProdImage(String pic1orginalName, String pic2orginalName, String pic3orginalName, int prodID);

    @Modifying
    @Transactional
    @Query("UPDATE Products p SET p.prodName = ?2, p.price = ?3, p.isGiveAway = ?4, p.prodCategory = ?5, p.condition = ?6, p.prodDescription = ?7, p.isDelivery = ?8 WHERE p.prodID = ?1")
    void updateProdDetails(int prodId, String pname, int price, boolean isGiveAway, String prodCategory, String condition, String prodDescription, boolean isDelivery);

    @Query("SELECT p FROM Products p WHERE p.uid = ?1")
    List<Products> getProductsById(int uid);


    @Modifying
    @Transactional
    @Query("DELETE FROM Products p WHERE p.uid = ?1")
    void deleteByUid(int uid);
}
