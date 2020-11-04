package ru.mygame.pool;

import ru.mygame.base.SpritesPool;
import ru.mygame.math.Rect;
import ru.mygame.sprite.EnemyShip;
import ru.mygame.sprite.Explosion;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Rect worldBounds;

    public EnemyShipPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, explosionPool,  worldBounds);
    }
}
