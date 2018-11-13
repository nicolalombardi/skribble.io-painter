package com.nlombardi.scribbleiopainter;

public class SkribbleBot {
    private static SkribbleBot instance;

    protected SkribbleBot(){

    }

    public static SkribbleBot getInstance(){
        if(instance == null){
            instance = new SkribbleBot();
        }
        return instance;
    }






}
