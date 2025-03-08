package HotSwapServer.deployer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static HotSwapServer.config.ServerConfig.PORT;
import static HotSwapServer.config.ServerConfig.REQUEST_TOKEN;

/**
 * 작성자 : 장원용(wonyong92)
 * 작성일 : 2025-03-08 15:30
 * 작성 내용 :
 * - 싱글톤 패턴을 적용하여 서버-클라이언트 간의 연결을 관리하는 클래스
 * - 서버 소켓을 생성하고 클라이언트의 요청을 대기
 * - 클라이언트가 REQUEST_TOKEN 전달시 true 반환
 *  todo : 클라이언트 인증/인가 관리, 커넥션 풀 관리
 */
public class ConnectionManager {

    private static final ConnectionManager instance = new ConnectionManager();

    // 생성자 private -> 외부에서 인스턴스 생성 방지
    private ConnectionManager() {}

   public static ConnectionManager getInstance() {
        return instance;
    }

    /**
     * 클라이언트와의 연결을 수립하는 메서드
     * @return 클라이언트가 REQUEST_TOKEN 전달시 true 반환
     */
    public boolean connectionEstablish() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("업데이트 서버 시작. 클라이언트 대기 중...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));)
                     {
                    // 클라이언트 요청 메시지 읽기
                    String request = in.readLine();
                    if (REQUEST_TOKEN.equals(request)) {
                        return true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
