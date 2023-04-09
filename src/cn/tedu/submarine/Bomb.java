package cn.tedu.submarine;

import javax.swing.*;

//炸弹
public class Bomb extends SeaObject {
    public Bomb(int x,int y){
        super(9,12, x,y,3);

    }
    public void move(){
        y+=speed;//y+向下
    }

    //重写getImage（）来获取图片
    public ImageIcon getImage(){
        return Images.bomb;//返回图片
    };

    /**检测炸弹是否越界*/
    public boolean isOutOfBound(){
        return this.y >= World.HEIGHT;
    }

}
