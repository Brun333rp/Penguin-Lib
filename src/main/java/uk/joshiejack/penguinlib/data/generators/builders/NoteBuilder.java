package uk.joshiejack.penguinlib.data.generators.builders;

import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import uk.joshiejack.penguinlib.data.PenguinRegistries;
import uk.joshiejack.penguinlib.note.Note;
import uk.joshiejack.penguinlib.util.Icon;

import javax.annotation.Nonnull;


public class NoteBuilder extends SimplePenguinBuilder<Note> {
    private ResourceLocation category;
    private boolean isHidden;
    private boolean isDefault;
    private Icon icon = new Icon.ItemIcon(ItemStack.EMPTY);
    private CategoryBuilder categoryBuilder;
    public NoteBuilder() {
        super(PenguinRegistries.NOTE_SERIALIZER.get());
    }

    public static NoteBuilder note() {
        return new NoteBuilder();
    }

    public static NoteBuilder note(CategoryBuilder builder) {
        NoteBuilder note = note();
        note.categoryBuilder = builder;
        return note;
    }

    public CategoryBuilder end() {
        return categoryBuilder;
    }

    public NoteBuilder withCategory(CategoryBuilder builder) {
        category = builder.getId();
        return this;
    }

    public NoteBuilder setHidden() {
        isHidden = true;
        return this;
    }

    public NoteBuilder setDefault() {
        isDefault = true;
        return this;
    }

    public NoteBuilder withItemIcon(Item item) {
        icon = new Icon.ItemIcon(new ItemStack(item));
        return this;
    }

    public NoteBuilder withTextureIcon(ResourceLocation texture, int x, int y) {
        icon = new Icon.TextureIcon(texture, x, y);
        return this;
    }

    public NoteBuilder withPenguinIcon(int x, int y) {
        icon = new Icon.TextureIcon(Icon.DEFAULT_LOCATION, x, y);
        return this;
    }

    @Override
    public void serializeRecipeData(@Nonnull JsonObject json) {
        if (isHidden) json.addProperty("hidden", true);
        if (isDefault) json.addProperty("default", true);
        json.addProperty("category", category.toString());
        json.add("icon", icon.toJson(new JsonObject()));
    }
}