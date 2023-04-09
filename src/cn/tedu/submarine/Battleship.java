package cn.tedu.submarine;

import javax.swing.ImageIcon;

//战舰
public class Battleship extends SeaObject{
    /*
    属性：width,height,x,y,speed,life
    行为：move()
     */

    private int life;

    /**构造方法*/
    public Battleship(){
        super(66,26,270,124,40);
        life=5;
    }

    public void move(){
    }

    /**重写getImage（）来获取图片*/
    public ImageIcon getImage(){
        return Images.battleship;//返回战舰图片
    };

    /**发射炸弹-生成炸弹对象*/
    public Bomb shootBomb(){
        return new Bomb(this.x,this.y);
    }

    public void moveLeft(){
        x -=speed;
    }
    public void moveRight(){
        x +=speed;
    }

    /**战舰增命*/
    public void addLife(int num){
        life += num;//命数增num
    }

    /**获取命数*/
    public int getLife(){
        return life;//返回命数
    }

    /**战舰减命*/
    public void subtractLife(){
        life--;//命数减1
    }




}
