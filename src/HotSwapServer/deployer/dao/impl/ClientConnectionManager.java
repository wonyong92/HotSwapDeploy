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

import HotSwapServer.deployer.service.impl.FileManager;

import java.util.HashMap;
import java.util.Map;
public class ClientConnectionManager {
    private static ClientConnectionManager instance;
    private Map<String, Connector> connectors = new HashMap<>();
    private FileManager fileManager = FileManager.getInstance();

    private ClientConnectionManager() {}

    /** 싱글톤 인스턴스를 반환 */
    public static ClientConnectionManager getInstance() {
        if (instance == null) {
            instance = new ClientConnectionManager();
        }
        return instance;
    }

    /** 새로운 클라이언트 연결 추가 */
    public void addConnector(String uuid, Connector connector) {
        connectors.put(uuid, connector);
    }

    /** 특정 UUID에 해당하는 연결 객체 반환 */
    public Connector getConnector(String uuid) {
        return connectors.get(uuid);
    }

    /** 특정 연결 제거 및 종료 */
    public void removeConnector(String uuid) {
        Connector connector = connectors.remove(uuid);
        if (connector != null) {
            connector.close();
        }
    }

    /** 모든 클라이언트 연결 종료 */
    public void closeAllConnections() {
        for (Connector connector : connectors.values()) {
            connector.close();
        }
        connectors.clear();
    }

    /** 클라이언트가 요청한 파일 전송 요청 처리 */
    public void requestFileTransfer(String uuid, String fileName) {
        Connector connector = connectors.get(uuid);
        if (connector != null) {
            fileManager.transferFile(fileName, connector);
        }
    }

    /** 현재 연결된 모든 클라이언트 목록 반환 */
    public Map<String, Connector> getConnectors() {
        return connectors;
    }
}
