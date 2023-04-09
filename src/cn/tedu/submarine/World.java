package cn.tedu.submarine;
import javax.swing.*;
import java.awt.Graphics;
import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**整个窗口世界*/
public class World extends JPanel{//2.
    public static final int WIDTH =625;
    public static final int HEIGHT = 468;

    public static final int RUNNING = 0; //运行状态
    public static final int PAUSE = 1; //暂停状态
    public static final int GAVE_OVER =2; //结束状态
    private int state = RUNNING; //当前状态（默认运行状态）

    //如下这一对对象就是窗口中你所看到的对象
    private Battleship ship = new Battleship();
    private SeaObject[] submarines= {};
    private Mine [] mines = {};
    private Bomb[] bombs={};

    /**生成潜艇对象（侦查，鱼雷，水雷）*/
    public SeaObject nextSubmarines(){
        Random rand = new Random();
        int type = rand.nextInt(20);
        if(type<10){
            return new ObserveSubmarine();
        } else if(type<16){
            return new TorpedoSubmarine();
        }   else{
            return new MineSubmarine();
        }
    }

    private int subEnterIndex = 0;//潜艇入场计数
    /**潜艇入场 */
    private void submarineEnterAction(){  //每10毫秒走一次
        subEnterIndex++; //每10毫秒增1
        if(subEnterIndex%40==0){    //每400（40*10）毫秒走一次  40，80，120。。。
            SeaObject obj = nextSubmarines();     //获取潜艇对象
            submarines = Arrays.copyOf(submarines,submarines.length+1);//扩容
            submarines[submarines.length-1] = obj; //将新出来的潜艇对象装到最后一个数组里
        }
        
    }

    private int mineEnterIndex= 0;//水雷入场计数
    /**水雷入场*/
    private void mineEnterAction(){//  1000 ：100，200，300。。。
        mineEnterIndex++;//每10毫秒增1
        if(mineEnterIndex%100==0){//每100毫秒走一次
            for(int i=0;i<submarines.length;i++){//遍历所有潜艇
                if(submarines[i] instanceof MineSubmarine){//若潜艇为水雷潜艇
                    MineSubmarine ms=(MineSubmarine)submarines[i];//将潜艇强转为水雷潜艇类型
                    Mine obj=ms.shootMine();//获取水雷对象
                    mines =Arrays.copyOf(mines,mines.length+1);//扩容
                    mines[mines.length-1]= obj;//将obj添加到最后一个元素

                }

            }
        }
    }

    /**海洋对象移动*/
    private void moveAction(){//每10毫秒走一次
        for(int i=0;i<submarines.length;i++){
            submarines[i].move();//潜艇移动
        }
        for(int i =0;i<mines.length;i++){
            mines[i].move();//水雷移动
        }
        for(int i=0;i< bombs.length;i++){
            bombs[i].move();//炸弹移动
        }

    }

    /**删除越界和死了的的海洋对象*/
    private void outOfBoundsAction(){//每10毫秒走一次
        for(int i=0; i<submarines.length;i++){
            if(submarines[i].isOutOfBound() || submarines[i].isDead()){
                submarines[i]=submarines[submarines.length-1];
                submarines=Arrays.copyOf(submarines,submarines.length-1);
            }
        }
        for(int i=0;i<mines.length;i++){
            if(mines[i].isOutOfBound() || mines[i].isDead()){
                mines[i]=mines[mines.length-1];
                mines=Arrays.copyOf(mines,mines.length-1);
            }
        }
        for(int i=0;i< bombs.length;i++){
            if(bombs[i].isOutOfBound() || bombs[i].isDead()){
                bombs[i]=bombs[bombs.length-1];
                bombs=Arrays.copyOf(bombs,bombs.length-1);
            }
        }

    }

    private int score= 0;
    /**炸弹与潜艇碰撞*/
    private void bombBangAction(){//每10毫秒走一次
        for(int i=0;i<bombs.length;i++){//遍历所有炸弹
            Bomb b= bombs[i];//获取炸弹
            for(int j=0;j<submarines.length;j++){//宾利所有潜艇
                SeaObject s =submarines[j];//获取潜艇
                if(b.isLive() && s.isLive() && s.isHit(b)){//判断活着并且撞上了
                    s.goDead();
                    b.goDead();
                    if(s instanceof EnemyScore){//若被撞潜艇为分
                        EnemyScore es =(EnemyScore) s;//将被撞潜艇强转为得分接口
                        score +=es.getScore();//玩家得分
                    }
                    if(s instanceof EnemyLife){//若被撞潜艇为命
                        EnemyLife el = (EnemyLife)s; //将被撞潜艇强转为得命接口
                        int num = el.getLife();//获取命数
                        ship.addLife(num);//战舰增命

                    }
                }


            }

        }

    }

