package com.syscho.ms.cloudconfig.loader;

import java.io.IOException;
import java.util.Map;

public interface FileLoader {
     Map<String, Object> loadFile(String filename,String profile) throws IOException ;
}
