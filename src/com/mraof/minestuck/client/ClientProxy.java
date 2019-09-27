package com.mraof.minestuck.client;

import com.mraof.minestuck.CommonProxy;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.block.MSBlocks;
import com.mraof.minestuck.client.gui.MSScreenFactories;
import com.mraof.minestuck.client.model.BasiliskModel;
import com.mraof.minestuck.client.model.BishopModel;
import com.mraof.minestuck.client.model.GiclopsModel;
import com.mraof.minestuck.client.model.IguanaModel;
import com.mraof.minestuck.client.model.ImpModel;
import com.mraof.minestuck.client.model.LichModel;
import com.mraof.minestuck.client.model.NakagatorModel;
import com.mraof.minestuck.client.model.OgreModel;
import com.mraof.minestuck.client.model.RookModel;
import com.mraof.minestuck.client.model.SalamanderModel;
import com.mraof.minestuck.client.model.TurtleModel;
import com.mraof.minestuck.client.renderer.BlockColorCruxite;
import com.mraof.minestuck.client.renderer.MachineOutlineRenderer;
import com.mraof.minestuck.client.renderer.entity.DecoyRenderer;
import com.mraof.minestuck.client.renderer.entity.MinestuckEntityRenderer;
import com.mraof.minestuck.client.renderer.entity.GristRenderer;
import com.mraof.minestuck.client.renderer.entity.RenderHangingArt;
import com.mraof.minestuck.client.renderer.entity.HologramRenderer;
import com.mraof.minestuck.client.renderer.entity.MetalBoatRenderer;
import com.mraof.minestuck.client.renderer.entity.PawnRenderer;
import com.mraof.minestuck.client.renderer.entity.ShadowRenderer;
import com.mraof.minestuck.client.renderer.entity.VitalityGelRenderer;
import com.mraof.minestuck.client.renderer.entity.frog.FrogRenderer;
import com.mraof.minestuck.client.renderer.tileentity.GateRenderer;
import com.mraof.minestuck.client.renderer.tileentity.SkaiaPortalRenderer;
import com.mraof.minestuck.client.settings.MSKeyHandler;
import com.mraof.minestuck.editmode.ClientEditHandler;
import com.mraof.minestuck.entity.EntityBigPart;
import com.mraof.minestuck.entity.DecoyEntity;
import com.mraof.minestuck.entity.FrogEntity;
import com.mraof.minestuck.entity.carapacian.BishopEntity;
import com.mraof.minestuck.entity.carapacian.PawnEntity;
import com.mraof.minestuck.entity.carapacian.RookEntity;
import com.mraof.minestuck.entity.consort.IguanaEntity;
import com.mraof.minestuck.entity.consort.NakagatorEntity;
import com.mraof.minestuck.entity.consort.SalamanderEntity;
import com.mraof.minestuck.entity.consort.TurtleEntity;
import com.mraof.minestuck.entity.item.CrewPosterEntity;
import com.mraof.minestuck.entity.item.GristEntity;
import com.mraof.minestuck.entity.item.HologramEntity;
import com.mraof.minestuck.entity.item.MetalBoatEntity;
import com.mraof.minestuck.entity.item.SbahjPosterEntity;
import com.mraof.minestuck.entity.item.ShopPosterEntity;
import com.mraof.minestuck.entity.item.VitalityGelEntity;
import com.mraof.minestuck.entity.underling.BasiliskEntity;
import com.mraof.minestuck.entity.underling.GiclopsEntity;
import com.mraof.minestuck.entity.underling.ImpEntity;
import com.mraof.minestuck.entity.underling.LichEntity;
import com.mraof.minestuck.entity.underling.OgreEntity;
import com.mraof.minestuck.entity.underling.UnderlingPartEntity;
import com.mraof.minestuck.event.ClientEventHandler;
import com.mraof.minestuck.item.MSItems;
import com.mraof.minestuck.tileentity.GateTileEntity;
import com.mraof.minestuck.tileentity.SkaiaPortalTileEntity;
import com.mraof.minestuck.util.ColorCollector;

