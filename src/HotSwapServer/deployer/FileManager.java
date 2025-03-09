package HotSwapServer.deployer;

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
public class FileManager {

    private static final FileManager instance = new FileManager();
        // 생성자 private -> 외부에서 직접 인스턴스 생성 방지
    private FileManager() {}

    // 싱글톤 인스턴스 반환
    public static FileManager getInstance() {
        return instance;
    }

    private static boolean sendFile(BufferedWriter out, String fileName) throws IOException {

        File file = new File(SOURCE_FILE_PATH+fileName);

        boolean isExistFile = (file.exists() && file.isFile());

        if(out == null){
            System.out.println("outPut 스트림 닫힘 확인 필요");
            out.flush();
            return false;
        }
        else if (!isExistFile) {
            out.write("ERROR: File not found\n");
            out.flush();
            return false;
        }

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                out.write(line + "\n");
            }
            out.write("END_OF_FILE\n");
            out.flush();
            System.out.println("소스 파일 전송 완료.");
            return true;
        }
    }
}
