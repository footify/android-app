package eu.epitech.croucour.footify.Game;

/**
 * Created by roucou_c on 16/12/2016.
 */
public interface IGameAddView {
    void addGame(String babyFootEntity_id);

    String getTeam1User1();

    String getTeam1User2();

    String getTeam1Score();

    String getTeam2User1();

    String getTeam2User2();

    String getTeam2Score();

    void finishAddGame();

    void setErrorTeam1User1(int resId);

    void setErrorTeam1User2(int resId);

    void setErrorTeam1Score(int resId);

    void setErrorTeam2User1(int resId);

    void setErrorTeam2User2(int resId);

    void setErrorTeam2Score(int resId);
}
