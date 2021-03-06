package com.codenjoy.dojo.icancode.services;

/*-
 * #%L
 * expansion - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2016 - 2020 Codenjoy
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


import com.codenjoy.dojo.icancode.services.levels.Level;
import com.codenjoy.dojo.services.settings.Parameter;
import com.codenjoy.dojo.services.settings.Settings;
import com.codenjoy.dojo.services.settings.SettingsImpl;

import java.util.Arrays;

public final class SettingsWrapper {

    public static final String ALL_SINGLE = "All levels are single";
    public static final String CLASSSIC_TRAINING = "Single training & all in one final";
    public static final String ALL_IN_ROOMS = "All levels in rooms";
    public static final String TRAINING_MULTIMAP = "Single training & final in rooms";

    public static SettingsWrapper data;

    private final Parameter<Integer> perkDropRatio;
    private final Parameter<Integer> perkAvailability;
    private final Parameter<Integer> perkActivity;
    private final Parameter<Integer> deathRayRange;
    private final Parameter<Integer> winScore;
    private final Parameter<Integer> goldScore;
    private final Parameter<Integer> killZombieScore;
    private final Parameter<Integer> killHeroScore;
    private final Parameter<Boolean> enableKillScore;
    private final Parameter<Integer> gunRecharge;
    private final Parameter<Integer> gunShotQueue;
    private final Parameter<Integer> gunRestTime;
    private final Parameter<Integer> loosePenalty;
    private final Parameter<Boolean> isTrainingMode;
    private final Parameter<String> gameMode;
    private final Parameter<Integer> roomSize;
    private final Parameter<Integer> levelsCount;
    private final Parameter<String> defaultPerks;
    private final Settings settings;

    public static SettingsWrapper setup(Settings settings) {
        return new SettingsWrapper(settings);
    }

    // for testing
    public static SettingsWrapper setup() {
        return setup(new SettingsImpl());
    }

    private SettingsWrapper(Settings settings) {
        data = this;
        this.settings = settings;

        perkDropRatio = settings.addEditBox("Perk drop ratio").type(Integer.class).def(50);
        perkAvailability = settings.addEditBox("Perk availability").type(Integer.class).def(10);
        perkActivity = settings.addEditBox("Perk activity").type(Integer.class).def(10);
        deathRayRange = settings.addEditBox("Death-Ray perk range").type(Integer.class).def(10);
        winScore = settings.addEditBox("Win score").type(Integer.class).def(25);
        goldScore = settings.addEditBox("Gold score").type(Integer.class).def(10);
        killZombieScore = settings.addEditBox("Kill zombie score").type(Integer.class).def(5);
        killHeroScore = settings.addEditBox("Kill hero score").type(Integer.class).def(10);
        enableKillScore = settings.addCheckBox("Enable score for kill").type(Boolean.class).def(true);
        loosePenalty = settings.addEditBox("Loose penalty").type(Integer.class).def(5);
        isTrainingMode = settings.addCheckBox("Is training mode").type(Boolean.class).def(true);

        gunRecharge = settings.addEditBox("Heroes gun recharge").type(Integer.class).def(2);
        gunShotQueue = settings.addEditBox("Heroes gun need to relax after a series of shots").type(Integer.class).def(10);
        gunRestTime = settings.addEditBox("Heroes gun rest time(ticks)").type(Integer.class).def(10);

        defaultPerks = settings.addEditBox("Default hero perks on training and contest").type(String.class).def(",ajm");

        gameMode = settings.addSelect("Game mode", Arrays.asList(
                CLASSSIC_TRAINING, ALL_SINGLE, ALL_IN_ROOMS, TRAINING_MULTIMAP))
                .type(String.class).def(CLASSSIC_TRAINING);
        roomSize = settings.addEditBox("Room size").type(Integer.class).def(5);

        levelsCount = settings.addEditBox("Levels count").type(Integer.class).def(0);
        Levels.setup();
    }

    public int perkDropRatio() {
        return perkDropRatio.getValue();
    }

    public int perkAvailability() {
        return perkAvailability.getValue();
    }

    public int perkActivity() {
        return perkActivity.getValue();
    }

    public int getDeathRayRange() {
        return deathRayRange.getValue();
    }

    public int goldScore() {
        return goldScore.getValue();
    }

    public int gunRecharge() {
        return gunRecharge.getValue();
    }

    public int gunShotQueue() {
        return gunShotQueue.getValue();
    }

    public int gunRestTime() {
        return gunRestTime.getValue();
    }

    public int killZombieScore() {
        return killZombieScore.getValue();
    }

    public int killHeroScore() {
        return killHeroScore.getValue();
    }

    public Boolean enableKillScore() {
        return enableKillScore.getValue();
    }

    public int loosePenalty() {
        return loosePenalty.getValue();
    }

    public int winScore() {
        return winScore.getValue();
    }

    public boolean isTrainingMode() {
        return isTrainingMode.getValue();
    }

    public String gameMode() {
        return gameMode.getValue();
    }

    public int roomSize() {
        return (roomSize.getValue() == 0) ? Integer.MAX_VALUE : roomSize.getValue();
    }

    public String levelMap(int index) {
        String prefix = levelPrefix(index);
        return settings.addEditBox(prefix + "map").type(String.class).getValue();
    }

    public int levelsCount() {
        return levelsCount.getValue();
    }

    public String defaultPerks() {
        return defaultPerks.getValue();
    }

    public SettingsWrapper addLevel(int index, Level level) {
        levelsCount.update(index);

        String prefix = levelPrefix(index);
        settings.addEditBox(prefix + "map").multiline().type(String.class).def(level.map());
        settings.addEditBox(prefix + "help").multiline().type(String.class).def(level.help());
        settings.addEditBox(prefix + "default code").multiline().type(String.class).def(level.defaultCode());
        settings.addEditBox(prefix + "refactoring code").multiline().type(String.class).def(level.refactoringCode());
        settings.addEditBox(prefix + "win code").multiline().type(String.class).def(level.winCode());
        settings.addEditBox(prefix + "autocomplete").multiline().type(String.class).def(level.autocomplete().replace("'", "\""));
        settings.addEditBox(prefix + "befunge commands").multiline().type(String.class).def(String.join("\n", level.befungeCommands()));
        return this;
    }

    private String levelPrefix(int index) {
        return "Level" + index + " ";
    }

    // setters for testing

    public SettingsWrapper perkDropRatio(int value) {
        perkDropRatio.update(value);
        return this;
    }

    public SettingsWrapper perkAvailability(int value) {
        perkAvailability.update(value);
        return this;
    }

    public SettingsWrapper perkActivity(int value) {
        perkActivity.update(value);
        return this;
    }

    public SettingsWrapper deathRayRange(int value) {
        deathRayRange.update(value);
        return this;
    }

    public SettingsWrapper loosePenalty(int value) {
        loosePenalty.update(value);
        return this;
    }

    public SettingsWrapper goldScore(int value) {
        goldScore.update(value);
        return this;
    }

    public SettingsWrapper winScore(int value) {
        winScore.update(value);
        return this;
    }

    public SettingsWrapper gunRecharge(int value) {
        gunRecharge.update(value);
        return this;
    }

    public SettingsWrapper gunShotQueue(int value) {
        gunShotQueue.update(value);
        return this;

    }

    public SettingsWrapper gunRestTime(int value) {
        gunRestTime.update(value);
        return this;
    }

    public SettingsWrapper killZombieScore(int value) {
        killZombieScore.update(value);
        return this;
    }

    public SettingsWrapper killHeroScore(int value) {
        killHeroScore.update(value);
        return this;
    }

    public SettingsWrapper enableKillScore(boolean value) {
        enableKillScore.update(value);
        return this;
    }

    public SettingsWrapper isTrainingMode(boolean value) {
        isTrainingMode.update(value);
        return this;
    }

    public SettingsWrapper setGameMode(String mode) {
        gameMode.update(mode);
        return this;
    }

    public SettingsWrapper roomSize(int value) {
        roomSize.update(value);
        return this;
    }

    public SettingsWrapper levelsCount(int value) {
        levelsCount.update(value);
        return this;
    }

    public SettingsWrapper defaultPerks(String value) {
        defaultPerks.update(value);
        return this;
    }

}
