package eu.epitech.croucour.footify.Game;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.BabyFootEntity;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.R;
import mehdi.sakout.fancybuttons.FancyButton;


/**
 * Created by roucou_c on 01/09/2016.
 */
public final class GameActivity extends AppCompatActivity implements IGameAddView {

    private GamePresenter _gamePresenter;
    private Manager _manager;
    private BabyFootEntity _babyFootEntity;
    private MaterialEditText _game_add_team1_user1;
    private MaterialEditText _game_add_team1_user2;
    private MaterialEditText _game_add_team1_score;
    private MaterialEditText _game_add_team2_user1;
    private MaterialEditText _game_add_team2_user2;
    private MaterialEditText _game_add_team2_score;
    private FancyButton _game_add_submit;
    private CoordinatorLayout _coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _manager = Manager.getInstance(getApplicationContext());
        TokenEntity tokenEntity = _manager._tokenManager.select();

        _gamePresenter = new GamePresenter(this, _manager, tokenEntity);
        _babyFootEntity = (BabyFootEntity) getIntent().getSerializableExtra("babyFootEntity");

        setContentView(R.layout.game);

        _game_add_team1_user1 = (MaterialEditText) findViewById(R.id.game_add_team1_user1);
        _game_add_team1_user2 = (MaterialEditText) findViewById(R.id.game_add_team1_user2);
        _game_add_team1_score = (MaterialEditText) findViewById(R.id.game_add_team1_score);

        _game_add_team2_user1 = (MaterialEditText) findViewById(R.id.game_add_team2_user1);
        _game_add_team2_user2 = (MaterialEditText) findViewById(R.id.game_add_team2_user2);
        _game_add_team2_score = (MaterialEditText) findViewById(R.id.game_add_team2_score);

        _game_add_submit = (FancyButton) findViewById(R.id.game_add_submit);
        _game_add_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _gamePresenter.addGame();
            }
        });

        _coordinatorLayout = (CoordinatorLayout) findViewById(R.id.display_snackbar);


        _game_add_team1_user1.setText("user1");
        _game_add_team1_user2.setText("user2");
        _game_add_team2_user1.setText("user3");
        _game_add_team2_user2.setText("user4");
        _game_add_team2_score.setText("1");
        _game_add_team1_score.setText("9");
    }

    @Override
    public void addGame(String babyFootEntity_id) {
        _gamePresenter.addGame();
    }

    @Override
    public String getTeam1User1() {
        return _game_add_team1_user1.getText().toString();
    }

    @Override
    public String getTeam1User2() {
        return _game_add_team1_user2.getText().toString();
    }

    @Override
    public String getTeam1Score() {
        return _game_add_team1_score.getText().toString();
    }

    @Override
    public String getTeam2User1() {
        return _game_add_team2_user1.getText().toString();
    }

    @Override
    public String getTeam2User2() {
        return _game_add_team2_user2.getText().toString();
    }

    @Override
    public String getTeam2Score() {
        return _game_add_team2_score.getText().toString();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return true;
    }

    @Override
    public void finishAddGame() {
        this.finish();
    }

    @Override
    public void setErrorTeam1User1(int resId) {
        _game_add_team1_user1.setError(resId == 0 ? null : getString(resId));
    }

    @Override
    public void setErrorTeam1User2(int resId) {
        _game_add_team1_user2.setError(resId == 0 ? null : getString(resId));
    }

    @Override
    public void setErrorTeam1Score(int resId) {
        _game_add_team1_score.setError(resId == 0 ? null : getString(resId));
    }

    @Override
    public void setErrorTeam2User1(int resId) {
        _game_add_team2_user1.setError(resId == 0 ? null : getString(resId));
    }

    @Override
    public void setErrorTeam2User2(int resId) {
        _game_add_team2_user2.setError(resId == 0 ? null : getString(resId));
    }

    @Override
    public void setErrorTeam2Score(int resId) {
        _game_add_team2_score.setError(resId == 0 ? null : getString(resId));
    }

    @Override
    public void setErrorPseudoNotExist(String pseudo) {

        String team1_user1 = getTeam1User1();
        String team1_user2 = getTeam1User2();
        String team2_user1 = getTeam2User1();
        String team2_user2 = getTeam2User2();

        if (Objects.equals(pseudo, team1_user1)) {
            setErrorTeam1User1(R.string.pseudo_already_use);
        }
        else if (Objects.equals(pseudo, team1_user2)) {
            setErrorTeam1User2(R.string.pseudo_already_use);
        }
        else if (Objects.equals(pseudo, team2_user1)) {
            setErrorTeam2User1(R.string.pseudo_already_use);
        }
        else if (Objects.equals(pseudo, team2_user2)) {
            setErrorTeam2User2(R.string.pseudo_already_use);
        }
        else {
            if (_coordinatorLayout != null) {
                Snackbar snackbar = Snackbar
                        .make(_coordinatorLayout, R.string.error, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }

    @Override
    public BabyFootEntity getBabyFootEntity() {
        return _babyFootEntity;
    }
}