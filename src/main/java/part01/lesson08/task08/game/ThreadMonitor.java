package part01.lesson08.task08.game;

import part01.lesson08.task08.exceptions.GameLogicException;

import java.util.ArrayList;



public class ThreadMonitor extends Thread {
    
    private Map gameSessionMap = null;
    private ArrayList<Cell> cells = null;
    /** All cell values are stored in this array before they are simultaneously changed.
     * This array is mainly used for checking that not a single cell changes its value
     * before ThreadMonitor gives a permission to do so. */
    private String cellValues = null;
    private int maxGenerations = 10;
    private int threadsFinishedNextValueCalculation = 0;
    private int threadsFinishedNextValueSet = 0;

    public ThreadMonitor(final Map map) {
        this.gameSessionMap = map;
    }
    
    public final void setMaxGenerations(final int maxGenerations) {
        this.maxGenerations = maxGenerations;
    }
    
    public final void runSimulation() {
        setCellsThreadMonitor();
        startCellThreads();
        computeGenerations();

    }

    private void setCellsThreadMonitor() {
        cells = (ArrayList<Cell>) gameSessionMap.getCells();
        for (Cell cell : cells) {
            cell.setThreadMonitor(this);
        }
    }
    
    private void startCellThreads() {
        for (Cell cell : cells) {
            cell.start();
        }
    }
    
    private synchronized void computeGenerations() {
        /**
         * When this method is called for the first time, cells are calculating their next
         * generation values. */
        for (int i = 1; i <= maxGenerations; i++) {
            // saveCellValues(); // Can be used for testing purposes.
            waitNextGenerationValueCalculations();
            // checkCellValues(); // Can be used for testing purposes.
            
            wakeUpCellThreads();
            waitNextGenerationValueSets();
            
            printGeneration(i);
            
            // Will the loop be executed after this round?
            if (i < maxGenerations) {
                wakeUpCellThreads(); // Cells will start calculating their next generation values.
            }
        }
        
        stopThreads();
        wakeUpCellThreads(); // Cells will check their interrupted status before continuing to the next generation.

    }

    private void saveCellValues() {
        cellValues = "";
        for (Cell cell : cells) {
            cellValues += String.valueOf(cell.getValue());
        }
    }

    private void checkCellValues() {
        String newCellValues = "";
        
        for (Cell cell : cells) {
            newCellValues += String.valueOf(cell.getValue());
        }
        
        if (!newCellValues.contains(cellValues)) {
            throw new GameLogicException("At least one cell has changed it's value before ThreadMonitor "
                    + "gave a permission to do so. The simulation can not continue.");
        }
    }
    
    private void waitNextGenerationValueCalculations() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitNextGenerationValueSets() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void wakeUpCellThreads() {
        for (Cell cell : cells) {
            cell.continueGame();
        }
    }

    private void printGeneration(int generationNumber) {
        System.out.println("---------- " + "Generation" + " " + generationNumber + " ----------");
        gameSessionMap.printMap();
    }
    

    private void stopThreads() {
        for (Cell cell : cells) {
            cell.stopGame();
        }
    }

    /**
     * Cell Thread calls this method when it has finished calculating it's next generation value.
     * If all threads have calculated their next generation value.
     * */
    public final synchronized void threadFinishedNextValueCalculation() {
        threadsFinishedNextValueCalculation++;
        
        if (threadsFinishedNextValueCalculation == cells.size()) {
            threadsFinishedNextValueCalculation = 0;
            notify();
        }
    }
    
    /**
     * Cell Thread calls this method when it has finished setting it's next generation value.
     * If all threads have set their next generation value.
     * */
    public final synchronized void threadFinishedNextValueSet() {
        threadsFinishedNextValueSet++;
        
        if (threadsFinishedNextValueSet == cells.size()) {
            threadsFinishedNextValueSet = 0;
            notify();
        }
    }

}
