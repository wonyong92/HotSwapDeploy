package HotSwapServer.deployer.service.impl;

import HotSwapServer.config.ServerConfig;
import HotSwapServer.deployer.dao.impl.Connector;

import java.io.*;

import static HotSwapServer.config.ServerConfig.SOURCE_FILE_PATH;


/**
 * 작성자 : 장원용(wonyong92)
 * 작성일 : 2025-03-08 23:50
 * 작성 내용 :
 * - 싱글톤 패턴을 적용하여 서버의 소스 파일 전송 기능 담당
 * - 지정된 로컬 소스 파일을 읽어 스트림으로 데이터 전달
 * - outPut 스트림을 전달 받아 데이터 전달
 * - 업뎉이트 대상 파일이 없는 경우 전송하지 않음
 * todo : 1:M 전송 기능 구현, 인터페이스 분리, DB 기반 파일 관리
 */
public class FileManager  {

    private static FileManager instance;

    // private 생성자로 외부에서 직접 인스턴스 생성 방지
    private FileManager() {}

    /** 싱글톤 인스턴스를 반환 */
    public static FileManager getInstance() {
        if (instance == null) {
            synchronized (FileManager.class) { // 멀티스레드 환경에서 동기화 보장
                if (instance == null) {
                    instance = new FileManager();
                }
            }
        }
        return instance;
    }


    public void transferFile(String fileName, Connector connector) {
        try {
            File file = new File(ServerConfig.SOURCE_FILE_PATH + fileName);
            if (!file.exists()) {
                connector.getWriteStream().write("File not found\n");
                connector.getWriteStream().flush();
                return;
            }

            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = connector.getWriteStream();
            String line;
            while ((line = fileReader.readLine()) != null) {
                writer.write(line + "\n");
            }
            writer.flush();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
