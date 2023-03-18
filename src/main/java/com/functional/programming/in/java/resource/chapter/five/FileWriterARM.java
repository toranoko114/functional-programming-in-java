package com.functional.programming.in.java.resource.chapter.five;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterARM implements AutoCloseable {

  private final FileWriter writer;

  public FileWriterARM(final String fileName) throws IOException {
    writer = new FileWriter(fileName);
  }

  public void writeStuff(final String message) throws IOException {
    writer.write(message);
  }

  public void close() throws IOException {
    System.out.println("close called automatically...");
    writer.close();
  }
}
