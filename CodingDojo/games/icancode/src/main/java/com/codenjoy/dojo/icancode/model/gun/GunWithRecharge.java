package com.codenjoy.dojo.icancode.model.gun;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

public class GunWithRecharge implements Gun {

    protected boolean canShoot;
    protected int ticks;

    public GunWithRecharge() {
        reset();
    }

    @Override
    public void reset() {
        recharge();
    }

    private void recharge() {
        ticks = 0;
        canShoot = true;
    }

    protected void discharge() {
        ticks = 0;
        canShoot = false;
    }

    @Override
    public void shoot() {
        discharge();
    }

    @Override
    public void unlimitedShoot() {
        shoot();
    }

    @Override
    public void tick() {
        if (!canShoot) {
            ticks++;
        }
        int charge = charge();
        if (charge <= 0) {
            canShoot = true;
        } else if (ticks == charge + 1) {
            recharge();
        }
    }

    @Override
    public boolean canShoot() {
        return canShoot;
    }
}