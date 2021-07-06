package com.music.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.common.BizException;
import com.demo.dao.FirstCommentsMapper;
import com.demo.dao.MemberMapper;
import com.demo.dao.ReplyCommentsMapper;
import com.demo.entity.FirstComments;
import com.demo.entity.Member;
import com.demo.entity.MemberToken;
import com.demo.entity.ReplyComments;
import com.demo.interfaceService.CommentsService;
import com.demo.param.*;
import com.demo.util.PageResult;
import com.demo.vo.FindCommentsVO;
import com.demo.vo.ReplyCommentsVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Resource
    private FirstCommentsMapper firstCommentsMapper;

    @Resource
    private ReplyCommentsMapper replyCommentsMapper;

    @Resource
    private ExecutorService executorService;

    @Resource
    private MemberMapper memberMapper;

    //拉取评论信息
    public PageResult<FindCommentsVO> findCommentsById(FindCommentsByIdParam findCommentsByIdParam) {

        if (findCommentsByIdParam.getPageNo() != -1) {
            PageHelper.startPage(findCommentsByIdParam.getPageNo(), findCommentsByIdParam.getPageSize());
        }

        if (findCommentsByIdParam.getType() == null) {
            findCommentsByIdParam.setType(0);
        }

        if (findCommentsByIdParam.getSortingType() == null) {
            findCommentsByIdParam.setSortingType(0);
        }

        List<FindCommentsVO> findCommentsVOList = firstCommentsMapper.findCommentsById(findCommentsByIdParam.getId(), findCommentsByIdParam.getType(), findCommentsByIdParam.getSortingType());

        //暂时使用该方法 --> 不知道如何优化，代以后技术牛逼来改
        List<Future<List<ReplyCommentsVO>>> list = new ArrayList<>();
        //暂定拉取二级评论根据热度排序前3条
        for (FindCommentsVO findCommentsVO:findCommentsVOList) {
            Future<List<ReplyCommentsVO>> futureList = executorService.submit(() -> replyCommentsMapper.findReplyCommentsByCommentsIdFixed(findCommentsVO.getCommentsId(), 3));
            list.add(futureList);
        }

//        Future<List<ReplyCommentsVO>> futureListOne = executorService.submit(() -> replyCommentsMapper.findReplyCommentsByCommentsIdFixed(findCommentsVOList.get(0).getCommentsId(), 3));
//        Future<List<ReplyCommentsVO>> futureListTwo = executorService.submit(() -> replyCommentsMapper.findReplyCommentsByCommentsIdFixed(findCommentsVOList.get(1).getCommentsId(), 3));
//        Future<List<ReplyCommentsVO>> futureListThree = executorService.submit(() -> replyCommentsMapper.findReplyCommentsByCommentsIdFixed(findCommentsVOList.get(2).getCommentsId(), 3));
//        Future<List<ReplyCommentsVO>> futureListFour = executorService.submit(() -> replyCommentsMapper.findReplyCommentsByCommentsIdFixed(findCommentsVOList.get(3).getCommentsId(), 3));
//        Future<List<ReplyCommentsVO>> futureListFive = executorService.submit(() -> replyCommentsMapper.findReplyCommentsByCommentsIdFixed(findCommentsVOList.get(4).getCommentsId(), 3));

        try {
//            findCommentsVOList.get(0).setReplyCommentsVOList(futureListOne.get());
//            findCommentsVOList.get(1).setReplyCommentsVOList(futureListTwo.get());
//            findCommentsVOList.get(2).setReplyCommentsVOList(futureListThree.get());
//            findCommentsVOList.get(3).setReplyCommentsVOList(futureListFour.get());
//            findCommentsVOList.get(4).setReplyCommentsVOList(futureListFive.get());
            for (int i = 0; i < findCommentsVOList.size(); i++) {
                findCommentsVOList.get(i).setReplyCommentsVOList(list.get(i).get());
            }
        } catch (InterruptedException | ExecutionException e) {
            BizException.fail("拉取线程数据异常");
        }

        //避免大于最后一页还返回数据
        PageInfo pageInfo = new PageInfo(findCommentsVOList);
        if(pageInfo.getPageNum() < findCommentsByIdParam.getPageNo()){
            pageInfo.setList(new ArrayList());
        }

        PageResult<FindCommentsVO> pageResult = new PageResult(pageInfo.getList(), new Long(pageInfo.getTotal()).intValue(), pageInfo.getPageSize(), findCommentsByIdParam.getPageNo());

        return pageResult;
    }

    //继续拉取回复评论
    public PageResult<ReplyCommentsVO> findReplyCommentsByCommentsId(FindReplyCommentsByCommentsIdParam findReplyCommentsByCommentsIdParam) {

        if (findReplyCommentsByCommentsIdParam.getPageNo() != -1) {
            PageHelper.startPage(findReplyCommentsByCommentsIdParam.getPageNo(), findReplyCommentsByCommentsIdParam.getPageSize());
        }

        if (findReplyCommentsByCommentsIdParam.getSortingType() == null) {
            findReplyCommentsByCommentsIdParam.setSortingType(0);
        }

        List<ReplyCommentsVO> replyCommentsVOS = replyCommentsMapper.findReplyCommentsByCommentsId(findReplyCommentsByCommentsIdParam.getCommentsId(), findReplyCommentsByCommentsIdParam.getSortingType());
        //避免大于最后一页还返回数据
        PageInfo pageInfo = new PageInfo(replyCommentsVOS);
        if(pageInfo.getPageNum() < findReplyCommentsByCommentsIdParam.getPageNo()){
            pageInfo.setList(new ArrayList());
        }

        PageResult<ReplyCommentsVO> pageResult = new PageResult(pageInfo.getList(), new Long(pageInfo.getTotal()).intValue(), pageInfo.getPageSize(), findReplyCommentsByCommentsIdParam.getPageNo());
        return pageResult;
    }

    //添加一级评论
    public boolean firstComments(MemberToken memberToken, FirstCommentsParam firstCommentsParam) {
        Member member = memberMapper.selectByPrimaryKey(memberToken.getMemberId());
        FirstComments firstComments = new FirstComments();
        firstComments.setMemberId(member.getMemberId());
        firstComments.setMemberName(member.getMemberName());
        //用户头像暂无
        firstComments.setArticleId(firstCommentsParam.getArticleId());
        firstComments.setArticleType(firstCommentsParam.getArticleType());
        firstComments.setCommentsContent(firstCommentsParam.getCommentsContent());

        return firstCommentsMapper.insertSelective(firstComments) == 1;
    }

    //添加回复评论
    public boolean replyComments(MemberToken memberToken, ReplyCommentsParam replyCommentsParam) {
        Member member = memberMapper.selectByPrimaryKey(memberToken.getMemberId());
        ReplyComments replyComments = new ReplyComments();
        replyComments.setCommentsId(replyCommentsParam.getCommentsId());
        replyComments.setReplyMemberId(member.getMemberId());
        replyComments.setReplyMemberName(member.getMemberName());
        //用户头像暂无
        replyComments.setReplyContent(replyCommentsParam.getReplyComments());
        replyComments.setByReplyMemberId(replyCommentsParam.getByReplyMemberId());
        replyComments.setByReplyMemberName(replyCommentsParam.getByReplyMemberName());
        replyComments.setByReplyMemberComments(replyCommentsParam.getByReplyMemberComments());

        return replyCommentsMapper.insertSelective(replyComments) == 1;
    }

    //点赞评论
    public boolean thumbComments(MemberToken memberToken, ThumbComments thumbComments) {

        if (thumbComments.getType() == 0) {
            FirstComments firstComments = firstCommentsMapper.selectByPrimaryKey(thumbComments.getId());
            firstComments.setThumbUpNumber(firstComments.getThumbUpNumber() + 1);
            return firstCommentsMapper.updateByPrimaryKeySelective(firstComments) == 1;
        } else {
            ReplyComments replyComments = replyCommentsMapper.selectByPrimaryKey(thumbComments.getId());
            replyComments.setThumbUpNumber(replyComments.getThumbUpNumber() + 1);
            return replyCommentsMapper.updateByPrimaryKeySelective(replyComments) == 1;
        }
    }
}
