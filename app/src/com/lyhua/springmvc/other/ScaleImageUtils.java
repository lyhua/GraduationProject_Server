package com.lyhua.springmvc.other;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

 
/**
 * <b>function:</b> ����ͼƬ�����࣬��������ͼ������ͼƬ����
 * @author hoojo
 * @createDate 2012-2-3 ����10:08:47
 * @file ScaleImageUtils.java
 * @package com.hoo.util
 * @blog http://blog.csdn.net/IBM_hoojo
 * http://hoojo.cnblogs.com
 * @email hoojo_@126.com
 * @version 1.0
 */
public  class ScaleImageUtils {
 
    private static final float DEFAULT_SCALE_QUALITY = 1f;
    private static final String DEFAULT_IMAGE_FORMAT = ".jpg"; // ͼ���ļ��ĸ�ʽ 
    private static final String DEFAULT_FILE_PATH = "E:/Target";
    
    /**
     * <b>function:</b> ����ͼƬѹ������ö���ࣻ
     * Some guidelines: 0.75 high quality��0.5  medium quality��0.25 low quality
     * @author hoojo
     * @createDate 2012-2-7 ����11:31:45
     * @file ScaleImageUtils.java
     * @package com.hoo.util
     * @project JQueryMobile
     * @blog http://blog.csdn.net/IBM_hoojo
     * @email hoojo_@126.com
     * @version 1.0
     */
    public enum ImageQuality {
        max(1.0f), high(0.75f), medium(0.5f), low(0.25f);
        
        private Float quality;
        public Float getQuality() {
            return this.quality;
        }
        ImageQuality(Float quality) {
            this.quality = quality;
        }
    }
    
    private static Image image;
    
    /**
     * <b>function:</b> ͨ��Ŀ�����Ĵ�С�ͱ�׼��ָ������С�����ͼƬ��С�ı���
     * @author hoojo
     * @createDate 2012-2-6 ����04:41:48
     * @param targetWidth Ŀ��Ŀ��
     * @param targetHeight Ŀ��ĸ߶�
     * @param standardWidth ��׼��ָ�������
     * @param standardHeight ��׼��ָ�����߶�
     * @return ��С�ĺ��ʱ���
     */
    public static double getScaling(double targetWidth, double targetHeight, double standardWidth, double standardHeight) {
        double widthScaling = 0d;
        double heightScaling = 0d;
        if (targetWidth > standardWidth) {
            widthScaling = standardWidth / (targetWidth * 1.00d);
        } else {
            widthScaling = 1d;
        }
        if (targetHeight > standardHeight) {
            heightScaling = standardHeight / (targetHeight * 1.00d);
        } else {
            heightScaling = 1d;
        }
        return Math.min(widthScaling, heightScaling);
    }
    
    /**
     * <b>function:</b> ��Image�Ŀ�ȡ��߶����ŵ�ָ��width��height����������savePathĿ¼
     * @author hoojo
     * @createDate 2012-2-6 ����04:54:35
     * @param width ���ŵĿ��
     * @param height ���ŵĸ߶�
     * @param savePath ����Ŀ¼
     * @param targetImage �������ŵ�Ŀ��ͼƬ
     * @return ͼƬ����·��������
     * @throws ImageFormatException
     * @throws IOException
     */
    public static String resize(int width, int height, String savePath, Image targetImage) throws ImageFormatException, IOException {
        width = Math.max(width, 1);
        height = Math.max(height, 1);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawImage(targetImage, 0, 0, width, height, null);
        
        if (savePath == null || "".equals(savePath)) {
            savePath = DEFAULT_FILE_PATH + System.currentTimeMillis() + DEFAULT_IMAGE_FORMAT;
        }
        
        FileOutputStream fos = new FileOutputStream(new File(savePath));
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
        encoder.encode(image);
 
        image.flush();
        fos.flush();
        fos.close();
        
        return savePath;
    }
    
    /**
     * <b>function:</b> ��������ͼƬ�������������ҿ��Ը���ָ���Ŀ������ͼƬ
     * @author hoojo
     * @createDate 2012-2-7 ����11:01:27
     * @param width ���ŵĿ��
     * @param height ���ŵĸ߶�
     * @param quality ͼƬѹ�����������ֵ��1�� ʹ��ö��ֵ��{@link ImageQuality}
     *             Some guidelines: 0.75 high quality��0.5  medium quality��0.25 low quality
     * @param savePath ����Ŀ¼
     * @param targetImage �������ŵ�Ŀ��ͼƬ
     * @return ͼƬ����·��������
     * @throws ImageFormatException
     * @throws IOException
     */
    public static String resize(int width, int height, Float quality, String savePath, Image targetImage) throws ImageFormatException, IOException {
        width = Math.max(width, 1);
        height = Math.max(height, 1);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawImage(targetImage, 0, 0, width, height, null);
        
        if (savePath == null || "".equals(savePath)) {
            savePath = DEFAULT_FILE_PATH + System.currentTimeMillis() + DEFAULT_IMAGE_FORMAT;
        }
        
        FileOutputStream fos = new FileOutputStream(new File(savePath));
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
        
        JPEGEncodeParam encodeParam = JPEGCodec.getDefaultJPEGEncodeParam(image); 
        if (quality == null || quality <= 0) {
            quality = DEFAULT_SCALE_QUALITY;
        }
        /** ����ͼƬѹ������ */  
        encodeParam.setQuality(quality, true);  
        encoder.encode(image, encodeParam);  
 
        image.flush();
        fos.flush();
        fos.close();
        
        return savePath;
    }
    
