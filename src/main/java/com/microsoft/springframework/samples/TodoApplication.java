/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.springframework.samples;

import com.microsoft.springframework.samples.dao.ServiceConfigRepository;
import com.microsoft.springframework.samples.dao.SubtaskRepository;
import com.microsoft.springframework.samples.model.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.xml.ws.Service;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableCaching
public class TodoApplication {

    public static void main(String[] args) { SpringApplication.run(TodoApplication.class, args); }

}
