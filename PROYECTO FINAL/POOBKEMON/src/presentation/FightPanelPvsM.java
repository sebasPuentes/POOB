package presentation;

import domain.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Panel especializado para mostrar batallas entre un jugador humano y una máquina.
 */
public class FightPanelPvsM extends FightsPanel {
    private POOBkemonGUI gui;
    private Timer machineActionTimer;
    private final int MACHINE_ACTION_DELAY = 1500; // ms

    /**
     * Constructor del panel de batalla entre jugador y máquina.
     *
     * @param gui Referencia a la GUI principal
     */
    public FightPanelPvsM(POOBkemonGUI gui) {
        super(gui);
        this.gui = gui;
        initializeMachineActionTimer();
    }

    @Override
    public void startBattle(String player1, String player2) {
        super.startBattle(player1, player2);
        // Si es turno de la máquina, iniciar su acción automáticamente
        if (inBattle && mainGUI.pooBkemon.getCurrentTrainer().getNombre().equals(mainGUI.player2)) {
            machineActionTimer.setInitialDelay(1500);
            machineActionTimer.restart();
        }
    }
    
    /**
     * Inicializa el timer para las acciones de la máquina
     */
    private void initializeMachineActionTimer() {
        machineActionTimer = new Timer(MACHINE_ACTION_DELAY, e -> {
            performMachineAction();
        });
        machineActionTimer.setRepeats(false);
    }
    
    /**
     * Ejecuta la acción de la máquina
     */
    private void performMachineAction() {
        try {
            Trainer machineTrainer = mainGUI.pooBkemon.getCurrentTrainer();
            if (machineTrainer == null || !(machineTrainer instanceof MachineTrainer)) {
                System.out.println("El oponente no es una máquina válida o no es su turno");
                return;
            }
            Pokemon playerPokemon = mainGUI.pooBkemon.getOpponentTrainer().getActivePokemon();
            Movement machineMove = ((MachineTrainer)machineTrainer).decide(playerPokemon);
            if (machineMove != null) {
                showMessage(machineTrainer.getNombre() + " usa " + machineMove.getName() + " con " + 
                        machineTrainer.getActivePokemon().getName());
                mainGUI.pooBkemon.useMovement(machineMove.getName());
                updateBattlePokemonInfo();

                try {
                    mainGUI.pooBkemon.afterAction();
                    updateTurnInfo();
                    checkGameOver();
                } catch (POOBkemonException e) {
                    if (e.getMessage().equals(POOBkemonException.DEAD_POKEMON)) {
                        handleMachineDeadPokemon();
                    } else {
                        showMessage("Error: " + e.getMessage());
                    }
                }
            } else {
                // Si no tiene movimiento disponible, pasar turno
                showMessage(machineTrainer.getNombre() + " no puede realizar ninguna acción");
                try {
                    mainGUI.pooBkemon.afterAction();
                    updateTurnInfo();
                    checkGameOver();
                } catch (POOBkemonException e) {
                    showMessage("Error: " + e.getMessage());
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error en la acción de la máquina: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Maneja el caso cuando un Pokémon de la máquina es derrotado
     */
    private void handleMachineDeadPokemon() {
        try {
            Trainer machineTrainer = mainGUI.pooBkemon.getCurrentTrainer();
            showMessage("¡El Pokémon de " + machineTrainer.getNombre() + " ha sido derrotado!");
            ArrayList<String> alivePokemon = new ArrayList<>();
            TreeMap<String, Pokemon> pokemons = machineTrainer.getInventory().getPokemons();
            for (Pokemon pokemon : pokemons.values()) {
                if (pokemon.getCurrentPs() > 0) {
                    alivePokemon.add(pokemon.getName());
                }
            }
            if (!alivePokemon.isEmpty()) {
                int randomIndex = (int) (Math.random() * alivePokemon.size());
                String nextPokemon = alivePokemon.get(randomIndex);
                
                Timer delayTimer = new Timer(1000, e -> {
                    try {
                        showMessage(machineTrainer.getNombre() + " cambia a " + nextPokemon);
                        mainGUI.pooBkemon.changePokemon(nextPokemon);
                        updateBattlePokemonInfo();
                        
                        Timer advanceTimer = new Timer(1000, evt -> {
                            try {
                                mainGUI.pooBkemon.afterAction();
                                updateTurnInfo();
                                checkGameOver();
                            } catch (POOBkemonException ex) {
                                showMessage("Error: " + ex.getMessage());
                            }
                        });
                        advanceTimer.setRepeats(false);
                        advanceTimer.start();
                        
                    } catch (Exception ex) {
                        showMessage("Error al cambiar Pokémon: " + ex.getMessage());
                    }
                });
                delayTimer.setRepeats(false);
                delayTimer.start();
            } else {
                checkGameOver();
            }
            
        } catch (Exception e) {
            showMessage("Error al manejar Pokémon derrotado: " + e.getMessage());
        }
    }
    
    /**
     * Sobrescribe el método para manejar el cambio de turno en el modo PvsM
     */
    @Override
    protected void updateTurnInfo() {
        super.updateTurnInfo();
        if (inBattle && mainGUI.pooBkemon.getCurrentTrainer().getNombre().equals(mainGUI.player2)) {
            machineActionTimer.setInitialDelay(1500);
            machineActionTimer.restart();
        }
    }
    
    /**
     * Finaliza la batalla y restaura el panel a su estado inicial
     */
    @Override
    public void endBattle() {
        super.endBattle();
        if (machineActionTimer != null && machineActionTimer.isRunning()) {
            machineActionTimer.stop();
        }
    }
}
