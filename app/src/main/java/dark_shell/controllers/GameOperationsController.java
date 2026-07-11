package dark_shell.controllers;

import dark_shell.dao.*;
import dark_shell.models.database.*;
import dark_shell.models.database.Character;
import dark_shell.utils.SupportFunctions;
import java.io.*;
import java.util.Date;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GameOperationsController {
    private CharactersDAO charactersDAO;
    private CyberpunkCharacteristicsDAO cyberpunkCharacteristicsDAO;
    private FantasyCharacteristicsDAO fantasyCharacteristicsDAO;
    private PostApocalypseCharacteristicsDAO postApocalypseCharacteristicsDAO;
    private StatisticsDAO statisticsDAO;

    public GameOperationsController(
            CharactersDAO charactersDAO,
            CyberpunkCharacteristicsDAO cyberpunkCharacteristicsDAO,
            FantasyCharacteristicsDAO fantasyCharacteristicsDAO,
            PostApocalypseCharacteristicsDAO postApocalypseCharacteristicsDAO,
            StatisticsDAO statisticsDAO) {
        this.charactersDAO = charactersDAO;
        this.cyberpunkCharacteristicsDAO = cyberpunkCharacteristicsDAO;
        this.fantasyCharacteristicsDAO = fantasyCharacteristicsDAO;
        this.postApocalypseCharacteristicsDAO = postApocalypseCharacteristicsDAO;
        this.statisticsDAO = statisticsDAO;
    }

    public List<Character> getAllCharacters() {
        List<Character> allCharacters = null;
        allCharacters = this.charactersDAO.getAll();

        return allCharacters;
    }

    public Character createNewCharacter(String name) {
        Character newCharacter = SupportFunctions.createEmptyCharacter(name);

        this.cyberpunkCharacteristicsDAO.create(newCharacter.getCyberpunkCharacteristics());
        this.fantasyCharacteristicsDAO.create(newCharacter.getFantasyCharacteristics());
        this.postApocalypseCharacteristicsDAO.create(newCharacter.getPostApocalypseCharacteristics());
        this.statisticsDAO.create(newCharacter.getStatistic());
        this.charactersDAO.create(newCharacter);

        return newCharacter;
    }

    public boolean renameCharacter(Long id, String newName) {
        Character characterToUpdate = this.charactersDAO.get(id);

        if (characterToUpdate != null) {
            characterToUpdate.setName(newName);
        }

        return this.charactersDAO.update(characterToUpdate);
    }

    public boolean removeCharacter(Long id) {
        Character characterToRemove = this.charactersDAO.get(id);

        return this.charactersDAO.remove(characterToRemove);
    }

    public void exportCharacter(Character character, File fileToSave) {
        JSONObject characterObj = new JSONObject();

        JSONParser parser = new JSONParser();

        try {
            Long id = character.getId();
            String name = character.getName();
            Date creationDate = character.getCreationDate();
            CyberpunkCharacteristic cyberpunkCharacteristics = character.getCyberpunkCharacteristics();
            FantasyCharacteristic fantasyCharacteristics = character.getFantasyCharacteristics();
            PostApocalypseCharacteristic postApocalypseCharacteristics = character.getPostApocalypseCharacteristics();
            Statistic statistic = character.getStatistic();

            characterObj.put("id", id);
            characterObj.put("name", name);
            characterObj.put("creationDate", creationDate.toInstant().getEpochSecond());

            String cyberpunkCharacteristicsJSON = cyberpunkCharacteristicToJSON(cyberpunkCharacteristics);
            JSONObject cyberpunkCharacteristicsObj = (JSONObject) parser.parse(cyberpunkCharacteristicsJSON);
            characterObj.put("cyberpunkCharacteristics", cyberpunkCharacteristicsObj);

            String fantasyCharacteristicsJSON = fantasyCharacteristicToJSON(fantasyCharacteristics);
            JSONObject fantasyCharacteristicsObj = (JSONObject) parser.parse(fantasyCharacteristicsJSON);
            characterObj.put("fantasyCharacteristics", fantasyCharacteristicsObj);

            String postApocalypseCharacteristicsJSON =
                    postApocalypseCharacteristicToJSON(postApocalypseCharacteristics);
            JSONObject postApocalypseCharacteristicsObj = (JSONObject) parser.parse(postApocalypseCharacteristicsJSON);
            characterObj.put("postApocalypseCharacteristics", postApocalypseCharacteristicsObj);

            String statisticJSON = statisticToJSON(statistic);
            JSONObject statisticObj = (JSONObject) parser.parse(statisticJSON);
            characterObj.put("statistic", statisticObj);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        StringWriter stringWriter = new StringWriter();

        try {
            characterObj.writeJSONString(stringWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SupportFunctions.writeContentInNewFile(
                fileToSave.getParentFile(), fileToSave.getName(), List.of(stringWriter.toString()));
    }

    private String cyberpunkCharacteristicToJSON(CyberpunkCharacteristic cyberpunkCharacteristic) {
        JSONObject cyberpunkCharacteristicObj = new JSONObject();

        Long id = cyberpunkCharacteristic.getId();
        Long level = cyberpunkCharacteristic.getLevel();
        Long currentHP = cyberpunkCharacteristic.getCurrentHP();
        Long maxHP = cyberpunkCharacteristic.getMaxHP();
        Long infiltration = cyberpunkCharacteristic.getInfiltration();
        Long technic = cyberpunkCharacteristic.getTechnic();
        Long cool = cyberpunkCharacteristic.getCool();
        Long reputation = cyberpunkCharacteristic.getReputation();
        Long noise = cyberpunkCharacteristic.getNoise();
        Long experience = cyberpunkCharacteristic.getExperience();
        Long yen = cyberpunkCharacteristic.getYen();
        Long experienceToNextLevel = cyberpunkCharacteristic.getExperienceToNextLevel();

        cyberpunkCharacteristicObj.put("id", id);
        cyberpunkCharacteristicObj.put("level", level);
        cyberpunkCharacteristicObj.put("currentHP", currentHP);
        cyberpunkCharacteristicObj.put("maxHP", maxHP);
        cyberpunkCharacteristicObj.put("infiltration", infiltration);
        cyberpunkCharacteristicObj.put("technic", technic);
        cyberpunkCharacteristicObj.put("cool", cool);
        cyberpunkCharacteristicObj.put("reputation", reputation);
        cyberpunkCharacteristicObj.put("noise", noise);
        cyberpunkCharacteristicObj.put("experience", experience);
        cyberpunkCharacteristicObj.put("yen", yen);
        cyberpunkCharacteristicObj.put("experienceToNextLevel", experienceToNextLevel);

        StringWriter stringWriter = new StringWriter();

        try {
            cyberpunkCharacteristicObj.writeJSONString(stringWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringWriter.toString();
    }

    private String fantasyCharacteristicToJSON(FantasyCharacteristic fantasyCharacteristic) {
        JSONObject fantasyCharacteristicObj = new JSONObject();

        Long id = fantasyCharacteristic.getId();
        Long level = fantasyCharacteristic.getLevel();
        Long currentHP = fantasyCharacteristic.getCurrentHP();
        Long maxHP = fantasyCharacteristic.getMaxHP();
        Long manaPoints = fantasyCharacteristic.getManaPoints();
        Long maxManaPoints = fantasyCharacteristic.getMaxManaPoints();
        Long strength = fantasyCharacteristic.getStrength();
        Long dexterity = fantasyCharacteristic.getDexterity();
        Long intelligence = fantasyCharacteristic.getIntelligence();
        Long willpower = fantasyCharacteristic.getWillpower();
        Long reputation = fantasyCharacteristic.getReputation();
        Long gold = fantasyCharacteristic.getGold();
        Long encumbrance = fantasyCharacteristic.getEncumbrance();
        Long maxEncumbrance = fantasyCharacteristic.getMaxEncumbrance();
        Long experience = fantasyCharacteristic.getExperience();
        Long experienceToNextLevel = fantasyCharacteristic.getExperienceToNextLevel();
        Long royalGuildFactionPoints = fantasyCharacteristic.getRoyalGuildFactionPoints();
        Long mageGuildFactionPoints = fantasyCharacteristic.getMageGuildFactionPoints();
        Long thievesGuildFactionPoints = fantasyCharacteristic.getThievesGuildFactionPoints();
        Long barbarianTribeFactionPoints = fantasyCharacteristic.getBarbarianTribeFactionPoints();

        fantasyCharacteristicObj.put("id", id);
        fantasyCharacteristicObj.put("level", level);
        fantasyCharacteristicObj.put("currentHP", currentHP);
        fantasyCharacteristicObj.put("maxHP", maxHP);
        fantasyCharacteristicObj.put("manaPoints", manaPoints);
        fantasyCharacteristicObj.put("maxManaPoints", maxManaPoints);
        fantasyCharacteristicObj.put("strength", strength);
        fantasyCharacteristicObj.put("dexterity", dexterity);
        fantasyCharacteristicObj.put("intelligence", intelligence);
        fantasyCharacteristicObj.put("willpower", willpower);
        fantasyCharacteristicObj.put("reputation", reputation);
        fantasyCharacteristicObj.put("gold", gold);
        fantasyCharacteristicObj.put("encumbrance", encumbrance);
        fantasyCharacteristicObj.put("maxEncumbrance", maxEncumbrance);
        fantasyCharacteristicObj.put("experience", experience);
        fantasyCharacteristicObj.put("experienceToNextLevel", experienceToNextLevel);
        fantasyCharacteristicObj.put("royalGuildFactionPoints", royalGuildFactionPoints);
        fantasyCharacteristicObj.put("mageGuildFactionPoints", mageGuildFactionPoints);
        fantasyCharacteristicObj.put("thievesGuildFactionPoints", thievesGuildFactionPoints);
        fantasyCharacteristicObj.put("barbarianTribeFactionPoints", barbarianTribeFactionPoints);

        StringWriter stringWriter = new StringWriter();

        try {
            fantasyCharacteristicObj.writeJSONString(stringWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringWriter.toString();
    }

    private String postApocalypseCharacteristicToJSON(PostApocalypseCharacteristic postApocalypseCharacteristic) {
        JSONObject postApocalypseCharacteristicObj = new JSONObject();

        Long id = postApocalypseCharacteristic.getId();
        Long level = postApocalypseCharacteristic.getLevel();
        Long currentHP = postApocalypseCharacteristic.getCurrentHP();
        Long maxHP = postApocalypseCharacteristic.getMaxHP();
        Long attack = postApocalypseCharacteristic.getAttack();
        Long defence = postApocalypseCharacteristic.getDefence();
        Long speed = postApocalypseCharacteristic.getSpeed();
        Long luck = postApocalypseCharacteristic.getLuck();
        Long gold = postApocalypseCharacteristic.getGold();
        Long experience = postApocalypseCharacteristic.getExperience();
        Long experienceToNextLevel = postApocalypseCharacteristic.getExperienceToNextLevel();

        postApocalypseCharacteristicObj.put("id", id);
        postApocalypseCharacteristicObj.put("level", level);
        postApocalypseCharacteristicObj.put("currentHP", currentHP);
        postApocalypseCharacteristicObj.put("maxHP", maxHP);
        postApocalypseCharacteristicObj.put("attack", attack);
        postApocalypseCharacteristicObj.put("defence", defence);
        postApocalypseCharacteristicObj.put("speed", speed);
        postApocalypseCharacteristicObj.put("luck", luck);
        postApocalypseCharacteristicObj.put("gold", gold);
        postApocalypseCharacteristicObj.put("experience", experience);
        postApocalypseCharacteristicObj.put("experienceToNextLevel", experienceToNextLevel);

        StringWriter stringWriter = new StringWriter();

        try {
            postApocalypseCharacteristicObj.writeJSONString(stringWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringWriter.toString();
    }

    private String statisticToJSON(Statistic statistic) {
        JSONObject statisticObj = new JSONObject();

        Long id = statistic.getId();
        Date lastPlayDate = statistic.getLastPlayDate();
        Long daysInGame = statistic.getDaysInGame();
        Long eventsConducted = statistic.getEventsConducted();
        Long numberOfSuccessfulEvents = statistic.getNumberOfSuccessfulEvents();
        Long maxLevel = statistic.getMaxLevel();
        Long maxExperience = statistic.getMaxExperience();

        statisticObj.put("id", id);
        statisticObj.put("lastPlayDate", lastPlayDate.toInstant().getEpochSecond());
        statisticObj.put("daysInGame", daysInGame);
        statisticObj.put("eventsConducted", eventsConducted);
        statisticObj.put("numberOfSuccessfulEvents", numberOfSuccessfulEvents);
        statisticObj.put("maxLevel", maxLevel);
        statisticObj.put("maxExperience", maxExperience);

        StringWriter stringWriter = new StringWriter();

        try {
            statisticObj.writeJSONString(stringWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringWriter.toString();
    }

    public Character importCharacter(File fileToLoad) {
        Character importedCharacter = null;

        List<String> fileContent = List.of();

        try {
            fileContent = SupportFunctions.readFileContent(new FileReader(fileToLoad));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (fileContent.isEmpty()) {
            return importedCharacter;
        }

        String characterJSON = fileContent.getFirst();

        JSONParser parser = new JSONParser();

        try {
            JSONObject characterObj = (JSONObject) parser.parse(characterJSON);

            Long id = (Long) characterObj.get("id");
            String name = (String) characterObj.get("name");
            Date creationDate = new Date((Long) characterObj.get("creationDate") * 1000);
            CyberpunkCharacteristic cyberpunkCharacteristics =
                    cyberpunkCharacteristicFromJSON((JSONObject) characterObj.get("cyberpunkCharacteristics"));
            FantasyCharacteristic fantasyCharacteristics =
                    fantasyCharacteristicFromJSON((JSONObject) characterObj.get("fantasyCharacteristics"));
            PostApocalypseCharacteristic postApocalypseCharacteristics = postApocalypseCharacteristicFromJSON(
                    (JSONObject) characterObj.get("postApocalypseCharacteristics"));
            Statistic statistic = statisticFromJSON((JSONObject) characterObj.get("statistic"));

            importedCharacter = new Character(
                    name,
                    creationDate,
                    cyberpunkCharacteristics,
                    fantasyCharacteristics,
                    postApocalypseCharacteristics,
                    statistic);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return importedCharacter;
    }

    private CyberpunkCharacteristic cyberpunkCharacteristicFromJSON(JSONObject cyberpunkCharacteristicsObj) {
        CyberpunkCharacteristic cyberpunkCharacteristics = null;

        Long level = (Long) cyberpunkCharacteristicsObj.get("level");
        Long currentHP = (Long) cyberpunkCharacteristicsObj.get("currentHP");
        Long maxHP = (Long) cyberpunkCharacteristicsObj.get("maxHP");
        Long infiltration = (Long) cyberpunkCharacteristicsObj.get("infiltration");
        Long technic = (Long) cyberpunkCharacteristicsObj.get("technic");
        Long cool = (Long) cyberpunkCharacteristicsObj.get("cool");
        Long reputation = (Long) cyberpunkCharacteristicsObj.get("reputation");
        Long noise = (Long) cyberpunkCharacteristicsObj.get("noise");
        Long experience = (Long) cyberpunkCharacteristicsObj.get("experience");
        Long yen = (Long) cyberpunkCharacteristicsObj.get("yen");
        Long experienceToNextLevel = (Long) cyberpunkCharacteristicsObj.get("experienceToNextLevel");

        cyberpunkCharacteristics = new CyberpunkCharacteristic(
                level,
                currentHP,
                maxHP,
                infiltration,
                technic,
                cool,
                reputation,
                noise,
                experience,
                yen,
                experienceToNextLevel);

        return cyberpunkCharacteristics;
    }

    private FantasyCharacteristic fantasyCharacteristicFromJSON(JSONObject fantasyCharacteristicsObj) {
        FantasyCharacteristic fantasyCharacteristics = null;

        Long level = (Long) fantasyCharacteristicsObj.get("level");
        Long currentHP = (Long) fantasyCharacteristicsObj.get("currentHP");
        Long maxHP = (Long) fantasyCharacteristicsObj.get("maxHP");
        Long manaPoints = (Long) fantasyCharacteristicsObj.get("manaPoints");
        Long maxManaPoints = (Long) fantasyCharacteristicsObj.get("maxManaPoints");
        Long strength = (Long) fantasyCharacteristicsObj.get("strength");
        Long dexterity = (Long) fantasyCharacteristicsObj.get("dexterity");
        Long intelligence = (Long) fantasyCharacteristicsObj.get("intelligence");
        Long willpower = (Long) fantasyCharacteristicsObj.get("willpower");
        Long reputation = (Long) fantasyCharacteristicsObj.get("reputation");
        Long gold = (Long) fantasyCharacteristicsObj.get("gold");
        Long encumbrance = (Long) fantasyCharacteristicsObj.get("encumbrance");
        Long maxEncumbrance = (Long) fantasyCharacteristicsObj.get("maxEncumbrance");
        Long experience = (Long) fantasyCharacteristicsObj.get("experience");
        Long experienceToNextLevel = (Long) fantasyCharacteristicsObj.get("experienceToNextLevel");
        Long royalGuildFactionPoints = (Long) fantasyCharacteristicsObj.get("royalGuildFactionPoints");
        Long mageGuildFactionPoints = (Long) fantasyCharacteristicsObj.get("mageGuildFactionPoints");
        Long thievesGuildFactionPoints = (Long) fantasyCharacteristicsObj.get("mageGuildFactionPoints");
        Long barbarianTribeFactionPoints = (Long) fantasyCharacteristicsObj.get("barbarianTribeFactionPoints");

        fantasyCharacteristics = new FantasyCharacteristic(
                level,
                currentHP,
                maxHP,
                manaPoints,
                maxManaPoints,
                strength,
                dexterity,
                intelligence,
                willpower,
                reputation,
                gold,
                encumbrance,
                maxEncumbrance,
                experience,
                experienceToNextLevel,
                royalGuildFactionPoints,
                mageGuildFactionPoints,
                thievesGuildFactionPoints,
                barbarianTribeFactionPoints);

        return fantasyCharacteristics;
    }

    private PostApocalypseCharacteristic postApocalypseCharacteristicFromJSON(
            JSONObject postApocalypseCharacteristicsObj) {
        PostApocalypseCharacteristic postApocalypseCharacteristics = null;

        Long level = (Long) postApocalypseCharacteristicsObj.get("level");
        Long currentHP = (Long) postApocalypseCharacteristicsObj.get("currentHP");
        Long maxHP = (Long) postApocalypseCharacteristicsObj.get("maxHP");
        Long attack = (Long) postApocalypseCharacteristicsObj.get("attack");
        Long defence = (Long) postApocalypseCharacteristicsObj.get("defence");
        Long speed = (Long) postApocalypseCharacteristicsObj.get("speed");
        Long luck = (Long) postApocalypseCharacteristicsObj.get("luck");
        Long gold = (Long) postApocalypseCharacteristicsObj.get("gold");
        Long experience = (Long) postApocalypseCharacteristicsObj.get("experience");
        Long experienceToNextLevel = (Long) postApocalypseCharacteristicsObj.get("experienceToNextLevel");

        postApocalypseCharacteristics = new PostApocalypseCharacteristic(
                level, currentHP, maxHP, attack, defence, speed, luck, gold, experience, experienceToNextLevel);

        return postApocalypseCharacteristics;
    }

    private Statistic statisticFromJSON(JSONObject statisticObj) {
        Statistic statistic = null;

        Date lastPlayDate = new Date((Long) statisticObj.get("lastPlayDate") * 1000);
        Long daysInGame = (Long) statisticObj.get("daysInGame");
        Long eventsConducted = (Long) statisticObj.get("eventsConducted");
        Long numberOfSuccessfulEvents = (Long) statisticObj.get("numberOfSuccessfulEvents");
        Long maxLevel = (Long) statisticObj.get("maxLevel");
        Long maxExperience = (Long) statisticObj.get("maxExperience");

        statistic = new Statistic(
                lastPlayDate, daysInGame, eventsConducted, numberOfSuccessfulEvents, maxLevel, maxExperience);

        return statistic;
    }
}
