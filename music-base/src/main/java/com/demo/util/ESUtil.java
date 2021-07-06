package com.demo.util;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

public class ESUtil {

    //第二种连接方法与在ymi配置一样
    public static JestClient getJestCline() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://127.0.0.1:9200")
                .multiThreaded(true)
                .build());
        return factory.getObject();
    }

    //es音乐的index名称
    public static String MUSIC_INDEX =  "music_index";

    //es歌单的index名称
    public static String PLAYLIST_INDEX = "playlist_index";

    //es歌单的标题字段名称
    public static String PLAYLIST_TITLE_NAME = "playlistTitle";
}
