package cn.tedu.submarine;
import javax.swing.ImageIcon;
/*
注意：点项目邮件new一个directory，起名img，将8张图片粘贴过来
 */
/** 图片类*/
public class Images {
    public static ImageIcon sea;
    public static ImageIcon battleship;
    public static ImageIcon obsersubm;
    public static ImageIcon torpesubm;
    public static ImageIcon minesubm;
    public static ImageIcon mine;
    public static ImageIcon bomb;
    public static ImageIcon gameover;

    static{//初始化静态图片
        //将img中的sea.png读取到静态变量sea中；前面变量名后边图片名字
        sea = new ImageIcon("img/sea.png");
        battleship = new ImageIcon("img/battleship.png");
        obsersubm = new ImageIcon("img/obsersubm.png");
        torpesubm = new ImageIcon("img/torpesubm.png");
        minesubm = new ImageIcon("img/minesubm.png");
        mine = new ImageIcon("img/mine.png");
        bomb = new ImageIcon("img/bomb.png");
        gameover = new ImageIcon("img/gameover.png");
    }

    public static void main(String[] args) {//这里的main主要是测试图片是否成功，主窗口在world
        //返回8表示图片读取成功，其他数字代表失败
        System.out.println(sea.getImageLoadStatus());//8
        System.out.println(battleship.getImageLoadStatus());
        System.out.println(obsersubm.getImageLoadStatus());
        System.out.println(torpesubm.getImageLoadStatus());
        System.out.println(minesubm.getImageLoadStatus());
        System.out.println(mine.getImageLoadStatus());
        System.out.println(bomb.getImageLoadStatus());
        System.out.println(gameover.getImageLoadStatus());





    }


}

//图片也是一个数据
//基本类型装不了，数组装不了（装一组数，图片是一个数）
//java提供了图片类型----Image,Icon,ImageIcon,BufferedImage...