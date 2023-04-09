package cn.tedu.submarine;

import javax.swing.*;
import java.util.Random;

//鱼类潜艇
public class TorpedoSubmarine extends SeaObject implements EnemyScore{

    public TorpedoSubmarine(){
        super(64,20);
    }
    public void move(){
        x+=speed;//x+向右移动
    }

    //重写getImage（）来获取图片

    public ImageIcon getImage(){
        return Images.torpesubm;//返回战舰图片
    };

    /**重写getscore（）得分*/
    public int getScore(){
        return 40;
    }



}
