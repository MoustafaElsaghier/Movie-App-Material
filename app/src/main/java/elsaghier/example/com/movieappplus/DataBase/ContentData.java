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
        /*final int match = sUriMatcher.match(uri);

        switch (match) {
            case Film: {
                return FilmContract.FilmEntry.CONTENT_DIR_TYPE;
            }
            case Film_WITH_ID: {
                return FilmContract.FilmEntry.CONTENT_ITEM_TYPE;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }*/
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
        /*final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numDeleted;
        switch (match) {
            case Film:
                numDeleted = db.delete(
                        FilmContract.FilmEntry.filmTableName, s, strings);
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        FilmContract.FilmEntry.filmTableName + "'");
                break;
            case Film_WITH_ID:
                numDeleted = db.delete(FilmContract.FilmEntry.filmTableName,
                        FilmContract.FilmEntry.id + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        FilmContract.FilmEntry.filmTableName + "'");
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return numDeleted;*/
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        /*final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int numUpdated = 0;

        if (contentValues == null) {
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch (sUriMatcher.match(uri)) {
            case Film: {
                numUpdated = db.update(FilmContract.FilmEntry.filmTableName,
                        contentValues,
                        s,
                        strings);
                break;
            }
            case Film_WITH_ID: {
                numUpdated = db.update(FilmContract.FilmEntry.filmTableName,
                        contentValues,
                        FilmContract.FilmEntry.id + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (numUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;*/
        return 0;
    }
}


