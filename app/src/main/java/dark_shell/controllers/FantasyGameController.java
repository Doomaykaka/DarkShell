package dark_shell.controllers;

import dark_shell.models.fantasy.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FantasyGameController {
    //    public void play() {
    //        Hero hero = createHero();
    //
    //        Map<Long, Location> locationWithDifficultyLevelOfTheLocation =
    //                generateLocationAndCalculateDifficultyLevelOfTheLocation(hero);
    //
    //        Set<Long> locationWithDifficultyLevels =
    //                locationWithDifficultyLevelOfTheLocation.keySet();
    //        long difficultyLevel = locationWithDifficultyLevels
    //                .stream()
    //                .findFirst()
    //                .get();
    //        Location location = locationWithDifficultyLevelOfTheLocation.get(
    //                difficultyLevel
    //        );
    //
    //        Enemy enemy = createEnemy(difficultyLevel);
    //
    //        boolean heroHaveCombatInitiative = calculateCombatInitiative(
    //                hero,
    //                location
    //        );
    //
    //        if (heroHaveCombatInitiative) {
    //            while (!hero.isDead() && !enemy.isDead()) {
    //                makeMoveHeroFirst(hero, enemy, difficultyLevel);
    //            }
    //        } else {
    //            while (!hero.isDead() && !enemy.isDead()) {
    //                makeMoveEnemyFirst(hero, enemy, difficultyLevel);
    //            }
    //        }
    //
    //        finalizeGame(hero, location);
    //    }

    public Hero createHero(Hero.Tactics tactic) {
        return new Hero(tactic);
    }

    public Enemy createEnemy(long difficultyLevel) {
        Enemy enemy = new Enemy(difficultyLevel);
        enemy.calculateEnemy();

        return enemy;
    }

    public Map<Long, Location> generateLocationAndCalculateDifficultyLevelOfTheLocation(Hero hero) {
        Map<Long, Location> locationWithDifficultyLevelOfTheLocation = new HashMap<>();

        List<String> locationsDescriptions = List.of(
                "Безопасная зона. Врагов нет или это слабые животные.",
                "Обычная зона. Бандиты, волки, гоблины.",
                "Опасная зона. Орки, скелеты, ловушки.",
                "Смертельно опасная зона. Древние драконы, демоны, проклятые рыцари.");

        Location location = new Location(hero.getLevel(), locationsDescriptions);

        long locationDifficultyLevel = location.calculateDifficultyLevel();

        locationWithDifficultyLevelOfTheLocation.put(locationDifficultyLevel, location);

        return locationWithDifficultyLevelOfTheLocation;
    }

    public boolean calculateCombatInitiative(Hero hero, Location location) {
        boolean heroHaveCombatInitiative = false;

        CombatInitiative combatInitiative =
                new CombatInitiative(hero.getDexterity(), location.getTheDifficultyLevelOfTheLocation());
        heroHaveCombatInitiative = combatInitiative.heroHaveCombatInitiative();

        return heroHaveCombatInitiative;
    }

    public void makeMoveHeroFirst(Hero hero, Enemy enemy, long locationWithDifficultyLevel) {
        if (hero.getCurrentTactics().equals(Hero.Tactics.FIGHT)) {
            UsersFightMove fightMove = new UsersFightMove(enemy, hero);
            fightMove.makeMove();
        } else {
            UsersMagicalMove magicalMove = new UsersMagicalMove(hero, enemy, locationWithDifficultyLevel);
            magicalMove.conjure();
        }

        if (enemy.isDead()) {
            return;
        }

        EnemysMove enemysMove = new EnemysMove(enemy, hero);
        enemysMove.makeMove();
    }

    public void makeMoveEnemyFirst(Hero hero, Enemy enemy, long locationWithDifficultyLevel) {
        EnemysMove enemysMove = new EnemysMove(enemy, hero);
        enemysMove.makeMove();

        if (hero.isDead()) {
            return;
        }

        if (hero.getCurrentTactics().equals(Hero.Tactics.FIGHT)) {
            UsersFightMove fightMove = new UsersFightMove(enemy, hero);
            fightMove.makeMove();

            System.out.println(fightMove);
        } else {
            UsersMagicalMove magicalMove = new UsersMagicalMove(hero, enemy, locationWithDifficultyLevel);
            magicalMove.conjure();

            System.out.println(magicalMove);
        }
    }

    public Reward finalizeGame(Hero hero, Location location) {
        Reward reward = null;

        if (!hero.isDead()) {
            reward = new Reward(location, hero);
            reward.giveReward();
        }

        return reward;
    }
}
