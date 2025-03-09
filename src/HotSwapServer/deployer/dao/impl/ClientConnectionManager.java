package HotSwapServer.deployer.dao.impl;

/**
 * ClientConnectionManager.java
 *
 * 작성자: 장원용
 * 작성일: 2025-03-09
 *
 * 기능:
 * 1. 싱글톤 패턴 적용
 * 2. 클라이언트별 Connector(BufferedReader, BufferedWriter 저장) 관리
 * 3. 특정 클라이언트의 Connector 추가, 조회, 삭제 기능 제공
 */

import java.util.HashMap;
import java.util.Map;

public class ClientConnectionManager {
    // 싱글톤 인스턴스
    private static ClientConnectionManager instance;

    // 클라이언트별 Connector 저장을 위한 HashMap
    private Map<String, Connector> connectionMap;

    // private 생성자 (외부에서 인스턴스 생성 방지)
    private ClientConnectionManager() {
        connectionMap = new HashMap<>();
    }

    // 싱글톤 인스턴스 반환 메서드 (Thread-Safe)
    public static synchronized ClientConnectionManager getInstance() {
        if (instance == null) {
            instance = new ClientConnectionManager();
        }
        return instance;
    }

    // 특정 클라이언트의 Connector 추가
    public synchronized void addConnector(String clientId, Connector connector) {
        if (clientId != null && connector != null) {
            connectionMap.put(clientId, connector);
            System.out.println(clientId + "의 Connector가 추가되었습니다.");
        }
    }

    // 특정 클라이언트의 Connector 조회
    public synchronized Connector getConnector(String clientId) {
        return connectionMap.get(clientId);
    }

    // 특정 클라이언트의 Connector 삭제
    public synchronized void removeConnector(String clientId) {
        Connector connector = connectionMap.remove(clientId);
        if (connector != null) {
            connector.close();
            System.out.println(clientId + "의 Connector가 삭제되었습니다.");
        } else {
            System.out.println(clientId + "의 Connector가 존재하지 않습니다.");
        }
    }

    // 현재 연결된 클라이언트 수 반환
    public synchronized int getClientCount() {
        return connectionMap.size();
    }
}
