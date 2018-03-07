package com.julyerr.interviews.designPattern.observe.internel;

import java.util.Observable;
import java.util.Observer;

public class Observer1 implements Observer {

    public Observer1(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        SubjectFor3d subjectFor3d = (SubjectFor3d) o;
        System.out.println("observer1  subjectFor3d's msg -- >" + subjectFor3d.getMsg());
    }
}
