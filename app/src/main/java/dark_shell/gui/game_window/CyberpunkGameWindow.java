package dark_shell.gui.game_window;

import dark_shell.controllers.CyberpunkGameController;
import dark_shell.controllers.GameOperationsController;
import dark_shell.dao.*;
import dark_shell.models.cyberpunk.*;
import dark_shell.models.database.Character;
import dark_shell.models.database.CyberpunkCharacteristic;
import dark_shell.utils.HibernateConfiguration;
import dark_shell.utils.Logger;
import dark_shell.utils.SupportFunctions;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.*;

public class CyberpunkGameWindow extends JFrame {
    private Character character;
    private CyberpunkGameController cyberpunkController;
    private GameOperationsController gameOperationsController;

    private JPanel leftPanel;
    private JPanel centerPanel;
    private JPanel rightPanel;
    private JPanel bottomPanel;

    private JLabel heroLevel;
    private JLabel heroHP;
    private JLabel heroInfiltration;
    private JLabel heroTechnic;
    private JLabel heroCool;
    private JLabel heroReputation;
    private JLabel heroNoise;
    private JLabel heroYen;
    private JLabel heroExperience;
    private JLabel heroExperienceToNextLevel;

    private JLabel enemyHealth;
    private JLabel enemyAttack;
    private JLabel enemyDefense;

    private boolean heroPanicChecked = false;

    private Hero gameHero;
    private Location gameLocation;
    private long gameLocationSecuritySystemLevel;
    private Enemy gameEnemy;
    private Reward reward;
    private Boolean heroHaveInitiative;
    private HackReward hackReward;
    private long hackComplexity = -1;
    private long successfulHacks = -1;

    private static final double HP_REGEN_POWER = 0.1;

    public CyberpunkGameWindow(Character character) {
        this.character = character;

        initController();
        initUI();
    }

    private void initController() {
        CharactersDAO charactersDAO = new CharactersDAO(HibernateConfiguration.getEntityManagerFactory());
        CyberpunkCharacteristicsDAO cyberpunkCharacteristicsDAO =
                new CyberpunkCharacteristicsDAO(HibernateConfiguration.getEntityManagerFactory());
        FantasyCharacteristicsDAO fantasyCharacteristicsDAO =
                new FantasyCharacteristicsDAO(HibernateConfiguration.getEntityManagerFactory());
        PostApocalypseCharacteristicsDAO postApocalypseCharacteristicsDAO =
                new PostApocalypseCharacteristicsDAO(HibernateConfiguration.getEntityManagerFactory());
        StatisticsDAO statisticsDAO = new StatisticsDAO(HibernateConfiguration.getEntityManagerFactory());

        gameOperationsController = new GameOperationsController(
                charactersDAO,
                cyberpunkCharacteristicsDAO,
                fantasyCharacteristicsDAO,
                postApocalypseCharacteristicsDAO,
                statisticsDAO);

        cyberpunkController = new CyberpunkGameController();
    }

