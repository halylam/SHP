package vn.shp.app.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class Utils {

    final static Logger logger = LoggerFactory.getLogger(Utils.class);

    private static char[] SOURCE_CHARACTERS = {'À', 'Á', 'Â', 'Ã', 'È', 'É',
            'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â',
            'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý',
            'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ',
            'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ',
            'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ',
            'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ',
            'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ',
            'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ',
            'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ',
            'ữ', 'Ự', 'ự',};

    private static char[] DESTINATION_CHARACTERS = {'A', 'A', 'A', 'A', 'E',
            'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a',
            'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u',
            'y', 'A', 'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u',
            'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
            'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e',
            'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
            'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
            'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
            'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
            'U', 'u', 'U', 'u',};

    public static void writeLog(String msg) {
        logger.debug(msg);
    }

    public static String writeValueAsString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private static Properties properties;


    public static boolean contains(List<?> coll, Object o) {
        return coll.contains(o);
    }


    public static String getCountQuery(String query) {
        String countQuery = "select count(*) as count ";
        int lastIndex = query.length();
        if (query.indexOf("ORDER BY") > 0) {
            lastIndex = query.indexOf("ORDER BY");
        }
        countQuery += query.substring(query.indexOf("FROM"), lastIndex);
        return countQuery;
    }

    public static String getDistinctCountQuery(String query) {
        String countQuery = "select count(DISTINCT *) as count ";
        int lastIndex = query.length();
        if (query.indexOf("ORDER BY") > 0) {
            lastIndex = query.indexOf("ORDER BY");
        }
        countQuery += query.substring(query.indexOf("FROM"), lastIndex);
        return countQuery;
    }

    public static boolean isNull(Object obj) {
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.get(obj) != null) {
                    return false;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return true;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return true;
            }
        }
        return true;
    }

    public static boolean isNullObjectRegular(Object obj) {
        for (Field f : obj.getClass().getFields()) {
            f.setAccessible(true);
            try {
                if (f.get(obj) != null) {
                    return false;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return true;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return true;
            }
        }
        return true;
    }

    public static Calendar setTimeToZero(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static Calendar setTimeToMax(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 99);
        return cal;
    }

    public static Date setTimeToZero(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date setTimeToMax(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 99);
        return cal.getTime();
    }

    public static String getFile(String fileName) {
        if (fileName == null)
            return null;
        int pos = fileName.lastIndexOf(".");
        if (pos == -1)
            return fileName;
        return fileName.substring(0, pos);
    }

    public static Font createFont(Workbook workbook) {
        Font newFont = workbook.createFont();
        newFont.setFontName("Arial");
        newFont.setFontHeightInPoints((short) 11);
        return newFont;
    }

    public static CellStyle createCellStyleNormal(Workbook workbook, Font font) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setFont(font);
        return cellStyle;
    }

    public static CellStyle createCellStyleDateNormal(Workbook workbook,
                                                      Font font) {
        CellStyle cellStyleDate = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyleDate.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleDate.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleDate.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleDate.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat(
                "dd/mm/yyyy"));
        cellStyleDate.setFont(font);
        return cellStyleDate;

    }

    public static String encryptMD5(String input) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPass = encoder.encode(input);
        return hashedPass;
    }

    public static void createFile(String pathToFileTmp,
                                  HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // export file pdf file
        File fileTemp = new File(pathToFileTmp);
        FileInputStream fileInputStream = new FileInputStream(fileTemp);
        byte[] arr = new byte[1024];
        int numRead = -1;
        String userAgent = request.getHeader("user-agent");
        if (userAgent.toLowerCase().contains("chrome")) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.addHeader("Content-Disposition", "attachment; filename=\""
                    + fileTemp.getName().replaceAll(".xlsx", "") + "\"");
        } else {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.addHeader("Content-Disposition", "attachment; filename=\""
                    + fileTemp.getName() + "\"");
        }

        while ((numRead = fileInputStream.read(arr)) != -1) {
            response.getOutputStream().write(arr, 0, numRead);
        }

        fileInputStream.close();
        fileTemp.delete();
    }

    public static <E> String convertListToString(List<E> lst) {
        String result = "";
        if (lst != null && lst.size() > 0) {
            int i = 0;
            for (E item : lst) {
                if (i == lst.size() - 1) {
                    result += item;
                } else {
                    result += item + ";";
                }

                ++i;
            }
        }
        return result;
    }


    public static Locale getLocale() {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale.toString().startsWith("en")) {
            locale = new Locale("en");
        } else {
            locale = new Locale("vi");
        }
        return locale;
    }

    public static boolean isNullOrEmpty(String str) {
        if (str == null) {
            return true;
        }

        return str.isEmpty();
    }

    public static boolean isAllNullOrEmpty(Object... obj) {
        for (Object object : obj) {
            if (object instanceof String) {
                if (isNotNullOrEmpty((String) object)) {
                    return false;
                }
            } else {
                if (object != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isAllNotNullOrEmpty(Object... obj) {
        for (Object object : obj) {
            if (object instanceof String) {
                if (isNullOrEmpty((String) object)) {
                    return false;
                }
            } else {
                if (object == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNotNullOrEmpty(Object str) {
        if (str == null) {
            return false;
        }
        if (str instanceof String) {
            String value = (String) str;
            return !value.isEmpty();
        }
        return true;
    }

    public static String readXmlNote(Element el, String noteName, int index) {
        String result = null;
        NodeList nlst = el.getElementsByTagName(noteName);
        if (nlst.getLength() > 0) {
            return nlst.item(0).getTextContent();
        }

        return result;
    }

    /**
     * Decode string to image
     *
     * @param imageString The string to decode
     * @return decoded image
     */
    public static BufferedImage decodeToImage(String imageString) {

        BufferedImage image = null;
        byte[] imageByte;
        try {

            // BASE64Decoder decoder = new BASE64Decoder();
            // imageByte = decoder.decodeBuffer(imageString);

            Base64 base64 = new Base64();
            imageByte = base64.decode(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public static byte[] decodeToBytes(String imageString) {

        byte[] imageByte = null;
        ;
        try {
            // BASE64Decoder decoder = new BASE64Decoder();
            // imageByte = decoder.decodeBuffer(imageString);
            Base64 base64 = new Base64();
            imageByte = base64.decode(imageString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageByte;
    }

    /**
     * Encode image to string
     *
     * @param image The image to encode
     * @param type  jpeg, bmp, ...
     * @return encoded string
     */
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            // BASE64Encoder encoder = new BASE64Encoder();
            // imageString = encoder.encode(imageBytes);
            Base64 base64 = new Base64();
            imageString = base64.encodeToString(imageBytes);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }


    public static String encodeToString(byte[] imageBytes) {
        String imageString = null;

        try {

            // BASE64Encoder encoder = new BASE64Encoder();
            // imageString = encoder.encode(imageBytes);
            Base64 base64 = new Base64();
            imageString = base64.encodeToString(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public static char removeAccent(char ch) {
        int index = Arrays.binarySearch(SOURCE_CHARACTERS, ch);
        if (index >= 0) {
            ch = DESTINATION_CHARACTERS[index];
        }
        return ch;
    }

    public static String removeAccent(String s, String space) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, removeAccent(sb.charAt(i)));
        }
        String output = sb.toString().trim();
        if (space != null) {
            output = output.replaceAll(" ", space);
        }
        return output;
    }

    public static void generateWordDoc(Hashtable<String, String> ht, String templatePathFilename, String outputPathFilename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(templatePathFilename));

            File destination = new File(outputPathFilename);
            BufferedWriter writer = new BufferedWriter(new FileWriter(destination));

            String thisLine;
            int i = 0;

            while ((thisLine = reader.readLine()) != null) {
                System.out.println(i);

                for (Enumeration<String> e = ht.keys(); e.hasMoreElements(); ) {
                    String name = (String) e.nextElement();
                    String value = ht.get(name).toString();
                    thisLine = thisLine.replaceAll(name.toUpperCase(), XmlEncode(value));
                }
                writer.write(thisLine);
                writer.newLine();
                i++;
            }
            reader.close();
            writer.close();
            System.out.println("done");
        } catch (Exception e) {
            System.out.println("exception!=" + e);
        }
    }

    private static String XmlEncode(String text) {
        int[] charsRequiringEncoding = {38, 60, 62, 34, 61, 39};
        for (int i = 0; i < charsRequiringEncoding.length - 1; i++) {
            text = text.replaceAll(String.valueOf((char) charsRequiringEncoding[i]), "&#" + charsRequiringEncoding[i] + ";");
        }
        return text;
    }

    public static String nullCheck(String input) {
        if (input == null) {
            return "";
        }
        return input;
    }

    public static String nullCheck(String input, String defalt) {
        if (input == null) {
            return defalt;
        }
        return input;
    }

    public static boolean isEmptyList(List lstLogger) {
        return lstLogger == null || lstLogger.size() == 0;
    }

    public static String unicodeToAscii(String unicode) {
        final StringBuilder out = new StringBuilder();
        for (int i = 0; i < unicode.length(); i++) {
            final char ch = unicode.charAt(i);
            if (ch <= 127) out.append(ch);
            else out.append("\\u").append(String.format("%04x", (int) ch));
        }
        return out.toString();
    }

    public static void sendEmailWithAttachments(String host, String port, String fromMain,
                                                String toAddress,
                                                String subject, String message,
                                                String[] attachFiles) throws AddressException,
            MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "false");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(fromMain));

        toAddress = toAddress.replaceAll(";", ",");
        String[] addr = toAddress.split(",");
        InternetAddress[] toAddresses = new InternetAddress[addr.length];
        for (int i = 0; i < addr.length; i++) {
            toAddresses[i] = new InternetAddress(addr[i].trim());
        }
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject((subject));
        msg.setSentDate(new Date());
        //msg.setContent("Content-Type", "text/plain; charset=utf-8");

        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent((message), "text/html; charset=utf-8");

        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // adds attachments
        if (attachFiles != null && attachFiles.length > 0) {
            for (String filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();

                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                multipart.addBodyPart(attachPart);
            }
        }
        msg.setContent(multipart);

        Transport.send(msg);

    }

    public static boolean equalsIgnoreSpaces(String value1, String value2) {
        if(value1 == null || value2 == null)
            return false;
        if(value1.trim().equals(value2.trim()))
            return true;
        return false;
    }

    public static String readFile(InputStream is) throws IOException {
        Reader in = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(in);
        String mess = org.apache.commons.io.IOUtils.toString(br);
        in.close();
        is.close();
        return mess;
    }
}
