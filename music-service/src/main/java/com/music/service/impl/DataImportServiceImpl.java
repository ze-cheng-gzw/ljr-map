package com.music.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.dao.*;
import com.demo.entity.*;
import com.demo.interfaceService.DataImportService;
import com.demo.vo.ReadExcelVO;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DataImportServiceImpl implements DataImportService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource
    private MusicMapper musicMapper;

    @Resource
    private SingerMapper singerMapper;

    @Resource
    private AlbumMapper albumMapper;

    @Resource
    private PlaylistMapper playlistMapper;

    @Resource
    private PlaylistMusicMapper playlistMusicMapper;

    //导入歌曲+歌手+专辑
    public boolean handleFileUpload(ReadExcelVO readExcelVO) {
        SqlSession session = null;

        //将音乐添加到数据库
//        List<Music> musicList = readExcelVO.getMusicList();
//        for (Music music:musicList) {
//            musicMapper.insertSelective(music);
//        }

//        try {
//            session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
//            int a = 5; //每次提交100条
//            int loop = (int) Math.ceil(musicList.size() / (double) a);
//            logger.info("次数：" + loop);
//            List<Music> tempList = new ArrayList<Music>(a);
//            int start, stop = 0;
//            for (int i = 0; i < loop; i++) {
//                tempList.clear();
//                start = i * a;
//                stop = Math.min(i * a + a - 1, musicList.size() - 1);
//                logger.info("range:" + start + " - " + stop);
//                for (int j = start; j <= stop; j++) {
//                    tempList.add(musicList.get(j));
//                }
//                musicMapper.addMusicList(tempList);
//                session.clearCache();
//                logger.info("已经插入" + (stop + 1) + " 条");
//            }
//
//            session.commit();
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            session.rollback();
//
//        } finally {
//
//            if (session != null) {
//                session.close();
//            }
//
//        }

//        logger.info("-------------------------------------------");
//        //将歌手添加到数据库
//        List<Singer> singerList = readExcelVO.getSingerList();
//        System.out.println("歌手数量：" + singerList.size());
//        for (Singer singer: singerList) {
//            System.out.println(singer.toString());
//        }
//        for (Singer singer:singerList) {
//            singerMapper.insertSelective(singer);
//        }
//        try {
//            session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
//            int a = 100; //每次提交100条
//            int loop = (int) Math.ceil(singerList.size() / (double) a);
//            logger.info("次数：" + loop);
//            List<Singer> tempList = new ArrayList<Singer>(a);
//            int start, stop = 0;
//            for (int i = 0; i < loop; i++) {
//                tempList.clear();
//                start = i * a;
//                stop = Math.min(i * a + a - 1, singerList.size() - 1);
//                logger.info("range:" + start + " - " + stop);
//                for (int j = start; j <= stop; j++) {
//                    tempList.add(singerList.get(j));
//                }
//                logger.info("tempList中的元素数量：{}",tempList.size());
//                singerMapper.addSingerList(tempList);
//                session.clearCache();
//                logger.info("已经插入" + (stop + 1) + " 条");
//            }
//
//            session.commit();
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            session.rollback();
//
//        } finally {
//
//            if (session != null) {
//                session.close();
//            }
//
//        }
//
//        logger.info("---------------------");
//        //将专辑信息添加到数据库
//        List<Album> albumList = readExcelVO.getAlbumList();
//        for(Album album:albumList) {
//            albumMapper.insertSelective(album);
//        }
//        try {
//            session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
//            int a = 100; //每次提交100条
//            int loop = (int) Math.ceil(albumList.size() / (double) a);
//            logger.info("次数：" + loop);
//            List<Album> tempList = new ArrayList<Album>(a);
//            int start, stop = 0;
//            for (int i = 0; i < loop; i++) {
//                tempList.clear();
//                start = i * a;
//                stop = Math.min(i * a + a - 1, albumList.size() - 1);
//                logger.info("range:" + start + " - " + stop);
//                for (int j = start; j <= stop; j++) {
//                    tempList.add(albumList.get(j));
//                }
//                albumMapper.addAlbumList(tempList);
//                session.clearCache();
//                logger.info("已经插入" + (stop + 1) + " 条");
//            }
//
//            session.commit();
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            session.rollback();
//
//        } finally {
//
//            if (session != null) {
//                session.close();
//            }
//
//        }
        return true;
    }

    @Override
    public boolean importPlaylist(ReadExcelVO readExcelVO) {
        //导入歌单
//        List<Playlist> playlistList = readExcelVO.getPlaylists();
//        for (Playlist playlist:playlistList) {
//            playlistMapper.insertSelective(playlist);
//        }
//        List<PlaylistMusic> playlistMusicList = readExcelVO.getPlaylistMusics();
//        for (PlaylistMusic playlistMusic:playlistMusicList) {
//            playlistMusicMapper.insertSelective(playlistMusic);
//        }
        List<Music> musicList = readExcelVO.getMusicList();
        for (Music music:musicList) {
            musicMapper.insertSelective(music);
        }
        return true;
    }
}
