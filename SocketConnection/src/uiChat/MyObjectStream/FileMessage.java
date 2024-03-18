package uiChat.MyObjectStream;

import java.io.*;

public class FileMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String fileName;
    private long fileSize;
    private byte[] fileData;
    private String sender;
    private String receiver;
    private String fileType;
    public FileMessage(File file,String sender,String receiver) {
        fileType=getFileExtension(file);
        this.sender=sender;
        this.receiver=receiver;
        fileName=file.getName();
        fileSize=file.length();
        fileData = readFileToByteArray(file);
    }
    private byte[] readFileToByteArray(File file) {
        byte[] data = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    private String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }
    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public byte[] getFileData() {
        return fileData;
    }


    public String getSender() {
        return sender;
    }


    public String getReceiver() {
        return receiver;
    }

    public String getFileType() {
        return fileType;
    }
}
