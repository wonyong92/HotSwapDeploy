package HotSwapServer.deployer.main;

import HotSwapServer.deployer.dao.impl.ClientConnectionManager;
import HotSwapServer.deployer.service.impl.ConnectionManagerImpl;
import HotSwapServer.deployer.service.impl.FileManager;
import HotSwapServer.deployer.service.impl.SourceManager;

public class UpdateServer {
       public static void main(String[] args) {
           ConnectionManagerImpl connectionManager = ConnectionManagerImpl.getInstance();
           FileManager fileManager = FileManager.getInstance();
           SourceManager sourceManager = SourceManager.getInstance();
           ClientConnectionManager clientConnectionManager=ClientConnectionManager.getInstance();

    }


}
