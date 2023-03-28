package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Fruit;
import model.Node;
import model.Snake;

import java.io.File;
import java.util.*;


public class GameWindow {
    private final GraphicsContext gc;
    private final Scene scene;
    private final Snake snake;
    public static final int WINDOW_SIZE = 750;
    public static final int UNIT_SIZE = 15;
    private static final int MAX_FRUIT = 10;
    private final List<Fruit> fruits = new ArrayList<>();
    private long duration = 0;
    private int score = 0;
    private final Text scoreLabel = new Text("当前分数：0");
    private static MediaPlayer bgMusicPlayer;
    private static MediaPlayer itemMusicPlayer;

    GameWindow() {
        // 创建根节窗格
        Pane pane = new Pane();

        // 创建window大小为nxn的场景，n=windowSize
        this.scene = new Scene(pane, WINDOW_SIZE, WINDOW_SIZE);

        // 创建画布，并把画布放入根节点里
        Canvas canvas = new Canvas(WINDOW_SIZE, WINDOW_SIZE);
        pane.getChildren().add(canvas);

        // 把分数文本标签放入根节点里
        scoreLabel.setX(20);
        scoreLabel.setY(WINDOW_SIZE - 20);
        scoreLabel.setFont(new Font(20));
        scoreLabel.setFill(Paint.valueOf("white"));
        pane.getChildren().add(scoreLabel);

        // 拿到画布的2D画板
        gc = canvas.getGraphicsContext2D();

        this.snake = new Snake();

        keyboardBind();

        // 生成10个水果
        addNewFruit(10);

        // 设置音乐
        Media bgMusic = new Media(new File("src/main/resources/bg_music.mp3").toURI().toString());
        bgMusicPlayer = new MediaPlayer(bgMusic);
        bgMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        bgMusicPlayer.setAutoPlay(true);

        Media itemMusic = new Media(new File("src/main/resources/get_item.mp3").toURI().toString());
        itemMusicPlayer = new MediaPlayer(itemMusic);
    }

    public void addNewFruit(int n) {
        int added = 0;
        while (added < n && fruits.size() <= MAX_FRUIT) {
            Fruit newFruit = Fruit.generateFruit();

            // 如果新生成的水果和已生成的水果位置有冲突，重新生成
            boolean conflict = false;
            for (Fruit fruit : fruits) {
                if (newFruit.getX() == fruit.getX() && newFruit.getY() == fruit.getY()) {
                    conflict = true;
                    break;
                }
            }
            if (!conflict) {
                fruits.add(newFruit);
                added++;
            }
        }
    }

    public void keyboardBind() {
        List<KeyCode> validKeys = Arrays.asList(KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP, KeyCode.DOWN);
        scene.setOnKeyPressed(event -> {
            if (!validKeys.contains(event.getCode())) {
                return;
            }

            if (snake.nextDirection == null) {
                snake.nextDirection = event.getCode();
            } else {
                snake.directionStack = event.getCode();
            }
        });
    }

    Scene getScene() {
        return this.scene;
    }

    void run() {
        // 每一帧持续125微秒，也就是说我们的游戏刷新率是8帧每秒
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(125),
                (t) -> {
                    this.draw();
                    duration++;
                    if (duration % 40 == 0) {
                        addNewFruit(3);
                    }
                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // 每一帧会做的事
    private void draw() {
        if (snake.tick(fruits) > 0) {
            itemMusicPlayer.play();
            itemMusicPlayer.seek(Duration.millis(0));
            score++;
            scoreLabel.setText("当前分数：" + score);
        }

        gc.setFill(Paint.valueOf("black"));
        gc.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);

        gc.setFill(Paint.valueOf("red"));
        for (Fruit fruit : fruits) {
            gc.fillRect(fruit.getX(), fruit.getY(), UNIT_SIZE, UNIT_SIZE);
        }

        Node head = snake.getHead();
        gc.setFill(Paint.valueOf("limegreen"));
        gc.fillRect(head.getX(), head.getY(), UNIT_SIZE, UNIT_SIZE);

        gc.setFill(Paint.valueOf("black"));
        if (snake.getHead().getyVel() == 0) {
            gc.fillOval(head.getX() + UNIT_SIZE / 2.0 - 2.5, head.getY() + UNIT_SIZE / 2.0 - 2.5 - 3, 5, 5);
            gc.fillOval(head.getX() + UNIT_SIZE / 2.0 - 2.5, head.getY() + UNIT_SIZE / 2.0 - 2.5 + 3, 5, 5);
        } else {
            gc.fillOval(head.getX() + UNIT_SIZE / 2.0 - 2.5 - 3, head.getY() + UNIT_SIZE / 2.0 - 2.5, 5, 5);
            gc.fillOval(head.getX() + UNIT_SIZE / 2.0 - 2.5 + 3, head.getY() + UNIT_SIZE / 2.0 - 2.5, 5, 5);
        }


        gc.setFill(Paint.valueOf("green"));
        Node node = head.getNext();
        while (node != null) {
            gc.fillRect(node.getX(), node.getY(), UNIT_SIZE, UNIT_SIZE);
            node = node.getNext();
        }
    }
}
