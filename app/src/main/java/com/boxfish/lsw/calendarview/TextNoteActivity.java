package com.boxfish.lsw.calendarview;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.boxfish.lishaowei.Note;
import com.boxfish.lishaowei.NoteDao;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;


/**
 * Created by lishaowei on 16/12/10
 */
public class TextNoteActivity extends ListActivity {
    private Cursor cursor;
    private EditText editText;
    public static final String TAG = "TextNoteActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_note);
        String textColumn = NoteDao.Properties.Text.columnName;
        String orderBy = textColumn + " COLLATE LOCALIZED ASC";
        cursor = getDb().query(getNoteDao().getTablename(), getNoteDao().getAllColumns(), null, null, null, null, orderBy);
        String[] from = {textColumn, NoteDao.Properties.Comment.columnName};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from,
                to);
        setListAdapter(adapter);
        editText = (EditText) findViewById(R.id.editTextNote);

    }

    private NoteDao getNoteDao() {
        // 通过 BaseApplication 类提供的 getDaoSession() 获取具体 Dao
        return ((BaseApplication) this.getApplicationContext()).getDaoSession().getNoteDao();
    }

    public SQLiteDatabase getDb() {
        return ((BaseApplication) this.getApplicationContext()).getDb();
    }

    /**
     * Button 点击的监听事件
     *
     * @param view
     */
    public void onMyButtonClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAdd:
                addNote();
                break;
            case R.id.buttonQuery:
                search();
                break;
            case R.id.buttonDeleteAll:
                deleteAll();
                break;
            default:
                ToastUtils.show(getApplicationContext(), "What's wrong ?");
                break;
        }
    }


    private void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");
        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());
        String title = "LSW";
        if (noteText == null || noteText.equals("")) {
            ToastUtils.show(getApplicationContext(), "Please enter a note to add");
        } else {
//            // 插入操作，简单到只要你创建一个 Java 对象
//            Note note = new Note(null, noteText, comment, new Date());
//            getNoteDao().insert(note);
//            Log.d(TAG, "Inserted new note, ID: " + note.getId());
//            cursor.requery();
            Note note = new Note(null, noteText, comment, new Date(), title);
            getNoteDao().insert(note);
            cursor.requery();
        }

    }

    private void search() {
        String noteText = editText.getText().toString();
        editText.setText("");
        if (noteText == null || noteText.equals("")) {
            ToastUtils.show(getApplicationContext(), "Please enter a note to query");
        } else {
            // Query 类代表了一个可以被重复执行的查询
            Query query = getNoteDao().queryBuilder()
                    .where(NoteDao.Properties.Text.eq(noteText))
                    .orderAsc(NoteDao.Properties.Date)
                    .build();
            // 查询结果以 List 返回
            List notes = query.list();
            ToastUtils.show(getApplicationContext(), "There have " + notes.size() + " records");
        }
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    private void deleteAll() {
        ToastUtils.show(getApplicationContext(), "There have " + getNoteDao().count() + " to delete");
        getNoteDao().deleteAll();
        cursor.requery();
    }

    /**
     * ListView 的监听事件，用于删除一个 Item
     *
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // 删除操作，你可以通过「id」也可以一次性删除所有
        getNoteDao().deleteByKey(id);
//        getNoteDao().deleteAll();
        ToastUtils.show(getApplicationContext(), "Deleted note, ID: " + id);
        Log.d(TAG, "Deleted note, ID: " + id);
        cursor.requery();
    }
}
