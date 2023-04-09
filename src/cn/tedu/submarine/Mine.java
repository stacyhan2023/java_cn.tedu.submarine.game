package cn.tedu.submarine;

import javax.swing.*;

//水雷
public class Mine extends SeaObject{


    public Mine(int x,int y){//每个水雷的初始坐标都不一样
       super(11,11,x,y,1);

    }

    public void move(){
        y-=speed;//y-向上
    }

    //重写getImage（）来获取图片
    public ImageIcon getImage(){
        return Images.mine;//返回图片
    };

    /**检测水雷是否越界*/
    public boolean isOutOfBound(){
        return this.y<=150-this.height;
    }


}
