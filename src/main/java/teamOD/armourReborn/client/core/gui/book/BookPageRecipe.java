package teamOD.armourReborn.client.core.gui.book;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;
import teamOD.armourReborn.client.core.gui.GuiDocumentation;
import teamOD.armourReborn.common.crafting.ModCraftingRecipes;
import teamOD.armourReborn.common.lib.LibMisc;

public class BookPageRecipe extends BookPage {
	
	public final Item recipes ;

	public BookPageRecipe(String unlocalizedName, Item recipes) {
		super(unlocalizedName) ;
		this.recipes = recipes ;
		this.texture = new ResourceLocation (LibMisc.MOD_ID, "textures/gui/guiBookCrafting.png") ;
	}
	
	public BookPageRecipe(String unlocalizedName, Block recipes) {
		this (unlocalizedName, Item.getItemFromBlock(recipes)) ;
	}
	
	@Override
	public void renderPage (GuiDocumentation parent) {
		TextureManager renderer = Minecraft.getMinecraft().renderEngine ;
		
		IRecipe recipe = ModCraftingRecipes.getModRecipe(recipes) ;
		
		if(recipe instanceof ShapedRecipes) {
			ShapedRecipes shaped = (ShapedRecipes)recipe; 
			
			for(int y = 0; y < shaped.recipeHeight; y++) {
				for(int x = 0; x < shaped.recipeWidth; x++) {
					renderItemOnGrid(parent, 1 + x, 1 + y, shaped.recipeItems[y * shaped.recipeWidth + x]);
				}
			
			}
			
		} else if (recipe instanceof ShapedOreRecipe) {
			ShapedOreRecipe shaped = (ShapedOreRecipe) recipe;

			int width = ReflectionHelper.getPrivateValue(ShapedOreRecipe.class, shaped, 4);
			int height = ReflectionHelper.getPrivateValue(ShapedOreRecipe.class, shaped, 5);

			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					Object input = shaped.getInput()[y * width + x];

					if(input != null) {
						renderItemOnGrid(parent, 1 + x, 1 + y, input instanceof ItemStack ? (ItemStack) input : ((List<ItemStack>) input).get(0));
					}
				}
			}
		}
		
		renderItem(parent.getLeft() + 62, parent.getTop() + 42, recipe.getRecipeOutput());
	}
}
