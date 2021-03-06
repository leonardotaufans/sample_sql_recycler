package com.leontaufan.wuttpad;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    /*
     * SQLite adalah database offline untuk Android. SQLite dapat digunakan sebagai
     * cache server, atau untuk menyimpan data secara luring.
     *
     * SQLiteOpenHelper adalah implementasi yang digunakan untuk membuat SQLite.
     * */

    /* Pemberian nama statis diperlukan untuk mempermudah pemrograman, dan memudahkan debugging
     * jika ada kesalahan pengetikan.
     */

    // Nama database
    private static final String DATABASE_NAME = "PersonDatabase";
    // Database version
    private static final int DATABASE_VERSION = 2;
    // Nama tabel
    private static final String TABLE_PERSON = "person_table";
    // Nama kolom
    private static final String PERSON_ID = "id";
    private static final String PERSON_NAME = "name";
    private static final String PERSON_BIRTH = "dateOfBirth";
    private static final String PERSON_GENDER = "gender";

    private static final String[] columns = {PERSON_NAME, PERSON_BIRTH, PERSON_GENDER};

    /*
     * Sama seperti SQL biasanya (dapat dilihat jika melakukan ekspor pada SQL), hal pertama yang
     * perlu dilakukan adalah membuat tabel terlebih dahulu jika diperlukan.
     */


    public MySQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * SQLiteDatabase.execSQL {@link SQLiteDatabase#execSQL(String)} adalah perintah untuk menjalankan query SQL biasa.
     * String.format {@link String#format(String, Object...)}adalah pengganti dari tanda + (contoh: "CREATE TABLE" + TABLE_NAME +
     * "(" + ... (dan seterusnya), membuat query lebih terbaca.
     * Penjelasan String.format() => https://www.javatpoint.com/java-string-format
     * @param sqLiteDatabase merupakan inheritance dari SQLiteDatabase.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(String.format(
                "CREATE TABLE %s ( %s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT)",
                TABLE_PERSON, PERSON_ID, PERSON_NAME, PERSON_BIRTH, PERSON_GENDER)
        );


    }


    /**
     * Pada onUpgrade, jika ada pembaruan versi database maka tabel akan didrop,
     * kemudian dibuat ulang dengan menjalankan onCreate()
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        onCreate(sqLiteDatabase);

    }


    /**
     * [[addPerson]]
     * Pada create ini, SQLiteDatabase akan meminta database yang bisa ditulis, dalam
     * kasus ini tabel PersonDatabase.
     * <p>
     * ContentValues digunakan untuk menggabungkan dan menyimpan sementara data dari
     * person yang akan dimasukkan. Di sinilah kegunaan dari model class.
     * <p>
     * SQLiteDatabase memiliki method insert() dan tidak membutuhkan query.
     * insert() akan meminta nama tabel. nullColumnHack
     * (agar dapat mengisi kolom dengan nilai null), dan ContentValues
     *
     * @param person digunakan sebagai jembatan untuk pengambilan data.
     */
    public void addPerson(ModelPerson person) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PERSON_ID, person.getId());
        values.put(PERSON_NAME, person.getName());
        values.put(PERSON_BIRTH, person.getDateOfBirth());
        values.put(PERSON_GENDER, person.getGender());

        Log.d("DEBUG_NANI", values.getAsString(PERSON_NAME));
        db.insert(TABLE_PERSON, null, values);
        db.close();

    }

    /**
     * [[getPersonById()]]
     * Pada getPersonById, dijalankan db.query(). db.query() meminta sejumlah data:
     * - Nama tabel (String),
     * - Daftar kolom dalam bentuk ArrayList (lihat di baris 40),
     * - selection (WHERE pada SQL Query);
     * Note:
     * Pada PERSON_ID +  "=?", tanda tanya tersebut akan diisi oleh selectionArgs
     * ini mirip dengan parameterized queries
     * (https://www.ptsecurity.com/ww-en/analytics/knowledge-base/how-to-prevent-sql-injection-attacks)
     * <p>
     * - selectionArgs dalam bentuk ArrayList String,
     * - groupBy (nama kolom), having (function), orderBy (nama kolom), dan limit.
     * <p>
     * Cursor, seperti namanya, akan menunjukkan posisi dimana kursor saat ini pada tabel,
     * dan menyimpan sementara data tersebut.
     * <p>
     * Setelah query dijalankan, maka diperiksa apakah cursor menghasilkan null, dan jika tidak,
     * maka Cursor akan dipindahkan ke daftar paling atas dari tabel.
     * <p>
     * getPersonByName() akan mengembalikan data berupa model class ModelPerson, yang dapat
     * dibaca oleh RecyclerView nantinya.
     *
     * @param id adalah ID yang dimiliki orang tersebut, dan akan berguna untuk melakukan editing atau
     *           membuat layout detail
     * @return akan mengikuti urutan dari yang ada di tabel. Hati-hati untuk tidak melakukan kesalahan
     * input atau tertukar posisinya di sini.
     */
    public ModelPerson getPersonById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.query(
                TABLE_PERSON,
                columns,
                PERSON_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        db.close();
        cursor.close();

        return new ModelPerson(
                cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));


    }

    // Read semua data

    /**
     * Sebelumnya pada getPersonByName(), db.query() meminta sejumlah data, termasuk selection
     * dan selectionArgs. Tetapi, jika kedua data tersebut diberikan null, maka semua data akan
     * diambil oleh Cursor.
     * <p>
     * Oleh karena itu, dibutuhkan penampungan data sementara berupa List ModelPerson, atau
     * kumpulan dari ModelPerson.
     * <p>
     * Setelah memeriksa apakah Cursor menghasilkan null, cursor akan dipindahkan ke paling
     * pertama. Lalu, do-while digunakan untuk, pada dasarnya, melakukan getPersonByName secara
     * berulang-ulang hingga cursor tidak menemukan data lagi.
     * <p>
     * Kemudian cursor akan meletakkan data tersebut pada List ModelPerson bernama personList
     * untuk kemudian di-return.
     */
    public List<ModelPerson> getAllPerson() {
        SQLiteDatabase db = getReadableDatabase();
        List<ModelPerson> personList = new ArrayList<>();

        @SuppressLint("Recycle")
        Cursor cursor = db.query(
                TABLE_PERSON, columns,
                null, null, null, null, PERSON_NAME, null);

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    Log.d("DEBUG_WUTT", cursor.getString(1));
                    ModelPerson person = new ModelPerson(
                            cursor.getInt(0),
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2)
                    );
                    personList.add(person);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return personList;


    }

    /**
     * {@link #updatePerson} hampir sama dengan db.insert(), tetapi meminta where dan whereArgs seperti
     * getPersonByName().
     */
    public int updatePerson(ModelPerson person) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PERSON_ID, person.getId());
        values.put(PERSON_NAME, person.getName());
        values.put(PERSON_BIRTH, person.getDateOfBirth());
        values.put(PERSON_GENDER, person.getGender());

        db.close();
        return db.update(TABLE_PERSON, values,
                PERSON_ID + " = ? ",
                new String[]{String.valueOf(person.getId())}
        );


    }

    /**
     * db.delete hanya meminta person ID yang harus dihapus, dan sudah ada di ModelPerson.
     */
    public void deletePerson(ModelPerson person) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PERSON,
                PERSON_ID + " = ? ",
                new String[]{String.valueOf(person.getId())});
        db.close();

    }
}
