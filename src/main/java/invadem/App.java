package invadem;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.*;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;



public class App extends PApplet {
    
    
    int frameCounter;
    PFont font;
    int hi;
    int score;
    int p2score;
    int extraLifeCheck;
    int p2ExtraLife;
    int extraLifeRequirement;
    
    
    Tank tank;
    PImage tankImg;
    
    
    Tank player2;
    PImage player2Img;
    boolean right2;
    boolean left2;
    
    List<BarrierSegment> barrL; //X: 200 Y:height-75
    List<BarrierSegment> barrM; //X: width/2 Y: height - 75
    List<BarrierSegment> barrR; //X: 430 Y: height-75
    
    List<PImage> barrTop;
    List<PImage> barrLeft;
    List<PImage> barrRight;
    List<PImage> barrSolid;
    int barrierTop;
    
    List<PImage> normInvade;
    List<PImage> powerInvade;
    List<PImage> armorInvade;
    int invStartx;
    int invStarty; 
    
    List<List<SuperInvader>> invaders;
    
    List<Projectile> bullets;
    List<Projectile> p2Bullets;
    PImage projImg;
    
    int gameState; // 0= NORMAL GAME; 1=INVADER WIN; 2=PLAYER WIN
    int level;
    int timeToShoot;
    
    boolean player2In;
    
    //here
    Clip clip;
    Clip fire;
    Clip down;
    
    boolean downplayed = false;
    boolean testMode = false;
    //herre
    
    public App() {
        //Set up your objects
        bullets = new ArrayList<Projectile>();
        p2Bullets = new ArrayList<Projectile>();
        barrTop = new ArrayList<PImage>();
        barrL = new ArrayList<BarrierSegment>();
        barrM = new ArrayList<BarrierSegment>();
        barrR = new ArrayList<BarrierSegment>();
        invaders = new ArrayList<List<SuperInvader>>();
    }

    public void setup() {
        frameRate(60);
        background(0,0,0);
        frame.setTitle("Invadem");
        
        
        //here
        try{
            Scanner scan = new Scanner(System.in);
            File file = new File("./src/main/resources/invade_fire.wav");
            File f = new File("./src/main/resources/fire.wav");
            File dfile = new File("./src/main/resources/down.wav");
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            AudioInputStream fireStream = AudioSystem.getAudioInputStream(f);
            AudioInputStream downStream = AudioSystem.getAudioInputStream(dfile);
            
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            
            fire = AudioSystem.getClip();
            fire.open(fireStream);
            
            down = AudioSystem.getClip();
            down.open(downStream);
            
            
        }catch(UnsupportedAudioFileException e){
            System.out.println("UNSUPPORTED ERROR");
        }catch(IOException e){
            System.out.println("IO ERROR");
            e.printStackTrace();
        }catch(LineUnavailableException e){
            System.out.println("LINE ERROR");
        }
        //here
        
        player2In=false;
        level =0;
        extraLifeCheck = 0;
        p2ExtraLife = 0;
        extraLifeRequirement = 20000;
        timeToShoot =300;
        font = createFont("Blocky-Regular.ttf", 200);
        hi = 10000;
        score = 0;
        p2score = 0;
        tankImg=loadImage("tank1.png");
        gameState= -1;
        player2Img=loadImage("tank_purple.png");
        tank = new Tank(tankImg, width/2, height-50, 22, 16);
        player2 = new Tank(player2Img, width/2, height-50, 22, 16);
        //MAKE OBJECTS
        Reset();
        
        
    }

    public void settings() {
        size(640, 480);
        
        
        
    }

