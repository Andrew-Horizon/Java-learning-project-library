package uiChat;

import uiChat.MyObjectStream.MyObjectInputStream;
import uiChat.MyObjectStream.MyObjectOutputStream;
import uiChat.UI.chatFrame;

import java.io.IOException;

public class testClass {
    public static void main(String[] args) throws IOException {
        chatFrame frame=new chatFrame("winnie",new MyObjectOutputStream(null),new MyObjectInputStream(null));

    }
}
