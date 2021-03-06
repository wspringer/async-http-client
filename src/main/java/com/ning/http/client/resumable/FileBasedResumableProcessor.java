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
 *
 */
package com.ning.http.client.resumable;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

/**
 * A {@link com.ning.http.client.resumable.ResumableAsyncHandler.ResumableProcessor} which use a {@link RandomAccessFile}
 * to store the download index information.
 */
public class FileBasedResumableProcessor implements ResumableAsyncHandler.ResumableProcessor {

    private final RandomAccessFile watchFile;
    private final String name;

    public FileBasedResumableProcessor(String name, RandomAccessFile watchFile) {
        this.watchFile = watchFile;
        this.name = name;
    }

    public void put(String key, long transferredBytes) {
    }

    public void remove(String key) {
    }

    public void save(Map<String, Long> map) {
    }

    public Map<String, Long> load() {
        HashMap<String, Long> p = new HashMap<String, Long>();
        try {
            p.put(name, watchFile.length());
        } catch (IOException e) {
        }
        return p;
    }
}
