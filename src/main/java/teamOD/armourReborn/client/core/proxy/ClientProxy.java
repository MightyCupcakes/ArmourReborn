package teamOD.armourReborn.client.core.proxy;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import teamOD.armourReborn.client.core.models.ModelHandler;
import teamOD.armourReborn.common.core.proxy.CommonProxy;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit (FMLPreInitializationEvent event) {
		super.preInit(event) ;
		
		ModelHandler.registerModels() ;
	}
	
	
}
