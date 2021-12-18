﻿# 贪吃蛇小游戏开发
## 0.成果展示
最终效果图：

![在这里插入图片描述](https://img-blog.csdnimg.cn/f059275fe5b746f7a0d2d2e0626f4953.png)
视频已上传到b站

https://www.bilibili.com/video/BV1ki4y1d7zo?share_source=copy_web


## 1.游戏引擎
### 核心逻辑
![一个游戏引擎的核心逻辑](https://img-blog.csdnimg.cn/2bb4a418627e4e07aa193e1fbc82d699.png#pic_center)
因为逻辑和画面的是一直在改变的，所以需要一个循环

```java
//无限循环的while，循环逻辑更新和画面重画
while(true){
            engine.updateLogic();
            game.repaint();//用repaint可以不用填参数
        }
```
有了驱动游戏的引擎,接下来还需要思考两个问题
		第一个是**游戏包含什么元素**
		第二个是**游戏的核心玩法是什么**
## 2.游戏元素
![在这里插入图片描述](https://img-blog.csdnimg.cn/edafd423091c44f7846f72e5a437ccb7.png)
蛇和苹果(食物)都是经典元素，而**矩形**是我们这个游戏的**最基础元素**，蛇和苹果包括还没写的障碍物都是由一个个小矩形构成，更具体的说明的会在对应的步骤里讲到
## 3.游戏核心玩法
还是直接上图，一目了然
```flow
flowchat
flowchat
flowchat
st=>start: 游戏初始化
e=>end: 游戏结束
op=>operation: 键盘输入
op2=>operation: 移动
op3=>operation: 身体长一节
cond=>condition: 是否吃到苹果
cond2=>condition: 是否撞墙或吃到自己

st->op->op2->cond
cond(yes)->op3
cond(no)->cond2
cond2(yes)->e
cond2(no)->op
```
这样准备工作就完成了，接下来是正式的开发步骤
## 4.开发步骤
### （1）创建游戏引擎
创建GameEngine抽象模板类，在构造方法内实现了KeyListener键盘监听的匿名内部类，这里的keyCode就是键盘输入的按键用getter获取，返回值的定义在KeyEvent里
```java
public GameEngine() {
        this.keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                keyCode = e.getKeyCode();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
```
另外还有两个抽象方法updateLogic和renderUI分别是游戏逻辑更新和画面更新
### （2）生成窗体，绘制面板
首先创建一个Game类这个类继承了JPanel，实例化JFrame来生成窗口，写一个init初始化方法，方法内要设置好窗体和面板的各个属性
```java
//实例化JFrame
		frame = new JFrame(title);
		//设置窗口关闭方法
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
        Game类是JPanel的子类，paint在game类自己创建的面板中重写并覆盖
        所以要用Game类new一个面板的对象
        这样画笔画出来才是自己的画
         */
         //实例化panel
        Game game = new Game();
        //添加键盘监听时间
        game.addKeyListener(engine.keyListener);
        //焦点事件
        game.setFocusable(true);
        //设置窗口大小
        game.setPreferredSize(new Dimension(WINDOW_WIDTH,
                WINDOW_HEIGHT));

        frame.add(game);    //面板镶嵌到窗口上
        frame.pack();     //窗口适应面板大小
        frame.setLocation(Config.WINDOW_LOCATION_X,Config.WINDOW_LOCATION_Y); //设置窗口初始位置
        frame.setResizable(false);  //窗口大小固定
        frame.setVisible(true);		//窗口可见
```
效果图
![](https://img-blog.csdnimg.cn/a03a14e9f2b74bb487319a26a8a6ba2c.png)

### （3）生成格子，便于调试
为了方便调试，我们想要在面板上画好网格，那么首先还是创建一个新的类叫做Grid，每当新建一个类时都要思考，这个类**有什么静态属性，以及有什么动态的方法**，这个类需要把自己画出来，创建一个draw方法，其实Grid的size应该是要在Grid类中定义，但是为了省事，我把一些静态属性统一放在Config类中定义成常量
```java
public void draw(Graphics2D graphics2D) {
        //把竖线画出来
        //i每加一，x加十，x从零开始
        for (int i = 0; i < (Config.COLUMN); i++) {
            graphics2D.drawLine(Config.GRID_SIZE * i, 0,
                    Config.GRID_SIZE * i, Config.WINDOW_HEIGHT);
        }
        //横线同上
        for (int i = 0; i < (Config.LINE); i++) {
            graphics2D.drawLine(0, Config.GRID_SIZE * i,
                    Config.WINDOW_WIDTH, Config.GRID_SIZE * i);
        }
    }
```
用两个for循环就解决了，分别生成竖线和横线
![在这里插入图片描述](https://img-blog.csdnimg.cn/9fa01245afa344f3ab7db7ca3c7464fd.png)
### （4）生成节点，苹果和蛇
前面提到本游戏的元素（蛇苹果）都是由节点组成，每一个网格就是一个节点，那么依旧是先创建类叫做Node，每个节点要有相应的坐标，因为网格已经成型，每个节点对应坐标都在生成时创建，写在构造方法中
```java
 public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
```
还是需要把自己画出来draw方法，节点都是小矩形所以用fillRect()
```java
public void draw(Graphics2D graphics2D){
        //(3,1),填充矩形要用到fillRect()
        graphics2D.fillRect(Config.GRID_SIZE *x, Config.GRID_SIZE*y,
                Config.GRID_SIZE, Config.GRID_SIZE);
    }
```
在Apple类draw方法中实例化Node并调用draw方法，苹果颜色设置成红色，当然苹果是随机生成的需要用到Random类，让苹果在地图中随机生成，写在构造方法中
```java
public Apple() {
        this.apple = new Node(random.nextInt(Config.COLUMN), random.nextInt(Config.LINE));
    }

public void draw (Graphics2D graphics2D){
        graphics2D.setColor(Color.red);
        apple.draw(graphics2D);
    }
```
Snake稍稍复杂些，用到了链表LinkedList，增删都方便，后面写移动和死亡检测的逻辑就会省很多事
```java
	LinkedList<Node> snake = new LinkedList<Node>();
public void draw(Graphics2D graphics2D) {
        for (int i = 0; i < snake.size(); i++) {
            //蛇头
            if (i == 0) {
                graphics2D.setColor(Color.GREEN);
            } else {     //蛇身
                graphics2D.setColor(Color.black);
            }
            //snake里的node取出来draw
            snake.get(i).draw(graphics2D);
        }
    }
```
写到这游戏看起来基本就成型了
![在这里插入图片描述](https://img-blog.csdnimg.cn/204ed9ea904b4e71b98818bfd2b1fa2e.png)

### （5）实现蛇的移动和吃苹果
蛇的移动也比较简单，加一个头减掉一个尾巴就可以了，要注意坐标轴长这样![在这里插入图片描述](https://img-blog.csdnimg.cn/885ce6e1a5d04d9a83828ae1a9019b6e.png#pic_center)
举个例子，向上移动新头y-1，x不变，去掉尾巴
```java
public moveUp(){
snake.addfirst(new Node(snake.getFirst.x,snake.getFirst.y-1));
snake.removeLast();
}
```
其余同理，不再赘述
蛇吃苹果的思路是，检测蛇头是否和苹果重合，如果重合蛇身要长一节，封装一个方法isEatApple检测是否重合返回布尔值，在移动方法接收这个布尔值如果为真则不移除尾巴
```java
public boolean isEatApple(Apple apple) {
        if (snake.getFirst().x == apple.getAppleX() &&
                snake.getFirst().y == apple.getAppley()) {
            return true;
        } else {
            return false;
        }
    }

public void moveUp(boolean isEatApple) {
            snake.addFirst(new Node(snake.getFirst().x, snake.getFirst().y - 1));
            if (isEatApple == true) {
            } else {
                snake.removeLast();
            }
    }
```
蛇吃完苹果，还得新生成一个，在Apple类中，在isEatApple方法中如果检测蛇吃到苹果调用这个方法
```java
public void generateNext() {
        apple =  new Node(random.nextInt(Config.COLUMN), random.nextInt(Config.LINE));
    }
}
```
## 5.总结
一个完整项目基本搭建完成，其实还是剩下不少细节可以优化，包括这篇博客有很多地方不知道如何去表达，导致不少在实际开发时一直卡着的地方都一笔带过了，比方说在实例化面板的时候，死活画不出东西，原来是实例化的时候用的是JPanel，那当然不行，用父类JPanel实例化面板，用的就是JPanel的paint方法，这个方法是父类的方法，需要在自己的面板类里面实现，所以任由我怎么在paint里画，面板都是不会有画面的，像这样的小问题很多很多，一个人常常会有犯蠢的时候，总会搞出一些匪夷所思的问题，好的团队这时就起作用了，你可能想个一天半天才能解决的问题，同伴来瞅一眼一下子就解决了，我的团队也给了我很大的帮助，提供思路，纠错等等，非常感谢他们。