    public void draw() { 
        //Main Game Loop
        background(0,0,0);
        fill(76, 175, 79);
        //MENU
        if(gameState == -1){
            textFont(font);
            textSize(32);
            text("INVADEM", (this.width)/2-56, (this.height/2)- 8);
            text("PRESS ENTER TO PLAY", (this.width/2)-135, (this.height/2)+35);
        }
        
        
        //NORMAL GAME BEGINS
        if(gameState == 0){
            
            
            if(extraLifeCheck >= extraLifeRequirement){
                tank.extraLife();
                extraLifeCheck -= extraLifeRequirement;
            }
            
            if(player2In && p2ExtraLife >= extraLifeRequirement){
                player2.extraLife();
                p2ExtraLife -= extraLifeRequirement;
            }
            
           
            frameCounter++;
            printImages();
            
            //INVADERS GET SHOT
            InvaderShot();
            
            //PLAYER GETS SHOT
            PlayerShot(tank, bullets);
            if(player2In){
                PlayerShot(player2, bullets);
            }
            
        
            this.barrColl(barrL, bullets);
            this.barrColl(barrR, bullets);
            this.barrColl(barrM, bullets);
            
            this.barrColl(barrL, p2Bullets);
            this.barrColl(barrR, p2Bullets);
            this.barrColl(barrM, p2Bullets);
        
            if(tank.getRight()){
                tank.move('r');
            }
            if(tank.getLeft()){
                tank.move('l');
            }
            
            if(player2.getRight()){
                player2.move('r');
            }
            if(player2.getLeft()){
                player2.move('l');
            }
            
            
         
            //BULLETS OUT OF SCREEN
            bulletsOut(bullets);
            bulletsOut(p2Bullets);
            
            
        
            if(frameCounter % timeToShoot == 0){
            
                RandomInvaderShoot();
            }
            
            
            if(!player2In){
                if(tank.getHealth() <= 0){
                    gameState = 1;
                    frameCounter =0;
                }
            }
            
            if(player2In){
                if(tank.getHealth() <=0 && player2.getHealth()<=0){
                    gameState = 1;
                    frameCounter = 0;
                }
            }
            if(InvaderEmpty(invaders)){
                gameState=2;
                frameCounter =0;
            }
            
            winCheck:
            for(List<SuperInvader> i : invaders){
                for(SuperInvader j: i){
                    if(j.invaderWin()){
                        gameState = 1;
                        frameCounter = 0;
                        break winCheck;
                    }
                }
            }
        }//NORMAL GAME ENDS
        
        if(gameState == 1 || gameState==2){ //1: GAME OVER, 2: NXT LVL
            frameCounter++;
            
            if(gameState==1){
                if(!player2In){
                    this.image(loadImage("gameover.png"), (this.width/2)-56, (this.height/2)-8, 112, 16);
                }
                if(player2In){
                    if(score > p2score){
                        text("PLAYER 1 WINS!", (this.width/2)-90, (this.height/2)-8);
                    }
                    if(p2score > score){
                        text("PLAYER 2 WINS!", (this.width/2)-90, (this.height/2)-8);
                    }
                    if(p2score == score){
                        text("TIED GAME", (this.width/2)-56, (this.height/2)-8);
                    }
                }
                
            }
            else{
                this.image(loadImage("nextLevel.png"), (this.width/2)-56, (this.height/2)-8, 112,16);
            }
            
            if(frameCounter == 120){
                if(gameState == 2){
                    level++;
                    gameState=0;
                }
                if(gameState == 1){
                    if(score>hi){
                        hi = score;
                    }
                    if(p2score>hi){
                        hi = p2score;
                    }
                    
                    gameState = -1;
                    score = 0;
                    p2score =0;
                    extraLifeCheck = 0;
                    p2ExtraLife =0;
                    this.level =0;
                    tank = new Tank(tankImg, width/2, height-50, 22, 16);
                    player2 = new Tank(player2Img, width/2, height-50, 22, 16);
                }
                ResetLists();
                Reset();
            }
        }
    }
    
    public void ResetLists(){
        bullets = new ArrayList<Projectile>();
        p2Bullets = new ArrayList<Projectile>();
        barrTop = new ArrayList<PImage>();
        barrL = new ArrayList<BarrierSegment>();
        barrM = new ArrayList<BarrierSegment>();
        barrR = new ArrayList<BarrierSegment>();
        invaders = new ArrayList<List<SuperInvader>>();
    }
    
    public void Reset(){
        
        if(level == 0){
            timeToShoot = 300;
        }
        
        else{
            timeToShoot -= (level*60);
            if(timeToShoot < 60){
                timeToShoot = 60;
            }
        }
        
        
        frameCounter = 0;
        
        
        
        barrTop=new ArrayList<PImage>();
        barrTop.add(loadImage("barrier_top1.png"));
        barrTop.add(loadImage("barrier_top2.png"));
        barrTop.add(loadImage("barrier_top3.png"));
        
        barrLeft=new ArrayList<PImage>();
        barrLeft.add(loadImage("barrier_left1.png"));
        barrLeft.add(loadImage("barrier_left2.png"));
        barrLeft.add(loadImage("barrier_left3.png"));
        
        barrRight = new ArrayList<PImage>();
        barrRight.add(loadImage("barrier_right1.png"));
        barrRight.add(loadImage("barrier_right2.png"));
        barrRight.add(loadImage("barrier_right3.png"));
        
        barrSolid = new ArrayList<PImage>();
        barrSolid.add(loadImage("barrier_solid1.png"));
        barrSolid.add(loadImage("barrier_solid2.png"));
        barrSolid.add(loadImage("barrier_solid3.png"));
        
        barrierTop = height-85;
        int barrLSource = 200;
        int barrMSource = (width/2);
        int barrRSource = 430;
        
        barrL = makeBarrier(barrLSource, barrL);
        barrM = makeBarrier(barrMSource, barrM);
        barrR = makeBarrier(barrRSource, barrR);
       
        
        normInvade = new ArrayList<PImage>();
        normInvade.add(loadImage("invader1.png"));
        normInvade.add(loadImage("invader2.png"));
        
        powerInvade = new ArrayList<PImage>();
        powerInvade.add(loadImage("invader1_power.png"));
        powerInvade.add(loadImage("invader2_power.png"));
        
        armorInvade = new ArrayList<PImage>();
        armorInvade.add(loadImage("invader1_armoured.png"));
        armorInvade.add(loadImage("invader2_armoured.png"));
        
        invStartx=210;
        invStarty = 46;
        
        for(int i =0; i<4; i++){
            invaders.add(new ArrayList<SuperInvader>());
            for(int j=0; j<10 ; j++){
                if(i == 0){
                    invaders.get(i).add(new ArmorInvader(armorInvade, barrierTop-26, invStartx + (j*32), invStarty+(i*32), 16, 16));
                }
                if(i == 1){
                    invaders.get(i).add(new PowerInvader(powerInvade, barrierTop-26, invStartx + (j*32), invStarty+(i*32), 16, 16));
                }
                if(i>1){
                    invaders.get(i).add(new Invader(normInvade, barrierTop-26, invStartx +(j*32), invStarty+(i*32), 16, 16));
                }
            }
        }
        
        tank.setShoot(true);
        player2.setShoot(true);
        projImg = loadImage("projectile.png");
    }
    
