package dino.game.oop.states;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dino.game.oop.DinoGame;
import dino.game.oop.extra.Score;
import dino.game.oop.music.MainSong;

import java.util.ArrayList;

public class HighScoreState extends State{
    private static final int NUM_WIDTH = 24;
    private static final int NUM_HEIGHT = 36;


    private String[] number = {"0.png", "1.png" ,"2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png"};

    private Texture background, scoreboard, score;
    private ArrayList<Texture> badge= new ArrayList<Texture>();
    private Texture homeBtn, exitBtn, resetBtn;

    private MainSong mainSong;
    private Sound c_btn;

    private float time;
    private Texture dust, backdrop;

    private String[] num_frame;

    private ArrayList<Texture> one, ten, rank;

    private boolean flag;


    public HighScoreState(GameStateManager gsm, MainSong mainSong) {
        super(gsm);

        num_frame = new String[20];

        one = new ArrayList<Texture>();
        ten = new ArrayList<Texture>();
        rank = new ArrayList<Texture>();

        for (int i = 0; i < 20 ; i++){
            num_frame[i] = "gif/"+Integer.toString(i+1)+".gif";
        }

        cam.setToOrtho(false, DinoGame.WIDTH,DinoGame.HEIGHT);
        background = new Texture("bg.png");
        scoreboard = new Texture("board.png");
        score = new Texture("score.png");

        homeBtn = new Texture("homebtn.png");
        exitBtn = new Texture("exitbutton.png");
        resetBtn = new Texture("reset.png");

        badge.add(new Texture("1st.png"));
        badge.add(new Texture("2nd.png"));
        badge.add(new Texture("3rd.png"));

        System.out.println(Score.getScore());
        this.mainSong = mainSong;

        c_btn = Gdx.audio.newSound(Gdx.files.internal("Sound/click.wav"));

        for (int i = 0 ; i < 3 ; i++){
            one.add(new Texture(number[Score.getScore().get(i)%10]));
            ten.add(new Texture(number[Score.getScore().get(i)/10]));
            rank.add(new Texture(number[i+1]));
        }

        time = 0;
        backdrop = new Texture("backdrop.png");
        dust = new Texture(num_frame[0]);

        flag = false;
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched() && Gdx.input.getX() >= 1159 && Gdx.input.getX() <= 1272 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711){
            c_btn.play(0.5f);
            System.exit(0);
        }
        else if(Gdx.input.justTouched() && Gdx.input.getX() >= 9 && Gdx.input.getX() <= 121 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711) {
            c_btn.play(0.5f);
            gsm.set(new MenuState(gsm, mainSong));
        }else if(Gdx.input.justTouched() && Gdx.input.getX() >= 1280/2 - exitBtn.getWidth()/2 && Gdx.input.getX() <= 1280/2 + exitBtn.getWidth()/2 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711){
            c_btn.play(0.5f);
            Score.ResetScore();
            gsm.set(new HighScoreState(gsm, mainSong));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - cam.viewportWidth/2,0, DinoGame.WIDTH, DinoGame.HEIGHT);

        time += 0.5;
        dust.dispose();
        dust = new Texture(num_frame[(int) time%20]);

        sb.draw(dust, 0,0, 1280, 720);

        sb.draw(backdrop, 0 ,0);

        sb.draw(scoreboard,
                cam.position.x - scoreboard.getWidth()/4 ,
                cam.position.y - scoreboard.getHeight()/4,
                scoreboard.getWidth()/2,
                scoreboard.getHeight()/2);

        sb.draw(score,
                cam.position.x - score.getWidth()/4,
                cam.position.y + 100,
                score.getWidth()/2,
                score.getHeight()/2);


        for (int i = 0; i < 3; i++){
            // Draw Rank
            sb.draw(badge.get(i) ,
                    cam.position.x - (NUM_WIDTH*4 + 10),
                    cam.viewportHeight/2 - NUM_HEIGHT*2*i + 10,
                    NUM_WIDTH*2,
                    NUM_HEIGHT*2);

            sb.draw(rank.get(i) ,
                    cam.position.x - (NUM_WIDTH*2),
                    cam.viewportHeight/2 - NUM_HEIGHT*2*i + 10,
                    NUM_WIDTH*2,
                    NUM_HEIGHT*2);
            sb.draw(ten.get(i) ,
                    cam.position.x + 100 - (NUM_WIDTH + NUM_WIDTH*2) - 2,
                    cam.viewportHeight/2 - NUM_HEIGHT*2*i + 10,
                    NUM_WIDTH*2,
                    NUM_HEIGHT*2);
            sb.draw(one.get(i) ,
                    cam.position.x + 100 - NUM_WIDTH - 2,
                    cam.viewportHeight/2 - NUM_HEIGHT*2*i + 10,
                    NUM_WIDTH*2,
                    NUM_HEIGHT*2);
        }

        if(Gdx.input.getX() >= 9 && Gdx.input.getX() <= 121 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711) {
            sb.draw(homeBtn, 5 - 2, 5 - 2, homeBtn.getWidth() + 4, homeBtn.getHeight() + 4);
        }else{
            sb.draw(homeBtn, 5, 5);
        }

        // reset score
        if(Gdx.input.getX() >= cam.position.x - exitBtn.getWidth()/2 && Gdx.input.getX() <= cam.position.x + exitBtn.getWidth()/2 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711) {
            sb.draw(resetBtn, cam.position.x - exitBtn.getWidth()/2 -2, 5-2 , exitBtn.getWidth() + 4 , exitBtn.getHeight() + 4);
        }else{
            sb.draw(resetBtn, cam.position.x - exitBtn.getWidth()/2, 5);
        }

        if(Gdx.input.getX() >= 1159 && Gdx.input.getX() <= 1272 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711) {
            sb.draw(exitBtn, DinoGame.WIDTH -125 - 2, 5-2 , exitBtn.getWidth() + 4 , exitBtn.getHeight() + 4);
        }else{
            sb.draw(exitBtn, DinoGame.WIDTH -125, 5);
        }

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();

        for (int i = 0 ; i < 3; i++){
            badge.get(i).dispose();
            rank.get(i).dispose();
            ten.get(i).dispose();
            one.get(i).dispose();
        }

        dust.dispose();
        backdrop.dispose();

        homeBtn.dispose();
        exitBtn.dispose();
        resetBtn.dispose();

        System.out.println("HighScoreState Dispose");
    }

}
