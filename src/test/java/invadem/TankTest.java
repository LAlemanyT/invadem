package invadem;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class TankTest {

    @Test
    public void testTankConstruction() {
        Tank tank = new Tank(null, 0, 0, 22, 16);
        assertNotNull(tank);
    }

    @Test
    public void testTankProjectile() {
        Tank tank = new Tank(null, 0, 0, 22, 16);
        assertNotNull(tank.shoot(new ArrayList<Projectile>(), null));
    }

    @Test
    public void testTankIsNotDead() {
        Tank tank = new Tank(null, 0, 0, 22, 16);
        assertEquals(false, tank.isDead());
    }
    
    @Test
    public void testTankIsDead() {
        Tank tank = new Tank(null, 0, 0, 22, 16);
        tank.isHit(tank.getHealth()); //HIT FOR VALUE OF CURRENT HEALTH
        assertEquals(true, tank.isDead());
    }
    
    @Test
    public void testTankExtraHealth() {
        Tank tank = new Tank(null, 0, 0, 22, 16);
        int startHP = tank.getHealth();
        tank.extraLife();
        assertEquals(startHP+1, tank.getHealth());
    }
    
    @Test
    public void testTankIsHit() {
        Tank tank = new Tank(null, 0, 0, 22, 16);
        int startHP = tank.getHealth();
        tank.isHit(1);
        assertEquals(startHP-1, tank.getHealth());
    }
    
    @Test
    public void testTankMoveLeft() {
        Tank tank = new Tank(null, 200, 0, 22, 16);
        int x = tank.getX();
        tank.move('l');
        assertEquals(x-1, tank.getX());
    }
    
    @Test
    public void testTankMoveRight() {
        Tank tank = new Tank(null, 200, 0, 22, 16);
        int x = tank.getX();
        tank.move('r');
        assertEquals(x+1, tank.getX());
    }
    
    @Test
    public void testTankMoveWrong() {
        Tank tank = new Tank(null, 200, 0, 22, 16);
        int x = tank.getX();
        tank.move('a');
        assertEquals(x, tank.getX());
    }
    
    @Test
    public void testTankGetLeftNoChange() {
        Tank tank = new Tank(null, 0, 0, 22, 16);
        assertFalse(tank.getLeft());
    }
    
    @Test
    public void testTankGetRightNoChange() {
        Tank tank = new Tank(null, 0, 0, 22, 16);
        assertFalse(tank.getRight());
    }
    
    @Test
    public void testTankSetLeft() {
        Tank tank = new Tank(null, 0, 0, 22, 16);
        boolean change = true;
        tank.setLeft(change);
        assertEquals(change, tank.getLeft());
    }
    
    @Test
    public void testTankSetRight() {
        Tank tank = new Tank(null, 0, 0, 22, 16);
        boolean change = true;
        tank.setRight(change);
        assertEquals(change, tank.getRight());
    }
    
    @Test
    public void testTankGetShootNoChange() {
        Tank tank = new Tank(null, 0, 0, 22, 16);
        assertTrue(tank.canShoot());
    }
    
    @Test
    public void testTankSetShoot() {
        Tank tank = new Tank(null, 0, 0, 22, 16);
        boolean change = false;
        tank.setShoot(change);
        assertEquals(change, tank.canShoot());
    }

    

}
