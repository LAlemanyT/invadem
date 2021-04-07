/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invadem;

import processing.core.PApplet;
/**
 *
 * @author luisa
 */
public abstract class Item {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    
    
    public Item(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
    }
    
    public abstract void draw(PApplet app);
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
}
