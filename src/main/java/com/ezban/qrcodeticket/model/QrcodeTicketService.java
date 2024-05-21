package com.ezban.qrcodeticket.model;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

@Service("qrcodeTicketService")
public class QrcodeTicketService {

    @Autowired
    QrcodeTicketRepository qrcodeTicketrepository;


    @Autowired
    ApplicationContext applicationContext;


    public void addQrcodeTicket(QrcodeTicket qrcodeTicket) {
        qrcodeTicketrepository.save(qrcodeTicket);
    }

    public void updateQrcodeTicket(QrcodeTicket qrcodeTicket){
        qrcodeTicketrepository.save(qrcodeTicket);
    }

//    public void deleteByQrcodeTicket(Long ticketNo){
//        if (repository.existsById(ticketNo))
//            repository.deleteByQrcodeTicketNo(ticketNo);
//            repository.deleteById(ticketNo);
//    }

    public QrcodeTicket getOneQrcodeTicket(Long ticketNo) {
        Optional<QrcodeTicket> optional = qrcodeTicketrepository.findById(ticketNo);
        return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
    }

    public List<QrcodeTicket> findAll() {
        return qrcodeTicketrepository.findAll();
    }

    /*******************************用在QR Code插入Logo*************************************/
    public BufferedImage generateQRCodeLogo(String data, int width, int height) throws WriterException, IOException {
        System.out.println("QR Code 內容: " + data);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        //設定QR Code編碼格式
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        //產生QR Code
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        //Logo路徑
        BufferedImage logo = ImageIO.read(applicationContext.getResource("classpath:/static/images/ezban.png").getInputStream());
        return insertLogo(qrImage, logo);
    }

    /*******************************insertLogo*********************************************/
    private BufferedImage insertLogo(BufferedImage qrImage, BufferedImage logoImage) {
        int qrWidth = qrImage.getWidth();
        int qrHeight = qrImage.getHeight();
        int logoWidth = logoImage.getWidth();
        int logoHeight = logoImage.getHeight();


        double logoMaxWidth = qrWidth * 0.3;
        double logoMaxHeight = qrHeight * 0.3;

        double scaleFactor = Math.min(logoMaxWidth / logoWidth, logoMaxHeight / logoHeight);

        int scaledWidth = (int) (logoWidth * scaleFactor);
        int scaledHeight = (int) (logoHeight * scaleFactor);

        int x = (qrWidth - scaledWidth) / 2;
        int y = (qrHeight - scaledHeight) / 2;

        Graphics2D g = qrImage.createGraphics();
        g.drawImage(logoImage, x, y, scaledWidth, scaledHeight, null);
        g.dispose();

        return qrImage;
    }

    /*******************************判斷QR Code狀態*****************************************/
    public boolean redeemTicket(Long ticketNo) {
        Optional<QrcodeTicket> optional = qrcodeTicketrepository.findById(ticketNo);
        if (optional.isPresent()) {
            QrcodeTicket qrcodeTicket = optional.get();
            if (qrcodeTicket.getTicketUsageStatus() == QrcodeTicket.TicketUsageStatus.UNUSED) {
                qrcodeTicket.setTicketUsageStatus(QrcodeTicket.TicketUsageStatus.USED);
                qrcodeTicketrepository.save(qrcodeTicket);
                return true;
            } else if (qrcodeTicket.getTicketUsageStatus() == QrcodeTicket.TicketUsageStatus.EXPIRED) {
                return false;
            }
        }
        return false;
    }


    public List<QrcodeTicket> findByOrderDetailNo(Integer ticketOrderDetailNo) {

        return qrcodeTicketrepository.findByTicketOrderDetailTicketOrderDetailNo(ticketOrderDetailNo);
    }

    public Integer countByEventNo(Integer eventNo){
        return qrcodeTicketrepository.countByEventNo(eventNo);
    }

    public Integer countByEventNoAndTicketUsageStatus(Integer eventNo, Byte ticketUsageStatus){
        return qrcodeTicketrepository.countByEventNoAndTicketUsageStatus(eventNo, ticketUsageStatus);
    }
}
