package uk.joshiejack.penguinlib.data.loot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AddLootGlobalModifier extends LootModifier {
    private final List<Item> items = new ArrayList<>();

    public AddLootGlobalModifier(ILootCondition[] conditions, List<Item> items) {
        super(conditions);
        this.items.addAll(items);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(new ItemStack(items.get(context.getRandom().nextInt(items.size()))));
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<AddLootGlobalModifier> {
        @Override
        public AddLootGlobalModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
            JsonArray array = JSONUtils.getAsJsonArray(object, "items");
            List<Item> items = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                String item = array.get(i).getAsString();
                if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation(item)))
                    items.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(item)));
            }

            return new AddLootGlobalModifier(conditions, items);
        }

        @Override
        public JsonObject write(AddLootGlobalModifier instance) {
            JsonArray array = new JsonArray();
            for (Item item: instance.items)
                array.add(item.getRegistryName().toString());
            JsonObject object = new JsonObject();
            object.add("items", array);
            return object;
        }
    }
}