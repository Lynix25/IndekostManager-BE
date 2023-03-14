package com.indekos.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Utils {
    public static String passwordHashing(String plainPassword){
//        return DigestUtils.sha256Hex(plainPassword);
        return "hash"+plainPassword;
    }

    public static String UUID4(){
        return UUID.randomUUID().toString();
    }

    public static double dayDiv(Long date1, Long date2){
        Long dayInmilis = 86400000L;
        Long millisecond = Math.abs(date1-date2);

        double dayDiff = (double) millisecond/ (double) dayInmilis;

        return dayDiff;
    }

    public static byte[] compressImage(byte[] data) {
        if (data == null) return null;
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception e) {
        }
        return outputStream.toByteArray();
    }

    public static byte[] compressImage(MultipartFile image) {
        if (image == null) return null;
        try {
            byte[] data = image.getBytes();
            Deflater deflater = new Deflater();
            deflater.setLevel(Deflater.BEST_COMPRESSION);
            deflater.setInput(data);
            deflater.finish();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
            byte[] tmp = new byte[4*1024];
            while (!deflater.finished()) {
                int size = deflater.deflate(tmp);
                outputStream.write(tmp, 0, size);
            }
            try {
                outputStream.close();
            } catch (Exception e) {
            }
            return outputStream.toByteArray();
        }catch (IOException e){
            System.out.println(e);
        }
        return new byte[1];
    }

    public static byte[] decompressImage(byte[] data) {
        if (data == null) return null;
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception exception) {
        }
        return outputStream.toByteArray();
    }

}
