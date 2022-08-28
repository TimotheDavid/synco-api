package timothe.synco.service;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.el.stream.Stream;
import timothe.synco.dto.link.FileUploadDTO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class QrCodeGenerator {

    public static byte[] getQrCodeImage(String text, int width, int height) throws IOException, WriterException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutPutStream = new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig(0xFF000002 , 0xFFFFC041);

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutPutStream, con);
        return pngOutPutStream.toByteArray();
    }




}
