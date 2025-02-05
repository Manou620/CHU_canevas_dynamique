package com.chu.canevas.service.implementation;

import com.chu.canevas.dto.Personnel.QrRequest;
import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.repository.PersonnelRepository;
import com.chu.canevas.service.QrCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QrCodeServiceImpl implements QrCodeService {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Override
    public byte[] generateEmployeQrCode(QrRequest qrRequest) {

        if(checkParameter(qrRequest)){
            try {
                int width = 300;
                int height = 300;
                String filetype = "png";

                // QR Code encoding settings
                Map<EncodeHintType, Object> hints = new HashMap<>();
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

                // Generate QR code BitMatrix
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(qrRequest.getImmatriculation(), BarcodeFormat.QR_CODE, width, height, hints);

                // Convert BitMatrix to BufferedImage
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = bitMatrixToBufferedImage(width, height, bitMatrix, image);

                // Convert BufferedImage to byte array
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, filetype, byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();

            } catch (WriterException | IOException e) {
                System.out.println(e);
                throw new RuntimeException("Erreur de creation d'image");
            }
        }
        return new byte[0];
    }

    private boolean checkParameter(QrRequest qrRequest){
        if( qrRequest.getImmatriculation() == null || qrRequest.getImmatriculation().trim().isEmpty()){
            throw new RuntimeException("Paramètre vide envoyé");
        }
        if(!personnelRepository.existsByImmatriculation(qrRequest.getImmatriculation())){
            throw new ElementNotFoundException("Personnel ", qrRequest.getImmatriculation());
        }
        return true;
    }

    private Graphics2D bitMatrixToBufferedImage (int width, int height, BitMatrix bitMatrix, BufferedImage image){
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE); // Background color
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK); // QR Code color

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                if(bitMatrix.get(x, y)) {
                    graphics.fillRect(x, y, 1, 1);
                }
            }
        }

        graphics.dispose();

        return graphics;
    }
}
