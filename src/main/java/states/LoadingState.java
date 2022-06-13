package states;

import gameObjects.Constants;
import graphics.Assets;
import graphics.Loader;
import graphics.Text;
import math.Vector2D;

import java.awt.*;

public class LoadingState extends State {

    private Thread loadingThread;

    private Font font;

    public LoadingState(Thread loadingThread) {
        this.loadingThread = loadingThread;
        this.loadingThread.start();
        font = Loader.loadFont("/fonts/futureFont.ttf", 38);
    }

    @Override
    public void update() {
        if (Assets.loaded) {
            State.changeState(new MenuState());
            try {
                loadingThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void draw(Graphics g) {
        GradientPaint gp = new GradientPaint(
                Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2,
                Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2,
                Color.WHITE,
                Constants.WIDTH / 2 + Constants.LOADING_BAR_WIDTH / 2,
                Constants.HEIGHT / 2 + Constants.LOADING_BAR_HEIGHT / 2,
                Color.BLUE
        );

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(gp);

        float percentage = (Assets.count / Assets.MAX_COUNT);

        g2d.fillRect(Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2,
                Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2,
                (int) (Constants.LOADING_BAR_WIDTH * percentage),
                Constants.LOADING_BAR_HEIGHT);

        g2d.drawRect(Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2,
                Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2,
                Constants.LOADING_BAR_WIDTH,
                Constants.LOADING_BAR_HEIGHT);

        Text.drawText(g2d, "ASTEROIDS CLONE", new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2 - 50),
                true, Color.WHITE, font);


        Text.drawText(g2d, "LOADING...", new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2 + 40),
                true, Color.WHITE, font);

    }

}
