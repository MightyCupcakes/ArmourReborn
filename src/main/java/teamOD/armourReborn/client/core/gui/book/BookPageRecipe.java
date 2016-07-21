package teamOD.armourReborn.client.core.gui.book;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;
import teamOD.armourReborn.client.core.gui.GuiDocumentation;
import teamOD.armourReborn.client.core.gui.RenderUtils;
import teamOD.armourReborn.common.crafting.ModCraftingRecipes;
import teamOD.armourReborn.common.lib.LibMisc;

public class BookPageRecipe extends BookPage {
	
	public final List<Item> recipes = Lists.newLinkedList() ;
	private int recipeAt = 0 ;
	private int meta = -1 ;

	public BookPageRecipe(String unlocalizedName, Item... recipesToDisplay) {
		super(unlocalizedName) ;
		
		for (Item item: recipesToDisplay) {
			this.recipes.add(item) ;
		}
		this.texture = new ResourceLocation (LibMisc.MOD_ID, "textures/gui/guiBookCrafting.png") ;
	}
	
	public BookPageRecipe(String unlocalizedName, Block recipes) {
		this (unlocalizedName, Item.getItemFromBlock(recipes)) ;
	}
	
	public BookPageRecipe(String unlocalizedName, Item recipesToDisplay, int meta) {
		this(unlocalizedName, recipesToDisplay) ;
		this.meta = meta ;
	}
	
	@Override
	public void renderPage (GuiDocumentation parent, int mx, int my) {		
		IRecipe recipe = null;
		
		if (recipes.size() == 0) return ;
		
		if (recipes.size() == 1) {
			
			if (meta == -1) {
				recipe = ModCraftingRecipes.getModRecipe(recipes.get(0)) ;
			} else {
				recipe = ModCraftingRecipes.getModRecipeWithMeta(recipes.get(0), meta) ;
			}
		} else {
			recipe = ModCraftingRecipes.getModRecipe(recipes.get(recipeAt)) ;
		}
		
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
		
		int outputX = parent.getLeft() + 62 ;
		int outputY = parent.getTop() + 42 ;
		
		renderItem(outputX, outputY, recipe.getRecipeOutput());
		
		if(mx >= outputX && my >= outputY && mx < outputX + 16 && my < outputY + 16) {
			String name = I18n.format(recipe.getRecipeOutput().getUnlocalizedName()) ;
			RenderUtils.renderTooltip(mx, my, Collections.singletonList((name))) ;
		}
		
		BookPageText.renderText(parent.getLeft() + 15, parent.getTop() + 15, null, unlocalizedName);
	}
	
	@Override
	public void updateScreen(GuiDocumentation parent) {
		
		if(parent.ticksElapsed == 0) {
			recipeAt++;

			if(recipeAt == recipes.size()) {
				recipeAt = 0;
			}
		}
	}
}
