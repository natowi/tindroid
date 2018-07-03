package co.tinode.tindroid.db;


import android.database.Cursor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import co.tinode.tinodesdk.Storage;
import co.tinode.tinodesdk.model.Drafty;
import co.tinode.tinodesdk.model.MsgServerData;

/**
 * StoredMessage fetched from the database
 */
public class StoredMessage extends MsgServerData implements Storage.Message {
    public long id;
    public long topicId;
    public long userId;
    public int status;

    public StoredMessage() {
    }

    public StoredMessage(MsgServerData m) {
        topic = m.topic;
        head = m.head;
        from = m.from;
        ts = m.ts;
        seq = m.seq;
        content = m.content;
    }

    public StoredMessage(MsgServerData m, int status) {
        this(m);
        this.status = status;
    }

    public static StoredMessage readMessage(Cursor c) {
        StoredMessage msg = new StoredMessage();

        msg.id = c.getLong(MessageDb.COLUMN_IDX_ID);
        msg.topicId = c.getLong(MessageDb.COLUMN_IDX_TOPIC_ID);
        msg.userId = c.getLong(MessageDb.COLUMN_IDX_USER_ID);
        msg.status = c.getInt(MessageDb.COLUMN_IDX_STATUS);
        msg.from = c.getString(MessageDb.COLUMN_IDX_SENDER);
        msg.ts = new Date(c.getLong(MessageDb.COLUMN_IDX_TS));
        msg.seq = c.getInt(MessageDb.COLUMN_IDX_SEQ);
        msg.content = BaseDb.deserialize(c.getString(MessageDb.COLUMN_IDX_CONTENT));

        return msg;
    }

    public static int readSeqId(Cursor c) {
        return c.getInt(0);
    }

    public boolean isMine() {
        return BaseDb.isMe(from);
    }

    @Override
    public Object getContent() {
        return content;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Map<String, Object> getHeader() {
        return head;
    }
}
