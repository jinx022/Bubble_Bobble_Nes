package it.metodologie.bubblebobblenes.manager;

/**
 * Structure of the user profile
 */
public class UserProfile {
    /** Name of the player */
    private String nickname;
    /** Avatar of the player  */
    private String avatarPath;
    /** Number of games played */
    private int gamesPlayed;
    /** Number of times the player has won */
    private int gamesWon;
    /** Number of times the player has lost */
    private int gamesLost;
    /** Number value of the level to play */
    private int currentLevel;
    /** Number score of the game that is playing */
    private int currentScore;
    /** The best result in all the plays */
    private int highScore;

    /**
     * Default constructor initialized to zero for the game statistics
     *
     * @param nickname Name of the player
     * @param avatarPath Avater of the player
     */
    public UserProfile(String nickname, String avatarPath) {
        this(nickname, avatarPath, 0, 0, 0, 1, 0, 0);
    }

    /**
     * Constructor for the new player
     *
     * @param nickname Name of the player
     * @param avatarPath Avatar of the player
     * @param gamesPlayed Number of games played
     * @param gamesWon Number of times the player has won
     * @param gamesLost Number of times the player has lost
     * @param currentLevel Number value of the level to play
     * @param currentScore Number score of the game that is playing
     * @param highScore The best result in all the plays
     */
    public UserProfile(String nickname, String avatarPath, int gamesPlayed, int gamesWon, int gamesLost, int currentLevel, int currentScore, int highScore) {
        this.nickname = nickname;
        this.avatarPath = avatarPath;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.currentLevel = currentLevel;
        this.currentScore = currentScore;
        this.highScore = highScore;
    }

    /**
     * Return the name of the player
     *
     * @return Name of the player
     */
    public String getNickname() { return nickname; }

    /**
     * Set the name of the player
     *
     * @param nickName Name of the player
     */
    public void setNickname(String nickName) { this.nickname = nickName; }

    /**
     * Return the avatar of the player
     *
     * @return Avatar of the player
     */
    public String getAvatarPath() { return avatarPath; }

    /**
     * Set the avatar of the player
     *
     * @param avatarPath Avatar of the player
     */
    public void setAvatarPath(String avatarPath) { this.avatarPath = avatarPath; }

    /**
     * Return the number of games played
     *
     * @return Number of games played
     */
    public int getGamesPlayed() { return gamesPlayed; }

    /**
     * Set the number of games played
     *
     * @param gamesPlayed Number of games played
     */
    public void setGamesPlayed(int gamesPlayed) { this.gamesPlayed = gamesPlayed; }

    /**
     * Return the number of games won
     *
     * @return Number of games won
     */
    public int getGamesWon() { return gamesWon; }

    /**
     * Set the number of games won
     *
     * @param gamesWon Number of games won
     */
    public void setGamesWon(int gamesWon) { this.gamesWon = gamesWon; }

    /**
     * Return the number of games lost
     *
     * @return Number of games lost
     */
    public int getGamesLost() { return gamesLost; }

    /**
     * Set the number of games lost
     *
     * @param gamesLost Number of games lost
     */
    public void setGamesLost(int gamesLost) { this.gamesLost = gamesLost; }

    /**
     * Return the last level played
     *
     * @return Number value of the level to play
     */
    public int getCurrentLevel() { return currentLevel; }

    /**
     * Set the playing level
     *
     * @param currentLevel Number value of the playing level
     */
    public void setCurrentLevel(int currentLevel) { this.currentLevel = currentLevel; }

    /**
     * Return the score of the current play
     *
     * @return Sum of all the points obtained in the levels
     */
    public int getCurrentScore() { return currentScore; }

    /**
     * Set the score of the current play
     *
     * @param score Sum of all the points obtained in the levels
     */
    public void setCurrentScore(int score) { this.currentScore = score; }

    /**
     * Return the highest score obtained in all the plays
     *
     * @return Value of the highest score
     */
    public int getHighScore() { return highScore; }

    /**
     * Set the highest score when a new score is higher than the previous one - aka currentScore
     *
     * @param score Value of the highest score
     */
    public void setHighScore(int score){ this.highScore = score; }
}