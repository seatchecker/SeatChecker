package com.caucse.seatchecker.seatchecker;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash {
    private String password;
    MD5Hash(String password){
        this.password = password;
    }

    public String Encode(){

        try{
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            byte messageDigest[] = digest.digest(password.getBytes());

            BigInteger number = new BigInteger(1, messageDigest);
            StringBuilder hashtext = new StringBuilder(number.toString(16));

            while(hashtext.length()<32){
                hashtext.insert(0, "0");
            }
            return hashtext.toString();

        }catch (NoSuchAlgorithmException e){

            e.printStackTrace();
        }
        return null;
    }
}
