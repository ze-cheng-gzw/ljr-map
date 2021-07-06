package com.demo.util;

import com.demo.entity.Album;
import com.demo.entity.Music;
import com.demo.entity.Singer;
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

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        System.out.println(map.size());
        map.put("a", "100");
        System.out.println(map.containsKey("ss"));
        System.out.println(map.containsKey("a"));
        System.out.println(map.size());
        String s = "孙楠&周深";
        System.out.println(s.contains("&"));
        String s1 = "薛之谦";
        System.out.println(s1.contains("&"));
    }

}
