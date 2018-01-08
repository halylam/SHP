package vn.shp.app.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import ecm.service.EcmService;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.impl.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.shp.app.entity.AlfFile;
import vn.shp.app.service.AlfFileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sonlt on 13/12/16.
 */
@Controller
@RequestMapping("/document/files")
public class DocumentFileController  extends AbstractController{



    @Autowired
    private EcmService service;

    @Autowired
    AlfFileService alfFileService;


    @RequestMapping("/{uuid}")
    public void getFileByUnid(@PathVariable("uuid") String fileUuid, HttpServletResponse response) throws IOException {

        fileUuid = fileUuid.replace("|", ";").replace("_", ".");
        Document document = service.download(fileUuid);
        String filename = fileUuid;
        try {
            AlfFile file = alfFileService.findByUuid(fileUuid);
            filename = file.getName();
        } catch (Exception ex) {

        }
        String mimeType = document.getContentStream().getMimeType();
        response.setContentType(mimeType);
        if (!mimeType.equals("application/pdf")) {
            response.addHeader("Content-Disposition", "filename=\"" + filename + "\"");
//            response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        }else
            response.addHeader("Content-Disposition", "filename=\"" + filename + "\"");

        ContentStream cs = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            cs = document.getContentStream();

            if (mimeType.equals("application/pdf")) {
                try {
                    addWatermark(cs.getStream(), os);
                } catch (DocumentException e) {
                    serveFile(cs.getStream(), os);
                }
            } else {
                serveFile(cs.getStream(), os);
            }
            os.flush();
        } finally {
            IOUtils.closeQuietly(cs);
        }

    }

    private void serveFile(InputStream is, OutputStream os) throws IOException {
        try (
                ReadableByteChannel inputChannel = Channels.newChannel(is);
                WritableByteChannel outputChannel = Channels.newChannel(os)
        ) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(10240);
            while (inputChannel.read(buffer) != -1) {
                buffer.flip();
                outputChannel.write(buffer);
                buffer.clear();
            }
        }
    }

    private void addWatermark(InputStream is, OutputStream os) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(is);
        int n = reader.getNumberOfPages();
        PdfStamper stamper = new PdfStamper(reader, os);
        // text watermark
        Phrase phrase1 = new Phrase("SHP USERNAME", new Font(Font.FontFamily.HELVETICA, 50));
        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Phrase phrase2 = new Phrase("SHP EMAIL" + " " + simpleFormat.format(new Date()), new Font(Font.FontFamily.HELVETICA, 25));
        // transparency
        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(0.15f);
        // properties
        PdfContentByte over;
        Rectangle pagesize;
        float x, y;
        // loop over every page
        for (int i = 1; i <= n; i++) {
            pagesize = reader.getPageSizeWithRotation(i);
            x = (pagesize.getLeft() + pagesize.getRight()) / 2;
            y = (pagesize.getTop() + pagesize.getBottom()) / 2;
            over = stamper.getOverContent(i);
            over.saveState();
            over.setGState(gs1);
            ColumnText.showTextAligned(over, Element.ALIGN_CENTER, phrase1, x, y + 20, 45f);
            ColumnText.showTextAligned(over, Element.ALIGN_CENTER, phrase2, x, y - 20, 45f);
            over.restoreState();
        }
        stamper.close();
        reader.close();
    }


}
