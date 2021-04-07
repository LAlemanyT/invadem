/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invadem;
import processing.core.PApplet;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

import invadem.App;
/**
 *
 * @author luisa
 */
public class AppTest extends App{
    @Test
    public void testMakeBarr(){
        assertNotNull(makeBarrier(0, new ArrayList<BarrierSegment>()));
    }
    
    @Test
    public void testProjectilesOut(){
        List<Projectile> bulls = new ArrayList<Projectile>();
        bulls.add(new Projectile(null, -1, 1, true, 50, 500, 1, 3));
        bulls.add(new Projectile(null, -1, 1, true, 50, 100, 1, 3));
        bulletsOut(bulls);
        assertEquals(1, bulls.size());
    }
    
    @Test
    public void testResetLists(){
        //Ensure relevant lists are reset
        bullets.add(new Projectile(null, -1, 1, true, 50 ,100, 1, 3));
        assertFalse(bullets.isEmpty());
        ResetLists();
        assertTrue(bullets.isEmpty());
    }
    
    @Test
    public void testInvadersListEmpty(){
        List<List<SuperInvader>> invs = new ArrayList<List<SuperInvader>>();
        invs.add(new ArrayList<SuperInvader>());
        invs.get(0).add(new Invader(null, 100, 16, 16, 16, 16));
        assertFalse(InvaderEmpty(invs));
    }
    
    @Test
    public void testBarrCollEmpty(){
        List<BarrierSegment> barr = new ArrayList<BarrierSegment>();
        List<Projectile> proj = new ArrayList<Projectile>();
        
        assertFalse(barrColl(barr, proj));
    }
    
    @Test
    public void testBarrCollNoCollide(){
        List<BarrierSegment> barr = new ArrayList<BarrierSegment>();
        List<Projectile> proj = new ArrayList<Projectile>();
        barr.add(new BarrierSegment(null, 0, 0, 1, 1));
        proj.add(new Projectile(null, 1, 1, false, 100, 100, 1 , 1));
        assertFalse(barrColl(barr, proj));
    }
    
    @Test
    public void testBarrCollCollide(){
        List<BarrierSegment> barr = new ArrayList<BarrierSegment>();
        List<Projectile> proj = new ArrayList<Projectile>();
        barr.add(new BarrierSegment(null, 16, 16, 16, 16));
        proj.add(new Projectile(null, 1, 1, false, 16, 16, 16 , 16));
        assertTrue(barrColl(barr, proj));
    }
    
    @Test
    public void testPlayerCollision(){
        //test the collision checking method in app with invader projectile and tank
        Tank tank = new Tank(null, 100,100, 100, 100);
        List<Projectile> proj = new ArrayList<Projectile>();
        proj.add(new Projectile(null, 1, 1, false, 100, 100, 100, 100));
        assertTrue(PlayerShot(tank, proj));
    }
    
    @Test
    public void testPlayerCollisionPlayerProjectile(){
        //test the collision checking method in app with player projectile and tank
        Tank tank = new Tank(null, 100,100, 100, 100);
        List<Projectile> proj = new ArrayList<Projectile>();
        proj.add(new Projectile(null, 1, 1, true, 100, 100, 100, 100));
        assertFalse(PlayerShot(tank, proj));
    }
    
    @Test
    public void testPlayerNoCollision(){
        //test the collision checking method in app with no collision
        Tank tank = new Tank(null, 0,0, 1, 1);
        List<Projectile> proj = new ArrayList<Projectile>();
        proj.add(new Projectile(null, 1, 1, false, 16 , 16, 1, 1));
        assertFalse(PlayerShot(tank, proj));
    }
    
    @Test
    public void testInvaderShoot(){
        testMode = true;
        invaders = new ArrayList<List<SuperInvader>>();
        invaders.add(new ArrayList<SuperInvader>());
        invaders.get(0).add(new Invader(null, 100, 16, 16, 16, 16));
        bullets = new ArrayList<Projectile>();
        RandomInvaderShoot();
        System.out.println(bullets.size() + " BULLETS");
        assertEquals(1, bullets.size());
        
        
    }
    
    @Test
    public void testInvaderShootNoInvaders(){
        testMode = true;
        invaders = new ArrayList<List<SuperInvader>>();
        //invaders.add(new ArrayList<SuperInvader>());
        //invaders.get(0).add(new Invader(null, 100, 16, 16, 16, 16));
        bullets = new ArrayList<Projectile>();
        RandomInvaderShoot();
        assertEquals(0, bullets.size());
        
    }
    
    @Test
    public void testInvaderShot(){
        //invader collision with player projectile
        invaders = new ArrayList<List<SuperInvader>>();
        invaders.add(new ArrayList<SuperInvader>());
        invaders.get(0).add(new Invader(null, 100, 16, 16, 16, 16));
        bullets = new ArrayList<Projectile>();
        bullets.add(new Projectile(null, 1, 1, true, 16,16,16,16));
        InvaderShot();
        assertTrue(invaders.get(0).isEmpty());
        
    }
    
