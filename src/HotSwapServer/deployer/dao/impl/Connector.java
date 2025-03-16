package HotSwapServer.deployer.dao.impl;

/**
 * Connector.java
 *
 * 작성자: 장원용
 * 작성일: 2025-03-09
 *
 * 기능:
 * 1. 클라이언트의 BufferedReader 및 BufferedWriter 저장
 */
import java.io.*;
import java.net.Socket;
import java.util.UUID;


/**
 * 클라이언트와의 개별 연결을 관리하는 클래스
 * UUID, 입출력 스트림을 저장하고 연결 종료 기능을 제공
 */
public class Connector {
    private String uuid;
    private BufferedWriter writeStream;
    private BufferedReader readStream;
    private Socket socket;

    /** 새로운 클라이언트 연결 생성 */
    public Connector(Socket socket) throws IOException {
        this.uuid = UUID.randomUUID().toString();
        this.socket = socket;
        this.writeStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.readStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }


    public String getUuid() {
        return uuid;
    }

 
    public BufferedWriter getWriteStream() {
        return writeStream;
    }

 
    public BufferedReader getReadStream() {
        return readStream;
    }

 
    public void close() {
        try {
            if (writeStream != null) writeStream.close();
            if (readStream != null) readStream.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 
    public String toString() {
        return "Connector{uuid='" + uuid + "'}";
    }
}
