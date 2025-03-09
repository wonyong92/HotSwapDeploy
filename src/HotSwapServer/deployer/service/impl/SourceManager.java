package HotSwapServer.deployer.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static HotSwapServer.config.ServerConfig.SOURCE_FILE_PATH;

/**
 * SourceManager.java
 *
 * 작성자: 장원용
 * 작성일: 2025-03-09
 *
 * 기능:
 *  싱글톤 패턴 적용
 *  SOURCE_FILE_PATH(외부 스태틱 상수)에 저장된 .java 파일 목록 리스트를 반환
 *  특정 .java 파일이 존재하는지 확인하는 기능 제공
 *  todo : 응답 객체 구조화, 인터페이스 분리
 */

public class SourceManager implements HotSwapServer.deployer.service.interfaces.SourceManager {
    private static SourceManager instance;

    private SourceManager() {}

    public static synchronized SourceManager getInstance() {
        if (instance == null) {
            instance = new SourceManager();
        }
        return instance;
    }

    @Override
    public boolean isJavaFileExists(String fileName) {
        File file = new File(SOURCE_FILE_PATH, fileName);
        return file.exists() && file.isFile() && fileName.endsWith(".java");
    }

    @Override
    public List<String> getJavaFileList() {
        List<String> javaFiles = new ArrayList<>();
        File directory = new File(SOURCE_FILE_PATH);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".java"));

            if (files != null) {
                for (File file : files) {
                    javaFiles.add(file.getName());
                }
            }
        }

        return javaFiles;
    }
}
