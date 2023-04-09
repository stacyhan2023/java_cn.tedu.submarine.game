package cn.tedu.submarine;

import java.io.Serializable;
import java.util.Random;
import javax.swing.ImageIcon;
import java.awt.Graphics;

/**海洋对象*/
public abstract class SeaObject implements Serializable {
    public static final int LIVE =0;
    public static final int DEAD =1;
    protected int state= LIVE;

    protected int width;
    protected int height;
    protected int x;
    protected int y;
    protected int speed;

    //鱼雷潜艇，水雷潜艇，侦查潜艇的构造方法
    public SeaObject(int width,int height){
        this.width=width;
        this.height=height;
        x=-width;
        Random rand=new Random();
        y=rand.nextInt(World.HEIGHT-height-150+1)+150;
        speed= rand.nextInt(3)+1;
    }
    //专门给战舰，水雷，炸弹提供的构造方法
    public SeaObject(int width,int height,int x,int y,int speed){
        this.width=width;
        this.height=height;
        this.x=x;
        this.y=y;
        this.speed=speed;
    }

    /**行为不一样，移动，画图*/
    public abstract void move();
    public abstract ImageIcon getImage();

    /** 判断对象是活是死*/
    public boolean isLive(){
        return state == LIVE;//若当前状态为live，则返回true，否则返回false
    }
    public boolean isDead(){
        return state== DEAD;
    }

    /**画对象 g：画笔 *///----不要求掌握
    public void paintImage(Graphics g){
        if(this.isLive()){
            this.getImage().paintIcon(null,g,this.x,this.y);
        }
    }

    /**检测潜艇是否越界*/
    public boolean isOutOfBound(){
        return this.x >= World.WIDTH;
    }

    /**检测碰撞 this：一个对象； other：另一个对象*/
    public boolean isHit(SeaObject other){
        //假设：this表示潜艇，other表示炸弹
        int x1=this.x-other.width; //潜艇的x-炸弹的宽
        int x2=this.x+this.width;//潜艇的x+潜艇的宽
        int y1=this.y- other.height;//潜艇的y-炸弹的高
        int y2=this.y+this.height;//潜艇的y+潜艇的高

        int x= other.x;//x：炸弹的x
        int y= other.y;//y：炸弹的y

        return x>x1 && x<x2 && y>y1 && y<y2 ;

    }

    /**海洋对象去死*/
    public void goDead(){
        state = DEAD;//将当前对象改为dead
    }




}
