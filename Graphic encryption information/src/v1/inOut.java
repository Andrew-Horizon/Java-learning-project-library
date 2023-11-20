package v1;

public class inOut {




    private static byte[] splitIntToBytes(int value) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (value >> 24);
        bytes[1] = (byte) (value >> 16);
        bytes[2] = (byte) (value >> 8);
        bytes[3] = (byte) value;
        return bytes;
    }
    private static int combineBytesToInt(byte[] bytes) {
        int value = 0;
        value |= (bytes[0] & 0xFF) << 24;
        value |= (bytes[1] & 0xFF) << 16;
        value |= (bytes[2] & 0xFF) << 8;
        value |= bytes[3] & 0xFF;
        return value;
    }
    public static void main(String[] args) {
        int in=1234323;
        byte[] bytes=splitIntToBytes(in);
        int out=combineBytesToInt(bytes);
        System.out.println(out);
    }
}