package HotSwapServer.deployer.service.interfaces;

import java.util.List;

public interface SourceManager {
    boolean isJavaFileExists(String fileName);

    List<String> getJavaFileList();
}
