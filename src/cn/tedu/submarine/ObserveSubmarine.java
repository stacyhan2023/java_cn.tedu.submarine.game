package cn.tedu.submarine;

import javax.swing.ImageIcon;
import java.util.Random;

//侦查潜艇
public class ObserveSubmarine extends SeaObject implements EnemyScore{


    public ObserveSubmarine(){
        super(63,19);
        /*
        y=rand.nextInt(311);0-310
        y=rand.nextInt()+150
        y=(150,479-height)
        y=(150,460)
        y=rand.nextInt(479-height-150+1)+150
         */
    }

    public void move(){
        x+=speed;//x+向右移动
    }

    //重写getImage（）来获取图片
    public ImageIcon getImage(){
        return Images.obsersubm;//返回战舰图片
    };

    /**重写getscore（）得分*/
    public int getScore(){
        return 10; //打掉侦查潜艇得10分
    }

}
