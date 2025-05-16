package com.example.demo.dto;

import com.example.demo.model.ChatRoom;
import com.example.demo.repo.ChatRepo;

public class LoadInfo {

    private ChatRoom chatRepo;
    private int size;
    private int page;

    public ChatRoom getChatRepo() {
        return chatRepo;
    }

    public void setChatRepo(ChatRoom chatRepo) {
        this.chatRepo = chatRepo;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
