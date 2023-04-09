package cn.tedu.submarine;

import javax.swing.*;
import java.util.Random;

//水雷潜艇
public class MineSubmarine extends SeaObject implements EnemyLife{


    public MineSubmarine(){
        super(63,19);
    }

    /**重写move（）移动*/
    public void move(){
        x+=speed;//x+向右移动
    }

    /**重写getImage（）来获取图片*/
    public ImageIcon getImage(){
        return Images.minesubm;//返回战舰图片
    };

    /**发射水雷-生成水雷对象*/
    public Mine shootMine(){
        return new Mine(this.x-this.width,this.y-11);//水雷的x，水雷的y
    }

    /**重写getlife（）得命*/
    public int getLife(){
        return 1; //打掉水雷潜艇，战舰得一条命
    }

}
