/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invadem;

import java.util.List;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author luisa
 */
public abstract class SuperInvader extends Item {
private List<PImage> imgs;
    private int win;
    int state;
    int velX;
    int velY;
    boolean horizontal;
    boolean move;
    int movedX;
    int movedY;
    int health;
    protected int damage;
    protected int score;
    
    SuperInvader(List<PImage> imgs, int dmg, int win, int health, int x, int y, int width, int height){
        super(x, y, width, height);
        this.imgs = imgs;
        this.damage = dmg;
        this.win=win;
        this.state =0;
        this.velX = -1;
        this.velY = 1;
        horizontal = true;
        move = true;
        movedX = 0;
        movedY = 0;
        this.health = health;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public int getHealth(){
        return this.health;
    }
    
    public void hit(){
        this.health--;
    }
    
    public void tick(){
        if(horizontal){
            this.x += velX;
            movedX += velX;
            if(movedX==30 || movedX== -30){
                movedX =0;
                horizontal = false;
                velX *= -1;
                state = 1;
            }
        }
        else if(!horizontal){
            this.y += velY;
            movedY += velY;
            
            if(movedY == 8){
                movedY = 0;
                horizontal = true;
                state = 0;
            }
        }
    }
    
    public boolean checkCollision(Projectile bullet){
        if( ( this.x < (bullet.getX() + bullet.getWidth()) ) &&
            ( (this.x + this.width) > bullet.getX() ) &&
            ( this.y < (bullet.getY() + bullet.getHeight()) ) &&
            ( (this.height + this.y) > bullet.getY() )){
                return true;
        }

    return false;
    }
    
    public boolean invaderWin(){
        if(this.y >= this.win){
            return true;
        }
        return false;
    }
    
    public void draw(PApplet app){
        
        app.image(this.imgs.get(state), this.x, this.y, this.width, this.height);
        if(move){
        tick();
        move = false;
        }
        else{
        move = true;
        }
    }
    
    public abstract List<Projectile> Shoot(List<Projectile> bulls, PImage img);
    
    public int getDMG(){
        return this.damage;
    }
    
    public boolean isDead(){
        if(this.health > 0){
            return false;
        }
        return true;
    }
    
}   

