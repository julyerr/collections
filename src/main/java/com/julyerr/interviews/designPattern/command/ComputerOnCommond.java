package com.julyerr.interviews.designPattern.command;

public class ComputerOnCommond implements Command {
    private Computer computer ;

    public ComputerOnCommond( Computer computer){
        this.computer = computer;
    }

    @Override
    public void execute(){
        computer.on();
    }

}
