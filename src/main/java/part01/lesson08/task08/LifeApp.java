package part01.lesson08.task08;


import part01.lesson08.task08.game.GameSession;

import java.io.IOException;


public class LifeApp {
    
    private static LifeApp instanceOfThis = null;
    private GameSession gameSession = null;
    
    public static final LifeApp getInstance() {
        if (instanceOfThis == null) {
            instanceOfThis = new LifeApp();
        }
        
        return instanceOfThis;
    }

    public void run(final String inputFile, final int maxGenerations) throws IOException {
        System.out.println("Running simulation...");
        gameSession = new GameSession();
        gameSession.startSimulation(inputFile, maxGenerations);
        System.out.println("Stopping simulation.");
    }
    
}