    /**
     * <b>function:</b> ͨ��ָ����С��ͼƬ�Ĵ�С�������ͼƬ��С�ĺ��ʴ�С
     * @author hoojo
     * @createDate 2012-2-6 ����05:53:10
     * @param width ָ���Ŀ��
     * @param height ָ���ĸ߶�
     * @param image ͼƬ�ļ�
     * @return ���ؿ�ȡ��߶ȵ�int����
     */
    public static int[] getSize(int width, int height, Image image) {
        int targetWidth = image.getWidth(null);
        int targetHeight = image.getHeight(null);
        double scaling = getScaling(targetWidth, targetHeight, width, height);
        long standardWidth = Math.round(targetWidth * scaling);
        long standardHeight = Math.round(targetHeight * scaling);
        return new int[] { Integer.parseInt(Long.toString(standardWidth)), Integer.parseInt(String.valueOf(standardHeight)) };
    }
    
    /**
     * <b>function:</b> ͨ��ָ���ı�����ͼƬ���󣬷���һ���Ŵ����С�Ŀ�ȡ��߶�
     * @author hoojo
     * @createDate 2012-2-7 ����10:27:59
     * @param scale ���ű���
     * @param image ͼƬ����
     * @return ���ؿ�ȡ��߶�
     */
    public static int[] getSize(float scale, Image image) {
        int targetWidth = image.getWidth(null);
        int targetHeight = image.getHeight(null);
        long standardWidth = Math.round(targetWidth * scale);
        long standardHeight = Math.round(targetHeight * scale);
        return new int[] { Integer.parseInt(Long.toString(standardWidth)), Integer.parseInt(String.valueOf(standardHeight)) };
    }
    
    public static int[] getSize(int width, Image image) {
        int targetWidth = image.getWidth(null);
        int targetHeight = image.getHeight(null);
        long height = Math.round((targetHeight * width) / (targetWidth * 1.00f));
        return new int[] { width, Integer.parseInt(String.valueOf(height)) };
    }
    
    public static int[] getSizeByHeight(int height, Image image) {
        int targetWidth = image.getWidth(null);
        int targetHeight = image.getHeight(null);
        long width = Math.round((targetWidth * height) / (targetHeight * 1.00f));
        return new int[] { Integer.parseInt(String.valueOf(width)), height };
    }
    
    /**
     * 
     * <b>function:</b> ��ָ����targetFileͼƬ�ļ��Ŀ�ȡ��߶ȴ���ָ��width��height��ͼƬ��С����������savePathĿ¼
     * @author hoojo
     * @createDate 2012-2-6 ����04:57:02
     * @param width ��С�Ŀ��
     * @param height ��С�ĸ߶�
     * @param savePath ����Ŀ¼
     * @param targetImage �ı��Ŀ��ͼƬ
     * @return ͼƬ����·��������
     * @throws ImageFormatException
     * @throws IOException
     */
    public static String resize(int width, int height, String savePath, File targetFile) throws ImageFormatException, IOException {
        image = ImageIO.read(targetFile);
        int[] size = getSize(width, height, image);
        return resize(size[0], size[1], savePath, image);
    }
    
    /**
     * 
     * <b>function:</b> ��ָ����targetURL����ͼƬ�ļ��Ŀ�ȡ��߶ȴ���ָ��width��height��ͼƬ��С����������savePathĿ¼
     * @author hoojo
     * @createDate 2012-2-6 ����04:57:07
     * @param width ��С�Ŀ��
     * @param height ��С�ĸ߶�
     * @param savePath ����Ŀ¼
     * @param targetImage �ı��Ŀ��ͼƬ
     * @return ͼƬ����·��������
     * @throws ImageFormatException
     * @throws IOException
     */
    public static String resize(int width, int height, String savePath, URL targetURL) throws ImageFormatException, IOException {
        image = ImageIO.read(targetURL);
        int[] size = getSize(width, height, image);
        return resize(size[0], size[1], savePath, image);
    }
    
    /**
     * <b>function:</b> ��һ�����ص�ͼƬ�ļ�����ָ���ı�����������
     * @author hoojo
     * @createDate 2012-2-7 ����10:29:18
     * @param scale ���ű���
     * @param savePath �����ļ�·��������
     * @param targetFile ����ͼƬ�ļ�
     * @return �µ��ļ�����
     * @throws ImageFormatException
     * @throws IOException
     */
    public static String resize(float scale, String savePath, File targetFile) throws ImageFormatException, IOException {
        image = ImageIO.read(targetFile);
        int[] size = getSize(scale, image);
        return resize(size[0], size[1], savePath, image);
    }
    
