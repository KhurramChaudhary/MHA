package com.example.khurramshahzad.mha;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NoorN on 11/16/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "medical.db";

    // Contacts table name
    private static final String MEDICATION = "medication";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_SID = "sid";
    private static final String KEY_MEDICINE_NAME = "medicine_name";
    private static final String KEY_RECOMMENDED_BY = "recommended_by";

    private static final String KEY_MORNING = "morning";
    private static final String KEY_AFTERNOON = "afternoon";
    private static final String KEY_NIGHT = "night";


    private static final String KEY_MORNING_QUANTITY = "morning_quantity";
    private static final String KEY_AFTERNOON_QUANTITY = "afternoon_quantity";
    private static final String KEY_NIGHT_QUANTITY = "night_quantity";

    private static final String KEY_MORNING_TIME = "morning_time";
    private static final String KEY_AFTERNOON_TIME = "afternoon_time";
    private static final String KEY_NIGHT_TIME = "night_time";

    private static final String KEY_MORNING_COUNTER = "morning_counter";
    private static final String KEY_AFTERNOON_COUNTER = "afternoon_counter";
    private static final String KEY_NIGHT_COUNTER = "night_counter";

    private static final String KEY_COMMENT = "comment";

    private static final String KEY_MISSED ="missed";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableQuery = new StringBuilder("CREATE TABLE ")
                .append(MEDICATION)
                .append(" (")
                .append(KEY_ID).append(" INTEGER PRIMARY KEY").append(',')
                .append(KEY_SID).append(" TEXT ").append(',')
                .append(KEY_MEDICINE_NAME).append(" TEXT ").append(',')
                .append(KEY_RECOMMENDED_BY).append(" TEXT ").append(',')
                .append(KEY_MORNING).append(" BOOLEAN ").append(',')
                .append(KEY_MORNING_QUANTITY).append(" DOUBLE ").append(',')
                .append(KEY_MORNING_TIME).append(" INTEGER ").append(',')
                .append(KEY_MORNING_COUNTER).append(" INTEGER,")
                .append(KEY_AFTERNOON).append(" BOOLEAN ").append(',')
                .append(KEY_AFTERNOON_QUANTITY).append(" DOUBLE ").append(',')
                .append(KEY_AFTERNOON_TIME).append(" INTEGER ").append(',')
                .append(KEY_AFTERNOON_COUNTER).append(" INTEGER,")
                .append(KEY_NIGHT).append(" BOOLEAN ").append(',')
                .append(KEY_NIGHT_QUANTITY).append(" DOUBLE ").append(',')
                .append(KEY_NIGHT_TIME).append(" INTEGER ").append(',')
                .append(KEY_NIGHT_COUNTER).append(" INTEGER,")
                .append(KEY_COMMENT).append(" TEXT,")
                .append(KEY_MISSED).append(" INTEGER")
                .append(" )")
                .toString();

        db.execSQL(createTableQuery);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + MEDICATION);

        // Create tables again
        onCreate(db);
    }

    public boolean addMedicine(Medication medicine) {

        try (SQLiteDatabase db = this.getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(KEY_SID, medicine.getSid());
            values.put(KEY_MEDICINE_NAME, medicine.getMedicineName());
            values.put(KEY_RECOMMENDED_BY, medicine.getRecommendedBy());
            values.put(KEY_MORNING, medicine.isMorning() ? 1 : 0);
            values.put(KEY_MORNING_QUANTITY, medicine.getMorningQuantity());
            values.put(KEY_MORNING_TIME, medicine.getMorningTime());
            values.put(KEY_MORNING_COUNTER, medicine.getMorningCounter());
            values.put(KEY_AFTERNOON, medicine.isAfternoon() ? 1 : 0);
            values.put(KEY_AFTERNOON_QUANTITY, medicine.getAfternoonQuantity());
            values.put(KEY_AFTERNOON_TIME, medicine.getAfternoonTime());
            values.put(KEY_AFTERNOON_COUNTER, medicine.getAfternoonCounter());
            values.put(KEY_NIGHT, medicine.isNight() ? 1 : 0);
            values.put(KEY_NIGHT_QUANTITY, medicine.getNightQuantity());
            values.put(KEY_NIGHT_TIME, medicine.getNightTime());
            values.put(KEY_NIGHT_COUNTER, medicine.getNightCounter());
            values.put(KEY_COMMENT, medicine.getComment());
            values.put(KEY_MISSED,0);
            long rowsAdded = db.insert(MEDICATION, null, values);
            Log.d(getClass().getSimpleName(), MessageFormat.format("{0} row(s) inserted", rowsAdded));
            return rowsAdded > 0;
        } catch (SQLException ex) {
            Log.e(this.getClass().getName(), " Could not add medication", ex);
        }
        return false;
    }

    public Medication getMedicationBySid(int sid) {
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.query(MEDICATION, null, KEY_SID + "=?",
                new String[]{String.valueOf(sid)}, null, null, null, null)) {
            if (cursor != null) {
                cursor.moveToFirst();

                Medication medication = getFromCursor(cursor);
                return medication;
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), " Could not get medication", ex);
        }
        return null;
    }

    public List<Medication> getMedications() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Medication> medications = new ArrayList<>();
        try (Cursor cursor = db.query(MEDICATION, null, null, null, null, null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Medication medication = getFromCursor(cursor);
                medications.add(medication);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), " Could not get medications", ex);
        }
        return medications;
    }

    public List<Medication> getMedicationsByTime(String startTime, String endTime) {

        try (SQLiteDatabase db = this.getReadableDatabase()) {
            List<Medication> medications = new ArrayList<>();
            String where = new StringBuilder()
                    .append('(')
                    .append(KEY_MORNING).append(" AND ")
                    .append(KEY_MORNING_TIME).append(" >=? AND ")
                    .append(KEY_MORNING_TIME).append(" <? AND ")
                    .append(KEY_MORNING_COUNTER).append(" > 0")
                    .append(')')
                    .append(" OR ")
                    .append('(')
                    .append(KEY_AFTERNOON).append(" AND ")
                    .append(KEY_AFTERNOON_TIME).append(" >=? AND ")
                    .append(KEY_AFTERNOON_TIME).append(" <? AND ")
                    .append(KEY_AFTERNOON_COUNTER).append(" > 0")
                    .append(')')
                    .append(" OR ")
                    .append('(')
                    .append(KEY_NIGHT).append(" AND ")
                    .append(KEY_NIGHT_TIME).append(" >=? AND ")
                    .append(KEY_NIGHT_TIME).append(" <? AND ")
                    .append(KEY_NIGHT_COUNTER).append(" > 0")
                    .append(')')
                    .append(" AND ")
                    .append(KEY_MISSED).append("< 3")
                    .toString();

            try (Cursor cursor = db.query(MEDICATION, null, where, new String[]{startTime, endTime, startTime, endTime, startTime, endTime}, null, null, null)) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Medication medication = getFromCursor(cursor);
                    medications.add(medication);
                    cursor.moveToNext();
                }
            } catch (Exception ex) {
                Log.e(this.getClass().getName(), " Could not get medications", ex);
            }
            return medications;
        }
    }

    private Medication getFromCursor(final Cursor cursor) {

        Medication medication = new Medication();
        int index = 0;
        medication.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
        medication.setSid(cursor.getString(cursor.getColumnIndex(KEY_SID)));
        medication.setMedicineName(cursor.getString(cursor.getColumnIndex(KEY_MEDICINE_NAME)));
        medication.setRecommendedBy(cursor.getString(cursor.getColumnIndex(KEY_RECOMMENDED_BY)));
        medication.setMorning(cursor.getInt(cursor.getColumnIndex(KEY_MORNING)) == 1 ? true : false);
        medication.setMorningQuantity(cursor.getDouble(cursor.getColumnIndex(KEY_MORNING_QUANTITY)));
        medication.setMorningTime(cursor.getLong(cursor.getColumnIndex(KEY_MORNING_TIME)));
        medication.setMorningCounter(cursor.getInt(cursor.getColumnIndex(KEY_MORNING_COUNTER)));
        medication.setAfternoon(cursor.getInt(cursor.getColumnIndex(KEY_AFTERNOON)) == 1 ? true : false);
        medication.setAfternoonQuantity(cursor.getDouble(cursor.getColumnIndex(KEY_AFTERNOON_QUANTITY)));
        medication.setAfternoonTime(cursor.getLong(cursor.getColumnIndex(KEY_AFTERNOON_TIME)));
        medication.setAfternoonCounter(cursor.getInt(cursor.getColumnIndex(KEY_AFTERNOON_COUNTER)));
        medication.setNight(cursor.getInt(cursor.getColumnIndex(KEY_NIGHT)) == 1 ? true : false);
        medication.setNightQuantity(cursor.getDouble(cursor.getColumnIndex(KEY_NIGHT_QUANTITY)));
        medication.setNightTime(cursor.getLong(cursor.getColumnIndex(KEY_NIGHT_TIME)));
        medication.setNightCounter(cursor.getInt(cursor.getColumnIndex(KEY_NIGHT_COUNTER)));
        medication.setComment(cursor.getString(cursor.getColumnIndex(KEY_COMMENT)));
        medication.setMissed(cursor.getInt(cursor.getColumnIndex(KEY_MISSED)));
        return medication;
    }

    public boolean deleteMedication(Medication medication) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            int rowsDeleted = db.delete(MEDICATION, "id=?", new String[]{medication.getId().toString()});
            return rowsDeleted > 0;
        } catch (SQLException e) {
            Log.e(getClass().getSimpleName(), "Unable to delete medication", e);
        }
        return false;
    }

    public boolean updateMedication(Medication medication) {

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(KEY_SID, medication.getSid());
            values.put(KEY_MEDICINE_NAME, medication.getMedicineName());
            values.put(KEY_RECOMMENDED_BY, medication.getRecommendedBy());
            values.put(KEY_MORNING, medication.isMorning() ? 1 : 0);
            values.put(KEY_MORNING_QUANTITY, medication.getMorningQuantity());
            values.put(KEY_MORNING_TIME, medication.getMorningTime());
            values.put(KEY_MORNING_COUNTER, medication.getMorningCounter());
            values.put(KEY_AFTERNOON, medication.isAfternoon() ? 1 : 0);
            values.put(KEY_AFTERNOON_QUANTITY, medication.getAfternoonQuantity());
            values.put(KEY_AFTERNOON_TIME, medication.getAfternoonTime());
            values.put(KEY_AFTERNOON_COUNTER, medication.getAfternoonCounter());
            values.put(KEY_NIGHT, medication.isNight() ? 1 : 0);
            values.put(KEY_NIGHT_QUANTITY, medication.getNightQuantity());
            values.put(KEY_NIGHT_TIME, medication.getNightTime());
            values.put(KEY_NIGHT_COUNTER, medication.getNightCounter());
            values.put(KEY_COMMENT, medication.getComment());
            values.put(KEY_MISSED,medication.getMissed());
            int rowsUpdate = db.update(MEDICATION, values, "id=?", new String[]{medication.getId().toString()});
            return rowsUpdate > 0;
        } catch (SQLException e) {
            Log.e(getClass().getSimpleName(), "Unable to delete medication", e);
        }
        return false;
    }
}