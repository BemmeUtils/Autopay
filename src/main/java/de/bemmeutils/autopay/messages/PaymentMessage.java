package de.bemmeutils.autopay.messages;

import de.bemmeutils.autopay.Addon;
import de.bemmeutils.autopay.utils.DiscordWebhook;

import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PaymentMessage {
    String webhookUrl;
    Color color;
    String playerName;
    double amount;

    public PaymentMessage(String playerName, double amount) {
        this.webhookUrl = Addon.getPaymentWebhookUrl();
        this.color = Color.GREEN;
        this.playerName = playerName;
        this.amount = amount;
    }

    public DiscordWebhook getWebhook() {
        DiscordWebhook webhook = new DiscordWebhook(webhookUrl);
        DiscordWebhook.EmbedObject embedObject = new DiscordWebhook.EmbedObject();
        embedObject.setTitle(String.format("%s", this.playerName));
        embedObject.setColor(this.color);
        embedObject.addField("Menge", String.format("%s$", Addon.getNUMBER_FORMAT().format(this.amount)), true);
        embedObject.addField("Zeitpunkt", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), true);
        webhook.addEmbed(embedObject);
        return webhook;
    }
}
