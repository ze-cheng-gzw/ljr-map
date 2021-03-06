package com.demo.util;

import com.demo.entity.*;
import com.demo.vo.ReadExcelVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

public class ExcelUtil {

    private final static String xls = "xls";
    private final static String xlsx = "xlsx";//读取Excel文件

    public static ReadExcelVO readExcel(InputStream is, String type, Integer num /*需要展示的数量*/) {
        ReadExcelVO readExcelVO = new ReadExcelVO();
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(is, type);
        if (workbook != null) {
            //workbook.getNumberOfSheets()获取excel的sheet的数量
            for (int sheetNum = 0; sheetNum < 1; sheetNum++) {
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = 0;
                if (num == 0 || num == null) {
                    lastRowNum = sheet.getLastRowNum();
                } else {
                    lastRowNum = sheet.getLastRowNum() > num ? num : sheet.getLastRowNum();
                }
                List<Music> musics = new ArrayList<>();
                List<Singer> singers = new ArrayList<>();
                List<Album> albums = new ArrayList<>();

                Long musicId = 1L;
                Map<String, Integer> singerMap = new LinkedHashMap<>();
                Map<String, Integer> albumMap = new LinkedHashMap<>();
                Set<String> singerNames = new HashSet<>();
                for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }

                    //歌手
                    Integer singerId;
                    String singerName = row.getCell(5) == null ? "" : row.getCell(5).getStringCellValue();
                    if (singerNames.add(singerName)) { //当该歌手不存在时
                        singerId = singerMap.size() + 1;
                        singerMap.put(singerName, singerId);
                        Singer singer = new Singer();
                        singer.setSingerId(new Long(singerId));
                        singer.setSingerName(singerName);
                        String firstLetter = ChineseToFirstLetterUtil.ChineseToFirstLetterFirst(singerName);
                        String regex = "[A-Z]";
                        boolean flag = Pattern.matches(regex, firstLetter);
                        singer.setFirstLetter(flag ? firstLetter : "#");
                        singer.setSingerImg(row.getCell(6) == null ? "" : row.getCell(6).getStringCellValue());
                        singer.setNationality(row.getCell(7) == null ? "" : row.getCell(7).getStringCellValue());
                        singer.setPersonalIntroduce(row.getCell(8) == null ? "" : row.getCell(8).getStringCellValue());
                        singer.setSingerSex(singerName.contains("&") ? 2 : 0);
                        singers.add(singer);
                    } else {
                        singerId = singerMap.get(singerName);
                    }

                    //专辑
                    Album album = new Album();
                    Integer albumId;
                    String albumName = row.getCell(9) == null ? "" : row.getCell(9).getStringCellValue();
                    if (!albumMap.containsKey(albumName)) { //当该专辑不存在时
                        albumId = albumMap.size() + 1;
                        albumMap.put(albumName, albumId);
                        album.setAlbumId(new Long(albumId));
                        album.setAlbumName(albumName);
                        album.setAlbumImg(row.getCell(10) == null ? "" : row.getCell(10).getStringCellValue());
                        album.setAlbumIntroduction(row.getCell(11) == null ? "" : row.getCell(11).getStringCellValue());
                        album.setReleaseTime(row.getCell(12) == null ? "" : row.getCell(12).getStringCellValue());
                        albums.add(album);
                    } else {
                        albumId = albumMap.get(albumName);
                    }

                    //单曲信息填充
                    Music music = new Music();
                    music.setMusicId(musicId);
                    String musicName = row.getCell(0) == null ? "" : row.getCell(0).getStringCellValue();
                    music.setMusicName(musicName);
                    music.setCoverUrl(row.getCell(1) == null ? "" : row.getCell(1).getStringCellValue());
                    music.setMusicUrl(row.getCell(2) == null ? "" : row.getCell(2).getStringCellValue());
                    //获取歌词
                    String fileAddress = "/Users/zl/Downloads/music_Dowload/" + musicName + ".txt";
                    music.setMusicLyrics(ReadTxtFile.readTxt(fileAddress));
                    music.setMusicTimeLength(row.getCell(4) == null ? "" : row.getCell(4).getStringCellValue());
                    music.setAlbumId(new Long(albumId));
                    music.setSingerId(new Long(singerId));
                    music.setToVip(0);

                    musics.add(music);

                    musicId += 1;
                }
                //填充
                readExcelVO.setMusicList(musics);
                readExcelVO.setSingerList(singers);
                readExcelVO.setAlbumList(albums);
            }
        }
        return readExcelVO;
    }

    public static ReadExcelVO readExcelPlaylist(InputStream is, String type, Integer num /*需要展示的数量*/) {
        ReadExcelVO readExcelVO = new ReadExcelVO();
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(is, type);
        if (workbook != null) {
            //workbook.getNumberOfSheets()获取excel的sheet的数量
            Long musicId = 271L;
            for (int sheetNum = 0; sheetNum < 1; sheetNum++) {
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                } //专辑为：254     歌手为：209
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = 0;
                if (num == 0 || num == null) {
                    lastRowNum = sheet.getLastRowNum();
                } else {
                    lastRowNum = sheet.getLastRowNum() > num ? num : sheet.getLastRowNum();
                }
                List<Playlist> playlists = new ArrayList<>();
                List<Music> musicList = new ArrayList<>();
                List<PlaylistMusic> playlistMusics = new ArrayList<>();

                Map<String, Integer> playlistMap = new LinkedHashMap<>();
                Map<String, Integer> musicMap = new LinkedHashMap<>();
                for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }

                    //歌单
                    Integer playlistId;
                    String playlistName = row.getCell(0) == null ? "" : row.getCell(0).getStringCellValue();
                    if (!playlistMap.containsKey(playlistName)) { //改名称不存在时
                        playlistId = playlistMap.size() + 1;
                        playlistMap.put(playlistName, playlistId);
                        Playlist playlist = new Playlist();
                        playlist.setId(new Long(playlistId));
                        playlist.setPlaylistTitle(playlistName);
                        playlist.setPlaylistCover(row.getCell(1) == null ? "" : row.getCell(1).getStringCellValue());
                        playlist.setPlaylistLabel(row.getCell(2) == null ? "" : row.getCell(2).getStringCellValue());
                        playlist.setPlaylistIntroduction(row.getCell(3) == null ? "" : row.getCell(3).getStringCellValue());
                        playlist.setAmountOfPlay(new Long(row.getCell(4) == null ? "" : Integer.valueOf((int) row.getCell(4).getNumericCellValue()).toString()));
                        playlist.setMemberId(1L); //歌单的归属者id
                        playlists.add(playlist);
                    } else {
                        playlistId = playlistMap.get(playlistName);
                    }

                    //歌曲
                    Music music = new Music();
                    music.setMusicId(musicId);
                    String musicName = row.getCell(5) == null ? "" : row.getCell(5).getStringCellValue();
                    String singerName = row.getCell(6) == null ? "" : row.getCell(6).getStringCellValue();
                    music.setMusicName(musicName);
                    music.setMusicTimeLength(row.getCell(7) == null ? "" : row.getCell(7).getStringCellValue());
                    music.setCoverUrl(row.getCell(8) == null ? "" : row.getCell(8).getStringCellValue());
                    music.setMusicUrl(row.getCell(9) == null ? "" : row.getCell(9).getStringCellValue());
                    //获取歌词
                    String fileAddress = "/Users/zl/Downloads/song/" + musicName + "-" + singerName + ".txt";
                    music.setMusicLyrics(ReadTxtFile.readTxt(fileAddress));
                    music.setAlbumId(254L);
                    music.setSingerId(209L);
                    music.setToVip(0);
                    if (!musicMap.containsKey(musicName + "-" + singerName)) { //不存在时才添加
                        musicId++;
                        musicList.add(music);
                    }


                    //歌单+歌曲
                    PlaylistMusic playlistMusic = new PlaylistMusic();
                    playlistMusic.setPlaylistId(new Long(playlistId));
                    playlistMusic.setMusicId(music.getMusicId());
                    playlistMusic.setSingerName("导入歌手");
                    playlistMusic.setMusicTimeLength(music.getMusicTimeLength());
                    playlistMusic.setMusicName(musicName);
                    playlistMusics.add(playlistMusic);
                }
                readExcelVO.setMusicList(musicList);
                readExcelVO.setPlaylistMusics(playlistMusics);
                readExcelVO.setPlaylists(playlists);
            }
        }
        return readExcelVO;
    }



    //根据xls或者xlsx创建Workbook
    public static Workbook getWorkBook(InputStream is, String type) {
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (type.equals(xls)) {
                //2003
                workbook = new HSSFWorkbook(is);
            } else if (type.equals(xlsx)) {
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {

        }
        return workbook;
    }

}
