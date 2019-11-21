package com.practice.ch02.second;

import java.io.IOException;

public class StaticResourceProcessor {

    public void process(Request request, Response response){
        try{
            response.sendStaticResource();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
