package com.asakusafw.lang.compiler.testing;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Edits files.
 */
public final class FileEditor {

    private static final Charset ENCODING = Charset.forName("UTF-8");

    private FileEditor() {
        return;
    }

    /**
     * Creates a new input stream for the target file.
     * @param file the target file
     * @return the created stream
     */
    public static InputStream open(File file) {
        try {
            return new FileInputStream(file);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    /**
     * Creates a new output stream for the target file.
     * @param file the target file
     * @return the created stream
     */
    public static OutputStream create(File file) {
        File parent = file.getAbsoluteFile().getParentFile();
        if (parent.mkdirs() == false && parent.isDirectory() == false) {
            throw new IOError(new IOException(MessageFormat.format(
                    "failed to create a folder: {0}",
                    parent)));
        }
        try {
            return new FileOutputStream(file);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    /**
     * Creates a new scanner for the target file.
     * @param file the target file
     * @return the created scanner
     */
    public static Scanner scanner(File file) {
        return new Scanner(new InputStreamReader(open(file), ENCODING));
    }

    /**
     * Creates a new writer for the target file.
     * @param file the target file
     * @return the created writer
     */
    public static PrintWriter writer(File file) {
        return new PrintWriter(new OutputStreamWriter(create(file), ENCODING));
    }

    /**
     * Creates a new file with contents.
     * @param file the target file
     * @param contents the file contents
     */
    public static void put(File file, String... contents) {
        try (PrintWriter w = writer(file)) {
            for (String s : contents) {
                w.println(s);
            }
        }
    }

    /**
     * Returns contents in the file.
     * @param file the target file
     * @return the contents
     */
    public static List<String> get(File file) {
        List<String> results = new ArrayList<>();
        try (Scanner s = scanner(file)) {
            while (s.hasNextLine()) {
                results.add(s.nextLine());
            }
        }
        return results;
    }

    /**
     * Copies a file or directory.
     * @param source the source file or directory
     * @param destination the destination file
     */
    public static void copy(File source, File destination) {
        if (source.isDirectory()) {
            copyDirectory(source, destination);
        } else {
            try (InputStream in = open(source)) {
                try (OutputStream out = create(destination)) {
                    copyStream(in, out);
                }
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
    }

    /**
     * Copies contents into a file.
     * @param source the source contents
     * @param destination the destination file
     */
    public static void copy(InputStream source, File destination) {
        try (OutputStream out = create(destination)) {
            copyStream(source, out);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    /**
     * Copies contents from a file.
     * @param source the source file
     * @param destination the destination stream
     */
    public static void copy(File source, OutputStream destination) {
        try (InputStream in = open(source)) {
            copyStream(in, destination);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    /**
     * Copies contents between two streams.
     * @param source the source stream
     * @param destination the destination stream
     */
    public static void copy(InputStream source, OutputStream destination) {
        try {
            copyStream(source, destination);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    /**
     * Copies a directory.
     * @param source the source directory
     * @param destination the destination file
     */
    public static void copyDirectory(File source, File destination) {
        if (source.isDirectory() == false) {
            return;
        }
        if (destination.mkdirs() == false && destination.isDirectory() == false) {
            throw new IOError(new IOException(MessageFormat.format(
                    "failed to create a directory: {0}",
                    destination)));
        }
        for (File file : source.listFiles()) {
            File target = new File(destination, file.getName());
            if (file.isDirectory()) {
                copyDirectory(file, target);
            } else {
                copy(file, target);
            }
        }
    }

    /**
     * Extracts entries into the directory.
     * @param source the source archive file
     * @param destination the destination file
     */
    public static void extract(File source, File destination) {
        try (ZipInputStream input = new ZipInputStream(open(source))) {
            while (true) {
                ZipEntry entry = input.getNextEntry();
                if (entry == null) {
                    break;
                }
                File target = new File(destination, entry.getName());
                if (entry.isDirectory()) {
                    target.mkdirs();
                } else {
                    copy(input, target);
                }
            }
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    /**
     * Extracts entries into the directory.
     * @param source the source archive input stream
     * @param destination the destination file
     */
    public static void extract(ZipInputStream source, File destination) {
        try {
            while (true) {
                ZipEntry entry = source.getNextEntry();
                if (entry == null) {
                    break;
                }
                File target = new File(destination, entry.getName());
                if (entry.isDirectory()) {
                    target.mkdirs();
                } else {
                    copy(source, target);
                }
            }
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    /**
     * Returns a contents in the file.
     * @param source the source file
     * @return the contents in bytes
     */
    public static byte[] dump(File source) {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            try (InputStream in = open(source)) {
                copyStream(in, output);
            }
            return output.toByteArray();
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    /**
     * Returns a contents in the stream.
     * @param source the source stream
     * @return the contents in bytes
     */
    public static byte[] dump(InputStream source) {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            copyStream(source, output);
            return output.toByteArray();
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    /**
     * Make the file or files in a directory executable.
     * @param file the target file or directory
     * @param extension the target file extension (requires a dot character)
     */
    public static void setExecutable(File file, String extension) {
        if (file.isFile()) {
            if (extension == null || file.getName().endsWith(extension)) {
                file.setExecutable(true, true);
            }
        } else if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                setExecutable(f, extension);
            }
        }
    }

    private static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buf = new byte[256];
        while (true) {
            int read = input.read(buf);
            if (read < 0) {
                break;
            }
            output.write(buf, 0, read);
        }
    }
}
