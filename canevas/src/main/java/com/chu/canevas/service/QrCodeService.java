package com.chu.canevas.service;

import com.chu.canevas.dto.Personnel.QrRequest;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

public interface QrCodeService {
    byte[] generateEmployeQrCode(QrRequest qrRequest);
}
