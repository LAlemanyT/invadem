package invadem;

import org.junit.Test;
import static org.junit.Assert.*;

public class BarrierTest {

    @Test
    public void barrierConstruction() {
        BarrierSegment b = new BarrierSegment(null, 8, 8, 8, 8);
        assertNotNull(b);
    }

    @Test
    public void testBarrierNotDestroyed() {
        BarrierSegment b = new BarrierSegment(null, 8, 8, 8, 8);
        b.isHit(1); //takes in the damage of the projectile as an argument
        assertEquals(false, b.isDestroyed());
    }

    @Test
    public void testBarrierHitPointsMax() {
        BarrierSegment b = new BarrierSegment(null, 8, 8, 8, 8);
        assertEquals(0, b.getHits()); //I USE HITS BACKWARDS FOR IMAGE/STATE REASON
        //THIS MEANS A NEW BARRIER HAS 0 HITS AND A DESTROYED BARRIER HAS 3 HITS
    }

    @Test
    public void testBarrierHitPointsOneHit() {
        BarrierSegment b = new BarrierSegment(null, 8, 8, 8, 8);
        b.isHit(1);//again, takes in the damage of the projectile
        assertEquals(1, b.getHits());
    }

    @Test
    public void testBarrierHitPointsTwoHits() {
        BarrierSegment b = new BarrierSegment(null, 8, 8, 8, 8);
        b.isHit(1);
        b.isHit(1);
        assertEquals(2, b.getHits());
    }
    
    @Test
    public void testBarrierHitPointsMultiDamageHit() {
        BarrierSegment b = new BarrierSegment(null, 8, 8, 8, 8);
        b.isHit(2);
        assertEquals(2, b.getHits());
    }


    @Test
    public void testBarrierIsDestroyed() {
        BarrierSegment b = new BarrierSegment(null, 8, 8, 8, 8);
        b.isHit(1);
        b.isHit(1);
        b.isHit(1);
        assertEquals(true, b.isDestroyed());
    }
    
    @Test
    public void testBarrierMultiHitDestroyed() {
        BarrierSegment b = new BarrierSegment(null, 8, 8, 8, 8);
        b.isHit(3);
        assertEquals(true, b.isDestroyed());
    }
    
    @Test
    public void testCollisionPlayerProjectille(){
        BarrierSegment b = new BarrierSegment(null, 8, 8, 8, 8);
        Projectile proj = new Projectile(null, -1, 1, true, 8, 8, 8, 8);
        assertTrue(b.checkCollision(proj));
    }
    
    @Test
    public void testCollisionInvaderProjectille(){
        BarrierSegment b = new BarrierSegment(null, 8, 8, 8, 8);
        Projectile proj = new Projectile(null, 1, 1, false, 8, 8, 8, 8);
        assertTrue(b.checkCollision(proj));
    }
    
    @Test
    public void testNoCollisionProjectille(){
        BarrierSegment b = new BarrierSegment(null, 8, 8, 8, 8);
        Projectile proj = new Projectile(null, -1, 1, true, 1, 1, 1, 1);
        assertFalse(b.checkCollision(proj));
    }

}
