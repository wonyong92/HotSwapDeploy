package HotSwapServer.deployer.service.impl;

import HotSwapServer.deployer.dao.impl.ClientConnectionManager;
import HotSwapServer.deployer.dao.impl.Connector;
import HotSwapServer.deployer.service.interfaces.ConnectionManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

import static HotSwapServer.config.ServerConfig.PORT;
import static HotSwapServer.config.ServerConfig.REQUEST_TOKEN;

/**
 * 작성자 : 장원용(wonyong92)
 * 작성일 : 2025-03-08 23:30
 * 작성 내용 :
 * - 싱글톤 패턴을 적용하여 서버-클라이언트 간의 연결을 관리하는 클래스
 * - 서버 소켓을 생성하고 클라이언트의 요청을 대기
 * - 클라이언트가 REQUEST_TOKEN 전달시 true 반환
 *  todo : 클라이언트 인증/인가 관리, 커넥션 풀 관리(1:N 연결 관리), 모니터 스레드 생성
 */
public class ConnectionManagerImpl implements ConnectionManager {

    private static final ConnectionManagerImpl instance = new ConnectionManagerImpl();

    // 생성자 private -> 외부에서 인스턴스 생성 방지
    private ConnectionManagerImpl() {}

   public static ConnectionManagerImpl getInstance() {
        return instance;
    }

    /**
     * 클라이언트와의 연결을 수립하는 메서드
     * @return 클라이언트가 REQUEST_TOKEN 전달시 true 반환
     */

    public static String generateClientId() {
        return "Client-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    public BufferedWriter connectionEstablish(ClientConnectionManager clientConnectionManager) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("업데이트 서버 시작. 클라이언트 대기 중...");

            while (true) {
                try (
                        Socket clientSocket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                ) {
                    // 클라이언트 요청 메시지 읽기
                    String request = in.readLine();
                    if (REQUEST_TOKEN.equals(request)) {
                        String clientId = generateClientId();
                        Connector connector = new Connector(clientSocket);
                        clientConnectionManager.addConnector(clientId,connector);
                        return out;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
