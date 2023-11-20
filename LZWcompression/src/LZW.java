import java.io.*;
import java.util.*;

public class LZW {
    public static String readEnglishTextFromTxt(String fileName) {
        StringBuilder englishText = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                englishText.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return englishText.toString();
    }

    public static ArrayList<Integer> compress(String uncompressed) {
        HashMap<String, Integer> dictionary = new HashMap<>();
        int dictSize = 256; // ASCII字符集大小
        for (int i = 0; i < 256; i++) {
            dictionary.put("" + (char) i, i);
        }

        String current = "";
        ArrayList<Integer> result = new ArrayList<>();

        for (char c : uncompressed.toCharArray()) {
            String next = current + c;
            if (dictionary.containsKey(next)) {
                current = next;
            } else {
                result.add(dictionary.get(current));
                dictionary.put(next, dictSize++);
                current = "" + c;
            }
        }

        if (!current.equals("")) {
            result.add(dictionary.get(current));
        }

        return result;
    }

    public static void writeCompressedCodesToTxt(List<Integer> compressedCodes, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (Integer code : compressedCodes) {
                if (code != null) {
                    writer.write(code.toString());
                    writer.write(" ");
                }
            }
            writer.close();
            System.out.println("压缩编码已写入文件：" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> readCompressedCodesFromTxt(String fileName) {
        ArrayList<Integer> compressedCodes = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] codeStrings = line.split(" ");
                for (String codeString : codeStrings) {
                    compressedCodes.add(Integer.parseInt(codeString));
                }
            }
            reader.close();
            return compressedCodes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decompress(ArrayList<Integer> compressed) {
        HashMap<Integer, String> dictionary = new HashMap<>();
        int dictSize = 256; // ASCII字符集大小
        for (int i = 0; i < 256; i++) {
            dictionary.put(i, "" + (char) i);
        }

        String current = "" + (char)(int)compressed.remove(0);
        StringBuilder result = new StringBuilder(current);

        for (int k : compressed) {
            String entry;
            if (dictionary.containsKey(k)) {
                entry = dictionary.get(k);
            } else if (k == dictSize) {
                entry = current + current.charAt(0);
            } else {
                throw new IllegalArgumentException("Bad compressed k: " + k);
            }

            result.append(entry);

            dictionary.put(dictSize++, current + entry.charAt(0));

            current = entry;
        }

        return result.toString();
    }

    public static void writeEnglishTextToTxt(String text, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        System.out.println("请输入0或1进行压缩或解压操作");
        Scanner scanner=new Scanner(System.in);
        int mod=scanner.nextInt();
        if(mod==0){
            System.out.println("请输入读取的文件名");
            String inFile=scanner.next();
            String txt=readEnglishTextFromTxt(inFile);
            System.out.println("请输入生成的文件名");
            String outFile=scanner.next();
            writeCompressedCodesToTxt(compress(txt),outFile);
        }
        if(mod==1){
            System.out.println("请输入读取的文件名");
            String inFile=scanner.next();
            System.out.println("请输入生成的文件名");
            String outFile=scanner.next();
            writeEnglishTextToTxt(decompress(readCompressedCodesFromTxt(inFile)),outFile);
        }
    }
}
