package com.julyerr.interviews.designPattern.command;

public class QuickCommond implements Command {
    private Command[] commands;

    public QuickCommond(Command[] commands){
        this.commands = commands;
    }

    @Override
    public void execute(){
        for (int i = 0; i < commands.length; i++){
            commands[i].execute();
        }
    }

}
