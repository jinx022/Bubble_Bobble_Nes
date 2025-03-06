package it.metodologie.bubblebobblenes.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Saver and loader of the player infos and statistics
 */
public class UserProfileManager {

    /**
     * File directory where are stored all the profiles
     */
    private static final String PROFILES_DIRECTORY = "profiles";

    /**
     * Save the current info, used when the game is stopped or when a level is completed
     *
     * @param profile Player in use
     */
    public static void saveProfile(UserProfile profile) {
        File directory = new File(PROFILES_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = profile.getNickname() + ".properties";
        File profileFile = new File(directory, fileName);

        Properties properties = new Properties();

        properties.setProperty("nickname", profile.getNickname());
        properties.setProperty("avatarPath", profile.getAvatarPath());
        properties.setProperty("gamesPlayed", String.valueOf(profile.getGamesPlayed()));
        properties.setProperty("gamesWon", String.valueOf(profile.getGamesWon()));
        properties.setProperty("gamesLost", String.valueOf(profile.getGamesLost()));
        properties.setProperty("currentLevel", String.valueOf(profile.getCurrentLevel()));
        properties.setProperty("currentScore", String.valueOf(profile.getCurrentScore()));
        properties.setProperty("highScore", String.valueOf(profile.getHighScore()));

        try (FileOutputStream fos = new FileOutputStream(profileFile)) {
            properties.store(fos, "User Profile Data");
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio del profilo: " + e.getMessage());
        }
    }

    /**
     * Load current profile
     *
     * @return Infos about the profile in use
     */
    public static UserProfile loadProfile() {
        File directory = new File(PROFILES_DIRECTORY);
        if (!directory.exists()) {
            return new UserProfile("no_profile_created", "no_avatar");
        }

        File[] profileFiles = directory.listFiles((dir, name) -> name.endsWith(".properties"));
        if (profileFiles == null || profileFiles.length == 0) {
            return new UserProfile("no_profile_created", "no_avatar");
        }

        File lastModifiedProfile = profileFiles[0];
        for (File file : profileFiles) {
            if (file.lastModified() > lastModifiedProfile.lastModified()) {
                lastModifiedProfile = file;
            }
        }
        return loadFromFile(lastModifiedProfile);
    }

    /**
     * Load a specific profile
     *
     * @param nickname Name of the user to load
     * @return Infos about the user selected
     */
    public static UserProfile loadProfile(String nickname) {
        if (!profileExists(nickname)) {
            return new UserProfile("no_profile_created", "no_avatar");
        }

        String fileName = nickname + ".properties";
        return loadFromFile(new File(PROFILES_DIRECTORY, fileName));
    }

    /**
     * Load the actual file
     *
     * @param profileFile File stored in the directory
     * @return Return a new UserProfile with the loaded properties
     */
    private static UserProfile loadFromFile(File profileFile) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(profileFile)) {
            properties.load(fis);

            return new UserProfile(
                    properties.getProperty("nickname", "no_profile_created"),
                    properties.getProperty("avatarPath", "no_avatar"),
                    Integer.parseInt(properties.getProperty("gamesPlayed", "0")),
                    Integer.parseInt(properties.getProperty("gamesWon", "0")),
                    Integer.parseInt(properties.getProperty("gamesLost", "0")),
                    Integer.parseInt(properties.getProperty("currentLevel", "1")),
                    Integer.parseInt(properties.getProperty("currentScore", "0")),
                    Integer.parseInt(properties.getProperty("highScore", "0"))
            );
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento del profilo: " + e.getMessage());
            return new UserProfile("no_profile_created", "no_avatar");
        }
    }

    /**
     * Check if a user has already the name choosen
     *
     * @param nickname Name to check
     * @return True if already exist, False otherwise
     */
    public static boolean profileExists(String nickname) {
        File directory = new File(PROFILES_DIRECTORY);
        if (!directory.exists()) {
            return false;
        }

        String fileName = nickname + ".properties";
        return new File(directory, fileName).exists();
    }
}