    public boolean PlayerShot(Tank player, List<Projectile> proj){
        boolean ans = false;
        other: for(int i =0; i<proj.size(); i++){
                if(!proj.get(i).isPlayer()){
                    boolean destroy = player.checkCollision(proj.get(i));
                    if(destroy){
                        ans= true;
                        player.isHit(proj.get(i).getDMG());
                        proj.remove(i);
                        i--; 
                        continue other;
                    }
              
                    }
                }
        return ans;
    }
    
    
    
    public boolean InvaderEmpty(List<List<SuperInvader>> aliens){
        
        for(int i =0; i<aliens.size(); i++){
            if(aliens.get(i).isEmpty()){
                aliens.remove(i);
                i--;
            }
        }
        if(aliens.isEmpty()){
            return true;
        }
        return false;
    }
    
   
    
    public void InvaderShot(){
        for(int i = 0; i< invaders.size(); i++){
            inv: for(int j =0; j<invaders.get(i).size();j++){
                
                //bullets
                for(int k =0; k<bullets.size(); k++){
                    if(bullets.get(k).isPlayer()){
                        boolean destroy = invaders.get(i).get(j).checkCollision(bullets.get(k));
                        if(destroy){
                            invaders.get(i).get(j).hit();
                            if(invaders.get(i).get(j).getHealth() == 0){
                                score += invaders.get(i).get(j).getScore();
                                extraLifeCheck += invaders.get(i).get(j).getScore();
                                invaders.get(i).remove(j);
                            }
                            bullets.remove(k);
                            j--;
                            continue inv;
                        }
                    }
                    
                }
                    
                //Player2 bullets
                if(player2In){
                    for(int k =0; k<p2Bullets.size(); k++){
                        if(p2Bullets.get(k).isPlayer()){
                            boolean destroy = invaders.get(i).get(j).checkCollision(p2Bullets.get(k));
                            if(destroy){
                                invaders.get(i).get(j).hit();
                                if(invaders.get(i).get(j).getHealth() == 0){
                                    p2score += invaders.get(i).get(j).getScore();
                                    p2ExtraLife += invaders.get(i).get(j).getScore();
                                    invaders.get(i).remove(j);
                                }
                                p2Bullets.remove(k);
                                j--;
                                continue inv;
                            }
                        }
                    
                    }
                }
            }
        }
    
    }
    
    
    public void RandomInvaderShoot(){
        if(!testMode){
            clip.setFramePosition(0);
            clip.start();
        }
        
        for(int i =0; i<invaders.size(); i++){
            if(invaders.get(i).isEmpty()){
                invaders.remove(i);
                i--;
            }
        }
            
        if(!invaders.isEmpty()){
            Random rand = new Random();
            Random rand2 = new Random();
            
            int temp = rand.nextInt(invaders.size());
            int temp2 = rand2.nextInt(invaders.get(temp).size());
                    
            bullets = invaders.get(temp).get(temp2).Shoot(bullets, projImg);
                    
        }
    }
    
