package com.sp.igeeti.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.view.View;
import android.widget.ImageView;

import com.sp.igeeti.R;

public class MusicMetaData {
    private String artist,album,genre;
    private Bitmap image;

    public MusicMetaData(String uri){
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(uri);
            try {
                byte[] art = retriever.getEmbeddedPicture();
                if(art != null) image = BitmapFactory.decodeByteArray(art,0,art.length);
                else image = null;
                artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                album = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                genre = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);

            }
            catch (Exception e){
                image = null;
                artist = "Unknown Artist";
                album = "Unknown Album";
                genre = "Unknown Genre";
                e.printStackTrace();
            }
        retriever.release();
    }

    public String getArtist() {
        return artist!=null && !artist.isEmpty()?artist:"Unknown Artist";
    }

    public String getAlbum() {
        return album!=null && !album.isEmpty() ? album : "Unknown Album";
    }

    public String getGenre() {
        return genre != null && !genre.isEmpty() ? genre : "Unknown Genre";
    }

    public static String getArtistName(String  musicFile){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(musicFile);
        String name = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        retriever.release();
        return  name == null || name.isEmpty() ? "Unknown Artist":name;
    }
    public static void setImageIcon(ImageView imageView,String musicFile){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(musicFile);
        imageView.setPadding(0,0,0,0);
        byte[] imageResource = retriever.getEmbeddedPicture();
        if (imageResource != null) imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageResource,0,imageResource.length));
        else {
            imageView.setImageResource(R.drawable.music_note);
            imageView.setPadding(12,12,12,12);
        }
        retriever.release();

    }

    public void setImage(ImageView imageView){
        if(image != null) imageView.setImageBitmap(image);
        else imageView.setImageResource(R.drawable.ic_music);
    }
    public <ViewGroup extends View> void setBackgroundDrawable(ViewGroup view, Context context){
        if (image != null)
        view.setBackground(BlurBuilder.getBlurDrawable(context,image));
        else view.setBackground(
                ColorGenerator.createGradientBottomTop(
                ColorGenerator.getRandomColorInt(168),50
                ));

    }
}
