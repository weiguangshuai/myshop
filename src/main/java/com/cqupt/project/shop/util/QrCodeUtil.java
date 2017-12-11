package com.cqupt.project.shop.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.*;
import java.util.HashMap;

/**
 * @author weigs
 * @date 17-12-8
 * 二维码生成器
 */
public class QrCodeUtil {

    public static String encode(String url, String path) throws FileNotFoundException {

        int width = 500;
        int height = 500;
        String format = "gif";

        HashMap<EncodeHintType, String> hint = new HashMap<>();
        hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hint);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        String fileName = System.currentTimeMillis() + ".gif";
        File outFile = new File(path, fileName);

        //输出二维码图片
        try {
            MatrixToImageWriter.writeToFile(bitMatrix, format, outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outFile.getName();
    }

}
