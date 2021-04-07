/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invadem;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.List;

/**
 *
 * @author luisa
 */
public class Tank extends Item{
    private PImage img;
    private int health;
    private boolean right;
    private boolean left;
    private boolean shoot;
    
    Tank(PImage img, int x, int y, int width, int height){
        super(x, y, width, height);
        this.img = img;
        this.health = 3;
        this.right = false;
        this.left = false;
        this.shoot = true;
        
    }
    
    public boolean canShoot(){
        return this.shoot;
    }
    
    public void setShoot(boolean newState){
        this.shoot = newState;
    }
    
    public boolean getRight(){
        return this.right;
    }
    public boolean getLeft(){
        return this.left;
    }
    
    public void setRight(boolean newState){
        this.right = newState;
    }
    
    public void setLeft(boolean newState){
        this.left = newState;
    }
    
    public void extraLife(){
        this.health++;
    }
    
    public int getHealth(){
        return this.health;
    }
    
    public void isHit(int dmg){
        this.health-= dmg;
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
    
    public void draw(PApplet app){
        app.image(this.img, this.x, this.y, this.width, this.height);
    }
    
    public void move(char dir){
        if(dir == 'r'){
            if(this.x<460){
                this.x+=1;
            }
        }
        else if(dir == 'l'){
            if(this.x>180){
                this.x-=1;
            }
        }
    }
    
    public List<Projectile> shoot(List<Projectile> bullets, PImage projImg){
        bullets.add(new Projectile(projImg, -1, 1, true, this.x+11, this.y, 1, 3));
        return bullets;
    }
    
    public boolean isDead(){
        if(this.health>0){
            return false;
        }
        return true;
    }
    
    
    
}
