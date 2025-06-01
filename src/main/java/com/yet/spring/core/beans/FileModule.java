package com.yet.spring.core.beans;

public interface FileModule {
    boolean supports(String filePath);

    String getDescription();

    void process(String filePath);
}
