package CtrlS;

import engine.GameState;

public class RoundState {
    private final GameState prevState;
    private final GameState currState;
    private final int roundScore;
    private final int roundBulletsShot;
    private final int roundShipsDestroyed;
    private final float roundHitRate;
    private final long roundTime;

    public RoundState(GameState prevState, GameState currState) {
        this.prevState = prevState;
        this.currState = currState;
        this.roundScore = currState.getScore() - prevState.getScore();
        this.roundBulletsShot = currState.getBulletsShot() - prevState.getBulletsShot();
        this.roundShipsDestroyed = currState.getShipsDestroyed() - prevState.getShipsDestroyed();
        this.roundHitRate = roundShipsDestroyed / (float) roundBulletsShot;
        this.roundTime = currState.getTime() - prevState.getTime();
    }

    public int getRoundScore() {
        return roundScore;
    }

    public float getRoundHitRate() {
        return roundHitRate;
    }

    public long getRoundTime() {
        return roundTime;
    }

}
