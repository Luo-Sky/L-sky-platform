package com.rs.platform.tools;

import java.io.File;

/**
 * @author : hongbo
 * @create 2022-07-11-13:53
 **/
public interface IFileService {
    void zipFiles(File[] srcFiles, File zipFile);
}
