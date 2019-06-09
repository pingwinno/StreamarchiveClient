package com.pingwinno.streamarchive;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class StreamsDataGetter {
    int offset = 0;
    private ObjectMapper mapper = new ObjectMapper();

    public List<StreamMeta> getStreams(int offset) throws IOException {
        return mapper.readValue(new URL("https://storage.streamarchive.net/db/streams/olyashaa?sortingOrder=desc&sortBy=date&skip=" + offset + "&limit=20"),
                new TypeReference<List<StreamMeta>>() {
                });
    }

    public StreamMeta getStream(String uuid) throws IOException {
        List<StreamMeta> streamMetaList = mapper.readValue(new URL("https://storage.streamarchive.net/db/streams/olyashaa/" + uuid),
                new TypeReference<List<StreamMeta>>() {
                });
        return streamMetaList.get(0);
    }
}
