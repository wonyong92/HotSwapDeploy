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
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Connector {
    private BufferedReader reader;
    private BufferedWriter writer;

    public Connector(BufferedReader reader, BufferedWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public void close() {
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
