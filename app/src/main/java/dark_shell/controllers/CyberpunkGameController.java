package dark_shell.controllers;

import dark_shell.models.cyberpunk.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CyberpunkGameController {
    public Hero createHero(Hero.Tactics tactic) {
        return new Hero(tactic);
    }

    public Map<Long, Location> generateLocationAndCalculateSecuritySystemLevel(Hero hero) {
        Map<Long, Location> locationWithSecuritySystemLevel = new HashMap<>();

        List<String> locationsDescriptions = List.of(
                "Трущобы (Slums). Низкий риск, мало денег, но можно найти хлам для крафта.",
                "Деловой сектор (Corporate Zone). Средний риск, хорошие деньги, много камер.",
                "Закрытая зона (Restricted). Высокий риск, элитные импланты, охота корпораций.");

        Location location = new Location(hero.getLevel(), locationsDescriptions);

        long locationSecuritySystemLevel = location.calculateSecuritySystemLevel();

        locationWithSecuritySystemLevel.put(locationSecuritySystemLevel, location);

        return locationWithSecuritySystemLevel;
    }

    public long calculateHackComplexity(Hero hero, Location location) {
        long complexity = 0;

        HackComplexity hackComplexity = new HackComplexity(location, hero);
        complexity = hackComplexity.calculateHackComplexity();

        return complexity;
    }

    public long makeHackStep(Hero hero, Location location, long complexity, long successHacks) {
        long newSuccessHacks = successHacks;

        UsersHackStep hackStep = new UsersHackStep(location, hero, complexity);

        if (hackStep.hack(newSuccessHacks)) {
            newSuccessHacks++;
        }

        return newSuccessHacks;
    }

    public HackReward finalizeHackGame(Hero hero, Location location) {
        HackReward reward = null;

        if (!hero.isDead()) {
            reward = new HackReward(location, hero);
            reward.giveReward();
        }

        return reward;
    }

    public Enemy createEnemy(long locationDangerLevel) {
        Enemy enemy = new Enemy(locationDangerLevel);
        enemy.calculateEnemy();

        return enemy;
    }

    public boolean calculateCombatInitiative(Hero hero, Location location) {
        boolean heroHaveCombatInitiative = false;

        CombatInitiative combatInitiative =
                new CombatInitiative(hero.getInfiltration(), location.getTheSecuritySystemLevelOfTheLocation());
        heroHaveCombatInitiative = combatInitiative.heroHaveCombatInitiative();

        return heroHaveCombatInitiative;
    }

    //    public void fight(
    //            Hero hero,
    //            Location location,
    //            Enemy enemy,
    //            boolean heroHaveInitiative
    //    ) {
    //        boolean heroPanicChecked = NO_PANIC;
    //
    //        if (heroHaveInitiative) {
    //            while (!hero.isDead() && !enemy.isDead()) {
    //                if (!heroPanicChecked && hero.inPanic()) {
    //                    heroPanicChecked = HAVE_PANIC;
    //                    makeMoveHeroFirst(hero, enemy, heroPanicChecked);
    //                } else {
    //                    makeMoveHeroFirst(hero, enemy, NO_PANIC);
    //                }
    //            }
    //        } else {
    //            while (!hero.isDead() && !enemy.isDead()) {
    //                if (!heroPanicChecked && hero.inPanic()) {
    //                    heroPanicChecked = HAVE_PANIC;
    //                    makeMoveEnemyFirst(hero, enemy, heroPanicChecked);
    //                } else {
    //                    makeMoveEnemyFirst(hero, enemy, NO_PANIC);
    //                }
    //            }
    //        }
    //
    //        finalizeGame(hero, location);
    //    }

    public void makeMoveHeroFirst(Hero hero, Enemy enemy, boolean heroInPanic) {
        if (!heroInPanic) {
            UsersMove usersMove = new UsersMove(enemy, hero);
            usersMove.makeMove();
        }

        if (enemy.isDead()) {
            return;
        }

        EnemysMove enemysMove = new EnemysMove(enemy, hero);
        enemysMove.makeMove();
    }

    public void makeMoveEnemyFirst(Hero hero, Enemy enemy, boolean heroInPanic) {
        EnemysMove enemysMove = new EnemysMove(enemy, hero);
        enemysMove.makeMove();

        if (hero.isDead()) {
            return;
        }

        if (!heroInPanic) {
            UsersMove usersMove = new UsersMove(enemy, hero);
            usersMove.makeMove();
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
