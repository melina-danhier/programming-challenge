package de.bcxp.challenge.common.reader;

import java.io.Reader;
import java.util.List;

public interface DataFileReader<T> {
    List<T> read(Reader reader);
}
