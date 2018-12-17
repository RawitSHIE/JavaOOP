package dino.game.oop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dino.game.oop.DinoGame;
import dino.game.oop.music.MainSong;

public class MenuState extends State {
    private Texture background;
    private Texture playBtn;
    private Texture leaderBtn;
    private Texture exitBtn;
    private Texture gameLogo;

    private Texture white;
    private boolean leaderboard = false;
    private MainSong mainSong;
    private Sound c_btn;

    private int time;
    private boolean flag;


    public MenuState(GameStateManager gsm, MainSong mainSong) {
        super(gsm);

        cam.setToOrtho(false, DinoGame.WIDTH,DinoGame.HEIGHT);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        leaderBtn = new Texture("leaderbutton.png");
        exitBtn = new Texture("exitbutton.png");
        gameLogo = new Texture("Logo.png");

        cam.setToOrtho(false, DinoGame.WIDTH, DinoGame.HEIGHT);
        cam.position.set(0,cam.position.y,0);
        this.mainSong = mainSong;
        c_btn = Gdx.audio.newSound(Gdx.files.internal("Sound/btn.mp3"));



        time = 100;
        flag = true;

    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched() && Gdx.input.getX() >= 545 && Gdx.input.getX() <= 734 && Gdx.input.getY() >= 264 && Gdx.input.getY() <= 452 && !leaderboard){
            c_btn.play();
            gsm.set(new PlayState(gsm, mainSong));
        }
        else if(Gdx.input.justTouched() && Gdx.input.getX() >= 9 && Gdx.input.getX() <= 121 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711 && !leaderboard){
            c_btn.play();
            gsm.set(new HighScoreState(gsm, mainSong));
        }
        if(Gdx.input.justTouched() && Gdx.input.getX() >= 1159 && Gdx.input.getX() <= 1272 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711){
            c_btn.play();
            System.exit(0);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        //firstpage effect
        if (flag){
            time--;
            if (time == 0){
                flag = false;
            }
        }else if (flag == false){
            time++;
            if (time == 150){
                flag = true;
            }
        }

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0, DinoGame.WIDTH, DinoGame.HEIGHT);


        sb.draw(gameLogo, DinoGame.WIDTH / 2 - gameLogo.getWidth() / 2, DinoGame.HEIGHT / 2 + 100);

        if ( Gdx.input.getX() >= 545 && Gdx.input.getX() <= 734 && Gdx.input.getY() >= 312 && Gdx.input.getY() <= 502 && !leaderboard){
            sb.draw(playBtn, DinoGame.WIDTH / 2 - playBtn.getWidth() / 2 - 2, DinoGame.HEIGHT / 2 - 100 - 2 - 50, playBtn.getWidth()+4 , playBtn.getHeight()+4);
        }else{
            sb.draw(playBtn, DinoGame.WIDTH / 2 - playBtn.getWidth() / 2, DinoGame.HEIGHT / 2 - 100 - 50);
        }


        if (Gdx.input.getX() >= 9 && Gdx.input.getX() <= 121 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711){
            sb.draw(leaderBtn, 5 - 2, 5 - 2, leaderBtn.getWidth() + 4 ,  leaderBtn.getHeight() + 4);
        }else{
            sb.draw(leaderBtn, 5, 5);
        }

        if(Gdx.input.getX() >= 1159 && Gdx.input.getX() <= 1272 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711) {
            sb.draw(exitBtn, DinoGame.WIDTH -125 - 2, 5 - 2 , exitBtn.getWidth() + 4, exitBtn.getHeight() + 4);
        }else{
            sb.draw(exitBtn, DinoGame.WIDTH -125, 5);
        }






        System.out.println(time);

        sb.end();


    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        leaderBtn.dispose();
        exitBtn.dispose();
        c_btn.dispose();
        gameLogo.dispose();
        System.out.println("MenuState Dispose");
    }
}
