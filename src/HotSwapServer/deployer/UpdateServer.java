package HotSwapServer.deployer;

import javax.xml.transform.Source;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static HotSwapServer.config.ServerConfig.PORT;
import static HotSwapServer.config.ServerConfig.SOURCE_FILE_PATH;

public class UpdateServer {
       public static void main(String[] args) {
           ConnectionManager connectionManager = ConnectionManager.getInstance();
           FileManager fileManager = FileManager.getInstance();
           SourceManager sourceManager = SourceManager.getInstance();
    }


}
