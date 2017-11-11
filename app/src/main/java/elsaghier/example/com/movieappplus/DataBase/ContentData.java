package elsaghier.example.com.movieappplus.DataBase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ELSaghier on 10/16/2017.
 */

public class ContentData extends ContentProvider {

    MyDBHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new MyDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor retCursor;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        retCursor = db.query(FilmContract.FilmEntry.filmTableName, strings, s, strings1, null, null, s1);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long i = db.insert(FilmContract.FilmEntry.filmTableName, null, contentValues);
        return ContentUris.withAppendedId(FilmContract.CONTENT_URI, i);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(FilmContract.FilmEntry.filmTableName, s, strings);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.update(FilmContract.FilmEntry.filmTableName, contentValues, s, strings);
    }
}


