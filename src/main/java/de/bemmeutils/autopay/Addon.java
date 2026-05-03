package de.bemmeutils.autopay;

import de.bemmeutils.autopay.event.EventBus;
import de.bemmeutils.autopay.listener.*;
import de.bemmeutils.autopay.utils.FileUtil;
import de.bemmeutils.autopay.utils.JsonUtil;
import de.byteandbit.velociraptor.api.VelociraptorAPI;
import lombok.Getter;
import lombok.Setter;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Material;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Addon extends LabyModAddon {
	@Getter
	private static ScheduledExecutorService scheduler;

	@Getter
	private static final String ADDON_FILE_PATH = String.format("%s/BemmeUtils/Autopay/data.json", Minecraft.getMinecraft().mcDataDir).replace("/", File.separator);
	@Getter
	@Setter
	private static JsonUtil jsonUtil;
	@Getter
	@Setter
	private static VelociraptorAPI velociraptorAPI;
	@Getter
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.GERMANY);
	@Getter
	@Setter
	private static long citybuildJoinTime = 0;

	@Getter
	@Setter
	private static String paymentWebhookUrl = "";

	@Override
	public void onEnable() {
		initVelociraptorAPI();
		EventBus.register(new TablistAddListener());
		EventBus.register(new TablistRemoveListener());
		EventBus.register(new ServerSwitchListener());
		EventBus.register(new TeamUpdateListener());
		FileUtil.migrate();
		FileUtil.setupAddonFiles(Arrays.asList("onetime", "recurring"));
		//JsonUtil depends on the addon files, so create the JSON file first
		try {
			jsonUtil = new JsonUtil(JsonUtil.readJsonFromFile(ADDON_FILE_PATH));
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}
		this.getApi().getEventManager().register(new MessageSendListener());
		scheduler = Executors.newScheduledThreadPool(1);
		NUMBER_FORMAT.setMinimumFractionDigits(2);
		NUMBER_FORMAT.setMaximumFractionDigits(2);
	}

	@Override
	public void loadConfig() {
		paymentWebhookUrl = getConfig().has("paymentWebhookUrl") ? getConfig().get("paymentWebhookUrl").getAsString() : "";
	}

	@Override
	protected void fillSettings(List<SettingsElement> list) {
		list.add(new StringElement(
				"Log Webhook",
				new ControlElement.IconData(Material.WEB),
				getPaymentWebhookUrl(),
				(value -> {
					setPaymentWebhookUrl(value);
					this.getConfig().addProperty("paymentWebhookUrl", value);
					this.saveConfig();
				})
		));
	}

	private void initVelociraptorAPI() {
		new Thread(() -> {
			try {
				Thread.sleep(10000);
				boolean foundApi = false;
				while (!foundApi) {
					System.out.println("SUCHE VELOCIRAPTOR API.....");
					try {
						Class.forName("de.byteandbit.velociraptor.api.VelociraptorAPI");
						foundApi = true;
					} catch (Exception exception) {
						exception.printStackTrace();
					}
					Thread.sleep(1000L);
				}
				velociraptorAPI = new VelociraptorAPI();
				System.out.println("Velociraptor NG API wurde erfolgreich eingebunden!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}


}