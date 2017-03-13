package com.qianshanding.framework.utils;

import java.io.File;

/**
 * Created by fish on 2017/3/13.
 */
public class GzipHelperTest {
    public void testGzip() {
        File[] sources = new File[]{new File("task.xml"), new File("app.properties")};
        File target = new File("release_package.tar");
        GzipHelper.compress(GzipHelper.pack(sources, target));
    }
}
