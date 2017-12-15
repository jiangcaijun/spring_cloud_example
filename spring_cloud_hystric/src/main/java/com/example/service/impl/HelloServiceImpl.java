package com.example.service.impl;

import com.example.service.HelloService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Autor jiangcaijun
 * @Date 2017/11/19
 * @Time 10:49
 */
@Service
public class HelloServiceImpl implements HelloService{

    @Autowired
    RestTemplate restTemplate;


    /**
     * 通过之前注入ioc容器的restTemplate来消费service-hi服务的“/hi”接口，
     * 在这里我们直接用的程序名替代了具体的url地址，在ribbon中它会根据服务名来选择具体的服务实例，根据服务实例在请求的时候会用具体的url替换掉服务名
     * @param name
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "hiError") //该注解对该方法创建了熔断器的功能，并指定了fallbackMethod熔断方法
    public String hiService(String name) {
        return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
    }

    @Override
    public String hiError(String name) {
        return "hi,"+name+",sorry,error!";
    }

}