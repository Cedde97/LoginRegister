package model;

import exception.*;
import source.DateiMemoDbSource;


/**
 * Created by Joshi on 11.08.2017.
 */

public class Corner {
    private double x;
    private double y;

    public Corner(double x,double y) throws XMustBeLargerThanZeroException, YMustBeLargerThanZeroException {

        if(x < 0){
            throw new XMustBeLargerThanZeroException("X has to be larger than Zero");
        }else if(y < 0){
            throw new YMustBeLargerThanZeroException("Y has to be larger than Zero");

        }else{
            setX(x);
            setY(y);
        }

    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String toString() {
        return getX() + ", " + getY();
    }
}
