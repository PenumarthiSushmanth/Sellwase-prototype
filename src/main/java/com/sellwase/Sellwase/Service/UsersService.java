package com.sellwase.Sellwase.Service;

import com.sellwase.Sellwase.Model.Users;
import com.sellwase.Sellwase.Model.WishList;
import com.sellwase.Sellwase.Repository.ProductsRepo;
import com.sellwase.Sellwase.Repository.UsersRepo;
import com.sellwase.Sellwase.Repository.WishListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.SimpleTimeZone;

@Service
public class UsersService {

    @Autowired
    UsersRepo usersRepo;
    @Autowired
    ProductsRepo productsRepo;
    @Autowired
    WishListRepo wishListRepo;

    public static String uploadDirec = System.getProperty("user.dir") + "/src/main/Images/ProfilePics";

    public static String uploadProductDir = System.getProperty("user.dir") + "/src/main/Images/ProductImages";


    public void addNewUser(Users user)
    {
        usersRepo.save(user);
    }

    public Users checkLogin(String uname, String pass) {
        Optional<Users> user = usersRepo.findUser(uname, pass);
//        System.out.println(uname + " - " +pass);
        return user.orElse(null);
    }

    public Users checkEmail(String email) {
        Optional<Users> user = usersRepo.findByUemail(email);

        return user.orElse(null);
    }

    public String addProfilePic(MultipartFile file, int uid) throws IOException {


        String orginalName = uid+".png";
        Path fileNameAndPath = Paths.get(uploadDirec, orginalName);
        Files.write(fileNameAndPath, file.getBytes());

        System.out.println(fileNameAndPath.toString());

        usersRepo.saveProfilePic(orginalName, uid);

        return fileNameAndPath.toString();
    }


    public Users getUserDetails(int uid)
    {
        return usersRepo.findById(uid).orElse(null);
    }

    public void deleteProfile(int uid)
    {
        Users user = getUserDetails(uid);

        if(user != null) {

            String path = uploadDirec + "/" + user.getProfilePic();
            System.out.println(path);
            File fileob = new File(path);

            if (fileob.exists()) {
                if (fileob.delete()) {
                    System.out.println("file deleted!");
                }
            } else {
                System.out.println("file Not found");
            }

            usersRepo.deleteById(uid);


            // deleting products in wishlist
            wishListRepo.deleteProductByUid(uid);


            //delete products in products table
            File directory = new File(uploadProductDir + "/");

//            System.out.println(directory);

            if (directory.exists() && directory.isDirectory()) {

                FilenameFilter filter = (dir, name) -> name.startsWith(String.valueOf(uid));

                File[] files = directory.listFiles(filter);

                if (files != null && files.length > 0) {
                    for (File file : files) {
//                        System.out.println("Found file: " + file.getName());
                        if (file.delete()) {
                            System.out.println("file deleted!");
                        }
                    }
                }
            }

            productsRepo.deleteByUid(uid);


        }

    }


    public void updateProfile(Users users) {
        usersRepo.updateProfile(users.getUname(), users.getuPhoneNum(), users.getLocation(), users.getPass(), users.getUid());
    }

    public void updateRating(int uid, int rating) {
        Users user = usersRepo.findById(uid).orElse(null);

        if(user != null)
        {
            int count = user.getRatingCount();
            float rate = user.getUserRating();

            float newRating = ((count * rate) + rating) / (count + 1);

            BigDecimal bd = new BigDecimal(Float.toString(newRating));
            bd = bd.setScale(1, RoundingMode.HALF_UP);
            newRating = bd.floatValue();

//            System.out.println(newRating);
           usersRepo.updateRatings(uid, newRating, count+1);
        }
    }
}
