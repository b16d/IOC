package com.acl.data;

import com.acl.ioc.annotation.MyService;

@MyService
public class Service {

    private final Repo repo;

    private final DTO dto;

    private final Connector connector;


    public Service(Repo repo, DTO dto, Connector connector) {
        this.repo = repo;
        this.dto = dto;
        this.connector = connector;
    }
}