    @Test
    public void testInvaderShotP2(){
        //invader collision with player2 projectile
        player2In =true;
        invaders = new ArrayList<List<SuperInvader>>();
        invaders.add(new ArrayList<SuperInvader>());
        invaders.get(0).add(new Invader(null, 100, 16, 16, 16, 16));
        p2Bullets = new ArrayList<Projectile>();
        p2Bullets.add(new Projectile(null, 1, 1, true, 16,16,16,16));
        InvaderShot();
        assertTrue(invaders.get(0).isEmpty());
        
    }
    
    @Test
    public void testInvaderShotInvaderProj(){
        //invader collision with invader projectile
        invaders = new ArrayList<List<SuperInvader>>();
        invaders.add(new ArrayList<SuperInvader>());
        invaders.get(0).add(new Invader(null, 100, 16, 16, 16, 16));
        bullets = new ArrayList<Projectile>();
        bullets.add(new Projectile(null, 1, 1, false, 16,16,16,16));
        InvaderShot();
        assertFalse(invaders.get(0).isEmpty());
        
    }
    
    @Test
    public void testInvaderShotNoColl(){
        //no collision
        invaders = new ArrayList<List<SuperInvader>>();
        invaders.add(new ArrayList<SuperInvader>());
        invaders.get(0).add(new Invader(null, 100, 0, 0, 1, 1));
        bullets = new ArrayList<Projectile>();
        bullets.add(new Projectile(null, 1, 1, true, 16,16,1,1));
        InvaderShot();
        assertFalse(invaders.get(0).isEmpty());
        
    }
    
    @Test
    public void testStart(){
        //start game
        keyCode=10;
        keyPressed();
        delay(2000);
        keyReleased();
        assertEquals(0, gameState);
    }
    
    @Test
    public void testPlayer2In(){
        //player2 Joins
        gameState = 0;
        player2In=false;
        keyCode=9;
        keyPressed();
        delay(2000);
        keyReleased();
        assertTrue(player2In);
    }
    
    @Test
    public void testPlayer2Out(){
        //player2 leaves
        gameState = 0;
        player2In=true;
        keyCode=9;
        keyPressed();
        delay(2000);
        keyReleased();
        assertFalse(player2In);
    }
    
    @Test
    public void testFire(){
        //player1 shoots
        testMode = true;
        tank = new Tank(null, 0, 0, 0, 0);
        gameState = 0;
        tank.setShoot(true);
        keyCode=32;
        keyPressed();
        delay(2000);
        keyReleased();
        assertEquals(1, bullets.size());
    }
    
    @Test
    public void testFireP2(){
        //player2 shoots
        player2 = new Tank(null, 0, 0, 0, 0);
        gameState = 0;
        player2In = true;
        player2.setShoot(true);
        keyCode=87;
        keyPressed();
        delay(2000);
        keyReleased();
        assertEquals(1, p2Bullets.size());
    }
    
    @Test
    public void testMoveLeft(){
        //player1 moves left
        tank = new Tank(null, 0, 0, 0, 0);
        gameState = 0;
        keyCode=37;
        keyPressed();
        delay(2000);
        assertTrue(tank.getLeft());
    }
    
    @Test
    public void testMoveRight(){
        //player1 moves right
        tank = new Tank(null, 0, 0, 0, 0);
        gameState = 0;
        keyCode=39;
        keyPressed();
        delay(2000);
        assertTrue(tank.getRight());
    }
    
    @Test
    public void testMoveLeftP2(){
        //player2 moves left
        player2 = new Tank(null, 0, 0, 0, 0);
        gameState = 0;
        player2In = true;
        keyCode=65;
        keyPressed();
        delay(2000);
        assertTrue(player2.getLeft());
    }
    
    @Test
    public void testMoveRightP2(){
        //player2 moves right
        player2 = new Tank(null, 0, 0, 0, 0);
        gameState = 0;
        player2In = true;
        keyCode=68;
        keyPressed();
        delay(2000);
        assertTrue(player2.getRight());
    }
    
    @Test
    public void testStopMoveRight(){
        //player1 stops move right
        tank = new Tank(null, 0, 0, 0, 0);
        gameState = 0;
        keyCode=39;
        keyPressed();
        delay(2000);
        assertTrue(tank.getRight());
        keyReleased();
        delay(2000);
        assertFalse(tank.getRight());
    }
    
    @Test
    public void testStopMoveLeftP2(){
        //player1 stops move left
        player2 = new Tank(null, 0, 0, 0, 0);
        gameState = 0;
        player2In = true;
        keyCode=65;
        keyPressed();
        delay(2000);
        assertTrue(player2.getLeft());
        keyReleased();
        delay(2000);
        assertFalse(player2.getLeft());
    }
    
    @Test
    public void testStopMoveRightP2(){
        //player2 stops moves right
        player2 = new Tank(null, 0, 0, 0, 0);
        gameState = 0;
        player2In = true;
        keyCode=68;
        keyPressed();
        delay(2000);
        assertTrue(player2.getRight());
        keyReleased();
        delay(2000);
        assertFalse(player2.getRight());
    }
    
    
}
