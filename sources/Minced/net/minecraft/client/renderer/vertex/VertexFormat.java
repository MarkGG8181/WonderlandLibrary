// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.client.renderer.vertex;

import org.apache.logging.log4j.LogManager;
import com.google.common.collect.Lists;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class VertexFormat
{
    private static final Logger LOGGER;
    private final List<VertexFormatElement> elements;
    private final List<Integer> offsets;
    private int vertexSize;
    private int colorElementOffset;
    private final List<Integer> uvOffsetsById;
    private int normalElementOffset;
    
    public VertexFormat(final VertexFormat vertexFormatIn) {
        this();
        for (int i = 0; i < vertexFormatIn.getElementCount(); ++i) {
            this.addElement(vertexFormatIn.getElement(i));
        }
        this.vertexSize = vertexFormatIn.getSize();
    }
    
    public VertexFormat() {
        this.elements = (List<VertexFormatElement>)Lists.newArrayList();
        this.offsets = (List<Integer>)Lists.newArrayList();
        this.colorElementOffset = -1;
        this.uvOffsetsById = (List<Integer>)Lists.newArrayList();
        this.normalElementOffset = -1;
    }
    
    public void clear() {
        this.elements.clear();
        this.offsets.clear();
        this.colorElementOffset = -1;
        this.uvOffsetsById.clear();
        this.normalElementOffset = -1;
        this.vertexSize = 0;
    }
    
    public VertexFormat addElement(final VertexFormatElement element) {
        if (element.isPositionElement() && this.hasPosition()) {
            VertexFormat.LOGGER.warn("VertexFormat error: Trying to add a position VertexFormatElement when one already exists, ignoring.");
            return this;
        }
        this.elements.add(element);
        this.offsets.add(this.vertexSize);
        switch (element.getUsage()) {
            case NORMAL: {
                this.normalElementOffset = this.vertexSize;
                break;
            }
            case COLOR: {
                this.colorElementOffset = this.vertexSize;
                break;
            }
            case UV: {
                this.uvOffsetsById.add(element.getIndex(), this.vertexSize);
                break;
            }
        }
        this.vertexSize += element.getSize();
        return this;
    }
    
    public boolean hasNormal() {
        return this.normalElementOffset >= 0;
    }
    
    public int getNormalOffset() {
        return this.normalElementOffset;
    }
    
    public boolean hasColor() {
        return this.colorElementOffset >= 0;
    }
    
    public int getColorOffset() {
        return this.colorElementOffset;
    }
    
    public boolean hasUvOffset(final int id) {
        return this.uvOffsetsById.size() - 1 >= id;
    }
    
    public int getUvOffsetById(final int id) {
        return this.uvOffsetsById.get(id);
    }
    
    @Override
    public String toString() {
        String s = "format: " + this.elements.size() + " elements: ";
        for (int i = 0; i < this.elements.size(); ++i) {
            s += this.elements.get(i).toString();
            if (i != this.elements.size() - 1) {
                s += " ";
            }
        }
        return s;
    }
    
    private boolean hasPosition() {
        for (int i = 0, j = this.elements.size(); i < j; ++i) {
            final VertexFormatElement vertexformatelement = this.elements.get(i);
            if (vertexformatelement.isPositionElement()) {
                return true;
            }
        }
        return false;
    }
    
    public int getIntegerSize() {
        return this.getSize() / 4;
    }
    
    public int getSize() {
        return this.vertexSize;
    }
    
    public List<VertexFormatElement> getElements() {
        return this.elements;
    }
    
    public int getElementCount() {
        return this.elements.size();
    }
    
    public VertexFormatElement getElement(final int index) {
        return this.elements.get(index);
    }
    
    public int getOffset(final int index) {
        return this.offsets.get(index);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
            final VertexFormat vertexformat = (VertexFormat)p_equals_1_;
            return this.vertexSize == vertexformat.vertexSize && this.elements.equals(vertexformat.elements) && this.offsets.equals(vertexformat.offsets);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int i = this.elements.hashCode();
        i = 31 * i + this.offsets.hashCode();
        i = 31 * i + this.vertexSize;
        return i;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
