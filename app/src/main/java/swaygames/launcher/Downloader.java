package swaygames.launcher;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.hzy.libp7zip.P7ZipApi;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.DownloadBlock;

import java.io.File;
import java.util.List;

import swaygames.launcher.activity.MainActivity;
import swaygames.online.R;

public class Downloader extends AppCompatActivity {
    public TextView text_loader, text_percent;
    public RoundCornerProgressBar progress_loader;
    public Fetch fetch;

    public File cache, update, client;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_loader);

        text_loader = (TextView) findViewById(R.id.loading_text);
        text_percent = (TextView) findViewById(R.id.loading_percent);
        progress_loader = (RoundCornerProgressBar) findViewById(R.id.progress_bar);

        cache = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/files.zip"); // название архива с кэшем
        update = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/files-update.zip"); // название архива с кэшем
        client = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/swaygames.apk"); // название архива с кэшем

        if(Api.typeLoad == Api.eTypeLoad.FILES) {
            StartDownloadFiles(Api.cache_link, cache.getPath());
        } else if(Api.typeLoad == Api.eTypeLoad.UPDATE) {
            StartDownloadFiles(Api.update_link, update.getPath());
        } else if(Api.typeLoad == Api.eTypeLoad.CLIENT) {
            StartDownloadFiles(Api.client_link, update.getPath());
        }
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
                    progress_loader.setProgress(100);
                    StartUnZipCache(); // после успешной загрузки начинаем расспаковку скаченого архива (файлов игры)
                } else if(Api.typeLoad == Api.eTypeLoad.CLIENT) {
                    text_loader.setText("Установка лаунчера...");
                    text_percent.setVisibility(View.INVISIBLE);
                    progress_loader.setProgress(100);
                    ShowDialogAcceptInstall("Требуется установка", "Требуется установить обновление игрового клиентв, желаете его установить?");
                    //InstallClient();
                } else if(Api.typeLoad == Api.eTypeLoad.UPDATE) {
                    text_loader.setText("Распаковка обновления...");
                    text_percent.setVisibility(View.INVISIBLE);
                    progress_loader.setProgress(100);
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

    public void StartUnZipCache(){
        String mInputFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/files.zip";
        String mOutputPath = Environment.getExternalStorageDirectory().toString();
        new Thread() {
            public void run() {
                P7ZipApi.executeCommand(String.format("7z x '%s' '-o%s' -aoa", mInputFilePath, mOutputPath));
                Utils.delete(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/files.zip"));
                Utils.delete(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/files.zip"));
                runOnUiThread(() -> {
                    startActivity(new Intent(Downloader.this, MainActivity.class));
                });
            }
        }.start();
    }

    private void InstallClient() {
        File apkFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "swaygames.apk");
        Uri apkUri = FileProvider.getUriForFile(this, "swaygames.online.provider", apkFile);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);

        PackageManager packageManager = getPackageManager();
        if (packageManager != null) {
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "Ошибка установки APK файла, повторите попытку", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("ResourceType")
    private void ShowDialogAcceptInstall(String title, String description) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.launcher_dialog_with_two_buttons_title_description);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(17170445);
        dialog.getWindow().setLayout(-1, -2);

        ((TextView) dialog.findViewById(R.id.text_view_title)).setText(title);
        ((TextView) dialog.findViewById(R.id.text_view_description)).setText(description);

        dialog.findViewById(R.id.button_ok).setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(Downloader.this, R.anim.button_click));
            dialog.hide();
            InstallClient();
        });

        dialog.findViewById(R.id.button_no).setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(Downloader.this, R.anim.button_click));
            onDestroy();
        });

        dialog.show();
    }
}
