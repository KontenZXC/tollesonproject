package swaygames.launcher;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.DownloadBlock;
import java.util.List;
import swaygames.online.R;

public class Downloader extends AppCompatActivity {
    public TextView text_loader, text_percent;
    public RoundCornerProgressBar progress_loader;
    public Fetch fetch;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_loader);

        text_loader = (TextView) findViewById(R.id.loading_text);
        text_percent = (TextView) findViewById(R.id.loading_percent);
        progress_loader = (RoundCornerProgressBar) findViewById(R.id.progress_bar);
    }

    private void StartDownloadFiles(String file_url, String file_name) {
        FetchConfiguration configuration = new FetchConfiguration.Builder(this).setDownloadConcurrentLimit(3).build();
        fetch = Fetch.Impl.getInstance(configuration);

        final Request request = new Request(file_url, file_name);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);

        fetch.removeAll();
        fetch.deleteAll();

        fetch.enqueue(request, updatedRequest -> {

        }, error -> {

        });

        FetchListener fetchListener = new FetchListener() {
            public void onAdded(@NonNull Download download) {

            }

            public void onQueued(@NonNull Download download, boolean b) {
                if (request.getId() == download.getId()) {
                    fetch.resume(request.getId());
                }
            }

            public void onWaitingNetwork(@NonNull Download download) {
                Toast.makeText(Downloader.this, "Нестабильное подключение, проверьте интренет соединения!", Toast.LENGTH_SHORT).show();
                fetch.retry(request.getId());
            }

            public void onCompleted(@NonNull Download download) {
                if(Api.typeLoad == Api.eTypeLoad.FILES) {
                    text_loader.setText("Распаковка файлов...");
                    text_percent.setVisibility(View.INVISIBLE);
                    progress_loader.setProgress(99);
                } else if(Api.typeLoad == Api.eTypeLoad.CLIENT) {
                    text_loader.setText("Установка лаунчера...");
                    text_percent.setVisibility(View.INVISIBLE);
                    progress_loader.setProgress(99);
                } else if(Api.typeLoad == Api.eTypeLoad.UPDATE) {
                    text_loader.setText("Распаковка обновления...");
                    text_percent.setVisibility(View.INVISIBLE);
                    progress_loader.setProgress(99);
                }
            }

            public void onError(@NonNull Download download, @NonNull Error error, @Nullable Throwable throwable) {
                fetch.retry(request.getId());
            }

            public void onDownloadBlockUpdated(@NonNull Download download, @NonNull DownloadBlock downloadBlock, int i) {

            }

            public void onStarted(@NonNull Download download, @NonNull List<? extends DownloadBlock> list, int i) {
                text_loader.setText("Подготовка файлов...");
            }

            public void onProgress(@NonNull Download download, long l, long l1) {
                text_percent.setText(download.getProgress() + "%");

                if(Api.typeLoad == Api.eTypeLoad.FILES) {
                    text_loader.setText("Загрузка файлов игры...");
                } else if(Api.typeLoad == Api.eTypeLoad.CLIENT) {
                    text_loader.setText("Загрузка игрового клиента...");
                } else if(Api.typeLoad == Api.eTypeLoad.UPDATE) {
                    text_loader.setText("Загрузка обновления...");
                }

                progress_loader.setProgress(download.getProgress());
            }

            public void onPaused(@NonNull Download download) {
                fetch.pause(request.getId());
            }

            public void onResumed(@NonNull Download download) {
                fetch.pause(request.getId());
            }

            public void onCancelled(@NonNull Download download) {

            }

            public void onRemoved(@NonNull Download download) {

            }

            public void onDeleted(@NonNull Download download) {

            }
        };

        fetch.addListener(fetchListener);
    }
}
