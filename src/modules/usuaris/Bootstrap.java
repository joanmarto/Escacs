/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.usuaris;

import bootstrap.*;
import java.util.ArrayList;
import java.util.List;
import core.model.chesspieces.Pesa;
import core.model.game.EstatTauler;
import modules.usuaris.model.Partida;
import modules.usuaris.model.Arbitre;
import modules.usuaris.model.Jugador;
import modules.usuaris.model.User;
import modules.usuaris.repository.UsersRepository;
import modules.usuaris.repository.UsersRepositoryImpl;
import modules.usuaris.view.UsersWindow;

/**
 *
 * @author Bernat Galmés Rubert
 */
public class Bootstrap {

    UsersRepository users;
    List<Partida> partides;

    public Bootstrap() throws Exception {
        this.users = loadUsers();
        this.partides = loadGames();
    }

    public void run() {
        java.awt.EventQueue.invokeLater(() -> {
            new UsersWindow(users).setVisible(true);
        });
    }

    private List<Partida> loadGames() throws Exception {
        List<Partida> partides = new ArrayList<>();
        List<DataLoader.GameData> gamesData = DataLoader.gamesLoader();
        for (DataLoader.GameData gameData : gamesData) {
            Jugador j1 = (Jugador) users.findOne(gameData.jugador1);
            Jugador j2 = (Jugador) users.findOne(gameData.jugador2);
            
            Partida partida = new Partida(j1, j2);
            EstatTauler tauler = new EstatTauler(9);
            String[] pieces = gameData.tauler.split(" ");
            for (String piece : pieces) {
                System.out.println(piece);
                String[] posicio = piece.split("-");
                tauler.colocarPesa(posicio[0], Pesa.getPesa(posicio[1], Integer.parseInt(posicio[2])));
            }
            j1.registrarPartida(partida);
            j2.registrarPartida(partida);
            partida.setEstatActual(tauler);
            partides.add(partida);
        }

        return partides;
    }

    private static UsersRepository loadUsers() throws Exception {
        List<User> users = new ArrayList<>();
        List<DataLoader.UserData> usersData = DataLoader.usersLoader();
        for (DataLoader.UserData userData : usersData) {
                        User user;
            switch(userData.tipus){
                case "2":
                    user = new Arbitre(userData.id, userData.nom, userData.correuElectronic, userData.numAccessos);
                    break;
                case "1":
                    user = new User(userData.id, userData.nom, userData.correuElectronic, userData.numAccessos);
                    break;
                case "0":
                    user = new Jugador(userData.id, userData.nom, userData.correuElectronic, userData.numAccessos);
                    break;
                default:
                    throw new Exception("Incorrect user kind");
            }
            users.add(user);
        }

        return new UsersRepositoryImpl(users);
    }
}
