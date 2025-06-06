package src.presentation;

import src.domain.Movement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * Panel para seleccionar movimientos para los Pokémon
 * @author Julian Lopez, David Puentes
 * @version 1.0 (2025-05-08)
 */
public class MovementsSelectionPanel extends JPanel {

    private JPanel contentPanel;
    private JPanel controlPanel;
    private JLabel titleLabel;
    private JButton saveButton;
    private JButton backButton;
    private JButton nextPokemonButton;
    private JButton prevPokemonButton;

    private JPanel movementListPanel;
    private JPanel selectedMovesPanel;
    private JLabel pokemonNameLabel;
    private JLabel pokemonImageLabel;

    private List<String> playerPokemons;
    private List<String> opponentPokemons;
    private Map<String, List<String>> pokemonMovements;

    private Map<String, Map<String, Object>> availableMovements;
    private List<JToggleButton> movementButtons;
    private List<String> selectedMoves;

    private POOBkemonGUI mainGUI;
    private int currentPokemonIndex = 0;
    private boolean isSelectingPlayerPokemon = true;
    private final int MAX_MOVES_PER_POKEMON = 4;

    // Colores para la interfaz estilo Pokémon
    private final Color PANEL_BACKGROUND = new Color(24, 53, 53);
    private final Color TITLE_BACKGROUND = new Color(0, 100, 100);
    private final Color SELECTED_COLOR = new Color(100, 200, 100, 150);
    private final Color TYPE_COLORS = new Color(70, 130, 180);

    public MovementsSelectionPanel() {

    }

}