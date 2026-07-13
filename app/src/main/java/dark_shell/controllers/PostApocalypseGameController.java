package dark_shell.controllers;

import dark_shell.models.post_apocalypse.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostApocalypseGameController {
    public Hero createHero() {
        return new Hero();
    }

    public Map<Long, Location> generateLocationAndCalculateDangerLevel(Hero hero) {
        Map<Long, Location> locationWithDangerLevel = new HashMap<>();

        List<String> locationsDescriptions = List.of(
                "Безопасная зона (пустырь, заброшенный магазин). Врагов нет или они очень слабые.",
                "Обычная зона (окраины города, лес). Встречаются обычные мутанты или бандиты.",
                "Опасная зона (промзона, метро). Сильные враги, ловушки.",
                "Смертельно опасная зона (центр города, военные базы). Элитные враги, радиация, аномалии.");

        Location location = new Location(hero.getLevel(), locationsDescriptions);

        long locationDangerLevel = location.calculateDangerLevel();

        locationWithDangerLevel.put(locationDangerLevel, location);

        return locationWithDangerLevel;
    }

    public Enemy createEnemy(long locationDangerLevel) {
        Enemy enemy = new Enemy(locationDangerLevel);
        enemy.calculateEnemy();

        return enemy;
    }

    public boolean calculateCombatInitiative(Hero hero) {
        boolean heroHaveCombatInitiative = false;

        CombatInitiative combatInitiative = new CombatInitiative(hero.getSpeed());
        heroHaveCombatInitiative = combatInitiative.heroHaveCombatInitiative();

        return heroHaveCombatInitiative;
    }

    public void makeMoveHeroFirst(Hero hero, Enemy enemy) {
        UsersMove usersMove = new UsersMove(enemy, hero);
        usersMove.makeMove();

        if (enemy.isDead()) {
            return;
        }

        EnemysMove enemysMove = new EnemysMove(enemy, hero);
        enemysMove.makeMove();
    }

    public void makeMoveEnemyFirst(Hero hero, Enemy enemy) {
        EnemysMove enemysMove = new EnemysMove(enemy, hero);
        enemysMove.makeMove();

        if (hero.isDead()) {
            return;
        }

        UsersMove usersMove = new UsersMove(enemy, hero);
        usersMove.makeMove();
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
