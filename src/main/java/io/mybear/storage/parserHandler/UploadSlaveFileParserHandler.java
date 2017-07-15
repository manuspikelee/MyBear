package io.mybear.storage.parserHandler;

import io.mybear.storage.StorageDio;
import io.mybear.storage.storageNio.FastTaskInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * Created by jamie on 2017/7/12.
 */
public class UploadSlaveFileParserHandler implements ParserHandler<FastTaskInfo, ByteBuffer> {
    public static final int SIZE = 48;
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadSlaveFileParserHandler.class);

    @Override
    public long handleMetaData(FastTaskInfo con, ByteBuffer nioData) {
        long master_filename = nioData.getLong(0);
        long file_size = nioData.getLong(8);
        byte[] prefix = new byte[6];
        nioData.position(16);
        nioData.get(prefix, 0, 6);
        byte[] ext_name_bs = new byte[6];
        nioData.position(22);
        nioData.get(ext_name_bs, 0, 6);
        return con.length - SIZE;
    }

    @Override
    public void handle(FastTaskInfo con, ByteBuffer nioData) {

    }

    @Override
    public boolean handleEnd(FastTaskInfo con, ByteBuffer nioData) {
        StorageDio.queuePush(con);
        return false;
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
