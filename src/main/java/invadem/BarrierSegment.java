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
public class BarrierSegment extends Item {
    private List<PImage> imgs;
    private int hits;
    
    BarrierSegment(List<PImage> imgs, int x, int y, int width, int height){
        super(x, y, width, height);
        this.imgs = imgs;
        this.hits = 0;
    }
    
    
    public void isHit(int dmg){
        this.hits+=dmg;
    }
    
    public int getHits(){
        return this.hits;
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
        app.image(this.imgs.get(hits), this.x, this.y, this.width, this.height);
    }
    
    public boolean isDestroyed(){
        if(this.hits <3){
            return false;
        }
        return true;
    }
}
