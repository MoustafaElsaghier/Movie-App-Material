package elsaghier.example.com.movieappplus.DataBase;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ELSaghier on 10/16/2017.
 */

public class FilmContract {
    public static final String CONTENT_AUTHORITY = "com.elsaghier.movieAppPlus";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class FilmEntry implements BaseColumns {
        //TableName
        public static final String filmTableName = "Movies";

        //Columns
        public static final String id = "id";
        public static final String imgUrl = "poster_path";
        public static final String overview = "overview";
        public static final String original_title = "original_title";
        public static final String vote_average = "vote_average";
        public static final String release_date = "release_date";
        public static final String backdrop_path = "backdrop_path";
        public static final String isSelected = "isSelected";
        public static final String Generes = "Generes";


        // create content uri
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(filmTableName).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + filmTableName;
        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + filmTableName;

        // for building URIs on insertion
        public static Uri buildFlavorsUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
