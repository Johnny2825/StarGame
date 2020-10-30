package ru.mygame.sprite;

import ru.mygame.base.EnemySettingsDto;
import ru.mygame.base.Ship;
import ru.mygame.math.Rect;
import ru.mygame.pool.BulletPool;

public class EnemyShip extends Ship {

    public EnemyShip(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.v0.set(0, -0.3f);
    }

    @Override
    public void update(float delta) { // увелить скорость выкатывания и стрельба при нахождении на поле
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
    }

}
