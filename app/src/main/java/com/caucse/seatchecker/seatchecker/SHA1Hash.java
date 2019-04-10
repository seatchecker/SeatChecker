package com.caucse.seatchecker.seatchecker;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class SHA1Hash {
    private String password;
    SHA1Hash(String password){
        this.password = password;
    }

    String Encode(){

        String hash = null;
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = password.getBytes("UTF-8");
            digest.update(bytes,0,bytes.length);
            bytes = digest.digest();
            hash = bytesToHex(bytes);
        }catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return hash;
    }

    private final static char[] hexArray ="0123456789ABCDEF".toCharArray();
    private static String bytesToHex(byte[] bytes){

        char[] hexChars = new char[bytes.length * 2];
        for(int j = 0; j<bytes.length; j++){
            int v = bytes[j]&0xFF;
            hexChars[j*2] = hexArray[v >>>4];
            hexChars[j*2+1] = hexArray[v & 0x0F];
        }

        return new String(hexChars);
    }

}
