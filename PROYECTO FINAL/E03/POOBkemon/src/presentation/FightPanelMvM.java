package src.presentation;

import src.domain.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;
/**
 * Panel especializado para mostrar batallas automáticas entre máquinas.
 * Extiende el panel de batallas estándar, pero añade funcionalidad para
 * el juego automático entre entrenadores máquina.
 */
public class FightPanelMvM extends FightsPanel {
    private Timer autoPlayTimer;
    // Tiempo para ver que hace las maquinas....
    private final int AUTO_PLAY_DELAY = 2000;
    private JButton pauseResumeButton;
    private boolean battlePaused = false;
    private JSlider speedSlider;
    private JButton exitButton;
    private JPanel controlPanel;

    /**
     * Constructor del panel de batalla entre máquinas.
     *
     * @param gui Referencia a la GUI principal
     */
    public FightPanelMvM(POOBkemonGUI gui) {
        super(gui);
        setupMachineBattleControls();
    }

    /**
     * Configura los controles específicos para batallas automáticas
     */
    private void setupMachineBattleControls() {
        controlPanel = new JPanel();
        controlPanel.setOpaque(false);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
        pauseResumeButton = createOptionButton("Pausar");
        pauseResumeButton.addActionListener(e -> togglePause());
        // Control deslizante para la velocidad
        speedSlider = new JSlider(JSlider.HORIZONTAL, 500, 3000, AUTO_PLAY_DELAY);
        speedSlider.setInverted(true); // Valores más bajos = más rápido
        speedSlider.setMajorTickSpacing(500);
        speedSlider.setMinorTickSpacing(250);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setOpaque(false);
        speedSlider.setForeground(Color.WHITE);
        speedSlider.setPreferredSize(new Dimension(200, 40));
        speedSlider.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Velocidad",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 12),
                Color.WHITE));

        speedSlider.addChangeListener(e -> {
            if (!speedSlider.getValueIsAdjusting()) {
                updateBattleSpeed(speedSlider.getValue());
            }
        });
        exitButton = createOptionButton("Salir");
        exitButton.addActionListener(e -> confirmExit());
        controlPanel.add(pauseResumeButton);
        controlPanel.add(speedSlider);
        controlPanel.add(exitButton);
        add(controlPanel);
        //Iniciar temporizador ...
        initializeAutoPlayTimer();
    }

    /**
     * Inicializa el temporizador para el juego automático entre máquinas
     */
    private void initializeAutoPlayTimer() {
        autoPlayTimer = new Timer(AUTO_PLAY_DELAY, e -> {
            if (inBattle && !mainGUI.pooBkemon.isGameOver()) {
                performAIAction();
            } else {
                autoPlayTimer.stop();
            }
        });
        autoPlayTimer.setRepeats(true);
    }

    /**
     * Actualiza la posición de los controles en la pantalla
     */
    @Override
    protected void repositionButtons() {
        super.repositionButtons();

        // Posicionar el panel de control en la parte inferior
        if (controlPanel != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int controlPanelWidth = 500;
            int controlPanelHeight = 70;
            int controlPanelX = (panelWidth - controlPanelWidth) / 2;
            int controlPanelY = panelHeight - controlPanelHeight - 80;

            controlPanel.setBounds(controlPanelX, controlPanelY, controlPanelWidth, controlPanelHeight);
        }
    }

    /**
     * Inicia la batalla entre máquinas
     */
    @Override
    public void startBattle(String player1Name, String player2Name) {
        super.startBattle(player1Name, player2Name);
        showMessage("¡Batalla automática entre máquinas! Observa cómo juegan.");
        disableUserControls();
        Timer delayTimer = new Timer(1000, e -> startAutoPlay());
        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    /**
     * Deshabilita los controles de usuario durante una batalla automática
     */
    private void disableUserControls() {
        for (Component comp : getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                if (button != pauseResumeButton && button != exitButton) {
                    button.setEnabled(false);
                }
            }
        }
    }

    /**
     * Alterna entre pausar y reanudar la batalla automática
     */
    private void togglePause() {
        if (battlePaused) {
            startAutoPlay();
            pauseResumeButton.setText("Pausar");
            showMessage("Batalla reanudada");
        } else {
            pauseAutoPlay();
            pauseResumeButton.setText("Reanudar");
            showMessage("Batalla en pausa");
        }
        battlePaused = !battlePaused;
    }

    /**
     * Inicia el juego automático entre máquinas
     */
    private void startAutoPlay() {
        if (inBattle) {
            autoPlayTimer.start();
        }
    }

    /**
     * Pausa el juego automático entre máquinas
     */
    private void pauseAutoPlay() {
        if (autoPlayTimer != null && autoPlayTimer.isRunning()) {
            autoPlayTimer.stop();
        }
    }

    /**
     * Actualiza la velocidad de la batalla
     *
     * @param delay Tiempo de espera entre acciones (ms)
     */
    private void updateBattleSpeed(int delay) {
        if (autoPlayTimer != null) {
            autoPlayTimer.setDelay(delay);
            showMessage("Velocidad ajustada: " + (4000 - delay) / 30 + "%");
        }
    }

    /**
     * Realiza una acción automática según la IA del entrenador actual
     */
    private void performAIAction() {
        try {
            if (!inBattle || mainGUI.pooBkemon.isGameOver()) {
                autoPlayTimer.stop();
                return;
            }
            Trainer currentTrainer = mainGUI.pooBkemon.getCurrentTrainer();
            String trainerName = currentTrainer.getNombre();
            showMessage(trainerName + " está pensando...");
            Movement decision = currentTrainer.decide(mainGUI.pooBkemon.getOpponentTrainer().getActivePokemon());
            if (decision != null) {
                Timer delayTimer = new Timer(500, evt -> {
                    try {
                        showMessage(trainerName + " usa " + decision.getName());
                        Timer actionTimer = new Timer(800, actionEvt -> {
                            try {
                                mainGUI.pooBkemon.useMovement(decision.getName());
                                updateBattlePokemonInfo();
                                //Avanza al siguiente turno
                                Timer turnTimer = new Timer(500, turnEvt -> {
                                    try {
                                        mainGUI.pooBkemon.afterAction();
                                        updateTurnInfo();
                                        checkGameOver();
                                    } catch (POOBkemonException ex) {
                                        // Si el Pokémon está muerto, manejarlo
                                        if (ex.getMessage().equals(POOBkemonException.DEAD_POKEMON)) {
                                            handleDeadPokemon();
                                        } else {
                                            showMessage("Error: " + ex.getMessage());
                                        }
                                    }
                                });
                                turnTimer.setRepeats(false);
                                turnTimer.start();
                            } catch (POOBkemonException ex) {
                                showMessage("Error: " + ex.getMessage());
                            }
                        });
                        actionTimer.setRepeats(false);
                        actionTimer.start();
                    } catch (Exception ex) {
                        showMessage("Error: " + ex.getMessage());
                    }
                });
                delayTimer.setRepeats(false);
                delayTimer.start();
            } else {
                Timer skipTimer = new Timer(500, skipEvt -> {
                    try {
                        mainGUI.pooBkemon.afterAction();
                        updateTurnInfo();
                        checkGameOver();
                    } catch (POOBkemonException ex) {
                        if (ex.getMessage().equals(POOBkemonException.DEAD_POKEMON)) {
                            handleDeadPokemon();
                        } else {
                            showMessage("Error: " + ex.getMessage());
                        }
                    }
                });
                skipTimer.setRepeats(false);
                skipTimer.start();
            }
        } catch (Exception e) {
            showMessage("Error en acción automática: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Maneja el caso cuando un Pokémon es derrotado en batalla automática
     */
    private void handleDeadPokemon() {
        try {
            Trainer currentTrainer = mainGUI.pooBkemon.getCurrentTrainer();
            showMessage("El Pokémon de " + currentTrainer.getNombre() + " ha sido derrotado...");
            Timer selectionTimer = new Timer(1000, e -> {
                try {
                    ArrayList<String> alivePokemon = new ArrayList<>();
                    TreeMap<String, Pokemon> pokemons = currentTrainer.getInventory().getPokemons();
                    for (Pokemon pokemon : pokemons.values()) {
                        if (pokemon.getCurrentPs() > 0) {
                            alivePokemon.add(pokemon.getName());
                        }
                    }
                    if (!alivePokemon.isEmpty()) {
                        int randomIndex = (int) (Math.random() * alivePokemon.size());
                        String nextPokemon = alivePokemon.get(randomIndex);
                        showMessage(currentTrainer.getNombre() + " cambia a " + nextPokemon);
                        Timer changeTimer = new Timer(800, changeEvt -> {
                            try {
                                mainGUI.pooBkemon.changePokemon(nextPokemon);
                                updateBattlePokemonInfo();
                                Timer advanceTimer = new Timer(500, advanceEvt -> {
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
                        changeTimer.setRepeats(false);
                        changeTimer.start();
                    } else {
                        checkGameOver();
                    }
                } catch (Exception ex) {
                    showMessage("Error al procesar cambio de Pokémon: " + ex.getMessage());
                }
            });
            selectionTimer.setRepeats(false);
            selectionTimer.start();
        } catch (Exception e) {
            showMessage("Error en manejo de Pokémon derrotado: " + e.getMessage());
        }
    }

    /**
     * Verifica si la batalla ha terminado y muestra el resultado
     */
    protected void checkGameOver() {
        if (mainGUI == null || mainGUI.pooBkemon == null || !inBattle) return;
        if (mainGUI.pooBkemon.isGameOver()) {
            if (autoPlayTimer != null && autoPlayTimer.isRunning()) {
                autoPlayTimer.stop();
            }
            String winner = mainGUI.pooBkemon.getWinner();
            if (winner != null) {
                showMessage("¡La batalla ha terminado! " + winner + " es el ganador.");
                Timer resultTimer = new Timer(2000, e -> {
                    JOptionPane.showMessageDialog(this,
                            "¡La batalla ha terminado!\n\nGanador: " + winner,
                            "Fin de la batalla", JOptionPane.INFORMATION_MESSAGE);
                    endBattle();
                    mainGUI.cardLayout.show(mainGUI.contentPanel, "INICIO");
                });
                resultTimer.setRepeats(false);
                resultTimer.start();
            }
        }
    }

    /**
     * Finaliza la batalla actual
     */
    protected void endBattle() {
        inBattle = false;
        battlePaused = false;
        if (autoPlayTimer != null && autoPlayTimer.isRunning()) {
            autoPlayTimer.stop();
        }
        if (mainGUI != null && mainGUI.pooBkemon != null) {
            mainGUI.pooBkemon.leaveGame();
        }
    }

}
