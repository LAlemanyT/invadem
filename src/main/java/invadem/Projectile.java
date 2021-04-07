/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invadem;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author luisa
 */
public class Projectile extends Item{
    private PImage img;
    private int velocity;
    private boolean player;
    private int dmg;
    
    Projectile(PImage img, int velocity, int dmg, boolean player, int x, int y, int width, int height){
        super(x, y, width, height);
        this.img = img;
        this.velocity = velocity;
        this.dmg = dmg;
        this.player = player;
        
    }
    
    public int getDMG(){
        return this.dmg;
    }
    
    
    public boolean isPlayer(){
        return this.player;
    }
    
    public void draw(PApplet app){
        app.image(this.img, this.x, this.y, this.width, this.height);
        this.tick();
    }
    
    public void tick(){
        this.y+=velocity;
    }
    
    
    
}
