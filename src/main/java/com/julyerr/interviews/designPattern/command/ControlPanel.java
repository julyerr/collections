package com.julyerr.interviews.designPattern.command;

public class ControlPanel {
    private static final int CONTROL_SIZE = 9;
    private Command[] commands;

    public ControlPanel() {
        commands = new Command[CONTROL_SIZE];
        /**
         * 初始化所有按钮指向空对象
         */
        for (int i = 0; i < CONTROL_SIZE; i++) {
            commands[i] = new NoCommond();
        }
    }

    public void setCommand(int index, Command command) {
        commands[index] = command;
    }

    public void keyPressed(int index) {
        commands[index].execute();
    }

    public static void main(String[] args)
    {

        Light light = new Light();
        Computer computer = new Computer();

        ControlPanel controlPanel = new ControlPanel();
        // 为每个按钮设置功能
        controlPanel.setCommand(0, new LightOnCommond(light));
        controlPanel.setCommand(1, new LightOffCommond(light));
        controlPanel.setCommand(2, new ComputerOnCommond(computer));
        controlPanel.setCommand(3, new ComputerOffCommond(computer));

        // 模拟点击
        controlPanel.keyPressed(0);
        controlPanel.keyPressed(2);

        QuickCommond quickCommond = new QuickCommond(new Command[]{
                new LightOffCommond(light),
                new ComputerOnCommond(computer)
        });
        System.out.println("一键快捷方式");
        controlPanel.setCommand(4,quickCommond);
        controlPanel.keyPressed(4);
    }
}
