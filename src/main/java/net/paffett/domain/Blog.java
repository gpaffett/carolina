package net.paffett.domain;

import java.util.ArrayList;
import java.util.List;

public class Blog {
    private Author writer;
    private List<Entry> entries = new ArrayList<Entry>();

    public Blog(Author writer) {
            this.writer = writer;
    }

    public void add(Entry entry) {
            entries.add(entry);
    }

    public List<Entry> getContent() {
            return entries;
    }
}
