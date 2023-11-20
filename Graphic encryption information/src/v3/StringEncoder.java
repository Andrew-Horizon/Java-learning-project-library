package v3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StringEncoder {
    public static boolean[] encodeString(String s) {
        try {
            byte[] b = s.getBytes("GBK");
            boolean[] bl = new boolean[b.length * 8];
            for (int i = 0; i < b.length; i++) {
                bl[8 * i] = (((b[i]) & 0x1) == 1);
                bl[8 * i + 1] = (((b[i] >> 1) & 0x1) == 1);
                bl[8 * i + 2] = (((b[i] >> 2) & 0x1) == 1);
                bl[8 * i + 3] = (((b[i] >> 3) & 0x1) == 1);
                bl[8 * i + 4] = (((b[i] >> 4) & 0x1) == 1);
                bl[8 * i + 5] = (((b[i] >> 5) & 0x1) == 1);
                bl[8 * i + 6] = (((b[i] >> 6) & 0x1) == 1);
                bl[8 * i + 7] = (((b[i] >> 7) & 0x1) == 1);
            }
            return bl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decodeString( boolean[] bl) {
        try {
            byte[] b = new byte[bl.length / 8];
            for (int i = 0; i < b.length; i++) {
                b[i] = 0;
                if (bl[8 * i]) b[i] = (byte) (b[i] | 0x1);
                if (bl[8 * i + 1]) b[i] = (byte) (b[i] | 0x2);
                if (bl[8 * i + 2]) b[i] = (byte) (b[i] | 0x4);
                if (bl[8 * i + 3]) b[i] = (byte) (b[i] | 0x8);
                if (bl[8 * i + 4]) b[i] = (byte) (b[i] | 0x10);
                if (bl[8 * i + 5]) b[i] = (byte) (b[i] | 0x20);
                if (bl[8 * i + 6]) b[i] = (byte) (b[i] | 0x40);
                if (bl[8 * i + 7]) b[i] = (byte) (b[i] | 0x80);
            }
            return new String(b, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Failed to decode string!";
    }
    public static void main(String[] args) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        ImageMasker imageMasker = new ImageMasker();
        try {
            System.out.print("请输入文件路径：");
            String s = bufferedReader.readLine();
            imageMasker.read(s);
            System.out.print("写入内容还是读取(r/w):");
            s = bufferedReader.readLine();
            if (s.equals("r")) {
                System.out.print(StringEncoder.decodeString(imageMasker.content).trim());
            } else if (s.equals("w")) {
                System.out.print("输入数据(<=" + imageMasker.content.length / 8 + "):");
                s = bufferedReader.readLine();

                boolean[] value = StringEncoder.encodeString(s);
                for (int i = 0; i < value.length; i++)
                    imageMasker.content[i] = value[i];
                for (int i = value.length; i < imageMasker.content.length; i++) {
                    imageMasker.content[i] = false;
                }

                System.out.print("输出文件路径:");
                s = bufferedReader.readLine();
                imageMasker.write(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
