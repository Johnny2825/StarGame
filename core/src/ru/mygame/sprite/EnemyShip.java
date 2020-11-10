package ru.mygame.sprite;

import ru.mygame.base.EnemySettingsDto;
import ru.mygame.base.Ship;
import ru.mygame.math.Rect;
import ru.mygame.pool.BulletPool;
import ru.mygame.pool.ExplosionPool;

public class EnemyShip extends Ship {

    private static final float START_V_Y = -0.4f;

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        if (getTop() > worldBounds.getTop()){
            pos.mulAdd(v0, delta);
        } else {
            bulletPos.set(pos.x, getBottom());
            super.update(delta);
        }
        if (getBottom() < worldBounds.getBottom()) {
            destroy();
        }
    }

    public void set(EnemySettingsDto settings) {
        this.regions = settings.getRegions();
        this.v.set(settings.getV0());
        this.bulletRegion = settings.getBulletRegion();
        this.bulletHeight = settings.getBulletHeight();
        this.bulletV.set(settings.getBulletV());
        this.bulletSound = settings.getBulletSound();
        this.damage = settings.getDamage();
        this.reloadInterval = settings.getReloadInterval();
        setHeightProportion(settings.getHeight());
        this.hp = settings.getHp();
        this.v0.set(0, START_V_Y);
        reloadTimer = reloadInterval;
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(
                bullet.getRight() < getLeft()
                        || bullet.getLeft() > getRight()
                        || bullet.getBottom() > getTop()
                        || bullet.getTop() < pos.y
        );
    }

}
