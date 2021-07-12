package com.demo.controller.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.common.ApiResponseCode;
import com.demo.common.ApiResponseWrapper;
import com.demo.common.BizException;
import com.demo.common.Result;
import com.demo.entity.*;
import com.demo.interfaceService.DataImportService;
import com.demo.util.ExcelUtil;
import com.demo.vo.ReadExcelVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Api(value = "v1", tags = "数据导入")
@RequestMapping("/api/v1")
public class DataImportController {

    @Reference
    private DataImportService dataImportService;

    @PostMapping("/dataImport/handleFileUpload")
    @ApiOperation(value = "导入歌曲+歌手+专辑", notes = "返回结果")
    public Result<String> handleFileUpload(MultipartFile file) throws IOException {
        if(null == file){
            BizException.fail("上传文件为空");
        }
        String fileName = file.getOriginalFilename();
        System.out.println("文件名称：" +fileName );
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        System.out.println("后缀为：" + fileSuffix);
        //判断文件是否是excel文件
        if(!fileSuffix.equals("xls") && !fileSuffix.equals("xlsx")){ //不是excel文件
            BizException.fail("上传文件不是excel");
        }
        ReadExcelVO readExcelVO = ExcelUtil.readExcel(file.getInputStream(), fileSuffix, 0);
//        for (Music music:readExcelVO.getMusicList()) {
//            System.out.println(music.toString());
//            System.out.println("------------------------------");
//        }
//
//        for (Singer singer:readExcelVO.getSingerList()) {
//            System.out.println(singer.toString());
//            System.out.println("------------------------------");
//        }
//
//        for (Album album:readExcelVO.getAlbumList()) {
//            System.out.println(album.toString());
//            System.out.println("------------------------------");
//        }

        boolean result = dataImportService.handleFileUpload(readExcelVO);

        //退出成功
        if (result) {
            return ApiResponseWrapper.wrap("导入成功");
        }
        //退出失败
        return ApiResponseWrapper.wrap(ApiResponseCode.FAILURE, "导入失败");
    }

    @PostMapping("/dataImport/importPlaylist")
    @ApiOperation(value = "导入歌曲+歌单", notes = "返回结果")
    public Result<String> importPlaylist(MultipartFile file) throws IOException {
        if(null == file){
            BizException.fail("上传文件为空");
        }
        String fileName = file.getOriginalFilename();
        System.out.println("文件名称：" +fileName );
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        System.out.println("后缀为：" + fileSuffix);
        //判断文件是否是excel文件
        if(!fileSuffix.equals("xls") && !fileSuffix.equals("xlsx")){ //不是excel文件
            BizException.fail("上传文件不是excel");
        }
        ReadExcelVO readExcelVO = ExcelUtil.readExcelPlaylist(file.getInputStream(), fileSuffix, 0);
//        for (Music music:readExcelVO.getMusicList()) {
//            System.out.println(music.toString());
//            System.out.println("------------------------------");
//        }
        System.out.println("音乐的数量：" + readExcelVO.getMusicList().size());

//        for (Playlist playlist:readExcelVO.getPlaylists()) {
//            System.out.println(playlist.toString());
//            System.out.println("------------------------------");
//        }
        System.out.println("歌单的数量：" + readExcelVO.getPlaylists().size());

//        for (PlaylistMusic playlistMusic:readExcelVO.getPlaylistMusics()) {
//            System.out.println(playlistMusic.toString());
//            System.out.println("------------------------------");
//        }
        System.out.println("关联的数量：" + readExcelVO.getPlaylistMusics().size());

        boolean result = dataImportService.importPlaylist(readExcelVO);

        //退出成功
        if (result) {
            return ApiResponseWrapper.wrap("导入成功");
        }
        //退出失败
        return ApiResponseWrapper.wrap(ApiResponseCode.FAILURE, "导入失败");
    }
}
