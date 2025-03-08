package HotSwapServer.deployer;

import java.io.*;

import static HotSwapServer.config.ServerConfig.SOURCE_FILE_PATH;


/**
 * 작성자 : 장원용(wonyong92)
 * 작성일 : 2025-03-08 23:50
 * 작성 내용 :
 * - 싱글톤 패턴을 적용하여 서버의 소스 파일 전송 기능 담당
 * - 로컬 소스 파일을 읽어
 * - 클라이언트가 REQUEST_TOKEN 전달시 true 반환
 *  todo : 클라이언트 인증/인가 관리, 커넥션 풀 관리
 */
public class FileManager {

    private static final FileManager instance = new FileManager();
        // 생성자 private -> 외부에서 직접 인스턴스 생성 방지
    private FileManager() {}

    // 싱글톤 인스턴스 반환
    public static FileManager getInstance() {
        return instance;
    }

    private static void sendFile(BufferedWriter out) throws IOException {
        File file = new File(SOURCE_FILE_PATH);
        if (!file.exists()) {
            out.write("ERROR: File not found\n");
            out.flush();
            return;
        }

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                out.write(line + "\n");
            }
            out.write("END_OF_FILE\n");
            out.flush();
            System.out.println("소스 파일 전송 완료.");
        }
    }
}
