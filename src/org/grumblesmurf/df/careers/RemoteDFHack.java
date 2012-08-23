package org.grumblesmurf.df.careers;

import dfproto.CoreProtocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Arrays;

public class RemoteDFHack {
    public static final Charset ASCII = Charset.forName("ASCII");

    private static final byte[] REQUEST_MAGIC = "DFHack?\n".getBytes(ASCII);
    private static final byte[] RESPONSE_MAGIC = "DFHack!\n".getBytes(ASCII);
    private static final byte[] PROTOCOL_VERSION = new byte[]{1, 0, 0, 0};

    public static final int DEFAULT_PORT = 5000;
    private final Socket socket;

    public RemoteDFHack(Socket socket) {
        this.socket = socket;
    }

    public static RemoteDFHack connect() throws IOException {
        return connect(DEFAULT_PORT);
    }

    public static RemoteDFHack connect(int port) throws IOException {
        Socket s = new Socket();
        s.connect(new InetSocketAddress("127.0.0.1", port));
        return new RemoteDFHack(s).verify();
    }

    private RemoteDFHack verify() throws IOException {
        OutputStream out = socket.getOutputStream();
        out.write(REQUEST_MAGIC);
        out.write(PROTOCOL_VERSION);
        out.flush();
        byte[] buf = read(12);

        byte[] magic = Arrays.copyOfRange(buf, 0, RESPONSE_MAGIC.length);
        if (!Arrays.equals(RESPONSE_MAGIC, magic)) {
            throw new RuntimeException(String.format("Garbled response from remote: '%s'", new String(magic, ASCII)));
        }

        byte[] remoteVersion = Arrays.copyOfRange(buf, RESPONSE_MAGIC.length, buf.length);
        if (!Arrays.equals(PROTOCOL_VERSION, remoteVersion)) {
            throw new RuntimeException(String.format("Incompatible remote protocol version: %d", toInt(remoteVersion)));
        }

        return this;
    }

    private int toInt(byte[] bytes) {
        int result = 0;
        int power = 0;
        for (byte b : bytes) {
            result += b << (power++ * 8);
        }
        return result;
    }

    private byte[] read(int bytes) throws IOException {
        byte[] buf = new byte[bytes];
        int offset = 0, read;
        InputStream in = socket.getInputStream();

        while ((read = in.read(buf, offset, buf.length - offset)) > 0) {
            offset += read;
        }
        if (offset != buf.length) {
            throw new RuntimeException("Short read from remote");
        }
        return buf;
    }

    public void close() throws IOException {
        socket.getOutputStream().write(new byte[] {-4, -1, 0, 0, 0, 0, 0, 0});
        socket.close();
    }

    public void runCommand(String command, String... args) throws IOException {
        CoreProtocol.CoreRunCommandRequest msg = CoreProtocol.CoreRunCommandRequest.newBuilder()
                .setCommand(command)
                .addAllArguments(Arrays.asList(args))
                .build();

        OutputStream out = socket.getOutputStream();
        byte[] buf = new byte[8];
        buf[0] = 1;
        buf[4] = (byte) msg.getSerializedSize();
        out.write(buf);  // request id
        msg.writeTo(out);
        out.flush();

        boolean done = false;
        while (!done) {
            byte[] response = read(8);
            int size = toInt(Arrays.copyOfRange(response, 4, 8));
            switch ((int) response[0]) {
                case -1:  // RESULT
                    read(size);
                    done = true;
                    break;
                case -2:  // FAIL
                    done = true;
                    break;
                case -3:  // TEXT
                    read(size);
                    break;
            }
        }
    }
}
