package com.ahao.forum.guitar.module.thread.service;

import com.ahao.commons.entity.IDataSet;

import java.util.List;

public interface ThreadService {

    long saveThread(Long threadId, String title, String content, Long userId, Long forumId);

    int deleteThread(Long... threadIds);

    IDataSet getThread(Long threadId);

    List<IDataSet> getPosts(Long threadId);

}
