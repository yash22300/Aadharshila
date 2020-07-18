package com.techtitudetribe.aadharshila;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.ybq.android.spinkit.SpinKitView;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.List;

public class PdfViewActivity extends AppCompatActivity {

    private TextView pdfTitle;
    private PDFView pdfView;
    private ProgressBar spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        pdfTitle = (TextView) findViewById(R.id.pdf_title_name);
        pdfView = (PDFView)findViewById(R.id.pdfView);
        spinKitView = findViewById(R.id.pdf_spinkit);

        String title = getIntent().getExtras().getString("title");
        pdfTitle.setText(title);

        String url = getIntent().getExtras().getString("url");
        spinKitView.setVisibility(View.VISIBLE);
        FileLoader.with(this)
                .load(url)
                .fromDirectory("PDFfiles",FileLoader.DIR_EXTERNAL_PRIVATE)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        spinKitView.setVisibility(View.GONE);
                        File pdfUri = response.getBody();
                        pdfView.fromFile(pdfUri)
                                .enableSwipe(true) // allows to block changing pages using swipe
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .defaultPage(1)
                                // allows to draw something on the current page, usually visible in the middle of the screen
                                .onDraw(new OnDrawListener() {
                                    @Override
                                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                                    }
                                })  .onDrawAll(new OnDrawListener() {
                            @Override
                            public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                            }
                        })
                                .onPageError(new OnPageErrorListener() {
                                    @Override
                                    public void onPageError(int page, Throwable t) {
                                        Toast.makeText(PdfViewActivity.this, "Cannot load page" + page, Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .onTap(new OnTapListener() {
                                    @Override
                                    public boolean onTap(MotionEvent e) {
                                        return true;
                                    }
                                }).onRender(new OnRenderListener() {
                            @Override
                            public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                pdfView.fitToWidth(nbPages);
                            }

                        })

                                .spacing(10)
                                .scrollHandle(new DefaultScrollHandle(PdfViewActivity.this))
                                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms
                                .load();
                    }



                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        Toast.makeText(PdfViewActivity.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
                        spinKitView.setVisibility(View.INVISIBLE);
                    }
                });

    }


}