    private void initUI() {
        Logger.getInstance().info("initializing CyberpunkGameWindow UI");

        setTitle("Dark Shell — Cyberpunk Mode");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(new Color(15, 15, 25));

        setLayout(new BorderLayout(0, 0));

        leftPanel = createLeftPanel();
        centerPanel = createCenterPanel();
        rightPanel = createRightPanel();
        bottomPanel = createBottomPanel();

        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(20, 20, 35));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(0, 255, 255)));
        panel.setPreferredSize(new Dimension(280, 0));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel title = new JLabel("CHARACTER STATS");
        title.setForeground(new Color(0, 255, 255));
        title.setFont(new Font("Monospaced", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        fillHeroStats(panel);

        return panel;
    }

    private void fillHeroStats(JPanel panel) {
        CyberpunkCharacteristic hero = character.getCyberpunkCharacteristics();

        heroLevel = createStatLabel("LEVEL", hero.getLevel().toString());
        heroHP = createStatLabel("HP", hero.getCurrentHP() + "/" + hero.getMaxHP());
        heroInfiltration = createStatLabel("INF", hero.getInfiltration().toString());
        heroTechnic = createStatLabel("TECH", hero.getTechnic().toString());
        heroCool = createStatLabel("COOL", hero.getCool().toString());
        heroReputation = createStatLabel("REP", hero.getReputation().toString());
        heroNoise = createStatLabel("NOISE", hero.getNoise().toString());
        heroYen = createStatLabel("YEN", hero.getYen().toString());
        heroExperience = createStatLabel("EXP", hero.getExperience().toString());
        heroExperienceToNextLevel =
                createStatLabel("NEXT_LVL", hero.getExperienceToNextLevel().toString());

        panel.add(heroLevel);
        panel.add(heroHP);
        panel.add(heroInfiltration);
        panel.add(heroTechnic);
        panel.add(heroCool);
        panel.add(heroReputation);
        panel.add(heroNoise);
        panel.add(heroYen);
        panel.add(heroExperience);
        panel.add(heroExperienceToNextLevel);
    }

    private JLabel createStatLabel(String label, String value) {
        String text = label + ": " + value;
        JLabel l = new JLabel(text);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Dialog", Font.PLAIN, 14));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        l.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 0));
        return l;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(0, 0, new Color(10, 5, 20), 0, getHeight(), new Color(25, 10, 40));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(new Color(0, 100, 100, 80));
                int step = 64;
                for (int x = 0; x <= getWidth(); x += step) {
                    g2.drawLine(x, 0, x, getHeight());
                }
                for (int y = 0; y <= getHeight(); y += step) {
                    g2.drawLine(0, y, getWidth(), y);
                }

                g2.setColor(new Color(0, 255, 100));
                int playerW = 80;
                int playerH = 120;
                int playerX = 60;
                int playerY = getHeight() - playerH - 40;
                g2.fillRoundRect(playerX, playerY, playerW, playerH, 12, 12);
                drawGlowBorder(g2, playerX, playerY, playerW, playerH, new Color(0, 255, 100, 160));

                g2.setColor(new Color(255, 50, 50));
                int enemyW = 80;
                int enemyH = 120;
                int enemyX = getWidth() - enemyW - 60;
                int enemyY = getHeight() - enemyH - 40;
                g2.fillRoundRect(enemyX, enemyY, enemyW, enemyH, 12, 12);
                drawGlowBorder(g2, enemyX, enemyY, enemyW, enemyH, new Color(255, 50, 50, 160));
            }

            private void drawGlowBorder(Graphics2D g2, int x, int y, int w, int h, Color glow) {
                int thickness = 3;
                g2.setColor(glow);
                g2.setStroke(new BasicStroke(thickness));
                g2.drawRoundRect(x - thickness, y - thickness, w + thickness * 2, h + thickness * 2, 16, 16);
            }
        };
        panel.setBackground(new Color(15, 15, 25));
        panel.setOpaque(true);
        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(20, 20, 35));
        panel.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, new Color(255, 50, 50)));
        panel.setPreferredSize(new Dimension(280, 0));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel title = new JLabel("ENEMY STATS");
        title.setForeground(new Color(255, 50, 50));
        title.setFont(new Font("Monospaced", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        fillEnemyStats(panel);

        return panel;
    }

    private void fillEnemyStats(JPanel panel) {
        enemyHealth = createStatLabel("HP", "-/-");
        enemyAttack = createStatLabel("ATK", "-");
        enemyDefense = createStatLabel("DEF", "-");

        panel.add(enemyHealth);
        panel.add(enemyAttack);
        panel.add(enemyDefense);
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(10, 10, 20));
        panel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(0, 255, 255)));

        FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 40, 15);
        panel.setLayout(layout);

        JButton btnFight = new JButton("COMBAT TACTIC");
        JButton btnHack = new JButton("HACK TACTIC");

        setupCyberpunkButton(btnFight, new Color(0, 255, 100), Color.BLACK);
        setupCyberpunkButton(btnHack, new Color(0, 255, 255), Color.BLACK);

        btnFight.addActionListener(e -> {
            Logger.getInstance().info("Combat tactic selected");
            gameStartLogic(Hero.Tactics.FIGHT);
        });

        btnHack.addActionListener(e -> {
            Logger.getInstance().info("Hack tactic selected");
            gameStartLogic(Hero.Tactics.HACK);
        });

        panel.add(btnFight);
        panel.add(btnHack);
        return panel;
    }

    private void setupCyberpunkButton(JButton btn, Color fillColor, Color textColor) {
        btn.setForeground(textColor);
        btn.setBackground(fillColor);
        btn.setFont(new Font("Monospaced", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.getModel().addChangeListener(e -> {
            if (btn.getModel().isRollover()) {
                btn.setBackground(fillColor.brighter());
            } else {
                btn.setBackground(fillColor);
            }
        });
    }

    public void create() {
        Logger.getInstance().info("launch");
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    private void gameStartLogic(Hero.Tactics tactic) {
        if (gameHero == null) {
            initGame(tactic);
        }

        if (gameHero.isDead()) {
            SupportFunctions.showMessage("Hero dead");
            return;
        }

        if (gameEnemy != null && gameEnemy.isDead() || hackReward != null) {
            SupportFunctions.showMessage("Hero wins");

            boolean needNewRound = answerNeedNewRound();

            if (needNewRound) {
                Logger.getInstance().info("Continue cyberpunk game");

                CyberpunkGameWindow gameWindow = new CyberpunkGameWindow(character);
                gameWindow.create();

                this.dispose();
            }

            return;
        }

        if (tactic.equals(Hero.Tactics.HACK)) {
            hackStep();
        } else if (tactic.equals(Hero.Tactics.FIGHT)) {
            fightStep();
        }

        if (gameEnemy != null && gameEnemy.isDead() || hackReward != null || gameHero.isDead()) {
            saveHero();
        }
    }

    private boolean answerNeedNewRound() {
        boolean needNewRound = false;

        Object[] options = {"Yes", "No"};

        AtomicInteger choice = new AtomicInteger();

        choice.set(JOptionPane.showOptionDialog(
                null,
                "Continue?",
                "Game continue",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]));

        needNewRound = choice.get() == 0;

        return needNewRound;
    }

    private void initGame(Hero.Tactics tactic) {
        CyberpunkCharacteristic hero = character.getCyberpunkCharacteristics();
        gameHero = new Hero(
                hero.getLevel(),
                hero.getCurrentHP(),
                hero.getMaxHP(),
                hero.getInfiltration(),
                hero.getTechnic(),
                hero.getCool(),
                hero.getReputation(),
                hero.getNoise(),
                hero.getYen(),
                hero.getExperience(),
                hero.getExperienceToNextLevel(),
                tactic);

        updateHeroStats(gameHero);

        Map<Long, Location> locationWithSecuritySystemLevel =
                cyberpunkController.generateLocationAndCalculateSecuritySystemLevel(gameHero);

        Set<Long> locationSecuritySystemLevels = locationWithSecuritySystemLevel.keySet();
        gameLocationSecuritySystemLevel =
                locationSecuritySystemLevels.stream().findFirst().get();
        gameLocation = locationWithSecuritySystemLevel.get(gameLocationSecuritySystemLevel);

        SupportFunctions.showMessage("Location security level: " + gameLocationSecuritySystemLevel);
    }

    private void hackStep() {
        if (hackComplexity == -1) {
            hackComplexity = cyberpunkController.calculateHackComplexity(gameHero, gameLocation);
        }

        if (successfulHacks == -1) {
            successfulHacks = 0;
        }

        SupportFunctions.showMessage("Hack complexity (steps count): " + hackComplexity);

        if (!gameHero.isDead()) {
            successfulHacks = cyberpunkController.makeHackStep(gameHero, gameLocation, hackComplexity, successfulHacks);

            SupportFunctions.showMessage("Hack steps completed: " + successfulHacks);

            if (successfulHacks >= hackComplexity) {
                hackReward = cyberpunkController.finalizeHackGame(gameHero, gameLocation);

                if (hackReward == null) {
                    SupportFunctions.showMessage("Hero dead");
                } else {
                    SupportFunctions.showMessage("Hero wins");
                }
            }

            updateHeroStats(gameHero);
        }
    }

    private void fightStep() {
        if (gameEnemy == null) {
            gameEnemy = cyberpunkController.createEnemy(gameLocationSecuritySystemLevel);

            updateEnemyStats(gameEnemy);

            SupportFunctions.showMessage("Enemy generated");

            heroHaveInitiative = cyberpunkController.calculateCombatInitiative(gameHero, gameLocation);

            if (heroHaveInitiative) {
                SupportFunctions.showMessage("The hero has the initiative");
            } else {
                SupportFunctions.showMessage("The enemy has the initiative");
            }
        }

        fight(heroHaveInitiative, gameEnemy);

        if (gameHero.isDead() || gameEnemy.isDead()) {
            updateEnemyStats(gameEnemy);
            updateHeroStats(gameHero);

            reward = cyberpunkController.finalizeGame(gameHero, gameLocation);

            if (reward == null) {
                SupportFunctions.showMessage("Hero dead");
            } else {
                SupportFunctions.showMessage("Hero wins");
            }
        }
    }

    private void fight(boolean heroHaveInitiative, Enemy enemy) {
        if (heroHaveInitiative) {
            if (!gameHero.isDead() && !enemy.isDead()) {
                if (!heroPanicChecked && gameHero.inPanic()) {
                    heroPanicChecked = true;
                    cyberpunkController.makeMoveHeroFirst(gameHero, enemy, heroPanicChecked);
                } else {
                    heroPanicChecked = false;
                    cyberpunkController.makeMoveHeroFirst(gameHero, enemy, false);
                }
            }
        } else {
            if (!gameHero.isDead() && !enemy.isDead()) {
                if (!heroPanicChecked && gameHero.inPanic()) {
                    heroPanicChecked = true;
                    cyberpunkController.makeMoveEnemyFirst(gameHero, enemy, heroPanicChecked);
                } else {
                    heroPanicChecked = false;
                    cyberpunkController.makeMoveEnemyFirst(gameHero, enemy, false);
                }
            }
        }

        updateEnemyStats(gameEnemy);
        updateHeroStats(gameHero);
    }

    private void updateEnemyStats(Enemy enemy) {
        enemyHealth.setText("HP: " + enemy.getHealth());
        enemyAttack.setText("ATK: " + enemy.getAttack());
        enemyDefense.setText("DEF: " + enemy.getDefense());
    }

    private void updateHeroStats(Hero gameHero) {
        heroLevel.setText("LEVEL: " + gameHero.getLevel());
        heroHP.setText("HP: " + gameHero.getCurrentHP() + "/" + gameHero.getMaxHP());
        heroInfiltration.setText("INF: " + gameHero.getInfiltration());
        heroTechnic.setText("TECH: " + gameHero.getTechnic());
        heroCool.setText("COOL: " + gameHero.getCool());
        heroReputation.setText("REP: " + gameHero.getReputation());
        heroNoise.setText("NOISE: " + gameHero.getNoise());
        heroYen.setText("YEN: " + gameHero.getYen());
        heroExperience.setText("EXP: " + gameHero.getExperience());
        heroExperienceToNextLevel.setText("NEXT_LVL: " + gameHero.getExperienceToNextLevel());
    }

    private void saveHero() {
        if (hackReward != null) {
            giveHackReward();
        }

        if (reward != null) {
            giveReward();
        }

        long newHP = gameHero.getCurrentHP();

        if (hackReward != null || reward != null) {
            newHP = Double.valueOf(gameHero.getCurrentHP() + gameHero.getMaxHP() * HP_REGEN_POWER)
                    .intValue();
        }

        if (newHP > gameHero.getMaxHP()) {
            newHP = gameHero.getMaxHP();
        }

        long experienceToNextLevel = gameHero.getExperienceToNextLevel();
        long currentLevel = gameHero.getLevel();
        long levelUpCount = 0;

        while (gameHero.getExperience() > experienceToNextLevel) {
            levelUpCount++;
            experienceToNextLevel = gameHero.calculateExperienceToNextLevel(currentLevel + 1);
            currentLevel++;
        }

        java.util.List<Long> additionalSkillpoints = java.util.List.of(0L, 0L, 0L, 0L);

        if (levelUpCount > 0) {
            long skillPointsCount = levelUpCount * 2;

            additionalSkillpoints = chooseSkillpoints(skillPointsCount, currentLevel, gameLocationSecuritySystemLevel);
        }

        character.getCyberpunkCharacteristics().setLevel(currentLevel);
        character.getCyberpunkCharacteristics().setCurrentHP(newHP);
        character.getCyberpunkCharacteristics().setMaxHP(gameHero.getMaxHP());
        character
                .getCyberpunkCharacteristics()
                .setInfiltration(gameHero.getInfiltration() + additionalSkillpoints.get(0));
        character.getCyberpunkCharacteristics().setTechnic(gameHero.getTechnic() + additionalSkillpoints.get(1));
        character.getCyberpunkCharacteristics().setCool(gameHero.getCool() + additionalSkillpoints.get(2));
        character.getCyberpunkCharacteristics().setReputation(gameHero.getReputation() + additionalSkillpoints.get(3));
        character.getCyberpunkCharacteristics().setNoise(gameHero.getNoise());
        character.getCyberpunkCharacteristics().setYen(gameHero.getYen());
        character.getCyberpunkCharacteristics().setExperience(gameHero.getExperience());
        character.getCyberpunkCharacteristics().setExperienceToNextLevel(experienceToNextLevel);

        updateStatistic();

        gameOperationsController.updateCharacter(character);
    }

    private void updateStatistic() {
        LocalDate currentDate = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate lastPlayDate = character
                .getStatistic()
                .getLastPlayDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        long daysInGame = character.getStatistic().getDaysInGame();

        if (!lastPlayDate.equals(currentDate)) {
            daysInGame++;
            lastPlayDate = currentDate;
        }

        long eventsConducted = character.getStatistic().getEventsConducted() + 1;
        long numberOfSuccesfullEvents = character.getStatistic().getNumberOfSuccessfulEvents();

        if (character.getCyberpunkCharacteristics().getCurrentHP() > 0) {
            numberOfSuccesfullEvents++;
        }

        long maxLevel = character.getStatistic().getMaxLevel();

        if (character.getCyberpunkCharacteristics().getLevel() > maxLevel) {
            maxLevel = character.getCyberpunkCharacteristics().getLevel();
        }

        long maxExperience = character.getStatistic().getMaxExperience();

        if (character.getCyberpunkCharacteristics().getExperience() > maxExperience) {
            maxExperience = character.getCyberpunkCharacteristics().getExperience();
        }

        character
                .getStatistic()
                .setLastPlayDate(Date.from(
                        lastPlayDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        character.getStatistic().setDaysInGame(daysInGame);
        character.getStatistic().setEventsConducted(eventsConducted);
        character.getStatistic().setNumberOfSuccessfulEvents(numberOfSuccesfullEvents);
        character.getStatistic().setMaxLevel(maxLevel);
        character.getStatistic().setMaxExperience(maxExperience);
    }

    private void giveHackReward() {
        hackReward.giveReward();
    }

    private void giveReward() {
        reward.giveReward();
    }

    private java.util.List<Long> chooseSkillpoints(long skillPointsCount, long heroLevel, long locationSecurityLevel) {
        Long infiltration = 0L;
        Long technic = 0L;
        Long cool = 0L;
        Long reputation = 0L;

        Object[] options = {"Infiltration", "Technic", "Cool", "Reputation"};

        AtomicInteger choice = new AtomicInteger();

        for (int i = 0; i < skillPointsCount; i++) {
            choice.set(JOptionPane.showOptionDialog(
                    null,
                    "Select skillpoints",
                    "Skillpoints selection",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]));

            int elementsCount = 2;

            if (choice.get() == 0) {
                infiltration += (heroLevel + locationSecurityLevel) / elementsCount;
            } else if (choice.get() == 1) {
                technic += (heroLevel + locationSecurityLevel) / elementsCount;
            } else if (choice.get() == 2) {
                cool += (heroLevel + locationSecurityLevel) / elementsCount;
            } else if (choice.get() == 3) {
                reputation += (heroLevel + locationSecurityLevel) / elementsCount;
            }
        }

        return List.of(infiltration, technic, cool, reputation);
    }
}
