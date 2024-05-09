package com.ezban.qa.model;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QaInitializer {

    @Autowired
    private QaRepository qaRepository;

    @PostConstruct
    public void init() {
        qaRepository.save(new Qa("這是什麼？", "這是一個示例常見問題的折疊內容。"));
        qaRepository.save(new Qa("如何使用？", "可以點擊問題標題來展開或折疊答案。"));
        qaRepository.save(new Qa("是否可以自定義樣式？", "是的，你可以通過CSS來自定義問題和答案的樣式。"));
    }
}
