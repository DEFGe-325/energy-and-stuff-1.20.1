package net.defge.energyandstuff.item.custom;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

public class WandModel implements BakedModel {
    // Базовая модель жезла, например, wand_base.json
    protected final Supplier<BakedModel> baseModel;
    // Карта деталей: ключ — название модели (например, "wand_cap"), значение — поставщик модели
    protected final Map<String, Supplier<BakedModel>> parts;
    // Ссылка на ItemStack, содержащий NBT-теги для деталей жезла
    private final ItemStack stack;

    public WandModel(Supplier<BakedModel> baseModel, Map<String, Supplier<BakedModel>> parts, ItemStack stack) {
        this.baseModel = baseModel;
        this.parts = parts;
        this.stack = stack;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource random) {
        // Получаем каркас (quads) базовой модели
        List<BakedQuad> quads = new ArrayList<>(baseModel.get().getQuads(state, side, random));

        // Если у этого ItemStack есть теги, пытаемся добавить quads для деталей жезла
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            quads.addAll(getPartQuads(tag.getString("wand_cap"), state, side, random));
            quads.addAll(getPartQuads(tag.getString("wand_handle"), state, side, random));
            quads.addAll(getPartQuads(tag.getString("wand_extra"), state, side, random));
        }

        return quads;
    }

    // Метод для получения quads отдельной части жезла по тегу.
    private List<BakedQuad> getPartQuads(String partId, @Nullable BlockState state, @Nullable Direction side, RandomSource random) {
        // Преобразуем часть, например "cover_cap" → "wand_cover_cap"
        String modelName = "wand_" + partId;
        Supplier<BakedModel> partModelSupplier = parts.get(modelName);

        if (partModelSupplier != null) {
            return partModelSupplier.get().getQuads(state, side, random);
        }

        return Collections.emptyList();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false; // Или true, если требуется затенение
    }

    @Override
    public boolean isGui3d() {
        return false; // Для инвентаря обычно false, для мира (3D) — true
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false; // Если не используешь собственный рендерер, оставь false
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        // Рекомендуется возвращать иконку базовой модели, чтобы не было проблем с частицами.
        return baseModel.get().getParticleIcon();
    }

    @Override
    public ItemOverrides getOverrides() {
        // Если есть динамические оверрайды, можно вернуть кастомную реализацию, иначе лучше вернуть ItemOverrides.EMPTY
        return ItemOverrides.EMPTY;
    }
}