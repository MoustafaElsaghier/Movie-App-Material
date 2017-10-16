package elsaghier.example.com.movieappplus.DataBase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ELSaghier on 10/16/2017.
 */

public class ContentData extends ContentProvider {


    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MyDBHelper mOpenHelper;

    // Codes for the UriMatcher //////
    private static final int Film = 100;
    private static final int Film_WITH_ID = 200;


    private static UriMatcher buildUriMatcher() {
        // Build a UriMatcher by adding a specific code to return based on a match
        // It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FilmContract.CONTENT_AUTHORITY;

        // add a code for each type of URI you want
        matcher.addURI(authority, FilmContract.FilmEntry.filmTableName, Film);
        matcher.addURI(authority, FilmContract.FilmEntry.filmTableName + "/#", Film_WITH_ID);

        return matcher;
    }


    MyDBHelper dbHelper;
    private static final String AUTHORITY = "elsaghier.example.com";

    // create content URIs from the authority by appending path to database table
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/films");


    @Override
    public boolean onCreate() {
        dbHelper = new MyDBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // All Flavors selected
            case Film: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        FilmContract.FilmEntry.filmTableName,
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1);
                return retCursor;
            }
            // Individual flavor based on Id selected
            case Film_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        FilmContract.FilmEntry.filmTableName,
                        strings,
                        FilmContract.FilmEntry.id + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        s1);
                return retCursor;
            }
            default: {
                // By default, we assume a bad URI
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

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
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case Film: {
                long _id = db.insert(FilmContract.FilmEntry.filmTableName, null, contentValues);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    returnUri = FilmContract.FilmEntry.buildFlavorsUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
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
        return numDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
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

        return numUpdated;
    }
}