    public void keyPressed(){
    //KEYCODE: 37 = LEFT; 39 = RIGHT; 32 = SPACE; 9 = TAB; 16 = SHIFT; 65 =A; 68 = D; 87 =W
        
        if(keyCode==37){
            tank.setLeft(true);
        }
        if(keyCode == 39){
            tank.setRight(true);
        }
        
        if(keyCode == 65){
            if(player2In){
                player2.setLeft(true);
            }
        }
        
        if(keyCode == 68){
            if(player2In){
                player2.setRight(true);
            }
        }
        
        if(keyCode == 10 && gameState == -1){
            gameState=0;
        }
        
        if(keyCode == 32 && tank.canShoot() && tank.getHealth()>0){
            bullets=tank.shoot(bullets, projImg);
            
            if(!testMode){
                fire.setFramePosition(0);
                fire.start();
            };
            tank.setShoot(false);
        }
        
        if(keyCode == 87 && player2.canShoot() && player2In && player2.getHealth()>0){
            p2Bullets = player2.shoot(p2Bullets, projImg);
            player2.setShoot(false);
        }
        
        if(keyCode == 9){
            player2In = !player2In;
        }
    }
     
    public void keyReleased(){
        if(keyCode==37){
            tank.setLeft(false);
        }
        if(keyCode == 39){
            tank.setRight(false);
        }
        if(keyCode == 32){
            tank.setShoot(true);
        }
        
        if(keyCode == 65){
            if(player2In){
                player2.setLeft(false);
            }
        }
        
        if(keyCode == 68){
            if(player2In){
                player2.setRight(false);
            }
        }
        
        if(keyCode == 87){
            player2.setShoot(true);
        }
    }
    
    public List<BarrierSegment> makeBarrier(int source, List<BarrierSegment> barrier){
        barrier.add(new BarrierSegment(barrLeft, source, barrierTop, 8, 8));
        barrier.add(new BarrierSegment(barrTop, source+8, barrierTop, 8, 8));
        barrier.add(new BarrierSegment(barrRight, source+16, barrierTop, 8, 8));
        barrier.add(new BarrierSegment(barrSolid, source, barrierTop+8, 8, 8));
        barrier.add(new BarrierSegment(barrSolid, source, barrierTop+16, 8, 8));
        barrier.add(new BarrierSegment(barrSolid, source+16, barrierTop+8, 8, 8));
        barrier.add(new BarrierSegment(barrSolid, source+16, barrierTop+16, 8, 8));
        return barrier;
    }
    
    public boolean barrColl(List<BarrierSegment> barrier, List<Projectile> bulls){
        boolean reply = false;
        start: for(int i =0; i<bulls.size(); i++){
            for(int j =0; j<barrier.size(); j++){
                boolean destroy = barrier.get(j).checkCollision(bulls.get(i));
                if(destroy){
                    barrier.get(j).isHit(bulls.get(i).getDMG());
                    bulls.remove(i);
                    
                    if(barrier.get(j).getHits() >= 3){
                        barrier.remove(j);
                    }
                    i--;
                    reply = true;
                    continue start;
                    
                }
            }
        }
        return reply;
    }
    
    public void printImages(){
        textFont(font);
        textSize(32);
        text("HI "+hi, 500, 25);
        text(score, 15 , 25);
        text("EXTRA LIFE AT 20,000", 150, 25);
        if(player2In){
            text(p2score, 15 , 45);
        }
        if(tank.getHealth()>0){
                tank.draw(this);
            }
            
            
        if(player2In && player2.getHealth()>0){
            player2.draw(this);
                
            for(int i=0; i<player2.getHealth(); i++){
                this.image(player2Img, (width-27)-(i*25), height-27, 22, 14);
            }
        }
        
        for(int i=0; i<tank.getHealth(); i++){
            this.image(tankImg, 5+(i*25), height-27, 22, 14);
        }
            
            
            
        for(BarrierSegment i : barrL){
            i.draw(this);
        }
        
        for(BarrierSegment i : barrM){
            i.draw(this);
        }
        
        for(BarrierSegment i : barrR){
            i.draw(this);
        }
        
        if(!invaders.get(0).get(0).horizontal && !downplayed){
            if(!testMode){
                down.setFramePosition(0);
                down.start();
            }
            
            downplayed = true;
        }
        
        if(invaders.get(0).get(0).horizontal && downplayed){
            downplayed = false;
        }
        
        for(List<SuperInvader> i: invaders){
            for(SuperInvader j: i){
                
                j.draw(this);
                
            }
        }
        
        
        

        
        for(Projectile i: bullets){
            i.draw(this);
        }
        if(player2In){
            for(Projectile i: p2Bullets){
                i.draw(this);
            }
        }
    }
    
    public void bulletsOut(List<Projectile> bulls){
        for(int i =0; i<bulls.size(); i++){
                if(bulls.get(i).getY()<0 || bulls.get(i).getY()>height){
                    bulls.remove(i);
                    i--;
                    continue;
                }
            }
    }

    public static void main(String[] args) {
        PApplet.main("invadem.App");
    }

}