import net.minecraft.block.StemBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientProxy extends CommonProxy
{
	
	public static PlayerEntity getClientPlayer()	//Note: can't get the client player directly from FMLClientHandler either, as the server side will still crash because of the return type
	{
		return Minecraft.getInstance().player;	//TODO verify server functionality
	}
	
	public static void addScheduledTask(Runnable runnable)
	{
		//Minecraft.getInstance().addScheduledTask(runnable);
	}
	
	private static void registerRenderers()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(SkaiaPortalTileEntity.class, new SkaiaPortalRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(GateTileEntity.class, new GateRenderer());
//		MinecraftForgeClient.registerItemRenderer(Minestuck.captchaCard, new CardRenderer());
	}
	
	@SubscribeEvent
	public static void initBlockColors(ColorHandlerEvent.Block event)
	{
		BlockColors colors = event.getBlockColors();
		colors.register(new BlockColorCruxite(), MSBlocks.ALCHEMITER.TOTEM_PAD.get(), MSBlocks.TOTEM_LATHE.DOWEL_ROD.get(), MSBlocks.CRUXITE_DOWEL.get());
		colors.register((state, worldIn, pos, tintIndex) ->
		{
			int age = state.get(StemBlock.AGE);
			int red = age * 32;
			int green = 255 - age * 8;
			int blue = age * 4;
			return red << 16 | green << 8 | blue;
		}, MSBlocks.STRAWBERRY_STEM.get());
	}
	
	@SubscribeEvent
	public static void initItemColors(ColorHandlerEvent.Item event)
	{
		ItemColors colors = event.getItemColors();
		colors.register((stack, tintIndex) -> BlockColorCruxite.handleColorTint(ColorCollector.getColorFromStack(stack, 0) - 1, tintIndex),
				MSBlocks.CRUXITE_DOWEL.get(), MSItems.CRUXITE_APPLE, MSItems.CRUXITE_POTION);
		//colors.register(new FrogRenderer.FrogItemColor(), MinestuckItems.FROG);
	}
	
	public static void init()
	{
		registerRenderers();
		
		MSScreenFactories.registerScreenFactories();
		
		RenderingRegistry.registerEntityRenderingHandler(FrogEntity.class, manager -> new FrogRenderer(manager));
		RenderingRegistry.registerEntityRenderingHandler(HologramEntity.class, HologramRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(NakagatorEntity.class, manager -> new MinestuckEntityRenderer<>(manager, new NakagatorModel(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(SalamanderEntity.class, manager -> new MinestuckEntityRenderer<>(manager, new SalamanderModel(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(IguanaEntity.class, manager -> new MinestuckEntityRenderer<>(manager, new IguanaModel(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(TurtleEntity.class, manager -> new MinestuckEntityRenderer<>(manager, new TurtleModel(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(ImpEntity.class, manager -> new MinestuckEntityRenderer<>(manager, new ImpModel(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(OgreEntity.class, manager -> new MinestuckEntityRenderer<>(manager, new OgreModel(), 2.8F));
		RenderingRegistry.registerEntityRenderingHandler(BasiliskEntity.class, manager -> new MinestuckEntityRenderer<>(manager, new BasiliskModel(), 2.8F));
		RenderingRegistry.registerEntityRenderingHandler(LichEntity.class, manager -> new MinestuckEntityRenderer<>(manager, new LichModel(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(GiclopsEntity.class, manager -> new MinestuckEntityRenderer<>(manager, new GiclopsModel(), 7.6F));
		RenderingRegistry.registerEntityRenderingHandler(BishopEntity.class, manager -> new MinestuckEntityRenderer<>(manager, new BishopModel(), 1.8F));
		RenderingRegistry.registerEntityRenderingHandler(RookEntity.class, manager -> new MinestuckEntityRenderer<>(manager, new RookModel(), 2.5F));
		RenderingRegistry.registerEntityRenderingHandler(UnderlingPartEntity.class, manager -> new ShadowRenderer<>(manager, 2.8F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBigPart.class, manager -> new ShadowRenderer<>(manager, 0F));
		RenderingRegistry.registerEntityRenderingHandler(PawnEntity.class, manager -> new PawnRenderer(manager, new BipedModel(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(GristEntity.class, GristRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(VitalityGelEntity.class, VitalityGelRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(DecoyEntity.class, DecoyRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(MetalBoatEntity.class, MetalBoatRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CrewPosterEntity.class, manager -> new RenderHangingArt<>(manager, "midnight_poster"));
		RenderingRegistry.registerEntityRenderingHandler(SbahjPosterEntity.class, manager -> new RenderHangingArt<>(manager, "sbahj_poster"));
		RenderingRegistry.registerEntityRenderingHandler(ShopPosterEntity.class, manager -> new RenderHangingArt<>(manager, "shop_poster"));

		MSKeyHandler.instance.registerKeys();
		MinecraftForge.EVENT_BUS.register(MSKeyHandler.instance);
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

		MinecraftForge.EVENT_BUS.register(ClientEditHandler.instance);
		MinecraftForge.EVENT_BUS.register(new MinestuckConfig());
		MinecraftForge.EVENT_BUS.register(MachineOutlineRenderer.class);
		//System.out.println("Adding onItemColors listener");
		//MinecraftForge.EVENT_BUS.register(ColorHandler.class);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandler::onItemColors);
	}
	
}
