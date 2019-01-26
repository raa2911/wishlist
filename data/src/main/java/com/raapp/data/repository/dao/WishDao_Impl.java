package com.raapp.data.repository.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.raapp.data.models.Wish;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class WishDao_Impl implements WishDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfWish;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfWish;

  public WishDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWish = new EntityInsertionAdapter<Wish>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `wish`(`id`,`title`,`privacy`,`link`,`description`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Wish value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        stmt.bindLong(3, value.getPrivacy());
        if (value.getLink() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getLink());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDescription());
        }
      }
    };
    this.__deletionAdapterOfWish = new EntityDeletionOrUpdateAdapter<Wish>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `wish` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Wish value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public void insert(final Wish wish) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfWish.insert(wish);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Wish wish) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfWish.handle(wish);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Wish> getAll() {
    final String _sql = "SELECT * FROM wish";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfPrivacy = CursorUtil.getColumnIndexOrThrow(_cursor, "privacy");
      final int _cursorIndexOfLink = CursorUtil.getColumnIndexOrThrow(_cursor, "link");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final List<Wish> _result = new ArrayList<Wish>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Wish _item;
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        final int _tmpPrivacy;
        _tmpPrivacy = _cursor.getInt(_cursorIndexOfPrivacy);
        final String _tmpLink;
        _tmpLink = _cursor.getString(_cursorIndexOfLink);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _item = new Wish(_tmpId,_tmpTitle,_tmpPrivacy,_tmpLink,_tmpDescription);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Wish loadAllById(final int wishId) {
    final String _sql = "SELECT * FROM wish WHERE uid IS (?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, wishId);
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
