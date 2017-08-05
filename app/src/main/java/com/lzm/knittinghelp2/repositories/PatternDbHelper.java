package com.lzm.knittinghelp2.repositories;

import android.content.Context;
import android.database.Cursor;

import com.lzm.knittinghelp2.db.DbHelper;
import com.lzm.knittinghelp2.domain.Pattern;

import java.util.Date;

public class PatternDbHelper extends DbHelper {

    public static final String NAME = "name";
    public static final String CONTENT = "content";
    public static final String ACTIVE_SECTION_ID = "active_section_id";

    public static final String[] KEYS = {NAME, CONTENT, ACTIVE_SECTION_ID};

    public PatternDbHelper(Context context) {
        super(context);
    }


    private Pattern setDatos(Cursor c) {
        long id = c.getLong((c.getColumnIndex(ID)));
        long creationDate = c.getLong(c.getColumnIndex(CREATION_DATE));
        long updateDate = c.getLong(c.getColumnIndex(UPDATE_DATE));
        String name = c.getString(c.getColumnIndex(NAME));
        String content = c.getString(c.getColumnIndex(CONTENT));
        long activeSectionId = c.getLong(c.getColumnIndex(ACTIVE_SECTION_ID));

        Pattern pattern = new Pattern(id, name, content);
        pattern.setCreationDate(new Date(creationDate));
        pattern.setUpdateDate(new Date(updateDate));
//        pattern.setActiveSectionId(activeSectionId);

        return pattern;
    }
}
