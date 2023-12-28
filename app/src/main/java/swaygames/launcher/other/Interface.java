package swaygames.launcher.other;

import swaygames.online.gui.models.Actions;
import swaygames.launcher.model.News;
import swaygames.launcher.model.Servers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Interface {

    @GET("http://wh3606.web3.maze-host.ru/zakazi/35/servers.json")
    Call<List<Servers>> getServers();

    @GET("http://wh3606.web3.maze-host.ru/zakazi/35/stories.json")
    Call<List<News>> getNews();

    @GET("http://wh3606.web3.maze-host.ru/donate.json")
    Call<List<Actions>> getActions();
}
