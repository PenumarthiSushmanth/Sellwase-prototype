package com.sellwase.Sellwase.Repository;

import com.sellwase.Sellwase.Model.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, Integer>
{
    @Query("SELECT u FROM Users u WHERE u.uemail = ?1 AND u.pass = ?2")
    Optional<Users> findUser(String uname, String pass);

    @Query("SELECT u FROM Users u WHERE u.uemail = ?1")
    Optional<Users> findByUemail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.profilePic = ?1 WHERE u.uid = ?2")
    void saveProfilePic(String orginalName, int uid);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.uname = ?1, u.uPhoneNum = ?2, u.location = ?3, u.pass = ?4 WHERE u.uid = ?5")
    void updateProfile(String uname, String phonNum, String location, String pass, int uid);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.userRating = ?2, u.ratingCount = ?3 WHERE u.uid = ?1")
    void updateRatings(int uid, float newRating, int i);
}
