package com.asakusafw.lang.compiler.packaging;

import java.io.ByteArrayInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Arrays;

import com.asakusafw.lang.compiler.common.Location;

/**
 * An implementation of {@link ResourceItem} using array of bytes.
 */
public class ByteArrayItem implements ResourceItem {

    private final Location location;

    private final byte[] contents;

    /**
     * Creates a new instance.
     * @param location the resource location
     * @param contents the resource contents
     */
    public ByteArrayItem(Location location, byte[] contents) {
        this.location = location;
        this.contents = contents.clone();
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public InputStream openResource() {
        return new ByteArrayInputStream(contents);
    }

    /**
     * Returns a copy of the resource contents.
     * @return a copy of the resource contents
     */
    public byte[] getContents() {
        return contents.clone();
    }

    @Override
    public void writeTo(OutputStream output) throws IOException {
        output.write(contents);
    }

    /**
     * Writes the resource contents into the target output.
     * @param output the target output
     * @throws IOException if error occurred while writing the resource contents
     */
    public void writeTo(DataOutput output) throws IOException {
        output.write(contents);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + location.hashCode();
        result = prime * result + Arrays.hashCode(contents);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ByteArrayItem other = (ByteArrayItem) obj;
        if (!location.equals(other.location)) {
            return false;
        }
        if (!Arrays.equals(contents, other.contents)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "ByteArrayItem({0}=>{1}bytes)", //$NON-NLS-1$
                location,
                contents.length);
    }
}
