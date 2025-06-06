package test;
import domain.*;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.awt.Color;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;


/*
 * POOBkemonTest
 */
public class POOBkemonTest implements Serializable {
    @Test
    public void shouldSaveGamePre(){
        POOBkemon poobkemon = new POOBkemon();
        TributeEffect paraly  = new TributeEffect("Efecto de paralizar ", "Reduce la velocidad", 2,new HashMap<String,Integer>(){{
            put("Velocity",-50);}});

        //creacion estados de effecto
            StatusEffect Paralyze = new StatusMovil("Paralisis", "Paralisa al pokemon reduciendo su velocidad.", 2,paraly,0.25);
            StatusEffect Freeze = new StatusEffect("congelado","Un Pokémon congelado",10,0.90);
            StatusEffect Sleep = new StatusEffect("Dormido","Un Pokémon dormido no puede realizar ningún movimiento durante su turno",5,1);
            TributeEffect Burn  = new TributeEffect("Quemadura ", "Le inflinge daño al inicio de turno.",2, 
            new HashMap<String, Integer>() {{
                put("Defense", -10);
                put("PS", -30);}}  );
            TributeEffect Poison  = new TributeEffect("veneno ", "Le inflinge daño al inicio de turno.", 3,new HashMap<String, Integer>() {{
                put("Defense", -30);
                put("PS", -90);}});
            TributeEffect Defense = new TributeEffect("Descansar", "Descansa ese turno y recupera sus estadisticas principales.", 1,new HashMap<String, Integer>() {{
                put("Defense", 50);}});
            TributeEffect Regenerate  = new TributeEffect("Regenerar ", "", 1,new HashMap<String, Integer>() {{
                put("PS", 50);}});
            TributeEffect Electrocuted  = new TributeEffect("Electrocutar ", "Le inflinge daño al inicio de turno.", 4,new HashMap<String, Integer>() {{
                put("Attack", -20);
                put("PS", -30);}});
// Efectos de tributo adicionales
            TributeEffect confusionEffect = new TributeEffect("Efecto de confusión", "Puede hacer que el Pokémon se ataque a sí mismo.", 3,
                    new HashMap<String, Integer>() {{
                        put("Accuracy", -20);
                    }});

            TributeEffect flinchEffect = new TributeEffect("Efecto de retroceso", "Impide que el Pokémon actúe en su turno.", 1,
                    new HashMap<String, Integer>() {{
                        put("Priority", -100);
                    }});

            TributeEffect badlyPoisonEffect = new TributeEffect("Efecto de envenenamiento grave", "Causa daño creciente en cada turno.", 5,
                    new HashMap<String, Integer>() {{
                        put("PS", -15);
                        // El daño aumenta cada turno, representado por el tiempo de duración
                    }});

            TributeEffect infatuationEffect = new TributeEffect("Efecto de enamoramiento", "El Pokémon puede no atacar por amor.", 4,
                    new HashMap<String, Integer>() {{
                        put("Accuracy", -50);
                    }});

            TributeEffect leechSeedEffect = new TributeEffect("Efecto de drenadoras", "Drena PS cada turno y los transfiere al oponente.", 8,
                    new HashMap<String, Integer>() {{
                        put("PS", -20);
                    }});

            TributeEffect raiseAttackEffect = new TributeEffect("Aumentar Ataque", "Aumenta considerablemente el ataque del Pokémon.", 3,
                    new HashMap<String, Integer>() {{
                        put("Attack", 80);
                    }});

            TributeEffect raiseDefenseEffect = new TributeEffect("Aumentar Defensa", "Aumenta considerablemente la defensa del Pokémon.", 3,
                    new HashMap<String, Integer>() {{
                        put("Defense", 80);
                    }});

            TributeEffect raiseSpecialEffect = new TributeEffect("Aumentar Especial", "Aumenta el ataque y defensa especial del Pokémon.", 3,
                    new HashMap<String, Integer>() {{
                        put("Special Attack", 50);
                        put("Special Defense", 50);
                    }});

            TributeEffect raiseAttackSpeedEffect = new TributeEffect("Aumentar Ataque y Velocidad", "Aumenta el ataque y la velocidad del Pokémon.", 3,
                    new HashMap<String, Integer>() {{
                        put("Attack", 50);
                        put("Velocity", 50);
                    }});

    // Creación de estados de efecto
            StatusEffect Confusion = new StatusMovil("Confusión", "El Pokémon está confundido y puede dañarse a sí mismo.", 3, confusionEffect, 0.33);
            StatusEffect Flinch = new StatusEffect("Retroceso", "El Pokémon retrocede y no puede moverse este turno.", 1, 1.0);
            StatusEffect BadlyPoison = new StatusMovil("Gravemente Envenenado", "El veneno causa daño creciente cada turno.", 5, badlyPoisonEffect, 0.95);
            StatusEffect Infatuation = new StatusMovil("Enamoramiento", "El Pokémon está enamorado y puede no atacar.", 4, infatuationEffect, 0.50);
            StatusEffect LeechSeed = new StatusMovil("Drenadoras", "Semillas que drenan PS en cada turno y los transfieren al oponente.", 8, leechSeedEffect, 1.0);
            TributeEffect RaiseAttack = new TributeEffect("Aumentar Ataque", "Aumenta considerablemente el ataque.", 3,
                    new HashMap<String, Integer>() {{
                        put("Attack", 80);
                    }});
            TributeEffect RaiseDefense = new TributeEffect("Aumentar Defensa", "Aumenta considerablemente la defensa.", 3,
                    new HashMap<String, Integer>() {{
                        put("Defense", 80);
                    }});
            TributeEffect RaiseSpecial = new TributeEffect("Aumentar Especial", "Aumenta el ataque y defensa especial.", 3,
                    new HashMap<String, Integer>() {{
                        put("Special Attack", 50);
                        put("Special Defense", 50);
                    }});
            TributeEffect RaiseAttackSpeed = new TributeEffect("Aumentar Ataque y Velocidad", "Aumenta el ataque y velocidad.", 3,
                    new HashMap<String, Integer>() {{
                        put("Attack", 50);
                        put("Velocity", 50);
                    }});

            
            //Movimientos con sus efectos
            MovementState paralyze = new MovementState("Paralyze","",30, 30,50, PokemonType.PLANTA,Paralyze, 60, 0);
            MovementState freeze = new MovementState("Congelar","",20, 20,50, PokemonType.HIELO,Freeze, 60, 0);
            MovementState sleep = new MovementState("Dormir","",10, 15,50, PokemonType.NORMAL,Sleep, 60, 0);
            MovementTribute burn = new MovementTribute("Burn", "", 30, 39, 80, PokemonType.FUEGO, Burn, 10);
            MovementTribute poison = new MovementTribute("Super Burn", "", 15, 60, 80, PokemonType.VENENO, Poison, 0);
            MovementTribute defense = new MovementTribute("Defense", "", 25, 0, 100, PokemonType.NORMAL, Defense, 0);
            MovementTribute regenerate = new MovementTribute("Regenerar", "", 25, 0, 100, PokemonType.NORMAL, Regenerate, 0);
            MovementTribute electrocuted = new MovementTribute("Electrocutar", "", 12, 30, 70, PokemonType.ELECTRICO, Electrocuted, 30);
            //Movimiento Normales -> FISICOS -> ESPECIALES?
            Movement hyperBeam = new PhysicalMovement("Hyper Beam","A devastating attack that requires a turn to recharge.",5,150,90,PokemonType.NORMAL,0);
            Movement quickAttack = new PhysicalMovement("Quick Attack","A fast attack that always strikes first.",30,40,100,PokemonType.NORMAL,1);
            Movement earthquake = new PhysicalMovement("Earthqueake","Causes an earthquake that affects all Pokémon on the field.",10,100,100,PokemonType.TIERRA,0);
            Movement psychic = new SpecialMovement("Psychic","A mental attack that can reduce the enemy's special defense.",10,90,100,PokemonType.PSIQUICO,0);
            Movement dragonClaw = new SpecialMovement("Dragon Claw","Strike the enemy with a sharp claw of dragon energy.",15,80,100,PokemonType.DRAGON,0);
            Movement shadowBall = new SpecialMovement("Shadow ball","Throws a ball of dark energy. You can lower the special defense.",15,80,100,PokemonType.FANTASMA,0);
            Movement brickBreak = new PhysicalMovement("Brick break","Break the barriers of Reflection and Light Display.",15,75,100,PokemonType.LUCHA,0);
            Movement surf = new SpecialMovement("Surf","A water attack that hits all Pokémon in battle, except your partner in double battles.",15,90,100,PokemonType.AGUA,0);

            /*MovementState confused = new MovementState("Confundir", "Confunde al oponente y puede atacarse a sí mismo.", 20, 0, 100, PokemonType.PSIQUICO, Confusion, 70, 0);
            MovementState flinch = new MovementState("Intimidar", "Puede hacer retroceder al oponente.", 15, 65, 85, PokemonType.SINIESTRO, Flinch, 30, 0);
            MovementState toxic = new MovementState("Tóxico", "Envenena gravemente al oponente con daño creciente.", 10, 0, 90, PokemonType.VENENO, BadlyPoison, 100, 0);
            MovementState attract = new MovementState("Atracción", "Enamora al oponente del género opuesto.", 15, 0, 100, PokemonType.NORMAL, Infatuation, 100, 0);
            MovementState leechSeed = new MovementState("Drenadoras", "Planta semillas que drenan PS del oponente cada turno.", 10, 0, 90, PokemonType.PLANTA, LeechSeed, 100, 0);
			*/
            MovementTribute swordsDance = new MovementTribute("Danza Espada", "Eleva mucho el ataque.", 20, 0, 100, PokemonType.NORMAL, RaiseAttack, 0);
            MovementTribute ironDefense = new MovementTribute("Defensa Férrea", "Aumenta considerablemente la defensa.", 15, 0, 100, PokemonType.ACERO, RaiseDefense, 0);
            MovementTribute calmMind = new MovementTribute("Paz Mental", "Aumenta ataque especial y defensa especial.", 20, 0, 100, PokemonType.PSIQUICO, RaiseSpecial, 0);
            MovementTribute dragonDance = new MovementTribute("Danza Dragón", "Aumenta ataque y velocidad.", 20, 0, 100, PokemonType.DRAGON, RaiseAttackSpeed, 0);
            MovementTribute willOWisp = new MovementTribute("Fuego Fatuo", "Quema al oponente.", 15, 0, 85, PokemonType.FUEGO, Burn, 100);

            PhysicalMovement closeCombat = new PhysicalMovement("A Bocajarro", "Poderoso ataque que reduce defensas propias.", 5, 120, 100, PokemonType.LUCHA, 0);
            PhysicalMovement outrage = new PhysicalMovement("Enfado", "Ataca durante 2-3 turnos pero confunde al usuario.", 10, 120, 100, PokemonType.DRAGON, 0);
            PhysicalMovement rockSlide = new PhysicalMovement("Avalancha", "Lanza rocas que pueden hacer retroceder.", 10, 75, 90, PokemonType.ROCA, 30);
            PhysicalMovement ironHead = new PhysicalMovement("Cabeza Hierro", "Golpea con cabeza metálica, puede hacer retroceder.", 15, 80, 100, PokemonType.ACERO, 30);
            PhysicalMovement crunch = new PhysicalMovement("Triturar", "Muerde con colmillos afilados, puede bajar defensa.", 15, 80, 100, PokemonType.SINIESTRO, 20);
            PhysicalMovement leafBlade = new PhysicalMovement("Hoja Aguda", "Corta con hojas afiladas, alta prob. de crítico.", 15, 90, 100, PokemonType.PLANTA, 0);
            PhysicalMovement xScissor = new PhysicalMovement("Tijera X", "Corta al oponente en forma de X con guadañas.", 15, 80, 100, PokemonType.BICHO, 0);
            PhysicalMovement playRough = new PhysicalMovement("Carantoña", "Juega rudamente, puede bajar el ataque del rival.", 10, 90, 90, PokemonType.HADA, 10);
            PhysicalMovement poisonJab = new PhysicalMovement("Puya Nociva", "Ataca con tentáculo o brazo tóxico, puede envenenar.", 20, 80, 100, PokemonType.VENENO, 30);
            PhysicalMovement aquaJet = new PhysicalMovement("Acua Jet", "Ataque rápido de agua que siempre golpea primero.", 20, 40, 100, PokemonType.AGUA, 1);
            PhysicalMovement bravebird = new PhysicalMovement("Pájaro Osado", "Ataque temerario que también daña al usuario.", 15, 120, 100, PokemonType.VOLADOR, 0);

    // MOVIMIENTOS ESPECIALES
            SpecialMovement flamethrower = new SpecialMovement("Lanzallamas", "Lanza fuego intenso que puede quemar.", 15, 90, 100, PokemonType.FUEGO, 10);
            SpecialMovement thunderbolt = new SpecialMovement("Rayo", "Descarga eléctrica que puede paralizar.", 15, 90, 100, PokemonType.ELECTRICO, 10);
            SpecialMovement iceBeam = new SpecialMovement("Rayo Hielo", "Rayo congelante que puede congelar al oponente.", 10, 90, 100, PokemonType.HIELO, 10);
            SpecialMovement darkPulse = new SpecialMovement("Pulso Umbrío", "Onda siniestra que puede hacer retroceder.", 15, 80, 100, PokemonType.SINIESTRO, 20);
            SpecialMovement energyBall = new SpecialMovement("Energibola", "Bombardea con energía natural, puede bajar def. especial.", 10, 90, 100, PokemonType.PLANTA, 10);
            SpecialMovement moonblast = new SpecialMovement("Fuerza Lunar", "Ataca con poder lunar, puede bajar ataque especial.", 15, 95, 100, PokemonType.HADA, 30);
            SpecialMovement flashCannon = new SpecialMovement("Foco Resplandor", "Rayo de luz que puede bajar defensa especial.", 10, 80, 100, PokemonType.ACERO, 10);
            SpecialMovement bugBuzz = new SpecialMovement("Zumbido", "Vibración de alas que puede bajar defensa especial.", 10, 90, 100, PokemonType.BICHO, 10);
            SpecialMovement earthPower = new SpecialMovement("Tierra Viva", "La tierra explota bajo el rival, puede bajar def. especial.", 10, 90, 100, PokemonType.TIERRA, 10);
            SpecialMovement dragonPulse = new SpecialMovement("Pulso Dragón", "Onda de choque con forma de dragón.", 10, 85, 100, PokemonType.DRAGON, 0);
            SpecialMovement hurricane = new SpecialMovement("Vendaval", "Poderoso tornado que puede confundir.", 10, 110, 70, PokemonType.VOLADOR, 30);
            SpecialMovement sludgeBomb = new SpecialMovement("Bomba Lodo", "Lanza fango que puede envenenar.", 10, 90, 100, PokemonType.VENENO, 30);
            SpecialMovement focusBlast = new SpecialMovement("Onda Certera", "Concentra energía y libera un golpe, puede bajar def. especial.", 5, 120, 70, PokemonType.LUCHA, 10);
            SpecialMovement hydroPump = new SpecialMovement("Hidrobomba", "Potente chorro de agua a presión.", 5, 110, 80, PokemonType.AGUA, 0);
            SpecialMovement powerGem = new SpecialMovement("Joya de Luz", "Ataca con rayos de luz que parecen joyas.", 20, 80, 100, PokemonType.ROCA, 0);
            SpecialMovement dreameater = new SpecialMovement("Come Sueños", "Absorbe PS de un oponente dormido.", 15, 100, 100, PokemonType.PSIQUICO, 0);

    // MOVIMIENTOS MIXTOS (FÍSICOS/ESPECIALES ÚNICOS)
            Movement solarBeam = new SpecialMovement("Rayo Solar", "Absorbe luz un turno y ataca en el siguiente.", 10, 120, 100, PokemonType.PLANTA, 0);
            Movement fakeOut = new PhysicalMovement("Intimidación", "Siempre ataca primero y hace retroceder, sólo funciona el primer turno.", 10, 40, 100, PokemonType.NORMAL, 100);
            Movement megaDrain = new SpecialMovement("Mega Drenado", "Absorbe la mitad del daño causado.", 15, 40, 100, PokemonType.PLANTA, 0);
            Movement hyperVoice = new SpecialMovement("Vozarrón", "Ataca con un potente grito.", 10, 90, 100, PokemonType.NORMAL, 0);
            Movement thunderPunch = new PhysicalMovement("Puño Trueno", "Puñetazo eléctrico que puede paralizar.", 15, 75, 100, PokemonType.ELECTRICO, 10);
            Movement firePunch = new PhysicalMovement("Puño Fuego", "Puñetazo ardiente que puede quemar.", 15, 75, 100, PokemonType.FUEGO, 10);
            Movement icePunch = new PhysicalMovement("Puño Hielo", "Puñetazo helado que puede congelar.", 15, 75, 100, PokemonType.HIELO, 10);
            Movement drillPeck = new PhysicalMovement("Pico Taladro", "Picotea con pico giratorio a gran velocidad.", 20, 80, 100, PokemonType.VOLADOR, 0);
            Movement stoneedge = new PhysicalMovement("Roca Afilada", "Ataque con piedras puntiagudas, alta prob. de crítico.", 5, 100, 80, PokemonType.ROCA, 0);
            Movement waterPulse = new SpecialMovement("Pulso Agua", "Ondas de agua que pueden confundir.", 20, 60, 100, PokemonType.AGUA, 20);
            Movement gigaDrain = new SpecialMovement("Giga Drenado", "Absorbe la mitad del daño causado.", 10, 75, 100, PokemonType.PLANTA, 0);
            Movement airSlash = new SpecialMovement("Tajo Aéreo", "Corta con aire comprimido, puede hacer retroceder.", 15, 75, 95, PokemonType.VOLADOR, 30);
            Movement zenHeadbutt = new PhysicalMovement("Cabezazo Zen", "Concentra poder psíquico y golpea, puede hacer retroceder.", 15, 80, 90, PokemonType.PSIQUICO, 20);
            Movement shadowClaw = new PhysicalMovement("Garra Umbría", "Ataca con sombra afilada, alta prob. de crítico.", 15, 70, 100, PokemonType.FANTASMA, 0);
            Movement psychoCut = new PhysicalMovement("Psicocorte", "Corta con cuchillas psíquicas, alta prob. de crítico.", 20, 70, 100, PokemonType.PSIQUICO, 0);
            Movement poisonFang = new PhysicalMovement("Colmillo Veneno", "Muerde con colmillos tóxicos, puede envenenar gravemente.", 15, 50, 100, PokemonType.VENENO, 50);

            //movimientos que puede escoger el usuario para un pokemon
            try{
                poobkemon.addMovement(paralyze);
                poobkemon.addMovement(freeze);
                poobkemon.addMovement(sleep);
                poobkemon.addMovement(burn);
                poobkemon.addMovement(poison);
                poobkemon.addMovement(defense);
                poobkemon.addMovement(regenerate);
                poobkemon.addMovement(electrocuted);
                poobkemon.addMovement(hyperBeam);
                poobkemon.addMovement(quickAttack);
                poobkemon.addMovement(earthquake);
                poobkemon.addMovement(psychic);
                poobkemon.addMovement(dragonClaw);
                poobkemon.addMovement(shadowBall);
                poobkemon.addMovement(brickBreak);
                poobkemon.addMovement(surf);
                //poobkemon.addMovement(confused);
                //poobkemon.addMovement(flinch);
                //poobkemon.addMovement(toxic);
                //poobkemon.addMovement(attract);
                //poobkemon.addMovement(leechSeed);

                poobkemon.addMovement(swordsDance);
                poobkemon.addMovement(ironDefense);
                poobkemon.addMovement(calmMind);
                poobkemon.addMovement(dragonDance);
                poobkemon.addMovement(willOWisp);

                poobkemon.addMovement(closeCombat);
                poobkemon.addMovement(outrage);
                poobkemon.addMovement(rockSlide);
                poobkemon.addMovement(ironHead);
                poobkemon.addMovement(crunch);
                poobkemon.addMovement(leafBlade);
                poobkemon.addMovement(xScissor);
                poobkemon.addMovement(playRough);
                poobkemon.addMovement(poisonJab);
                poobkemon.addMovement(aquaJet);
                poobkemon.addMovement(bravebird);

                // Añadir los movimientos especiales
                poobkemon.addMovement(flamethrower);
                poobkemon.addMovement(thunderbolt);
                poobkemon.addMovement(iceBeam);
                poobkemon.addMovement(darkPulse);
                poobkemon.addMovement(energyBall);
                poobkemon.addMovement(moonblast);
                poobkemon.addMovement(flashCannon);
                poobkemon.addMovement(bugBuzz);
                poobkemon.addMovement(earthPower);
                poobkemon.addMovement(dragonPulse);
                poobkemon.addMovement(hurricane);
                poobkemon.addMovement(sludgeBomb);
                poobkemon.addMovement(focusBlast);
                poobkemon.addMovement(hydroPump);
                poobkemon.addMovement(powerGem);
                poobkemon.addMovement(dreameater);

                // Añadir los movimientos mixtos
                poobkemon.addMovement(solarBeam);
                poobkemon.addMovement(fakeOut);
                poobkemon.addMovement(megaDrain);
                poobkemon.addMovement(hyperVoice);
                poobkemon.addMovement(thunderPunch);
                poobkemon.addMovement(firePunch);
                poobkemon.addMovement(icePunch);
                poobkemon.addMovement(drillPeck);
                poobkemon.addMovement(stoneedge);
                poobkemon.addMovement(waterPulse);
                poobkemon.addMovement(gigaDrain);
                poobkemon.addMovement(airSlash);
                poobkemon.addMovement(zenHeadbutt);
                poobkemon.addMovement(shadowClaw);
                poobkemon.addMovement(psychoCut);
                poobkemon.addMovement(poisonFang);

                System.out.println("si sirve");
            }    
            catch(PoobkemonException e){
                System.out.println(e.getMessage());
            }

            //Pokemones con esos movs
            Pokemon venusaur = new Pokemon("Venusaur",100,364,289,328,291,328,284,PokemonType.PLANTA,PokemonType.VENENO,3);
            Pokemon charizard = new Pokemon("Charizard",100,360,293,348,280,295,328,PokemonType.FUEGO,PokemonType.VOLADOR,6);
            Pokemon blastoise = new Pokemon("Blastoise",100,362,291,295,328,339,280,PokemonType.AGUA,null,9);
            Pokemon raichu = new Pokemon("Raichu",100,324,306,306,229,284,350,PokemonType.ELECTRICO,null,26);
            Pokemon nidoking = new Pokemon("Nidoking",100,364,328,295,284,273,295,PokemonType.VENENO,PokemonType.TIERRA,34);
            Pokemon clefable = new Pokemon("Clefable",100,394,251,284,317,328,251,PokemonType.HADA,null,36);
            Pokemon arcanine = new Pokemon("Arcanine",100,384,350,317,295,284,328,PokemonType.FUEGO,null,59);
            Pokemon machamp = new Pokemon("Machamp",100,384,394,251,284,295,229,PokemonType.LUCHA,null,68);
            Pokemon slowbro = new Pokemon("Slowbro",100,394,306,328,339,273,174,PokemonType.AGUA,PokemonType.PSIQUICO,80);
            Pokemon gengar = new Pokemon("Gengar",100,324,251,394,240,273,350,PokemonType.FANTASMA,PokemonType.VENENO,94);
            Pokemon rhydon = new Pokemon("Rhydon",100,414,394,306,262,273,196,PokemonType.TIERRA,PokemonType.ROCA,112);
            Pokemon gyarados = new Pokemon("Gyarados",100,394,383,284,317,328,287,PokemonType.AGUA,PokemonType.VOLADOR,130);
            Pokemon snorlax = new Pokemon("Snorlax",100,524,350,251,251,350,174,PokemonType.NORMAL,null,143);
            Pokemon moltres = new Pokemon("Moltres",100,384,328,317,361,284,328,PokemonType.FUEGO,PokemonType.VOLADOR,146);
            Pokemon dragonite = new Pokemon("Dragonite",100,386,403,328,317,328,284,PokemonType.DRAGON,PokemonType.VOLADOR,149);
            Pokemon typhlosion = new Pokemon("Typhlosion",100,360,293,317,328,306,328,PokemonType.FUEGO,null,157);
            Pokemon feraligatr = new Pokemon("Feraligatr",100,384,339,306,295,284,306,PokemonType.AGUA,null,160);
            Pokemon togetic = new Pokemon("Togetic",100,314,196,284,295,339,196,PokemonType.HADA,PokemonType.VOLADOR,176);
            Pokemon ursaring = new Pokemon("Ursaring",100,424,394,273,251,273,229,PokemonType.NORMAL,null,217);
            Pokemon donphan = new Pokemon("Donphan",100,384,372,240,372,240,218,PokemonType.TIERRA,null,232);
            Pokemon delibird = new Pokemon("Delibird",100,294,229,251,207,207,273,PokemonType.HIELO,PokemonType.VOLADOR,225);
            Pokemon tyranitar = new Pokemon("Tyranitar",100,404,403,317,350,328,243,PokemonType.ROCA,PokemonType.SINIESTRO,248);
            Pokemon blaziken = new Pokemon("Blaziken",100,364,372,284,339,273,328,PokemonType.FUEGO,PokemonType.LUCHA,257);
            Pokemon gardevoir = new Pokemon("Gardevoir",100,340,251,383,251,361,284,PokemonType.PSIQUICO,PokemonType.HADA,282);
            Pokemon slaking = new Pokemon("Slaking",100,504,460,273,328,284,273,PokemonType.NORMAL,null,289);
            Pokemon metagross = new Pokemon("Metagross",100,364,404,317,394,306,262,PokemonType.ACERO,PokemonType.PSIQUICO,376);
            
            Pokemon venusaur1 = new Pokemon("Venusaur",100,364,289,328,291,328,284,PokemonType.PLANTA,PokemonType.VENENO,3);
            Pokemon charizard1 = new Pokemon("Charizard",100,360,293,348,280,295,328,PokemonType.FUEGO,PokemonType.VOLADOR,6);
            Pokemon blastoise1 = new Pokemon("Blastoise",100,362,291,295,328,339,280,PokemonType.AGUA,null,9);
            Pokemon raichu1 = new Pokemon("Raichu",100,324,306,306,229,284,350,PokemonType.ELECTRICO,null,26);
            Pokemon nidoking1 = new Pokemon("Nidoking",100,364,328,295,284,273,295,PokemonType.VENENO,PokemonType.TIERRA,34);
            Pokemon clefable1 = new Pokemon("Clefable",100,394,251,284,317,328,251,PokemonType.HADA,null,36);
            Pokemon arcanine1 = new Pokemon("Arcanine",100,384,350,317,295,284,328,PokemonType.FUEGO,null,59);
            Pokemon machamp1 = new Pokemon("Machamp",100,384,394,251,284,295,229,PokemonType.LUCHA,null,68);
            Pokemon slowbro1 = new Pokemon("Slowbro",100,394,306,328,339,273,174,PokemonType.AGUA,PokemonType.PSIQUICO,80);
            Pokemon gengar1 = new Pokemon("Gengar",100,324,251,394,240,273,350,PokemonType.FANTASMA,PokemonType.VENENO,94);
            Pokemon rhydon1 = new Pokemon("Rhydon",100,414,394,306,262,273,196,PokemonType.TIERRA,PokemonType.ROCA,112);
            Pokemon gyarados1 = new Pokemon("Gyarados",100,394,383,284,317,328,287,PokemonType.AGUA,PokemonType.VOLADOR,130);
            Pokemon snorlax1 = new Pokemon("Snorlax",100,524,350,251,251,350,174,PokemonType.NORMAL,null,143);
            Pokemon moltres1 = new Pokemon("Moltres",100,384,328,317,361,284,328,PokemonType.FUEGO,PokemonType.VOLADOR,146);
            Pokemon dragonite1 = new Pokemon("Dragonite",100,386,403,328,317,328,284,PokemonType.DRAGON,PokemonType.VOLADOR,149);
            Pokemon typhlosion1 = new Pokemon("Typhlosion",100,360,293,317,328,306,328,PokemonType.FUEGO,null,157);
            Pokemon feraligatr1 = new Pokemon("Feraligatr",100,384,339,306,295,284,306,PokemonType.AGUA,null,160);
            Pokemon togetic1 = new Pokemon("Togetic",100,314,196,284,295,339,196,PokemonType.HADA,PokemonType.VOLADOR,176);
            Pokemon ursaring1 = new Pokemon("Ursaring",100,424,394,273,251,273,229,PokemonType.NORMAL,null,217);
            Pokemon donphan1 = new Pokemon("Donphan",100,384,372,240,372,240,218,PokemonType.TIERRA,null,232);
            Pokemon delibird1 = new Pokemon("Delibird",100,294,229,251,207,207,273,PokemonType.HIELO,PokemonType.VOLADOR,225);
            Pokemon tyranitar1 = new Pokemon("Tyranitar",100,404,403,317,350,328,243,PokemonType.ROCA,PokemonType.SINIESTRO,248);
            Pokemon blaziken1 = new Pokemon("Blaziken",100,364,372,284,339,273,328,PokemonType.FUEGO,PokemonType.LUCHA,257);
            Pokemon gardevoir1 = new Pokemon("Gardevoir",100,340,251,383,251,361,284,PokemonType.PSIQUICO,PokemonType.HADA,282);
            Pokemon slaking1 = new Pokemon("Slaking",100,504,460,273,328,284,273,PokemonType.NORMAL,null,289);
            Pokemon metagross1 = new Pokemon("Metagross",100,364,404,317,394,306,262,PokemonType.ACERO,PokemonType.PSIQUICO,376);
                        
            poobkemon.addPokemon(charizard);
            poobkemon.addPokemon(snorlax);
            poobkemon.addPokemon(blastoise);
            poobkemon.addPokemon(venusaur);
            poobkemon.addPokemon(gengar);
            poobkemon.addPokemon(dragonite);
            poobkemon.addPokemon(togetic);
            poobkemon.addPokemon(tyranitar);
            poobkemon.addPokemon(gardevoir);
            poobkemon.addPokemon(metagross);
            poobkemon.addPokemon(donphan);
            poobkemon.addPokemon(machamp);
            poobkemon.addPokemon(delibird);
            poobkemon.addPokemon(raichu);
            poobkemon.addPokemon(nidoking);
            poobkemon.addPokemon(clefable);
            poobkemon.addPokemon(arcanine);
            poobkemon.addPokemon(slowbro);
            poobkemon.addPokemon(rhydon);
            poobkemon.addPokemon(gyarados);
            poobkemon.addPokemon(moltres);
            poobkemon.addPokemon(typhlosion);
            poobkemon.addPokemon(feraligatr);
            poobkemon.addPokemon(ursaring);
            poobkemon.addPokemon(blaziken);
            poobkemon.addPokemon(slaking);

            /* 
            poobkemon.addPokemon(charizard1);
            poobkemon.addPokemon(snorlax1);
            poobkemon.addPokemon(blastoise1);
            poobkemon.addPokemon(venusaur1);
            poobkemon.addPokemon(gengar1);
            poobkemon.addPokemon(dragonite1);
            poobkemon.addPokemon(togetic1);
            poobkemon.addPokemon(tyranitar1);
            poobkemon.addPokemon(gardevoir1);
            poobkemon.addPokemon(metagross1);
            poobkemon.addPokemon(donphan1);
            poobkemon.addPokemon(machamp1);
            poobkemon.addPokemon(delibird1);
            poobkemon.addPokemon(raichu1);
            */

            charizard1.setMovements(new Movement[] {paralyze, burn, quickAttack, dragonClaw, flamethrower, airSlash});
            snorlax1.setMovements(new Movement[] {hyperBeam, dragonClaw, brickBreak, surf});
            blastoise1.setMovements(new Movement[] {surf, psychic, earthquake, hyperBeam, hydroPump, iceBeam});
            venusaur1.setMovements(new Movement[] {paralyze, poison, regenerate, earthquake, gigaDrain});
            gengar1.setMovements(new Movement[] {shadowBall, psychic, poison, sleep, dreameater});
            dragonite1.setMovements(new Movement[] {dragonClaw, earthquake, quickAttack, hyperBeam, outrage, dragonDance});
            togetic1.setMovements(new Movement[] {sleep, psychic, shadowBall, regenerate, moonblast, airSlash});
            tyranitar1.setMovements(new Movement[] {earthquake, burn, brickBreak, hyperBeam, stoneedge, crunch});
            gardevoir1.setMovements(new Movement[] {psychic, sleep, regenerate, shadowBall, moonblast, calmMind});
            metagross1.setMovements(new Movement[] {brickBreak, psychic, earthquake, burn, zenHeadbutt, flashCannon});
            donphan1.setMovements(new Movement[] {earthquake, quickAttack, defense, burn, stoneedge});
            machamp1.setMovements(new Movement[] {brickBreak, quickAttack, hyperBeam, regenerate, closeCombat, rockSlide});
            delibird1.setMovements(new Movement[] {freeze, quickAttack, surf, paralyze, iceBeam, drillPeck});
            raichu1.setMovements(new Movement[] {electrocuted, quickAttack, paralyze, shadowBall, thunderbolt, thunderPunch});
            nidoking1.setMovements(new Movement[] {earthquake, poison, brickBreak, shadowBall});
            clefable1.setMovements(new Movement[] {moonblast, calmMind, psychic, flamethrower});
            arcanine1.setMovements(new Movement[] {flamethrower, crunch, outrage, burn});
            slowbro1.setMovements(new Movement[] {psychic, surf, calmMind, iceBeam});
            rhydon1.setMovements(new Movement[] {earthquake, stoneedge, brickBreak, hyperBeam});
            gyarados1.setMovements(new Movement[] {dragonDance, outrage, crunch, surf});
            moltres1.setMovements(new Movement[] {flamethrower, airSlash, burn, solarBeam});
            typhlosion1.setMovements(new Movement[] {flamethrower, brickBreak, earthquake, calmMind});
            feraligatr1.setMovements(new Movement[] {surf, crunch, hydroPump, iceBeam});
            ursaring1.setMovements(new Movement[] {hyperBeam, brickBreak, crunch, stoneedge});
            blaziken1.setMovements(new Movement[] {flamethrower, brickBreak, closeCombat, thunderbolt});
            slaking1.setMovements(new Movement[] {hyperBeam, earthquake, shadowBall, brickBreak});

            //items
            Item revive = new Revive();
            Item potion = new PsPotion("potion","",PotionType.NORMAL);
            Item superPotion = new SuperPotion("superPotion","A medicinal spray that restores a Pokémon's HP.",PotionType.SUPER);
            Item hyperPotion = new SuperPotion("superPotion","A medicinal spray that restores a Pokémon's HP.",PotionType.HYPER);
            
            Item defenseNormalPotion = new DefensePotion("hyperPotion", "Aumenta la defensa.", PotionType.NORMAL);
            Item attackNormalPotion = new AttackPotion("hyperPotion", "Aumenta el ataque.", PotionType.NORMAL);
            Item psNormalPotion = new PsPotion("potion", "Aumenta la vida.", PotionType.NORMAL);
            Item defenseSuperPotion = new DefensePotion("hyperPotion", "Aumenta la defensa.", PotionType.SUPER);
            Item attackSuperPotion = new AttackPotion("hyperPotion", "Aumenta el ataque.", PotionType.SUPER);
            Item psSuperPotion = new PsPotion("potion", "Aumenta la vida.", PotionType.SUPER);
            Item defenseHyperPotion = new DefensePotion("hyperPotion", "Aumenta la defensa.", PotionType.HYPER);
            Item attackSHyperPotion = new AttackPotion("hyperPotion", "Aumenta el ataque.", PotionType.HYPER);
            Item psHyperPotion = new PsPotion("potion", "Aumenta la vida.", PotionType.HYPER);
            
            poobkemon.addItem(revive);
            poobkemon.addItem(potion);
            poobkemon.addItem(superPotion);
            poobkemon.addItem(hyperPotion);

            poobkemon.addItem(defenseNormalPotion);
            poobkemon.addItem(attackNormalPotion);
            poobkemon.addItem(psNormalPotion);

            poobkemon.addItem(defenseSuperPotion);
            poobkemon.addItem(attackSuperPotion);
            poobkemon.addItem(psSuperPotion);

            poobkemon.addItem(defenseHyperPotion);
            poobkemon.addItem(attackSHyperPotion);
            poobkemon.addItem(psHyperPotion);

            //creacion trainers
            Trainer defensive1 = new DefensiveTrainer("Defensive",new Color(0,1,255));
            Trainer expert = new ExpertTrainer("Expert",new Color(3,0,255));
            Trainer changing1 = new ChangingTrainer("Changing",new Color(0,4,255));
            Trainer attacking = new AttackingTrainer("Attacking",new Color(0,50,255));

            Trainer defensive = new PlayerTrainer("tulio",new Color(0,0,255));
            Trainer changing = new PlayerTrainer("andrew",new Color(255,0,0));

            poobkemon.addTrainer(defensive);
            poobkemon.addTrainer(changing);
            poobkemon.addTrainer(expert);
            poobkemon.addTrainer(attacking);

            poobkemon.addTrainer(changing1);
            poobkemon.addTrainer(defensive1);
        try{
            //inventarios
            //1 denfensive
            Inventory inventarioDefensive = new Inventory(); 
            defensive.setInventory(inventarioDefensive);
            defensive.addPokemon(snorlax1);
            defensive.addPokemon(rhydon1);
            defensive.addPokemon(blastoise1);
            defensive.addPokemon(gyarados1);
            defensive.addPokemon(moltres1);
            defensive.addPokemon(typhlosion1);
            inventarioDefensive.addItem(defenseNormalPotion);
            inventarioDefensive.addItem(psNormalPotion);
            inventarioDefensive.addItem(revive);
            inventarioDefensive.addItem(attackSuperPotion);
            inventarioDefensive.addItem(revive);
            inventarioDefensive.addItem(superPotion);

            //2
            Inventory inventarioChanging = new Inventory();
            changing.setInventory(inventarioChanging);
            changing.addPokemon(clefable1);
            changing.addPokemon(machamp1);
            changing.addPokemon(slaking1);
            changing.addPokemon(feraligatr1);
            changing.addPokemon(ursaring1);
            changing.addPokemon(rhydon1);
            inventarioChanging.addItem(psNormalPotion);
            inventarioChanging.addItem(defenseNormalPotion);
            inventarioChanging.addItem(revive);
            inventarioChanging.addItem(revive);


            //3
            Inventory inventarioChanging1 = new Inventory();
            changing1.setInventory(inventarioChanging1);
            changing1.addPokemon(dragonite1);
            changing1.addPokemon(venusaur1);
            changing1.addPokemon(tyranitar1);
            changing1.addPokemon(gyarados1);
            changing1.addPokemon(moltres1);
            changing1.addPokemon(blaziken1);
            inventarioChanging1.addItem(psNormalPotion);
            inventarioChanging1.addItem(defenseNormalPotion);
            inventarioChanging1.addItem(revive);
            inventarioChanging1.addItem(revive);
            System.out.println("Tarea completada.");
            //4
            Inventory inventarioAttacking = new Inventory();
            attacking.setInventory(inventarioAttacking);
            attacking.addPokemon(machamp1);
            attacking.addPokemon(tyranitar1);
            attacking.addPokemon(dragonite1);
            attacking.addPokemon(blaziken1);
            attacking.addPokemon(gardevoir1);
            attacking.addPokemon(gyarados1);
            inventarioAttacking.addItem(psNormalPotion);
            inventarioAttacking.addItem(defenseNormalPotion);
            inventarioAttacking.addItem(revive);
            inventarioAttacking.addItem(revive);
            //5
            Inventory inventarioExpert = new Inventory();
            expert.setInventory(inventarioExpert);
            expert.addPokemon(venusaur1);
            expert.addPokemon(gyarados1);
            expert.addPokemon(metagross1);
            expert.addPokemon(dragonite1);
            expert.addPokemon(gardevoir1);
            expert.addPokemon(rhydon1);
            inventarioExpert.addItem(psNormalPotion);
            inventarioExpert.addItem(defenseNormalPotion);
            inventarioExpert.addItem(revive);
            inventarioExpert.addItem(revive);

            //6
            Inventory inventarioDefensive1 = new Inventory();
            defensive1.setInventory(inventarioDefensive1);
            defensive1.addPokemon(venusaur1);
            defensive1.addPokemon(slowbro1);
            defensive1.addPokemon(clefable1);
            defensive1.addPokemon(rhydon1);
            defensive1.addPokemon(togetic1);
            defensive1.addPokemon(tyranitar1);
            inventarioDefensive1.addItem(psNormalPotion);
            inventarioDefensive1.addItem(defenseNormalPotion);
            inventarioDefensive1.addItem(revive);
            inventarioDefensive1.addItem(revive);


            System.out.println("Tarea completada.");
            System.out.println("Tarea completada.");
            System.out.println("Tarea completada inventarios.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getClass().getName() + " - " + e.getMessage());
            e.printStackTrace(); 
            fail(); 
        }
        

        try {
            poobkemon.serializateGame();
        } catch (Exception e) {
            System.out.println("Error: " + e.getClass().getName() + " - " + e.getMessage());
            e.printStackTrace();
            fail(); 
        }
    }
    @Test
    public void deserializateGame(){
        try{
            POOBkemon poobkemon = new POOBkemon();
            poobkemon.deserializateGame();
        }
        catch(Exception e){
            System.out.println("Error: " + e.getClass().getName() + " - " + e.getMessage());
            e.printStackTrace();
            fail(); 
        }    
    }
    @Test
    public void shouldTrainerHavePokemons(){
        POOBkemon kemon = new POOBkemon();
        POOBkemon po = kemon.deserializateGame();
        Trainer t1 = po.getTrainer("tulio");
        assertEquals(t1.getInventory().getAlivePokemons().get(0).getLevel(),100);
    }
    
    @Test
    @Ignore("")
    public void shouldDoTheRightMovement(){
        POOBkemon kemon = new POOBkemon();
        POOBkemon po = kemon.deserializateGame();
        Trainer t1 = po.getTrainer("tulio");
        Trainer t2 = po.getTrainer("andrew");
        try{
            t1.setPokemonInUse("Snorlax");
            t2.setPokemonInUse("Clefable");
        }
        catch(PoobkemonException e){ System.out.println(e.getMessage());fail();}

        int psInicial = t2.getPokemonInUse().getPs();

        po.inicializateBattle("tulio", "andrew");
        try{
            po.movementPerformed("Hyper Beam");
            int psFinal = t2.getPokemonInUse().getPs();
            assertNotEquals(psInicial,psFinal); // Es falso ya que el pokemon fue afectado por el movimiento

        }catch (PoobkemonException e){
            fail();
        }
    }
    @Test
    public void shouldChangePokemon(){
        POOBkemon kemon = new POOBkemon();
        POOBkemon po = kemon.deserializateGame();
        Trainer t1 = po.getTrainer("tulio");
        Trainer t2 = po.getTrainer("andrew");
        Pokemon pokemonStart = t2.getPokemonInUse();

        po.inicializateBattle("tulio", "andrew");
        try {
            po.movementPerformed("dragonClaw");
            t2.changePokemon(t2.getInventory().getAlivePokemons().get(1));
            Pokemon pokemonAfter = t2.getPokemonInUse();
            assertNotEquals(pokemonStart, pokemonAfter); // ESTE TEST FUNCIONA YA QUE BLASTOISE TRAS RECIBIR X CANTIDAD DE DAÑO LO CAMBIO POR CHARIZARD
            // POR LO QUE TANTO EL POKEMON INICIAL COMO EL FINAL SON DIFERENTES;
        } catch (Exception e) {
            System.out.println("Los pokemones son iguales, tras la funcionalidad de cambio ");
        }
    }
    @Test
    public void shouldDiePokemon(){
        POOBkemon kemon = new POOBkemon();
        POOBkemon po = kemon.deserializateGame();
        Trainer t1 = po.getTrainer("tulio");
        Trainer t2 = po.getTrainer("andrew");
        po.inicializateBattle("tulio", "andrew");
        try {
            po.movementPerformed(t1.getPokemonInUse().getMovements().get(0).getName());
            po.movementPerformed(t1.getPokemonInUse().getMovements().get(0).getName());
            po.movementPerformed(t1.getPokemonInUse().getMovements().get(0).getName());
            po.movementPerformed(t1.getPokemonInUse().getMovements().get(0).getName());
            po.movementPerformed(t1.getPokemonInUse().getMovements().get(0).getName());
            po.movementPerformed(t1.getPokemonInUse().getMovements().get(0).getName());
            po.movementPerformed(t1.getPokemonInUse().getMovements().get(0).getName());
            po.movementPerformed(t1.getPokemonInUse().getMovements().get(0).getName());
            po.movementPerformed(t1.getPokemonInUse().getMovements().get(0).getName());
            po.movementPerformed(t1.getPokemonInUse().getMovements().get(0).getName());
            po.movementPerformed(t1.getPokemonInUse().getMovements().get(0).getName());
            po.movementPerformed(t1.getPokemonInUse().getMovements().get(0).getName());
            assertNotEquals(t2.getInventory().getPokemons().get("Blastoise").isAlive(), true);
            System.out.println(t2.getPokemonInUse().getName());
        } catch (PoobkemonException e) {
            System.out.println(t2.getPokemonInUse().getName());
        }
        //Tras recibir varios tipos de ataques blastoise muere y si queremos imprimir el pokemon que tendria este entrenador seria el siguiente
        //vivo como es Charizard
    }
    @Test
    public void shouldFightMachineVsMachine(){
        POOBkemon kemon = new POOBkemon();
        POOBkemon po = kemon.deserializateGame();
        Trainer t1 = po.getTrainer("Changing"); //changing
        Trainer t2 = po.getTrainer("Attacking"); //Attacking

        po.inicializateBattle("Changing", "Attacking");

        int psInicial1 = t1.getPokemonInUse().getPs();
        int psInicial2 = t2.getPokemonInUse().getPs();
        try {
            po.movementPerformed(t2.getPokemonInUse().getMovements().get(0).getName());
            int psFinal1 = t1.getPokemonInUse().getPs();
            int psFinal2 = t2.getPokemonInUse().getPs();
            assertNotEquals(psInicial1,psFinal1);
        } catch (PoobkemonException e) {
            fail("Son iguales los ps, no fue exitoso el ataque entre maquinas");
        }
        //Las maquinas escogen respectivamente los mejores movimientos posibles
        //Debido a la prioridad que hay un pokemon se ve afectado o no
        //En este caso el pokemon del entrenador 1 es quien se ve afectado
        //Por lo que no van a ser iguales sus ps iniciales como finales.
    }


}
