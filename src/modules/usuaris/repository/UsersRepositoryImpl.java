package modules.usuaris.repository;

import java.util.ArrayList;
import java.util.List;
import modules.usuaris.model.Arbitre;
import modules.usuaris.model.Jugador;
import modules.usuaris.model.User;

/**
 *
 * @author Bernat Galmés Rubert
 */
public class UsersRepositoryImpl implements UsersRepository {
    private List<User> users;

    public UsersRepositoryImpl(List<User> users) {
        this.users = users;
    }
    
    @Override
    public User findOne(long id) throws NotFoundException {
          for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new NotFoundException();
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public float[] meanAccessByClass() {
        
        float meanAccessosSocis = 0F;
        float meanAccessosArbitres = 0F;
        float meanAccessosJugadors = 0F;
        int numJugadors = 0, numSocis = 0, numArbitres = 0;

        for (User user : this.users) {
            if (user instanceof Arbitre) {
                numArbitres += 1;
                meanAccessosArbitres += user.getNumAccessos();
            } else if (user instanceof Jugador) {
                numJugadors += 1;
                meanAccessosJugadors += user.getNumAccessos();

            } else {
                numSocis += 1;
                meanAccessosSocis += user.getNumAccessos();

            }
        }

        meanAccessosArbitres /= numArbitres;
        meanAccessosSocis /= numSocis;
        meanAccessosJugadors /= numJugadors;

        return new float[]{
            meanAccessosArbitres, numSocis, numJugadors
        };
    }

    @Override
    public String findUserWithMaxAccess() {
        long maxAccessos = 0;
        User userMaxAcesos = null;
        for (User user : this.users) {
            if (user.getNumAccessos() > maxAccessos) {
                maxAccessos = user.getNumAccessos();
                userMaxAcesos = user;
            }
        }
        
        return userMaxAcesos.getNom();
    }

    @Override
    public List<String> findUsersWithNoAccess() {
        List<String> usersMaiAccess = new ArrayList<>();
        for (User user : this.users) {
            if (user.getNumAccessos() == 0) {
                usersMaiAccess.add(user.getNom());
            }
        }

        return usersMaiAccess;
    }

    @Override
    public String findUserWithMaxWins() {
                long maxGuanyades = 0;
        User userMaxGuanyades = null;
        for (User user : this.users) {
            if (user instanceof Jugador) {
                if (((Jugador) user).nombrePartidesGuanyades() > maxGuanyades) {
                    maxGuanyades = ((Jugador) user).nombrePartidesGuanyades();
                    userMaxGuanyades = user;
                }
            }
        }
        return userMaxGuanyades.getNom();
    }

    
}