    /**
     * <b>function:</b> ��һ������ͼƬ�ļ�����ָ���ı�����������
     * @author hoojo
     * @createDate 2012-2-7 ����10:30:56
     * @param scale ���ű���
     * @param savePath �����ļ�·��������
     * @param targetFile ����ͼƬ�ļ�
     * @return �µ��ļ�����
     * @throws ImageFormatException
     * @throws IOException
     */
    public static String resize(float scale, String savePath, URL targetURL) throws ImageFormatException, IOException {
        image = ImageIO.read(targetURL);
        int[] size = getSize(scale, image);
        return resize(size[0], size[1], savePath, image);
    }
    
    /**
     * <b>function:</b> ���չ̶���Ƚ��еȱ����ű���ͼƬ
     * @author hoojo
     * @createDate 2012-2-7 ����10:49:56
     * @param width �̶����
     * @param savePath ����·��������
     * @param targetFile ����Ŀ���ļ�
     * @return ���ر���·��
     * @throws ImageFormatException
     * @throws IOException
     */
    public static String resize(int width, String savePath, File targetFile) throws ImageFormatException, IOException {
        image = ImageIO.read(targetFile);
        int[] size = getSize(width, image);
        return resize(size[0], size[1], savePath, image);
    }
    
    /**
     * <b>function:</b> ���չ̶���Ƚ��еȱ���������ͼƬ
     * @author hoojo
     * @createDate 2012-2-7 ����10:50:52
     * @param width �̶����
     * @param savePath ����·��������
     * @param targetFile ����Ŀ���ļ�
     * @return ���ر���·��
     * @throws ImageFormatException
     * @throws IOException
     */
    public static String resize(int width, String savePath, URL targetURL) throws ImageFormatException, IOException {
        image = ImageIO.read(targetURL);
        int[] size = getSize(width, image);
        return resize(size[0], size[1], savePath, image);
    }
    
    /**
     * 
     * <b>function:</b> ���չ̶��߶Ƚ��еȱ����ű���ͼƬ
     * @author hoojo
     * @createDate 2012-2-7 ����10:51:17
     * @param height �̶��߶�
     * @param savePath ����·��������
     * @param targetFile ����Ŀ���ļ�
     * @return ���ر���·��
     * @throws ImageFormatException
     * @throws IOException
     */
    public static String resizeByHeight(int height, String savePath, File targetFile) throws ImageFormatException, IOException {
        image = ImageIO.read(targetFile);
        int[] size = getSizeByHeight(height, image);
        return resize(size[0], size[1], savePath, image);
    }
    
    /**
     * <b>function:</b> ���չ̶��߶Ƚ��еȱ���������ͼƬ
     * @author hoojo
     * @createDate 2012-2-7 ����10:52:23
     * @param height �̶��߶�
     * @param savePath ����·��������
     * @param targetFile ����Ŀ���ļ�
     * @return ���ر���·��
     * @throws ImageFormatException
     * @throws IOException
     */
    public static String resizeByHeight(int height, String savePath, URL targetURL) throws ImageFormatException, IOException {
        image = ImageIO.read(targetURL);
        int[] size = getSizeByHeight(height, image);
        return resize(size[0], size[1], savePath, image);
    }
    
    /**
     * <b>function:</b>
     * @author hoojo
     * @createDate 2012-2-3 ����10:08:47
     * @param args
     * @throws IOException 
     * @throws MalformedURLException 
     * @throws ImageFormatException 
     */
    public static void main(String[] args) throws ImageFormatException, MalformedURLException, IOException {
        
//        System.out.println(ScaleImageUtils.resize(140, 140, null, new URL("http://www.open-open.com/lib/images/logo.jpg")));
//        ScaleImageUtils.resize(100, 100, ImageQuality.high.getQuality(), null, ImageIO.read(new URL("http://www.open-open.com/lib/images/logo.jpg")));
    	
    	String path = "E:/Target/2.jpg";
    	File targetFile = new File("E:/Target/2.jpg");
    	double s = 0.25;
//    	Image image = ImageIO.read(targetFile);
//    	int w = (int) (image.getWidth(null) * 0.25);
//    	int h = (int) (image.getHeight(null) *0.25);
//    	System.out.println(w);
//    	System.out.println(h);
//    	ScaleImageUtils.resize(w, h, "E:/Target1/1.jpg", targetFile);
//    	System.out.println("______________________");
    	ScaleImageUtils.saveSmallPictures("E:/Target/2.jpg", "E:/Target1/1.jpg", 0.25);
    	
    	
    }
    
    
    /**
     * 
     * @param sourcePath ԴĿ��ͼƬ
     * @param savePath СͼƬ�����·��
     * @param scale ���ű���
     */
    public static void saveSmallPictures(String sourcePath,String savePath,double scale) throws IOException
    {
    	File targetFile = new File(sourcePath);
    	Image image = ImageIO.read(targetFile);
    	int w = (int) (image.getWidth(null) * scale);
    	int h = (int) (image.getHeight(null) * scale);
    	ScaleImageUtils.resize(w, h, savePath, targetFile);
    }
}
    
    
    
    
    