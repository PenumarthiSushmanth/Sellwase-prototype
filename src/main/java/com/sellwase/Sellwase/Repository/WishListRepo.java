package com.sellwase.Sellwase.Repository;

import com.sellwase.Sellwase.Model.Products;
import com.sellwase.Sellwase.Model.WishList;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Period;
import java.util.List;

@Repository
public interface WishListRepo extends JpaRepository<WishList, Integer> {

//    @Query("SELECT p FROM WishList w JOIN Products p ON p.uid = w.uid AND p.prodID = w.prodID WHERE p.uid = ?1")
    @Query("SELECT p FROM WishList w JOIN Products p ON p.prodID = w.prodID WHERE w.uid = ?1")
    List<Products> getMyWishList(int uid);

    @Modifying
    @Transactional
    @Query("DELETE FROM WishList w WHERE w.prodID = ?1")
    void deleteProduct(int prodId);

    @Modifying
    @Transactional
    @Query("DELETE FROM WishList w WHERE w.uid = ?1")
    void deleteProductByUid(int uid);

    @Modifying
    @Transactional
    @Query("DELETE FROM WishList w WHERE w.prodID = ?1 AND w.uid = ?2")
    void deleteProductFromWishLst(int prodId, int uid);
}
