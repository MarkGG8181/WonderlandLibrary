package net.minecraft.block;

public class MelonBlock extends StemGrownBlock
{
    protected MelonBlock(Properties builder)
    {
        super(builder);
    }

    public StemBlock getStem()
    {
        return (StemBlock)Blocks.MELON_STEM;
    }

    public AttachedStemBlock getAttachedStem()
    {
        return (AttachedStemBlock)Blocks.ATTACHED_MELON_STEM;
    }
}
