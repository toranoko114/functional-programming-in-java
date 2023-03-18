package com.functional.programming.in.java.resource.chapter.five;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterExample {

  private final FileWriter writer;

  public FileWriterExample(final String fileName) throws IOException {
    writer = new FileWriter(fileName);
  }

  public void writeStuff(final String message) throws IOException {
    writer.write(message);
  }

  protected void finalize() throws IOException {
    writer.close();
  }

  public void close() throws IOException {
    writer.close();
  }
}
