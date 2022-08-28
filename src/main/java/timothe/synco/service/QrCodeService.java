package timothe.synco.service;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import timothe.synco.db.LinkDao;
import timothe.synco.dto.link.response.QrCodeResponse;
import timothe.synco.error.HttpExceptions;
import timothe.synco.model.Link;

import java.io.IOException;

@Service
public class QrCodeService {


    @Autowired
    LinkDao links;

    public QrCodeResponse getQrCode(Link link, int width, int height) throws HttpExceptions {

        byte[] image;
        Link linkObject = links.getLinkFromUUID(link);

        try {
            image = QrCodeGenerator.getQrCodeImage(linkObject.getLongUrl(), width, height);

        } catch (IOException | WriterException e) {
            throw new HttpExceptions("500", new Throwable("image cannot be generated"), 500);
        }

        return new QrCodeResponse(image, linkObject.getName());
    }
}