    /**水雷与战舰的碰撞*/
    private void mineBangAction(){//每10毫秒走一次
        for(int i=0;i<mines.length;i++){//遍历所有水雷
            Mine m= mines[i];//获取每一个水雷
            if(m.isLive() && ship.isLive() && m.isHit(ship)){
                m.goDead();//水雷去死
                ship.subtractLife();//战舰减命
            }
        }
    }

    /**检测游戏结束*/
    private void checkGameOverAction(){//每10毫秒走一次
        if(ship.getLife()<=0){//若战舰命数<=0则结束游戏
            state = GAVE_OVER;//将当前状态修改为结束状态

        }
    }

    
    /**启动程序的执行*///实例方法访问实例变量（无static）
    private void action(){
        //游戏一开始，先询问是否读取存档
        File file=new File("game.sav");
        if(file.exists()){//判断存档文件是否存在，若存在则询问用户是否存档
            int r=JOptionPane.showConfirmDialog(
                    World.this,
                    "收否读取存档？"
            );
            if(r== JOptionPane.YES_OPTION){
                //读取存档
                try {
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    Object game=ois.readObject();
                    GameInfo gameInfo=(GameInfo) game;
                    //GameInfo gameInfo=(GameInfo) ois.readObject();
                    ship = gameInfo.getShip();
                    submarines=gameInfo.getSubmarines();
                    mines=gameInfo.getMines();
                    bombs=gameInfo.getBombs();
                    subEnterIndex=gameInfo.getSubEnterIndex();
                    mineEnterIndex=gameInfo.getMineEnterIndex();
                    score=gameInfo.getScore();

                    ois.close();


                }catch(Exception ex){}

            }

        }

        KeyAdapter k = new KeyAdapter() {
            /**重写keyReleased*/
            public void keyReleased(KeyEvent e) {//键盘侦听器
                if(e.getKeyCode()==KeyEvent.VK_P){//若抬起的是p键
                        state=PAUSE;//将游戏暂停

                   //方法返回值表示用户在弹出窗口上点击的是哪个按键
                    int r= JOptionPane.showConfirmDialog(
                            World.this,//参数1:缺人框在游戏窗口上弹出
                            "保存游戏吗"//参数2:确认框上的文字
                    );
                    //判断用户点击的是yes这个按钮我们才进行存档
                    if(r== JOptionPane.YES_OPTION){
                        //将当前world中各项信息都楚按到GameInfo中
                        GameInfo gameInfo= new GameInfo(
                                ship,submarines,mines,bombs,subEnterIndex,mineEnterIndex,score
                        );

                        try {
                            FileOutputStream fos = new FileOutputStream("game.sav");
                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                            oos.writeObject(gameInfo);//将当前游戏所有数据写入文件保存
                            oos.close();
                        }catch(Exception exception){ }



                    }

                    state = RUNNING;//当确认窗口消失让游戏运行

                }



                if(state == RUNNING){//仅在运行状态下执行
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        Bomb obj = ship.shootBomb();//获取炸弹对象
                        bombs =Arrays.copyOf(bombs,bombs.length+1);
                        bombs[bombs.length-1]=obj;
                    }
                    if(e.getKeyCode()==KeyEvent.VK_LEFT){
                        ship.moveLeft();
                    }
                    if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                        ship.moveRight();
                    }
                }
            }
        };
        this.addKeyListener(k);//添加监听，不要求掌握

        Timer timer =new Timer();//定时器对象
        int interval = 10;       //定时间隔（以毫秒为单位））
        timer.schedule(new TimerTask() {
            public void run() {   //run是定时干的事
                if(state == RUNNING){//仅在运行状态进行
                    submarineEnterAction();//潜艇入场
                    mineEnterAction();//水雷入场
                    moveAction();//海洋对象移动
                    outOfBoundsAction();//删除越界对象
                    bombBangAction();//炸弹与潜艇碰撞
                    mineBangAction();//水雷与战舰碰撞
                    checkGameOverAction();//检测游戏结束
                    repaint(); //重画 系统自动调用paint方法
                }
            }
        }, interval, interval);//定时日程计划

    }

    /**重写paint（）画 g：系统自带的画笔*/
    public void paint(Graphics g){ //每10毫秒走一次
        Images.sea.paintIcon(null,g,0,0);
        ship.paintImage(g);
        for(int i=0;i< submarines.length;i++){
            submarines[i].paintImage(g);
        }
        for(int i=0;i<mines.length;i++){
            mines[i].paintImage(g);
            }
        for(int i=0;i< bombs.length;i++){
            bombs[i].paintImage(g);
        }
        g.drawString("SCORE:"+score,200,50);//画分，不要求掌握
        g.drawString("LIFE:"+ship.getLife(),400,50);//画命，不要求掌握

        if(state == GAVE_OVER){
            Images.gameover.paintIcon(null,g,0,0);//画结束图，不要求掌握


        }

    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();//3.
        World world= new World();//new对象分配成员变量
        world.setFocusable(true);
        frame.add(world);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH+16,HEIGHT+39);//苹果不需要加宽16
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);//系统自动调用paint方法

        world.action();//启动程序的执行

    }
}
