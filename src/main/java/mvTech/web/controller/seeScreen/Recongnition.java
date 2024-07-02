package mvTech.web.controller.seeScreen;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class Recongnition {

    // 配置属性，直接初始化
    private String tessPath = "/Users/zyl-mac/Downloads/tesseract-5.4.0/tessdata/"; // Tesseract OCR 路径
    private String language = "eng"; // 语言类型

    // 公共方法：执行截图和OCR识别
    public void captureAndRecognize(int captureX, int captureY, int captureWidth, int captureHeight) {
        // 创建Tesseract实例并设置datapath和语言
        ITesseract instance = new Tesseract();
        instance.setDatapath(tessPath);
        instance.setLanguage(language);
        instance.setOcrEngineMode(1); // 使用 LSTM OCR 引擎模式
        instance.setPageSegMode(6); // 使用单行模式
        // 通过设置变量来调整OCR识别行为
        instance.setVariable("tessedit_char_whitelist", "0123456789."); // 只识别数字和小数点

        try {
            // 创建Robot对象用于捕获屏幕区域
            Robot robot = new Robot();
            // 捕获屏幕区域的BufferedImage
            Rectangle area = new Rectangle(captureX, captureY, captureWidth, captureHeight);
            BufferedImage screenshot = robot.createScreenCapture(area);
            // 调用Tesseract进行OCR识别
            String result = instance.doOCR(screenshot);
            result = result.replace("\n", "");
            System.out.println("OCR Result: " + result);
            boolean res = GetVal.makeMoney(new BigDecimal(result));
            if (res) {
                // 打开截图文件
                File tempFile = File.createTempFile("screenshot", ".png");
                ImageIO.write(screenshot, "png", tempFile);
                openScreenshot(tempFile);
                // 延时3秒钟
                Thread.sleep(3000);
                // 关闭预览应用程序并删除截图文件
                closePreviewAndDeleteScreenshot(tempFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 打开截图文件
    private void openScreenshot(File screenshotFile) {
        try {
            Desktop.getDesktop().open(screenshotFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 关闭预览应用程序并删除截图文件
    private void closePreviewAndDeleteScreenshot(File screenshotFile) {
        try {
            // 关闭预览应用程序
            closePreviewApp();
            // 删除截图文件
            screenshotFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 使用osascript命令关闭预览应用程序
    private void closePreviewApp() {
        try {
            ProcessBuilder pb = new ProcessBuilder("osascript", "-e", "tell application \"Preview\" to quit");
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Recongnition().captureAndRecognize(1392, 495, 59, 25);
    }
}
