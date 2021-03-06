/*
 * Copyright 2010 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.ning.http.client.extra;

import com.ning.http.client.resumable.ResumableListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A {@link com.ning.http.client.listener.TransferListener} which use a {@link RandomAccessFile} for storing the received bytes.
 */
public class ResumableRandomAccessFileHandler implements ResumableListener {
    private final RandomAccessFile file;
    private final static Logger logger = LoggerFactory.getLogger(ThrottleRequestFilter.class);

    public ResumableRandomAccessFileHandler(RandomAccessFile file) {
        this.file = file;
    }

    /**
     * This method uses the last valid bytes written on disk to position a {@link RandomAccessFile}, allowing
     * resumable file download.
     *
     * @param buffer a {@link ByteBuffer}
     * @throws IOException
     */
    public void onBytesReceived(ByteBuffer buffer) throws IOException {
        file.seek(file.length());
        file.write(buffer.array());
    }

    /**
     * {@inheritDoc}
     */
    public void onAllBytesReceived() {
        if (file != null) {
            try {
                file.close();
            } catch (IOException e) {
                ;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public long length() {
        try {
            return file.length();
        } catch (IOException e) {
            ;
        }
        return 0;
    }

}
